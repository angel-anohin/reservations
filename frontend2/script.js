function sendReservation() {
  const name = document.getElementById("name").value;
  const email = document.getElementById("email").value;
  const mobileNumber = document.getElementById("mobileNumber").value;
  const peopleCount = document.getElementById("peopleCount").value;
  const dateTime = document.getElementById("dateTime").value;
  const additionalInformation = document.getElementById("additionalInfo").value;

  fetch("http://localhost:8080/reservation/add", {
    method: "POST",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify({
      name: name,
      email: email,
      mobileNumber: mobileNumber,
      peopleCount: peopleCount,
      dateTime: dateTime,
      additionalInformation: additionalInformation
    })
  })
  .then(async res => {
    if (!res.ok) {
      const errors = await res.json(); // <-- ТУК
      throw errors;
    }
    return res.text();
  })
  .then(data => {
    document.getElementById("result").innerText = data;
  })
  .catch(errors => {
    // errors = { email: "Невалиден e-mail", name: "...", ... }
    let message = "";

    for (const field in errors) {
      message += errors[field] + "\n";
    }

    document.getElementById("result").innerText = message;
  });
}

async function sendReservationFromAI() {
    const input = document.getElementById("message");
    const text = input.value;
    if (!text) return;

    input.value = "";
    add("Ти", text);

    const res = await fetch("http://127.0.0.1:5000/chat", {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify({message: text})
    });

    const data = await res.json();
    add("Бот", data.reply);
}

function add(sender, text) {
    const chat = document.getElementById("chat");
    chat.innerHTML += `<b>${sender}:</b> ${text}<br>`;
    chat.scrollTop = chat.scrollHeight;
}