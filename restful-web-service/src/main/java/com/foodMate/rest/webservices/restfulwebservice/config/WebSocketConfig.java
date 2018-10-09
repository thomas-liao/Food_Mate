package com.foodMate.rest.webservices.restfulwebservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;


// STOMP: Simple Text Oriented Messaging Protocal - need STOMP on top of Websocket (protocal)
// s.t. we can define how message flows (e.g. one to one message, one to many(subscribers), one to all.)
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // this define that the messages whose destination starts with “/app”
        // should be routed to message-handling methods (defined elsewhere with @MessageMapping).
        registry.setApplicationDestinationPrefixes("/app");

        // thisdefines that the messages whose destination starts with “/topic” should be
        // routed to the message broker. Message broker broadcasts messages to all the
        // connected clients who are subscribed to a particular topic.
        registry.enableSimpleBroker("/topic");
    }
    }