package com.oose.group18.Entity;

import javax.persistence.*;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Post {
	
	@Id
	@GeneratedValue
	@Column(name="post_id")
	private Integer id;
	private String description;

	@ManyToOne(fetch = FetchType.LAZY)
	private Restaurant restaurant;

	private Date startDate;

	private Integer numOfGuest;

	@ManyToOne(fetch=FetchType.LAZY)
	@JsonIgnore
	private User user;

	//@JsonManagedReference
	@JsonIgnore
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable
	private List<User> guest;

	public Post() {

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

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<User> getGuest() {
		return guest;
	}

	public void setGuest(List<User> guest) {
		this.guest = guest;
	}

	public Integer getNumOfGuest() {
		return numOfGuest;
	}

	public void setNumOfGuest(Integer numOfGuest) {
		this.numOfGuest = numOfGuest;
	}
}
