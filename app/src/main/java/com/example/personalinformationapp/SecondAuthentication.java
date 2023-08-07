package com.example.personalinformationapp;

import android.util.Log;

public class SecondAuthentication {
    private int pin;

    public void setPin(int pin){
        this.pin = pin;
    }
    public int getPin(){
        return this.pin;
    }

    public boolean checkPin(int pin){

        if(getPin() == pin){
            return true;
        }else{
            return false;
        }
    }

}
