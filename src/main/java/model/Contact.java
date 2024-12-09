package model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Contact {

    @JsonProperty("name")
    private String name;

    @JsonProperty("surname")
    private String surname;

    @JsonProperty("address")
    private String address;

    @JsonProperty("phone")
    private String phone;

    public Contact
            (@JsonProperty("name") String name, @JsonProperty("surname") String surname,
             @JsonProperty("address") String address, @JsonProperty("phone") String phone) {
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.phone = phone;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    @Override
    public String toString() {
        return String.format(" %-8s | %-8s | %-8s | %-8s ",
                name, surname, address, phone);
    }


}
