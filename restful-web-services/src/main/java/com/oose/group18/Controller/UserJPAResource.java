package com.oose.group18.Controller;


import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.oose.group18.Entity.Post;
import com.oose.group18.Entity.Restaurant;
import com.oose.group18.Entity.User;
import com.oose.group18.Entity.UserView;
import com.oose.group18.Event.GuestJoinEvent;
import com.oose.group18.Event.UserLoginEvent;
import com.oose.group18.RecommenderController.Recommender;
import com.oose.group18.Repository.PostRepository;
import com.oose.group18.Repository.RestaurantRepository;
import com.oose.group18.Repository.UserRepository;
import com.oose.group18.Exception.PostNotFoundException;
import com.oose.group18.Exception.RestaurantNotFoundException;
import com.oose.group18.Exception.UserNotFoundException;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.swing.text.html.Option;

@RestController
public class UserJPAResource {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private RestaurantRepository restaurantRepository;

	@Autowired
	ApplicationEventPublisher applicationEventPublisher;

	Recommender recommender = new Recommender();

	@GetMapping("/users")
	public MappingJacksonValue retrieveAllUsers() {
		List<User> users = userRepository.findAll();
		List<UserView> result = new ArrayList<>();
		for (User user : users) {
			result.add(new UserView(user));
		}

		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("userName", "fullName", "description", "email");

		FilterProvider filters = new SimpleFilterProvider().addFilter("SomeBeanFilter", filter);

		MappingJacksonValue mapping = new MappingJacksonValue(result);

		mapping.setFilters(filters);


		return mapping;
	}

	@PostMapping("/login")
	public String userLogin(@RequestBody User loginUser) {
		List<User> users = userRepository.findAll();
		for (User user : users) {
			if (loginUser.getUserName().equals(user.getUserName()) && loginUser.getPassword().equals(user.getPassword())) {
				System.out.println(user.getUserName());
				System.out.println(user.getPassword());
				return String.valueOf(user.getId());
			}
		}
		return "-1";
	}

	@PostMapping("/register")
	public ResponseEntity<Object> createUser(@RequestBody User user) {
		user.setId(1000);
		System.out.println(user.toString());
		postRepository.save(new Post());
		//restaurantRepository.save(new Restaurant());

		//User savedUser = userRepository.save(new User());

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(100)
				.toUri();

		return ResponseEntity.created(location).build();

	}
	@GetMapping("/user/{id}")
	public MappingJacksonValue retrieveUser(@PathVariable int id) {
		Optional<User> user = userRepository.findById(id);

		if (!user.isPresent())
			throw new UserNotFoundException("id-" + id);
		UserView result = new UserView(user.get());
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("userName", "fullName", "description", "email");
		FilterProvider filters = new SimpleFilterProvider().addFilter("SomeBeanFilter", filter);

		MappingJacksonValue mapping = new MappingJacksonValue(result);
		mapping.setFilters(filters);

		UserLoginEvent event = new UserLoginEvent(this, user.get());
		applicationEventPublisher.publishEvent(event);

		return mapping;
	}

	@DeleteMapping("/user/{id}")
	public void deleteUser(@PathVariable int id) {
		userRepository.deleteById(id);
	}

	@GetMapping("user/{id}/host/restaurants")
	public List<Restaurant> retrieveAllRestaurants(@PathVariable int id) {
//		List<Integer> res = new ArrayList<>();
//		res.add(1);
//		res.add(2);
		List<Integer> res = recommender.getRecommend(id, 10);
		if (res == null) {
			System.out.println("return is empty");
			return null;
		}
		List<Restaurant> restaurants = restaurantRepository.findAllById(res);
		for(Restaurant restaurant : restaurants) {
			System.out.println(restaurant.getId());
		}
		return restaurants;
	}


	@GetMapping("/user/{id}/host/posts")
	public List<Post> retrieveAllPosts(@PathVariable int id) {
		Optional<User> userOptional = userRepository.findById(id);

		if(!userOptional.isPresent()) {
			throw new UserNotFoundException("id-" + id);
		}

		return userOptional.get().getPosts();
	}

	@PostMapping("/user/{id}/host/posts/{restaurantId}")
	public String createPost(@PathVariable int id, @RequestBody Post post, @PathVariable int restaurantId) {

		Optional<User> userOptional = userRepository.findById(id);

		Optional<Restaurant> restaurantOptional = restaurantRepository.findById(restaurantId);

		if(!userOptional.isPresent()) {
			throw new UserNotFoundException("id-" + id);
		}

		if(!restaurantOptional.isPresent()) {
			throw new RestaurantNotFoundException("id-" + id);
		}

		User user = userOptional.get();

		Restaurant restaurant = restaurantOptional.get();

		post.setUser(user);
		post.setRestaurant(restaurant);

		Post insertedPost = postRepository.save(post);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(post.getId())
				.toUri();

		return String.valueOf(insertedPost.getId());

	}

