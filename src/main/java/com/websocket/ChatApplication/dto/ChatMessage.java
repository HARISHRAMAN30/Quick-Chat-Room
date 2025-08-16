package com.websocket.ChatApplication.dto;

import com.websocket.ChatApplication.enums.MessageType;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatMessage {
    private String content;
    private String sender;
    private String timestamp;
    private MessageType type;
}
