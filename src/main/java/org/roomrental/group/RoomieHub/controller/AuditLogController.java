package org.roomrental.group.RoomieHub.controller;

import org.roomrental.group.RoomieHub.dto.AmenityRequestDTO;
import org.roomrental.group.RoomieHub.dto.AmenityResponseDTO;
import org.roomrental.group.RoomieHub.dto.AuditLogRequestDTO;
import org.roomrental.group.RoomieHub.dto.AuditLogResponseDTO;
import org.roomrental.group.RoomieHub.entity.AdminProfile;
import org.roomrental.group.RoomieHub.entity.Amenity;
import org.roomrental.group.RoomieHub.entity.AuditLog;
import org.roomrental.group.RoomieHub.mapper.AuditLogMapper;
import org.roomrental.group.RoomieHub.repository.AuditLogRepository;
import org.roomrental.group.RoomieHub.service.AuditLogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/AuditLog")
public class AuditLogController {

    private final AuditLogService auditLogService;
    private final AuditLogMapper auditLogMapper;

    public AuditLogController(AuditLogService auditLogService,
                              AuditLogMapper auditLogMapper) {
        this.auditLogService = auditLogService;
        this.auditLogMapper = auditLogMapper;
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody AuditLogRequestDTO auditLogRequestDTO){
        AuditLog entity = auditLogMapper.toEntity(auditLogRequestDTO);
        AuditLog auditLog = auditLogService.create(entity);
        AuditLogResponseDTO dto = auditLogMapper.toDTO(auditLog);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody AuditLogRequestDTO auditLogRequestDTO, @PathVariable Long id){
        AuditLog entity = auditLogMapper.toEntity(auditLogRequestDTO);
        AuditLog update = auditLogService.update(entity, id);
        AuditLogResponseDTO dto = auditLogMapper.toDTO(update);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuditLogResponseDTO> findById(@PathVariable Long id){
        AuditLog byId = auditLogService.findById(id);
        AuditLogResponseDTO dto = auditLogMapper.toDTO(byId);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/id")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        auditLogService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
