package org.roomrental.group.RoomieHub.dto;

import java.time.LocalDateTime;

public record AuditLogRequestDTO(
        String action,
        String entityType,
        Long entityId,
        String oldValues,
        String newValues,
        String userAgent
) {
}