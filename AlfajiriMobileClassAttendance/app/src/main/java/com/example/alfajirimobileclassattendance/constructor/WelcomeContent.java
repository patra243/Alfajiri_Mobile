package com.example.alfajirimobileclassattendance.constructor;

public class WelcomeContent {

    private String welcome_title;
    private String welcome_details;
    private int welcome_image;


    public String getWelcome_title() {
        return welcome_title;
    }

    public void setWelcome_title(String welcome_title) {
        this.welcome_title = welcome_title;
    }

    public String getWelcome_details() {
        return welcome_details;
    }

    public void setWelcome_details(String welcome_details) {
        this.welcome_details = welcome_details;
    }

    public int getWelcome_image() {
        return welcome_image;
    }

    public void setWelcome_image(int welcome_image) {
        this.welcome_image = welcome_image;
    }


    public WelcomeContent(String welcome_title, String welcome_details, int welcome_image) {
        this.welcome_title = welcome_title;
        this.welcome_details = welcome_details;
        this.welcome_image = welcome_image;
    }
}