	@GetMapping("/user/{id}/host/posts/{postId}/guests")
	public List<User> getGuest(@PathVariable int id, @PathVariable int postId) {
		Optional<User> userOptional = userRepository.findById(id);

		if(!userOptional.isPresent()) {
			throw new UserNotFoundException("id-" + id);
		}
		Optional<Post> postOptional = postRepository.findById(postId);
		if (!postOptional.isPresent()) {
			throw new PostNotFoundException("id-" + postId);
		}
		Post post = postOptional.get();
		return post.getGuest();
	}

	@PostMapping("/user/{id}/host/posts/{postId}/guests/{guestId}")
	public void rejectGuest(@PathVariable int id, @PathVariable int postId, int guestId) {
		Optional<User> userOptional = userRepository.findById(id);

		if(!userOptional.isPresent()) {
			throw new UserNotFoundException("id-" + id);
		}
		Optional<Post> postOptional = postRepository.findById(postId);
		Optional<User> guestOptional = userRepository.findById(guestId);
		User guest = guestOptional.get();
		Post post = postOptional.get();
		post.getGuest().remove(guest);
		guest.getJoinedPost().remove(post);
		postRepository.save(post);
		userRepository.save(guest);
	}

	@DeleteMapping("/user/{id}/host/posts/{post_id}")
	public void deletePost(@PathVariable int id, @PathVariable int post_id) {
		Optional<User> userOptional = userRepository.findById(id);

		if(!userOptional.isPresent()) {
			throw new UserNotFoundException("id-" + id);
		}
		Optional<Post> postOptional = postRepository.findById(post_id);
		Post post = postOptional.get();
		User user = userOptional.get();
		for (User guest : post.getGuest()) {
			guest.getJoinedPost().remove(post);
			userRepository.save(guest);
		}
		user.getPosts().remove(post);
		userRepository.save(user);
	}


	@PostMapping("user/{id}/guest/posts")
	public List<Post> retrieveAllRecommendedPosts(@PathVariable int id) {
		Optional<User> userOptional = userRepository.findById(id);
		if (!userOptional.isPresent()) {
			throw new UserNotFoundException("id-" + id);
		}
		User user = userOptional.get();
		List<Post> posts = postRepository.findAll();
		if (posts == null) {
			return posts;
		}
//		for (Iterator<Post> iterator = posts.iterator(); iterator.hasNext(); ) {
//			String value = iterator.next();
//			if (value.length() > 5) {
//				iterator.remove();
//			}
//		}
		posts.removeIf((Post post) -> user.getJoinedPost().contains(post) || post.getUser().getId().equals(user.getId()) || post.getGuest().size() >= post.getNumOfGuest());
//		for (Post recommendedPost : posts) {
//			if (user.getJoinedPost().contains(recommendedPost) || recommendedPost.getUser().getId().equals(user.getId()) || recommendedPost.getGuest().size() >= recommendedPost.getNumOfGuest()) {
//				posts.remove(recommendedPost);
//			}
//		}
		return posts;
	}


	@PostMapping("/user/{id}/guest/{post_id}")
	public Integer joinPost(@PathVariable int id, @PathVariable int post_id) {

		Optional<User> userOptional = userRepository.findById(id);


		Optional<Post> postOptional = postRepository.findById(post_id);

		if (!userOptional.isPresent()) {
			throw new UserNotFoundException("id-" + id);
		}

		if (!postOptional.isPresent()) {
			throw new PostNotFoundException("id-" + post_id);
		}

		User user = userOptional.get();

		Post post = postOptional.get();

		if (user.getId().equals(post.getUser().getId())) {
			return -1;
		}
		if (post.getNumOfGuest() <= post.getGuest().size()) {
			return -2;
		}
		if (post.getGuest().contains(user)) {
			return -3;
		}
		post.getGuest().add(user);
		user.getJoinedPost().add(post);
		userRepository.save(user);
		postRepository.save(post);
		GuestJoinEvent event = new GuestJoinEvent(this, user, post);
		applicationEventPublisher.publishEvent(event);

		return 1;
	}

	@GetMapping("/user/{id}/guest/posts")
	public List<Post> getAllJoinedPosts(@PathVariable int id) {
		Optional<User> userOptional = userRepository.findById(id);

		if(!userOptional.isPresent()) {
			throw new UserNotFoundException("id-" + id);
		}

		return userOptional.get().getJoinedPost();
	}

}
