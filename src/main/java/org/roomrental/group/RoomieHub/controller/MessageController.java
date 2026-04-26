package org.roomrental.group.RoomieHub.controller;

import org.roomrental.group.RoomieHub.entity.Message;
import org.roomrental.group.RoomieHub.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/conversations")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping("/{conversationId}/messages")
    public ResponseEntity<Message> sendMessage(
            @PathVariable Long conversationId,
            @RequestParam Long senderId,
            @RequestParam String text) {

        Message message = messageService.sendMessage(conversationId, senderId, text);
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    @GetMapping("/{conversationId}/messages")
    public ResponseEntity<List<Message>> getMessages(@PathVariable Long conversationId) {
        List<Message> messages = messageService.getMessages(conversationId);
        return ResponseEntity.ok(messages);
    }

    @PutMapping("/{conversationId}/read")
    public ResponseEntity<Void> markAsRead(
            @PathVariable Long conversationId,
            @RequestParam Long userId) {

        messageService.markAsRead(conversationId, userId);
        return ResponseEntity.noContent().build();
    }
}