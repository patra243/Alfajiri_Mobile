package com.example.alfajirimobileclassattendance.constructor;

public class AdminConstructor {

    private String name;
    private String email;
    private String uID;
    private String password;

    public AdminConstructor() {
    }

    public AdminConstructor(String name, String email, String uID, String password) {
        this.name = name;
        this.email = email;
        this.uID = uID;
        this.password = password;
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

    public String getuID() {
        return uID;
    }

    public void setuID(String uID) {
        this.uID = uID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
