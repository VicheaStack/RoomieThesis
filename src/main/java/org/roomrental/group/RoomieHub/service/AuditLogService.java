package org.roomrental.group.RoomieHub.service;

import org.roomrental.group.RoomieHub.entity.AuditLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface AuditLogService {

    AuditLog create(AuditLog auditLog);
    AuditLog update(AuditLog auditLog, Long id);
    AuditLog findById(Long id);
    Page<AuditLog> findAll(Pageable pageable);
    void deleteById(Long id);
}
