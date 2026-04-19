package org.roomrental.group.RoomieHub.serviceImpl;

import lombok.extern.slf4j.Slf4j;
import org.roomrental.group.RoomieHub.entity.AuditLog;
import org.roomrental.group.RoomieHub.repository.AuditLogRepository;
import org.roomrental.group.RoomieHub.service.AuditLogService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
public class AuditLogServiceImpl implements AuditLogService {

    private final AuditLogRepository auditLogRepository;

    public AuditLogServiceImpl(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    @Override
    public AuditLog create(AuditLog auditLog) {
        AuditLog save = auditLogRepository.save(auditLog);
        log.info("AuditLog Created Successfully");
        return save;
    }

    @Override
    public AuditLog update(AuditLog auditLog, Long id) {
        AuditLog auditLogNotFound = auditLogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("AuditLog Not Found"));

        auditLogNotFound.setAction(auditLog.getAction());
        auditLogNotFound.setEntityType(auditLog.getEntityType());
        auditLogNotFound.setEntityId(auditLog.getEntityId());
        auditLogNotFound.setOldValues(auditLog.getOldValues());
        auditLogNotFound.setNewValues(auditLog.getNewValues());
        auditLogNotFound.setUserAgent(auditLog.getUserAgent());

        AuditLog save = auditLogRepository.save(auditLog);
        log.info("AuditLog Updated Successfully");

        return save;
    }

    @Override
    public AuditLog findById(Long id) {
        AuditLog auditLogNotFound = auditLogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("AuditLog Not Found"));
        log.info("Finding AuditLog By Id Successfully");
        return auditLogNotFound;
    }

    @Override
    public Page<AuditLog> findAll(Pageable pageable) {
        log.debug("Finding All AuditLogs Successfully with pagination: page {}, size {} ",
                pageable.getPageNumber(),  pageable.getPageSize());
        return auditLogRepository.findAll(pageable);
    }

    @Override
    public void deleteById(Long id) {
        if(!auditLogRepository.existsById(id)) {
            throw new RuntimeException("AuditLog Not Found id: " + id);
        }
        auditLogRepository.deleteById(id);
        log.info("AuditLog Deleted Successfully {} ", id);
    }
}
