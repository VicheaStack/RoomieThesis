package org.roomrental.group.RoomieHub.auditLog;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AuditLogService {

    AuditLog create(AuditLog auditLog);
    AuditLog update(AuditLog auditLog, Long id);
    AuditLog findById(Long id);
    Page<AuditLog> findAll(Pageable pageable);
    void deleteById(Long id);
}
