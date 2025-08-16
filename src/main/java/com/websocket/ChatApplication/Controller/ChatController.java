package com.websocket.ChatApplication.Controller;

import com.websocket.ChatApplication.dto.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    // The @MessageMapping annotation maps messages sent to the /chat.sendMessage destination to the sendMessage method.
    @MessageMapping("/chat.sendMessage")    // Message sending endpoint

    // The @SendTo annotation indicates that the return value of the method should be sent to the specified destination, which in this case is /topic/public.
    @SendTo("/topic/public")        // Broadcasts messages to all subscribers of the /topic/public destination

    // The @Payload annotation indicates that the method parameter should be bound to the payload of the message sent to the /chat.sendMessage destination.
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
        return chatMessage;
    }


    // The @MessageMapping annotation maps messages sent to the /chat.addUser destination to the addUser method.
    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor simpMessageHeaderAccessor) {

        // The SimpMessageHeaderAccessor is used to access the message headers
        // and can be used to add or modify headers, such as adding the username of the user sending the message.

        simpMessageHeaderAccessor.getSessionAttributes().put("username", chatMessage.getSender()); // Adds username in websocket session.
        return chatMessage;
    }
}
