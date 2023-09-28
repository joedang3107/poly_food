package com.example.thuctap.payload.request;

public class LoginRequest {

    private String userName;

    private String password;

    public String getUserName() {
        return userName;
    }

    public void setUser_name(String user_name) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
