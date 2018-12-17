package com.oose.group18.Entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.swagger.annotations.ApiModel;

import javax.persistence.*;
import java.util.Date;

@ApiModel(description="All details about the Review.")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name="Review")
public class Review {

    @Id
    @GeneratedValue
    @Column(name="review_id")
    private Integer id;

    private Integer userId;
    private Integer restaurantId;
    private Integer score;
    private Date timeStep;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Date getTimeStep() {
        return timeStep;
    }

    public void setTimeStep(Date timeStep) {
        this.timeStep = timeStep;
    }
}

