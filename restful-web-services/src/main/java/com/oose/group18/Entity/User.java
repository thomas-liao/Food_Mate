package com.oose.group18.Entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description="All details about the user.")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name="user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name="user_id")
	private Integer id;

	@NotNull
	@Size(min=2, message="Name should have at least 2 characters")
	@ApiModelProperty(notes="Name should have at least 2 characters")
	private String userName;

	private String fullName;

//	@JsonIgnore
	@NotNull
	private String password;


	private String addr;

	private String description;

	@Email
	private String email;

	@JsonIgnore
	@OneToMany(mappedBy="user")
	private List<Post> posts;


	//@JsonManagedReference
	//@JsonBackReference
	@JsonIgnore
	@ManyToMany(mappedBy = "guest")
	private List<Post> joinedPost;

	public User() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
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

	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

	public List<Post> getJoinedPost() {
		return joinedPost;
	}

	public void setJoinedPost(List<Post> joinedPost) {
		this.joinedPost = joinedPost;
	}

	public List<PostView> getJoinedPostView() {
		List<PostView> result = new ArrayList<>();
		if (joinedPost == null) {
			return result;
		}
		for (Post post : joinedPost) {
			result.add(new PostView(post));
		}
		return result;
	}

	public List<PostView> getAllPostView() {
		List<PostView> result = new ArrayList<>();
		if (posts == null) {
			return result;
		}
		for (Post post : posts) {
			result.add(new PostView(post));
		}
		return result;
	}

	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", userName='" + userName + '\'' +
				", fullName='" + fullName + '\'' +
				", description='" + description + '\'' +
				", email='" + email + '\'' +
				'}';
	}

	public boolean isValidLogin() {
		return userName != null && password != null && !userName.isEmpty() && !password.isEmpty();
	}

	public boolean isHost(Post post) {
		return post.getUser().getId().equals(this.getId());
	}

	public boolean joinedPostBefore(Post post) {
		return joinedPost.contains(post);
	}
}
