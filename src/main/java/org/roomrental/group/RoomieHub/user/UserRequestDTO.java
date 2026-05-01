package org.roomrental.group.RoomieHub.user;

public record UserRequestDTO(
        String firstName,
        String lastName,
        String password,
        String phoneNumber,
        String email,
        String profilePhotoUrl
) {
}