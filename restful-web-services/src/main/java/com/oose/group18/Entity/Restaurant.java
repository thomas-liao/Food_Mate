package com.oose.group18.Entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;
@ApiModel(description="All details about the Restaurant.")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name="Restaurant")
public class Restaurant {
    @Id
    @GeneratedValue
    @Column(name="res_id")
    private Integer id;

    @Size(min=2, message="Name should have atleast 2 characters")
    @ApiModelProperty(notes="Name should have atleast 2 characters")
    private String name;

    @Size(min=2, message="Name should have atleast 2 characters")
    @ApiModelProperty(notes="Category should have atleast 2 characters")
    private String category;

    private Integer price;

    private Integer reviewCount;

    private Integer rating;
    private Double latitude;
    private Double longitude;

    @OneToMany(mappedBy="restaurant")
    private List<Post> posts;

    protected Restaurant() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(Integer reviewCount) {
        this.reviewCount = reviewCount;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }
}
