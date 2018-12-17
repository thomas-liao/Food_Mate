package com.oose.group18.Controller;


import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.oose.group18.Entity.*;
import com.oose.group18.RecommenderController.Recommender;
import com.oose.group18.RecommenderController.PostRecommender;
import com.oose.group18.Repository.*;
import com.oose.group18.Exception.PostNotFoundException;
import com.oose.group18.Exception.RestaurantNotFoundException;
import com.oose.group18.Exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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

	@Autowired
	ReviewRepository usedReviewRepository;


	private Recommender recommender; // = new Recommender();
	private PostRecommender postRecommender; // = new PostRecommender(10);

	
	UserJPAResource () {
		recommender = new Recommender();
		postRecommender = new PostRecommender();
	}
    // (int)userRepository.count()
	@GetMapping("/users")
	public List<UserView> retrieveAllUsers() {
		List<User> users = userRepository.findAll();
		List<UserView> result = new ArrayList<>();
		for (User user : users) {
			result.add(new UserView(user));
		}
		return result;
	}

	@PostMapping("/login")
	public String userLogin(@RequestBody User loginUser) {
		if (loginUser == null && !loginUser.isValidLogin()) {
			return "-1";
		}
		User selectedUser = userRepository.login(loginUser.getUserName(), loginUser.getPassword());
		if (selectedUser == null) {
			return "-1";
		}
		return selectedUser.getId().toString();
	}

	@PostMapping("/register")
	public ResponseEntity<Object> createUser(@RequestBody User user) {
		List<User> users = userRepository.findAll();
		for (User existsUser : users) {
			if (existsUser.getUserName() != null && user.getUserName() != null && existsUser.getUserName().equals(user.getUserName())) {
				return ResponseEntity.notFound().build();
			}
		}
		userRepository.save(user);
		postRecommender.update(100);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(100)
				.toUri();
		return ResponseEntity.created(location).build();

	}

	@GetMapping("user/{id}/host/restaurants")
	public List<Restaurant> retrieveAllRestaurants(@PathVariable int id) {
		List<Integer> res = recommender.getRecommend(id % 200, 11);
		if (res == null) {
			return null;
		}
		//System.out.println(postRecommender.getUserSimilarity(1, 2));
		return restaurantRepository.findAllById(res);
	}


	@GetMapping("/user/{id}/host/posts")
	public List<PostView> retrieveAllPosts(@PathVariable int id) {
		Optional<User> userOptional = userRepository.findById(id);

		if(!userOptional.isPresent()) {
			throw new UserNotFoundException("id-" + id);
		}

		User user = userOptional.get();
		return user.getAllPostView();
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
	public List<UserView> getGuest(@PathVariable int id, @PathVariable int postId) {
		Optional<User> userOptional = userRepository.findById(id);

		if(!userOptional.isPresent()) {
			throw new UserNotFoundException("id-" + id);
		}
		Optional<Post> postOptional = postRepository.findById(postId);
		if (!postOptional.isPresent()) {
			throw new PostNotFoundException("id-" + postId);
		}
		Post post = postOptional.get();
		return post.getGuestView();
	}



	@PostMapping("user/{id}/guest/posts")
	public List<PostView> retrieveAllRecommendedPosts(@PathVariable int id) {
		Optional<User> userOptional = userRepository.findById(id);
		if (!userOptional.isPresent()) {
			throw new UserNotFoundException("id-" + id);
		}
		User user = userOptional.get();
		List<Post> posts = postRepository.findAll();
		if (posts == null) {
			return null;
		}
		System.out.println(posts.size());
		List<Post> rec_posts = postRecommender.getRecommendPost(posts, id % 100, 10);
		rec_posts.removeIf((Post post) -> !post.canJoin(user));
		List<PostView> result = new ArrayList<>();
		for (Post post : rec_posts) {
			result.add(new PostView(post));
		}
		return result;
	}


	@PostMapping("/user/{id}/guest/{post_id}")
	public String joinPost(@PathVariable int id, @PathVariable int post_id) {

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

		if (user.isHost(post)) {
			return "-1";
		}
		if (post.isFull()) {
			return "-2";
		}
		if (user.joinedPostBefore(post)) {
			return "-3";
		}
		post.getGuest().add(user);
		user.getJoinedPost().add(post);
		userRepository.save(user);
		postRepository.save(post);

		return "1";
	}

	@GetMapping("/user/{id}/guest/posts")
	public List<PostView> getAllJoinedPosts(@PathVariable int id) {
		Optional<User> userOptional = userRepository.findById(id);

		if(!userOptional.isPresent()) {
			throw new UserNotFoundException("id-" + id);
		}
		return userOptional.get().getJoinedPostView();
	}

    @GetMapping("/user/{id}/guest/posts/{post_id}")
    public List<User> reviewJoinedPost(@PathVariable int id, @PathVariable int post_id) {
        Optional<User> userOptional = userRepository.findById(id);
        Optional<Post> postOptional = postRepository.findById(post_id);
        if(!userOptional.isPresent()) {
            throw new UserNotFoundException("id-" + id);
        }

        if (!postOptional.isPresent()) {
            throw  new NullPointerException();
        }

        User user = userOptional.get();
        Post post = postOptional.get();

        if (user.getJoinedPost().contains(post)) {
            return post.getGuest();
        }

        return null;
    }

	@PostMapping("/user/")
	public String addReview(@RequestBody Review review) {
		review.setTimeStep(new Date());
		review.setId(review.getUserId() % 100);
		usedReviewRepository.save(review);
		recommender.update(review);
		int n_user = 100;
		//System.out.println(n_user);
		postRecommender.update(n_user);
		return "1";
	}

	@PutMapping("/update/")
	public void update() {
		recommender.update_system();
		int n_user = 100;
		//System.out.println(n_user);
		postRecommender.update(n_user);
	}

	@GetMapping("/user/{user1Id}/{user2Id}")
	public  Float getSimilarity(@PathVariable int user1Id, @PathVariable int user2Id) {
		Optional<User> user1Optional = userRepository.findById(user1Id);
		Optional<User> user2Optional = userRepository.findById(user2Id);
		if (!user1Optional.isPresent() || !user2Optional.isPresent()) {
			return null;
		}
		return postRecommender.getUserSimilarity(user1Id, user2Id);
	}

}
