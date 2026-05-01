package org.roomrental.group.RoomieHub.auditLog;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AuditLogMapper {

    // Entity → DTO
    @Mapping(target = "userId", source = "user.userId")
    AuditLogResponseDTO toDTO(AuditLog auditLog);

    // DTO → Entity
    @Mapping(target = "ipAddress", ignore = true)
    @Mapping(target = "user", ignore = true) // set manually in service
    @Mapping(target = "logId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    AuditLog toEntity(AuditLogRequestDTO auditLogRequestDTO);
}