package org.roomrental.group.RoomieHub.auditLog;

public record AuditLogRequestDTO(
        String action,
        String entityType,
        Long entityId,
        String oldValues,
        String newValues,
        String userAgent
) {
}