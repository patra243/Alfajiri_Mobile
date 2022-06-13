package com.example.alfajirimobileclassattendance.constructor;

public class UnitConstructor {
    private String ID;
    private String unitName;
    private String unitCode;
    private String lecturer;
    private String lecturerID;
    private String selectFaculty;

    public UnitConstructor() {

    }

    public UnitConstructor(String ID, String unitName, String unitCode, String lecturer, String lecturerID, String selectFaculty) {
        this.ID = ID;
        this.unitName = unitName;
        this.unitCode = unitCode;
        this.lecturer = lecturer;
        this.lecturerID = lecturerID;
        this.selectFaculty = selectFaculty;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getLecturer() {
        return lecturer;
    }

    public void setLecturer(String lecturer) {
        this.lecturer = lecturer;
    }

    public String getLecturerID() {
        return lecturerID;
    }

    public void setLecturerID(String lecturerID) {
        this.lecturerID = lecturerID;
    }

    public String getSelectFaculty() {
        return selectFaculty;
    }

    public void setSelectFaculty(String selectFaculty) {
        this.selectFaculty = selectFaculty;
    }
}
