package com.example.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDateTime;

public class ReservationSaveDto {

    private Long id;

    @NotBlank(message = "Името е задължително")
    private String name;

    @NotBlank(message = "E-mail е задължителен")
    @Email(message = "Невалиден e-mail")
    private String email;

    @NotBlank(message = "Телефонният номер е задължителен")
    @Pattern(
            regexp = "^\\+?[0-9]{10,15}$",
            message = "Невалиден телефонен номер"
    )
    private String mobileNumber;

    @NotNull(message = "Моля изберете брой гости")
    private Integer peopleCount;

    @NotNull(message = "Моля изберете дата и час за резервацията")
    private LocalDateTime dateTime;

    private String additionalInformation;

    public ReservationSaveDto(Long id,
                              String name,
                              String email,
                              String mobileNumber,
                              Integer peopleCount,
                              LocalDateTime dateTime,
                              String additionalInformation
    ) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.peopleCount = peopleCount;
        this.dateTime = dateTime;
        this.additionalInformation = additionalInformation;
    }

    public ReservationSaveDto() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public Integer getPeopleCount() {
        return peopleCount;
    }

    public void setPeopleCount(Integer peopleCount) {
        this.peopleCount = peopleCount;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getAdditionalInformation() {
        return additionalInformation;
    }

    public void setAdditionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
    }
}
