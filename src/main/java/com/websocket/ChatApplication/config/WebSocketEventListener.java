package com.websocket.ChatApplication.config;

import com.websocket.ChatApplication.dto.ChatMessage;
import com.websocket.ChatApplication.enums.MessageType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketEventListener {

    private final SimpMessageSendingOperations simpMessageSendingOperations;  // This is used to send messages to other WebSocket clients.

    @EventListener
    public void handleWebSocketDisconnetListener(SessionDisconnectEvent event) {
        // This method listens for WebSocket disconnect events and logs the username of the user who disconnected.
        // It also creates a ChatMessage object indicating that the user has left the chat.

        //* The StompHeaderAccessor is used to access the session attributes, where the username is stored. *//
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        //* Retrieve the username from the session attributes *//
        // The username is stored in the session attributes when the user connects to the WebSocket.
        String username = headerAccessor.getSessionAttributes().get("username").toString();
        if (username != null) {
            log.info("User Disconnected : {}", username);
            var chatMessage = ChatMessage.builder()
                    .type(MessageType.LEAVE)
                    .sender(username)
                    .build();

            // Send the chat message to the /topic/public destination, which is where all users are subscribed to receive messages.
            //* This message will notify all connected users that the user has left the chat. *//
            // The /topic/public destination is where all users are subscribed to receive messages.
            simpMessageSendingOperations.convertAndSend("/topic/public", chatMessage); //
        }
    }
}
