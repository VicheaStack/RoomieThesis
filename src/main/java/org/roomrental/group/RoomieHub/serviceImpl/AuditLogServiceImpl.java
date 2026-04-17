package org.roomrental.group.RoomieHub.serviceImpl;

import org.roomrental.group.RoomieHub.entity.AuditLog;
import org.roomrental.group.RoomieHub.service.AuditLogService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class AuditLogServiceImpl implements AuditLogService {
    @Override
    public AuditLog create(AuditLog auditLog) {
        return null;
    }

    @Override
    public AuditLog update(AuditLog auditLog, Long id) {
        return null;
    }

    @Override
    public AuditLog findById(Long id) {
        return null;
    }

    @Override
    public Page<AuditLog> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
