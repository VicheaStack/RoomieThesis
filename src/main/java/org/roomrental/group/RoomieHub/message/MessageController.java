package org.roomrental.group.RoomieHub.message;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
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
    @Transactional
    public ResponseEntity<MessageResponseDTO> sendMessage(
            @PathVariable Long conversationId,
            @RequestParam Long senderId,
            @RequestParam String text) {

        Message message = messageService.sendMessage(conversationId, senderId, text);
        MessageResponseDTO dto = toDTO(message);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @GetMapping("/{conversationId}/messages")
    @Transactional(readOnly = true)
    public ResponseEntity<List<MessageResponseDTO>> getMessages(@PathVariable Long conversationId) {
        List<Message> messages = messageService.getMessages(conversationId);
        List<MessageResponseDTO> dtos = messages.stream()
                .map(this::toDTO)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    @PutMapping("/{conversationId}/read")
    @Transactional
    public ResponseEntity<Void> markAsRead(
            @PathVariable Long conversationId,
            @RequestParam Long userId) {
        messageService.markAsRead(conversationId, userId);
        return ResponseEntity.noContent().build();
    }

    private MessageResponseDTO toDTO(Message message) {
        return new MessageResponseDTO(
                message.getMessageId(),
                message.getConversation().getConversationId(),
                message.getSender().getUserId(),
                message.getSender().getFirstName(),
                message.getReceiver() != null ? message.getReceiver().getUserId() : null,
                message.getReceiver() != null ? message.getReceiver().getFirstName() : null,
                message.getMessageText(),
                message.getIsRead(),
                message.getCreatedAt()
        );
    }
}