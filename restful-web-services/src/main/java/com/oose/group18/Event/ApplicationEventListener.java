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
        sendPostRequest("let's eat", 2018, 11, 25, 2, 91, 2);
        sendPostRequest("let's eat", 2018, 11, 24, 2, 92, 3);
        sendPostRequest("let's eat", 2018, 11, 26, 2, 93, 4);
        sendPostRequest("let's eat", 2018, 11, 30, 2, 91, 5);
        sendPostRequest("let's eat", 2018, 11, 25, 2, 92, 6);
        sendPostRequest("let's eat", 2018, 11, 25, 2, 93, 7);
        sendPostRequest("let's eat", 2018, 11, 25, 2, 91, 8);
        sendPostRequest("let's eat", 2018, 11, 25, 2, 92, 9);
        sendPostRequest("let's eat", 2018, 11, 25, 2, 93, 10);
        addPost(90, 2);
        addPost(99, 2);
        addPost(98, 2);
        addPost(97, 2);
        addPost(96, 2);
        addPost(95, 2);
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
