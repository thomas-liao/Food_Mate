package com.oose.group18.Event;

import com.oose.group18.Entity.User;
import org.springframework.context.ApplicationEvent;

public class UserLoginEvent extends ApplicationEvent {
    private User user;
    public UserLoginEvent(Object source,User user) {
        super(source);
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
