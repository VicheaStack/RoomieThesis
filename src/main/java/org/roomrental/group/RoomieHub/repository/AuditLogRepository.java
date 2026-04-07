package org.roomrental.group.RoomieHub.repository;

import org.roomrental.group.RoomieHub.entity.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
}
