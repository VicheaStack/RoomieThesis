package org.roomrental.group.RoomieHub.adminProfile;

import java.util.List;

public record AdminProfileRequestDTO(
        String username,
        List<String> permissions
) {
}
