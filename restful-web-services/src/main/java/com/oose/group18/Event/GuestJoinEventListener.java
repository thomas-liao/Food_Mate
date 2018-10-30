package com.oose.group18.Event;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class GuestJoinEventListener {

    @EventListener
    public void handleGuestJoinEventListener(GuestJoinEvent event) {
        System.out.println(event.getGuest().getUserName() + event.getPost().getId());
    }
}
