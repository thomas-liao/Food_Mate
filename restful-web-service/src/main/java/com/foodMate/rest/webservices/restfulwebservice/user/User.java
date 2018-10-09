package com.foodMate.rest.webservices.restfulwebservice.user;

import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;

public class User {
    private Integer id;

    @Size(min=2, max=16, message = "Should be between 2 - 16 characters")
    private String fname;
    @Size(min=2, max=16, message = "Should be between 2 - 16 characters")
    private String lname;
    @Size(min=6, max=16, message = "Should be between 6 - 16 characters")
    private String userName;

    @Past
    private Date registrationDate;
    private enum food_pref {Chinese, Japanese, French, Italian, Mexican, American};
    private String addr;


    // need this otherwise bug   "error": "Internal Server Error",
    protected User() {

    }

    public User(Integer id, String fname, String lname, String userName, Date registrationDate, String addr) {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.userName = userName;
        this.registrationDate = registrationDate;
        this.addr = addr;
    }

    // getters and setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }


    // toString()
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", fname='" + fname + '\'' +
                ", lname='" + lname + '\'' +
                ", userName='" + userName + '\'' +
                ", registrationDate=" + registrationDate +
                ", addr='" + addr + '\'' +
                '}';
    }
}
