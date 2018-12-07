package com.oose.group18.Event;

import com.oose.group18.Entity.Post;
import com.oose.group18.Entity.User;
import io.swagger.models.auth.In;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class ApplicationEventListener {
    @EventListener(ApplicationReadyEvent.class)
    public void doSomethingAfterStartup() {
        sendPostRequest("let's eat", 2018, 11, 25, 2, 1, 2);
        sendPostRequest("let's eat", 2018, 11, 24, 2, 2, 3);
        sendPostRequest("let's eat", 2018, 11, 26, 2, 3, 4);
        sendPostRequest("let's eat", 2018, 11, 30, 2, 1, 5);
        sendPostRequest("let's eat", 2018, 11, 25, 2, 2, 6);
        sendPostRequest("let's eat", 2018, 11, 25, 2, 3, 7);
        sendPostRequest("let's eat", 2018, 11, 25, 2, 1, 8);
        sendPostRequest("let's eat", 2018, 11, 25, 2, 2, 9);
        sendPostRequest("let's eat", 2018, 11, 25, 2, 3, 10);
        addPost(10, 2);
        addPost(9, 2);
        addPost(8, 2);
        addPost(7, 2);
        addPost(6, 2);
        addPost(5, 2);
    }
    private void sendPostRequest(String description, int year, int month, int day, int numOfGuest, int userId, int resId) {
        RestTemplate restTemplate = new RestTemplate();
        Post post = new Post();
        post.setDescription(description);
        post.setStartDate(new GregorianCalendar(year, month, day).getTime().toString());
        post.setNumOfGuest(numOfGuest);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity("http://localhost:8080/user/"+ userId+"/host/posts/" + resId, post, String.class);
    }

    private void addPost(int userId, int postId){
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.postForEntity("http://localhost:8080/user/"+ userId+"/guest/" + postId, null, String.class);
    }
}
