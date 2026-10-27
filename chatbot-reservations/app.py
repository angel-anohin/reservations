from flask import Flask, request, jsonify, render_template
from openai import OpenAI
from datetime import datetime
import json
import requests
from flask_cors import CORS

app = Flask(__name__)
CORS(app)


SPRING_ENDPOINT = "http://localhost:8080/reservation/add"

client = OpenAI(api_key=OPENAI_API_KEY)

required_fields = ["name", "email", "mobileNumber", "peopleCount", "dateTime", "additionalInformation"]

# Simple in-memory session (за demo)
conversation_data = {}

# ---------- AI extraction ----------
def extract_reservation(text):
    prompt = f"""
Ти си AI асистент за резервации на български език.

От текста на потребителя извлечи следните полета:

- name → пълно име (пример: Ангел Анохин или Иван Георгиев или каквото и да е подобно име)
- email → валиден имейл (пример: something@gmail.com или abv.bg)
- mobileNumber → телефонен номер (Пример: 0888888888)
- peopleCount → цяло число 
- dateTime → формат: DD.MM.YYYY HH:MM (пример: 15.01.2026 19:00)
- additionalInformation → свободен текст (пример: пушачи, малки деца, изненади, предпочитания или каквото и да било)

Потребителят може да пише нормален разговорен текст.

Върни САМО валиден JSON, без обяснения, без текст отпред или отзад.

Формат:

{{
  "name": "",
  "email": "",
  "mobileNumber": "",
  "peopleCount": 0,
  "dateTime": "",
  "additionalInformation": ""
}}

Text:
{text}
"""

    try:
        response = client.chat.completions.create(
            model="gpt-4o-mini",
            messages=[{"role": "user", "content": prompt}]
        )

        result_text = response.choices[0].message.content
        data = json.loads(result_text)

        # Нормализиране на полета
        if "peopleCount" in data:
            try:
                data["peopleCount"] = int(data["peopleCount"])
            except:
                data["peopleCount"] = 0

        if "dateTime" in data and data["dateTime"]:
            try:
                dt = datetime.strptime(data["dateTime"], "%d.%m.%Y %H:%M")
                data["dateTime"] = dt.strftime("%Y-%m-%dT%H:%M:%S")

            except:
                pass


        for field in required_fields:
            if field not in data:
                data[field] = "" if field != "peopleCount" else 0

        return data

    except Exception as e:
        print("Failed to extract reservation:", e)
        return {field: "" if field != "peopleCount" else 0 for field in required_fields}

# ---------- Missing fields ----------
def check_missing(data):
    return [f for f in required_fields if not data.get(f)]

# ---------- AI question generator ----------
def generate_question(missing_fields):
    fields_str = ", ".join(missing_fields)
    prompt = f"""
You are a polite Bulgarian booking assistant.

Ask the user nicely to provide the following missing information:
{fields_str}
"""

    try:
        response = client.chat.completions.create(
            model="gpt-4o-mini",
            messages=[{"role": "user", "content": prompt}]
        )
        return response.choices[0].message.content.strip()
    except:
        return f"Моля въведете: {fields_str}"

# ---------- Send to Spring ----------
def send_to_spring(data):
    #try:
    #    r = requests.post(SPRING_ENDPOINT, json=data, timeout=10)
    #    return r.status_code in [200, 201]
    #except Exception as e:
    #    print("Failed to send to Spring:", e)
    #    return False
    
    try:
        print("SENDING TO SPRING:", data)
        r = requests.post(SPRING_ENDPOINT, json=data, timeout=10)
        print("SPRING STATUS:", r.status_code)
        print("SPRING RESPONSE:", r.text)
        return r.status_code in [200, 201]
    except Exception as e:
        print("Failed to send to Spring:", e)
        return False
    
def merge_data(old, new):
    for k, v in new.items():
        if v not in ["", 0, None]:
            old[k] = v
    

# ---------- Routes ----------
@app.route("/")
def index():
    return render_template("index.html")


@app.route("/chat", methods=["POST"])
def chat():
    global conversation_data

    user_text = request.json.get("message", "")

    extracted = extract_reservation(user_text)

    merge_data(conversation_data, extracted)

    missing = check_missing(conversation_data)

    if missing:
        question = generate_question(missing)
        return jsonify({"reply": question})

    success = send_to_spring(conversation_data)

    if success:
        reply = f"✅ Резервацията е направена за {conversation_data['name']} на {conversation_data['dateTime']} за {conversation_data['peopleCount']} човека."
    else:
        reply = "❌ Възникна проблем при запис на резервацията."

    conversation_data = {}

    return jsonify({"reply": reply})


if __name__ == "__main__":
    app.run(debug=True)








