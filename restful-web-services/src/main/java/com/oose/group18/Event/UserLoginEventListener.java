package com.oose.group18.Event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class UserLoginEventListener {


    @EventListener
    public void handleUserLoginEventListener(UserLoginEvent event) {
        System.out.println(event.getUser().getUserName());
    }
}
