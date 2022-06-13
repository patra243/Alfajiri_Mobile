package com.example.alfajirimobileclassattendance.constructor;

public class FacultyConstructor {
    private String id;
    private String name;
    private String faculty;
    private String designation;
    private String phone;
    private String email;
    private String address;
    private String password;
    private String mode;

    public FacultyConstructor() {
    }

    public FacultyConstructor(String id, String name, String faculty, String designation, String phone, String email, String address, String password, String mode) {
        this.id = id;
        this.name = name;
        this.faculty = faculty;
        this.designation = designation;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.password = password;
        this.mode = mode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }
}
