package com.foodMate.rest.webservices.restfulwebservice.user;

import java.util.Date;

public class User {
    private Integer id;
    private String fname;
    private String lname;
    private String userName;
    private Date birthDate;
    private enum food_pref {Chinese, Japanese, French, Italian, Mexican, American};
    private String addr;


    // need this otherwise bug   "error": "Internal Server Error",
    protected User() {

    }

    public User(Integer id, String fname, String lname, String userName, Date birthDate, String addr) {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.userName = userName;
        this.birthDate = birthDate;
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

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
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
                ", birthDate=" + birthDate +
                ", addr='" + addr + '\'' +
                '}';
    }
}
