package com.oose.group18.Event;

import com.oose.group18.Entity.Post;
import com.oose.group18.Entity.User;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class ApplicationEventListener {
    @EventListener(ApplicationReadyEvent.class)
    public void doSomethingAfterStartup() {
        RestTemplate restTemplate = new RestTemplate();
        Post post = new Post();
        post.setNumOfGuest(5);
        post.setDescription("testing");
        ResponseEntity<String> responseEntity = restTemplate.postForEntity("http://localhost:8080/user/1/host/posts/4", post, String.class);
        System.out.println(responseEntity.getBody());
    }
}