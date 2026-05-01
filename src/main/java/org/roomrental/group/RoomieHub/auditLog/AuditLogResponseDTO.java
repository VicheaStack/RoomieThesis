package org.roomrental.group.RoomieHub.auditLog;

import java.time.LocalDateTime;

public record AuditLogResponseDTO(
        Long logId,
        Long userId,
        String action,
        String entityType,
        Long entityId,
        String oldValues,
        String newValues,
        String ipAddress,
        String userAgent,
        LocalDateTime createdAt
) {
}
