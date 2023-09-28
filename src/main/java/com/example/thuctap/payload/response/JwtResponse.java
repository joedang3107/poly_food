package com.example.thuctap.payload.response;

import java.util.List;
import java.util.Set;

public class JwtResponse {

    private String token;
    private String type = "Bearer";
    private String userName;
    private String email;
    private String phone;
    private Set<String> roles;

    public JwtResponse(String token, String userName, String email, String phone, Set<String> roles) {
        this.token = token;
        this.userName = userName;
        this.email = email;
        this.phone = phone;
        this.roles = roles;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public Set<String> getRoles() {

        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}
