package com.kuldeepFirebase.AbhiFirebase;

import java.io.Serializable;

class UserInfo implements Serializable {

    private String userName="", userEmail="", mobileNumber="";

    public UserInfo() {
    }



    public UserInfo(String userName, String userEmail, String mobileNumber) {
        this.userName=userName;
        this.userEmail=userEmail;
        this.mobileNumber=mobileNumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName=userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail=userEmail;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber=mobileNumber;
    }
}
