package com.example.vimaljagruti.models;

public class UserBean {
    private String username,phone,registration,email;

    public UserBean(String username, String phone, String registration, String email) {
        this.username = username;
        this.phone = phone;
        this.registration = registration;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRegistration() {
        return registration;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
