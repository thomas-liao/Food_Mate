package com.oose.group18.Event;

import com.oose.group18.Entity.Post;
import com.oose.group18.Entity.User;
import org.springframework.context.ApplicationEvent;


public class GuestJoinEvent extends ApplicationEvent {
    private User guest;
    private Post post;
    public GuestJoinEvent(Object source,User guest, Post post) {
        super(source);
        this.guest = guest;
        this.post = post;
    }

    public User getGuest() {
        return guest;
    }

    public Post getPost() {
        return post;
    }
}
