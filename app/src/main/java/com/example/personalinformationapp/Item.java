package com.example.personalinformationapp;

public class Item {
    String placeToLog;
    String emailUsername;
    String password;

    public Item(String placeToLog, String emailUsername, String password) {
        this.placeToLog = placeToLog;
        this.emailUsername = emailUsername;
        this.password = password;
    }

    public String getPlaceToLog() {
        return placeToLog;
    }

    public void setPlaceToLog(String placeToLog) {
        this.placeToLog = placeToLog;
    }

    public String getEmailUsername() {
        return emailUsername;
    }

    public void setEmailUsername(String emailUsername) {
        this.emailUsername = emailUsername;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
