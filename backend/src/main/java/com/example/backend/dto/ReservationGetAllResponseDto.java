package com.example.backend.dto;

import java.time.LocalDateTime;

public class ReservationGetAllResponseDto {
    private Long id;

    private String name;

    private String email;

    private String mobileNumber;

    private Integer peopleCount;

    private LocalDateTime dateTime;

    private String additionalInformation;

    private String updatedBy;

    private String deletedBy;

    private boolean deleted;

    public ReservationGetAllResponseDto(Long id,
                              String name,
                              String email,
                              String mobileNumber,
                              Integer peopleCount,
                              LocalDateTime dateTime,
                              String additionalInformation,
                              String updatedBy,
                              String deletedBy,
                              boolean deleted
    ) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.peopleCount = peopleCount;
        this.dateTime = dateTime;
        this.additionalInformation = additionalInformation;
        this.updatedBy = updatedBy;
        this.deletedBy = deletedBy;
        this.deleted = deleted;
    }

    public ReservationGetAllResponseDto() {

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

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getDeletedBy() {
        return deletedBy;
    }

    public void setDeletedBy(String deletedBy) {
        this.deletedBy = deletedBy;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        deleted = deleted;
    }
}
