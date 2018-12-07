package com.oose.group18.Controller;


import com.oose.group18.Entity.Post;
import com.oose.group18.Entity.Restaurant;
import com.oose.group18.Entity.User;
import com.oose.group18.Repository.PostRepository;
import com.oose.group18.Repository.RestaurantRepository;
import com.oose.group18.Repository.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.xml.ws.Response;
import java.io.PipedOutputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class JPAResourceTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

//    @Test
//    public void getUserRequestTest() throws Exception {
//
//        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/user/1",
//                String.class)).contains("John").contains("first@example.com");
//
//        ResponseEntity<String> response1 = this.restTemplate.getForEntity("http://localhost:" + port + "/user/1", String.class);
//        assertThat(response1.getStatusCode()).isEqualTo(HttpStatus.OK);
//
//        ResponseEntity<String> response2 = this.restTemplate.getForEntity("http://localhost:" + port + "/user/9999", String.class);
//        assertThat(response2.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
//
//        ResponseEntity<String> response3 = this.restTemplate.getForEntity("http://localhost:" + port + "/user/abc", String.class);
//        assertThat(response2.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
//
//    }

    @Test
    public void getAllUserRequestTest() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/users",
                String.class)).contains("John").contains("first@example.com");

        ResponseEntity<String> response1 = this.restTemplate.getForEntity("http://localhost:" + port + "/users", String.class);
        assertThat(response1.getStatusCode()).isEqualTo(HttpStatus.OK);


    }

    @Test
    public void getRecommendedPostsTest() {

        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/user/92/guest/posts",
                String.class)).isEqualTo("[]");
    }


    @Test
    public void deleteUserRequestTest() throws Exception {
        this.restTemplate.delete("http://localhost:" + port + "/user/3");
        this.restTemplate.delete("http://localhost:"+port+String.format("/user/%s", 2));
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/user/3",
                String.class)).doesNotContain("Thomas").doesNotContain("third@example.com");
    }

    @Test
    public void userLoginTest() throws Exception {
        final String baseUrl = "http://localhost:" + port + "/login";
        URI uri = new URI(baseUrl);
        User mockUser1 = new User();
        mockUser1.setUserName("DonaldTrump");
        mockUser1.setPassword("123cdeF!");

        User mockUser2 = new User();
        mockUser2.setUserName("John");
        mockUser2.setPassword("John");


        HttpHeaders headers = new HttpHeaders();
        headers.set("X-COM-PERSIST", "false");
        HttpEntity<User> request = new HttpEntity<>(mockUser1, headers);
        ResponseEntity<String> result = this.restTemplate.postForEntity(uri, request, String.class);
        System.out.println(result.getBody());
        assert result.getBody().equals("-1"); // no such users
        //Verify request succeed

        headers.set("X-COM-PERSIST", "false");
        HttpEntity<User> request2 = new HttpEntity<>(mockUser2, headers);
        ResponseEntity<String> result2 = this.restTemplate.postForEntity(uri, request2, String.class);

        assert ! result2.getBody().equals("-1"); // return a valid id instead of -1 which indicates not found.
        Assert.assertEquals(200, result2.getStatusCodeValue());

    }


    @Test
    public void putUserRequestTest() throws Exception {
        final String baseUrl = "http://localhost:"+port+"/register";
        URI uri = new URI(baseUrl);
        User mockUser1 = new User();
        mockUser1.setAddr("somewhere");
        mockUser1.setFullName("DonaldTrump");
        mockUser1.setUserName("DonaldTrump123");
        mockUser1.setPassword("123cdeF!");
        mockUser1.setEmail("fifth@example.com");
        mockUser1.setDescription("abcdefg");
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-COM-PERSIST", "true");
        HttpEntity<User> request = new HttpEntity<>(mockUser1, headers);
        ResponseEntity<String> result = this.restTemplate.postForEntity(uri, request, String.class);

        //Verify request succeed
        Assert.assertEquals(201, result.getStatusCodeValue());
    }

