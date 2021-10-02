package com.yoviro.rest.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public class CompanyDTO extends ContactDTO {
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    public String getSocialReason(){
        return this.getName();
    }

    public void setSocialReason(String name){
        this.setName(name);
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
}