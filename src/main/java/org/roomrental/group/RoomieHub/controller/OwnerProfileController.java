package org.roomrental.group.RoomieHub.controller;

import org.roomrental.group.RoomieHub.dto.OwnerProfileRequestDTO;
import org.roomrental.group.RoomieHub.dto.OwnerProfileResponseDTO;
import org.roomrental.group.RoomieHub.entity.OwnerProfile;
import org.roomrental.group.RoomieHub.mapper.OwnerProfileMapper;
import org.roomrental.group.RoomieHub.service.OwnerProfileService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/owner-profiles")
@RequiredArgsConstructor
public class OwnerProfileController {

    private final OwnerProfileService ownerProfileService;
    private final OwnerProfileMapper ownerProfileMapper;

    @PostMapping("/{userId}/rate")
    public ResponseEntity<OwnerProfileResponseDTO> addRate(
            @PathVariable Long userId,
            @RequestBody OwnerProfileRequestDTO dto) {

        OwnerProfile updatedProfile = ownerProfileService.addRate(userId, dto.totalListings());
        return ResponseEntity.ok(ownerProfileMapper.toDto(updatedProfile));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<OwnerProfileResponseDTO> update(
            @PathVariable Long userId,
            @RequestBody OwnerProfileRequestDTO dto) {

        OwnerProfile entity = ownerProfileMapper.toEntity(dto);
        OwnerProfile updated = ownerProfileService.update(entity, userId);
        return ResponseEntity.ok(ownerProfileMapper.toDto(updated));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<OwnerProfileResponseDTO> findById(@PathVariable Long userId) {
        OwnerProfile profile = ownerProfileService.findById(userId);
        return ResponseEntity.ok(ownerProfileMapper.toDto(profile));
    }

    @GetMapping
    public ResponseEntity<Page<OwnerProfileResponseDTO>> findAll(
            @PageableDefault(size = 20, sort = "userId") Pageable pageable) {

        Page<OwnerProfile> page = ownerProfileService.findAll(pageable);
        return ResponseEntity.ok(page.map(ownerProfileMapper::toDto));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteById(@PathVariable Long userId) {
        ownerProfileService.deleteById(userId);
        return ResponseEntity.noContent().build();
    }
}