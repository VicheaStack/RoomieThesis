package org.roomrental.group.RoomieHub.dto;

import java.time.LocalDateTime;

public record UserResponseDTO(
        Long userId,
        String email,
        String fullName,
        String phoneNumber,
        String profilePhotoUrl,
        String role,
        Boolean isVerified,
        Boolean isActive,
        LocalDateTime lastLogin,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}