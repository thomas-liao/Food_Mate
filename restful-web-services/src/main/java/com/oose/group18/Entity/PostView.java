package com.oose.group18.Entity;

import java.util.Date;

public class PostView {
    private Integer id;
    private String description;
    private String startDate;
    private Integer numOfGuest;
    private String hostName;
    private String restaurantName;

    public PostView(){}
    public PostView(Post post) {
        this.id = post.getId();
        this.description = post.getDescription();
        this.startDate = post.getStartDate();
        this.numOfGuest = post.getNumOfGuest();
        this.hostName = post.getUser().getFullName();
        this.restaurantName = post.getRestaurant().getName();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public Integer getNumOfGuest() {
        return numOfGuest;
    }

    public void setNumOfGuest(Integer numOfGuest) {
        this.numOfGuest = numOfGuest;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }
}
