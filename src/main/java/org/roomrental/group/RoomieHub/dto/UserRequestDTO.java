package org.roomrental.group.RoomieHub.dto;

public record UserRequestDTO(
        String email,
        String password,
        String fullName,
        String phoneNumber,
        String profilePhotoUrl,
        String role
) {
}