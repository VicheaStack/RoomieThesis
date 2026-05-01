package org.roomrental.group.RoomieHub.review;

import java.time.LocalDateTime;

public record ReviewResponseDTO(
        Long reviewId,
        Long roomId,
        Long renterId,
        Long bookingId,
        Integer rating,
        String title,
        String comment,
        String ownerResponse,
        Boolean isVerified,
        Boolean isFlagged,
        String flagReason,
        Integer helpfulCount,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}