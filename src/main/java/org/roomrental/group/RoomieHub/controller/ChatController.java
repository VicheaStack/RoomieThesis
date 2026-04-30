package org.roomrental.group.RoomieHub.controller;

import org.roomrental.group.RoomieHub.entity.Message;
import org.roomrental.group.RoomieHub.service.MessageService;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    private final MessageService messageService;

    public ChatController(MessageService messageService) {
        this.messageService = messageService;
    }

    @MessageMapping("/conversations/{conversationId}")
    @SendTo("/topic/conversations/{conversationId}")
    public Message sendMessage(@DestinationVariable Long conversationId, Message message) {
        return messageService.sendMessage(
                conversationId,
                message.getSender().getUserId(),
                message.getMessageText()
        );
    }
}