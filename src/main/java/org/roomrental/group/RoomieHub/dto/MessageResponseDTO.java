package org.roomrental.group.RoomieHub.dto;

import java.time.LocalDateTime;

public record MessageResponseDTO(
        Long messageId,
        Long conversationId,
        Long senderId,
        String senderName,
        Long receiverId,
        String receiverName,
        String messageText,
        Boolean isRead,
        LocalDateTime createdAt
) {}