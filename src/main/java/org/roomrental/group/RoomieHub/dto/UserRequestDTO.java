package org.roomrental.group.RoomieHub.dto;

public record UserRequestDTO(
        String firstName,
        String lastName,
        String password,
        String phoneNumber,
        String email,
        String profilePhotoUrl
) {
}