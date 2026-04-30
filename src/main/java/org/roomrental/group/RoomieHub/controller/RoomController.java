package org.roomrental.group.RoomieHub.controller;

import org.roomrental.group.RoomieHub.dto.RoomRequestDTO;
import org.roomrental.group.RoomieHub.dto.RoomResponseDTO;
import org.roomrental.group.RoomieHub.entity.Room;
import org.roomrental.group.RoomieHub.mapper.RoomMapper;
import org.roomrental.group.RoomieHub.service.RoomService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/room")
public class RoomController {

    private final RoomService roomService;
    private final RoomMapper roomMapper;

    public RoomController(RoomService roomService, RoomMapper roomMapper) {
        this.roomService = roomService;
        this.roomMapper = roomMapper;
    }

    // CREATE
    @PostMapping
    public ResponseEntity<RoomResponseDTO> create(@RequestBody RoomRequestDTO requestDTO) {
        Room entity = roomMapper.toEntity(requestDTO);
        Room room = roomService.create(entity, requestDTO.ownerId());   // ← pass ownerId
        RoomResponseDTO dto = roomMapper.toDTO(room);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<RoomResponseDTO> update(@PathVariable Long id,
                                                  @RequestBody RoomRequestDTO requestDTO) {
        Room entity = roomMapper.toEntity(requestDTO);
        Room room = roomService.update(id, entity);
        RoomResponseDTO dto = roomMapper.toDTO(room);
        return ResponseEntity.ok(dto);
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<RoomResponseDTO> findById(@PathVariable Long id) {
        Room room = roomService.findById(id)
                .orElseThrow(() -> new RuntimeException("Room not found with id: " + id));
        RoomResponseDTO dto = roomMapper.toDTO(room);
        return ResponseEntity.ok(dto);
    }

    // GET ALL WITH PAGINATION
    @GetMapping
    public ResponseEntity<Page<RoomResponseDTO>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Room> rooms = roomService.findAllRoom(pageable);
        Page<RoomResponseDTO> dtoPage = rooms.map(roomMapper::toDTO);
        return ResponseEntity.ok(dtoPage);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        roomService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}