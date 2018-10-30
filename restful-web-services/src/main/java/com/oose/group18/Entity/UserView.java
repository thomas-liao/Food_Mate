package com.oose.group18.Entity;

import javax.validation.constraints.Email;
import java.util.Date;

public class UserView {
    private String userName;
    private String fullName;
    private String description;
    @Email
    private String email;

    public UserView(){

    }

    public UserView(User user) {
        this.userName = user.getUserName();
        this.fullName = user.getFullName();
        this.description = user.getDescription();
        this.email = user.getEmail();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
