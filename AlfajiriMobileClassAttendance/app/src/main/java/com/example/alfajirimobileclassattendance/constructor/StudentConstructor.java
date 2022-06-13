package com.example.alfajirimobileclassattendance.constructor;

public class StudentConstructor {
    private String name;
    private String id;
    private String year;
    private String semester;
    private String department;
    private String faculty;
    private String email;
    private String phone;
    private String unit;
    private String unitCode;
    private String mode;
    private String password;

    public StudentConstructor() {
    }

    public StudentConstructor(String name, String id, String year, String semester, String department, String faculty, String email, String phone, String unit, String unitCode, String mode, String password) {
        this.name = name;
        this.id = id;
        this.year = year;
        this.semester = semester;
        this.department = department;
        this.faculty = faculty;
        this.email = email;
        this.phone = phone;
        this.unit = unit;
        this.unitCode = unitCode;
        this.mode = mode;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
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

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