//    @Test
//    public void hostRejectGuestTest() throws Exception {
//
//        final String baseUrl = "http://localhost:"+port+"/user/1/host/posts/1/guests";
//        URI uri = new URI(baseUrl);
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("X-COM-PERSIST", "false");
//        HttpEntity<Integer> request = new HttpEntity<>((Integer) 1, headers);
//        ResponseEntity<String> result = this.restTemplate.postForEntity(uri, request, String.class);
//
//        // cannot be translated into Integer... TODO: need to fix this test.
//        Assert.assertEquals(405, result.getStatusCodeValue());
//    }

    @Test
    public void getAllPostsTest() {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/user/91/host/posts",
                String.class)).isEqualTo("[]");
    }

    @Test
    public void hostCreatePostTest() throws Exception {
//        POST /user/{id}/host/posts/{restaurantId}
        final String baseUrl = "http://localhost:"+port+"/user/1/host/posts/13";
        Restaurant mockRestaurant = new Restaurant();
        URI uri = new URI(baseUrl);
//        mockRestaurant.setId(13);
        mockRestaurant.setCategory("Pizza");
        mockRestaurant.setPrice(2);

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-COM-PERSIST", "true");
        HttpEntity<Restaurant> request = new HttpEntity<>(mockRestaurant, headers);
        ResponseEntity<String> result = this.restTemplate.postForEntity(uri, request, String.class);

        //Verify request succeed
        Assert.assertEquals(200, result.getStatusCodeValue());
    }

    @Test
    public void getGuestTest() throws Exception {
        // should successfully get something instead of return "[]"
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/user/2/host/posts/1/guests",
                String.class)).isNotEqualTo(("[]"));
    }

    @Test
    public void guestJoinPostTest() throws Exception {

        final String baseUrl = "http://localhost:"+port+"/user/1/guest/2";
        Post mockPost = new Post();
        URI uri = new URI(baseUrl);
        mockPost.setNumOfGuest(5);

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-COM-PERSIST", "true");
        HttpEntity<Post> request = new HttpEntity<>(mockPost, headers);
        ResponseEntity<String> result = this.restTemplate.postForEntity(uri, request, String.class);

        //Verify request succeed
        Assert.assertEquals(200, result.getStatusCodeValue());
    }


    // JPA layer tests
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private PostRepository postRepository;


    @Test
    public void findAllUserTest() throws Exception{
        List<User> temp =  userRepository.findAll();
        assert temp.size() > 1;
    }

    @Test
    public void findUsersByIdTest() {
        List<Integer> querryIds = new ArrayList<>();
        querryIds.add(91);
        querryIds.add(92);
        querryIds.add(93);
        List<User> temp =  userRepository.findAllById(querryIds);
        assert temp.size() == 3;
    }

    @Test
    public void checkUserNameTEST() {
        List<Integer> querryIds = new ArrayList<>();
        querryIds.add(91);
        querryIds.add(92);
        List<User> temp =  userRepository.findAllById(querryIds);
        assert temp.get(0).getUserName().equals("John");
    }

    @Test
    public void findAllRestaurantTest() {
        List<Restaurant> restaurants = restaurantRepository.findAll();
        assert restaurants.size() == 280;
    }

    @Test
    public void findAllRestaurantByIdTest() {
        List<Integer> temp = new ArrayList<>();
        temp.add(1);
        temp.add(3);
        temp.add(5);
        List<Restaurant> restaurants = restaurantRepository.findAllById(temp);
        assert restaurants.get(0).getId() == 1;
        assert restaurants.get(1).getId() == 3;
        assert restaurants.get(2).getId() == 5;
    }

    @Test
    public void findOneNonExistentUserTest() {
        // no such user ids exist
        List<Integer> nonexistentQuerry = new ArrayList<>();
        nonexistentQuerry.add(999);

        List<User> usrs = userRepository.findAllById(nonexistentQuerry);
        // should return []
        assert usrs.size() == 0;
    }

    @Test
    public void addOneUserTest() {
        User mockUser = new User();
        Integer tempId = 97;
        mockUser.setId(tempId);
        mockUser.setAddr("somewhere");
        mockUser.setFullName("ThomasL");
        mockUser.setUserName("tliao4");
        mockUser.setPassword("123cdeF!");
        mockUser.setEmail("tliao4@jhu.edu");
        mockUser.setDescription("abcdefg");

        userRepository.save(mockUser);
        List<Integer> querry = new ArrayList<>();
        querry.add(97);

        List<User> res = userRepository.findAllById(querry);

        assert res.size() == 1;
        assert res.get(0).getId() == tempId;
        assert res.get(0).getUserName().equals("tliao4");
        assert res.get(0).getAddr().equals("somewhere");
        assert res.get(0).getPassword().equals("123cdeF!");
        assert res.get(0).getEmail().equals("tliao4@jhu.edu");
        assert res.get(0).getDescription().equals("abcdefg");
    }

    @Test
    public void getRestaurantsTest() {
        // no restaurant at first, expecting "[]"
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/user/92/host/restaurants",
                String.class)).isNotEqualTo("[]");
    }

    @Test
    public void addMultipleUsersTest() {
        User mockUser1 = new User();
        Integer tempId1 = 95;
        mockUser1.setId(tempId1);
        mockUser1.setAddr("somewhere");
        mockUser1.setFullName("ThomasL");
        mockUser1.setUserName("tliao4");
        mockUser1.setPassword("123cdeF!");
        mockUser1.setEmail("tliao4@jhu.edu");
        mockUser1.setDescription("abcdefg");

        User mockUser2 = new User();
        Integer tempId2 = 96;
        mockUser2.setId(tempId2);
        mockUser2.setAddr("somewhere2");
        mockUser2.setFullName("mxg");
        mockUser2.setUserName("abcde123");
        mockUser2.setPassword("321defgG!");
        mockUser2.setEmail("xxx@jhu.edu");
        mockUser2.setDescription("whatever");

        List<User> usrs = new ArrayList<>();
        usrs.add(mockUser1);
        usrs.add(mockUser2);
        System.out.println("Sanity check");
        System.out.println(usrs.size());

        List<Integer> querryIDs = new ArrayList<>();
        querryIDs.add(tempId1);
        querryIDs.add(tempId2);

        userRepository.saveAll(usrs);

        List<User> res = userRepository.findAllById(querryIDs);

        assert res.size() == 2;
        assert res.get(0).getId() == tempId1;
        assert res.get(1).getId() == tempId2;
    }

    @Test
    public void findMultipleNonExistentUsersTest() {
        List<Integer> nonexistentQuerry = new ArrayList<>();
        nonexistentQuerry.add(999);
        nonexistentQuerry.add(888);
        nonexistentQuerry.add(12345);
        nonexistentQuerry.add(3333);

        List<User> usrs = userRepository.findAllById(nonexistentQuerry);
        // should return []
        assert usrs.size() == 0;
    }

    @Test
    public void findExistNonExistUsersTest() {
        Integer tempId1 = 95;
        Integer tempId2 = 96;
        Integer tempId3 = 111;
        Integer tempId4 = 222;

        User mockUser1 = new User();
        mockUser1.setId(tempId1);
        mockUser1.setAddr("somewhere");
        mockUser1.setFullName("ThomasL");
        mockUser1.setUserName("tliao4");
        mockUser1.setPassword("123cdeF!");
        mockUser1.setEmail("tliao4@jhu.edu");
        mockUser1.setDescription("abcdefg");

        User mockUser2 = new User();
        mockUser2.setId(tempId2);
        mockUser2.setAddr("somewhere2");
        mockUser2.setFullName("mxg");
        mockUser2.setUserName("abcde123");
        mockUser2.setPassword("321defgG!");
        mockUser2.setEmail("xxx@jhu.edu");
        mockUser2.setDescription("whatever");

        List<User> usrs = new ArrayList<>();
        usrs.add(mockUser1);
        usrs.add(mockUser2);
        System.out.println("Sanity check");
        System.out.println(usrs.size());

        List<Integer> querryIDs = new ArrayList<>();
        querryIDs.add(tempId1);
        querryIDs.add(tempId2);
        querryIDs.add(tempId3);
        querryIDs.add(tempId4);

        userRepository.saveAll(usrs);

        List<User> res = userRepository.findAllById(querryIDs);

        assert res.size() == 2; // should only return 2 instead of 4, although querry for 4 ids.w
        assert res.get(0).getId() == tempId1;
        assert res.get(1).getId() == tempId2;
    }
}


