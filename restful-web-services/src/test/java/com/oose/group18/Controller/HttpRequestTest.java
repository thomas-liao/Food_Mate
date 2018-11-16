package com.oose.group18.Controller;


import com.oose.group18.Entity.Post;
import com.oose.group18.Entity.Restaurant;
import com.oose.group18.Entity.User;
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

import javax.xml.ws.Response;
import java.io.PipedOutputStream;
import java.net.URI;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class HttpRequestTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void getUserRequestTest() throws Exception {

        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/user/1",
                String.class)).contains("John").contains("first@example.com");

        ResponseEntity<String> response1 = this.restTemplate.getForEntity("http://localhost:" + port + "/user/1", String.class);
        assertThat(response1.getStatusCode()).isEqualTo(HttpStatus.OK);

        ResponseEntity<String> response2 = this.restTemplate.getForEntity("http://localhost:" + port + "/user/9999", String.class);
        assertThat(response2.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

        ResponseEntity<String> response3 = this.restTemplate.getForEntity("http://localhost:" + port + "/user/abc", String.class);
        assertThat(response2.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

    }

    @Test
    public void deleteUserRequestTest() throws Exception {

        this.restTemplate.delete("http://localhost:" + port + "/user/3");
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/user/3",
                String.class)).doesNotContain("Thomas").doesNotContain("third@example.com");
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

//
//        ClientHttpRequestFactory requestFactory = getClientHttpRequestFactory();
//        RestTemplate restTemplate = new RestTemplate(requestFactory);
//
//        HttpEntity<Foo> request = new HttpEntity<>(new Foo("bar"));
//        Foo foo = restTemplate.postForObject(fooResourceUrl, request, Foo.class);
//        assertThat(foo, notNullValue());
//        assertThat(foo.getName(), is("bar"));
    }

    @Test
    public void getRestaurantRequestTest() throws Exception {
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


}