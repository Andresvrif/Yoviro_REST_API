package com.yoviro.rest.dto.search;

public class SearchPersonDTO extends SearchContactDTO {
    private String secondName;
    private String lastName;
    private String secondLastName;
    public String getFirstName() {
        return this.getName();
    }

    public void setFirstName(String firstName) {
        this.setName(firstName);
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSecondLastName() {
        return secondLastName;
    }

    public void setSecondLastName(String secondLastName) {
        this.secondLastName = secondLastName;
    }
}