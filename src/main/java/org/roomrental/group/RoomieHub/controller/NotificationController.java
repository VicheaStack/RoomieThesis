package org.roomrental.group.RoomieHub.controller;

import org.roomrental.group.RoomieHub.dto.NotificationRequestDTO;
import org.roomrental.group.RoomieHub.dto.NotificationResponseDTO;
import org.roomrental.group.RoomieHub.entity.Notification;
import org.roomrental.group.RoomieHub.mapper.NotificationMapper;
import org.roomrental.group.RoomieHub.service.NotificationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;
    private final NotificationMapper notificationMapper;

    public NotificationController(NotificationService notificationService,
                                  NotificationMapper notificationMapper) {
        this.notificationService = notificationService;
        this.notificationMapper = notificationMapper;
    }

    @PostMapping
    public ResponseEntity<NotificationResponseDTO> create(@RequestBody NotificationRequestDTO notificationRequestDTO) {
        Notification entity = notificationMapper.toEntity(notificationRequestDTO);
        Notification created = notificationService.create(entity);
        NotificationResponseDTO dto = notificationMapper.toDTO(created);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        notificationService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}