package model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class Contact {

    @JsonProperty("name")
    private String name;

    @JsonProperty("surname")
    private String surname;

    @JsonProperty("address")
    private String address;

    @JsonProperty("phone")
    private String phone;

    public Contact() {
    }

    public Contact
            (@JsonProperty("name") String name, @JsonProperty("surname") String surname,
             @JsonProperty("address") String address, @JsonProperty("phone") String phone) {
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.phone = phone;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Contact contact = (Contact) obj;
        return phone.replaceAll("\\s", "").equals(contact.phone.replaceAll("\\s", ""));
    }

    @Override
    public int hashCode() {
        return Objects.hash(phone.replaceAll("\\s", ""));
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
