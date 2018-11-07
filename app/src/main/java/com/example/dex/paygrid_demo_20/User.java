package com.example.dex.paygrid_demo_20;

public class User {

    private String name, email, phone, balance;


    public User(String name, String email, String phone) {
    }

    public User() {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.balance = balance;

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }
}

