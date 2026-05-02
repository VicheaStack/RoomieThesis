package org.roomrental.group.RoomieHub.ownerProfile;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/owner-profiles")
@RequiredArgsConstructor
public class OwnerProfileController {

    private final OwnerProfileService ownerProfileService;
    private final OwnerProfileMapper ownerProfileMapper;

    // CREATE
    @PostMapping("/{userId}")
    public ResponseEntity<OwnerProfileResponseDTO> create(@PathVariable Long userId) {
        OwnerProfile profile = ownerProfileService.create(userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(ownerProfileMapper.toDto(profile));
    }

    // RATE OWNER
    @PostMapping("/{userId}/rate")
    public ResponseEntity<OwnerProfileResponseDTO> addRate(
            @PathVariable Long userId,
            @RequestBody OwnerProfileRequestDTO dto) {

        OwnerProfile updatedProfile = ownerProfileService.addRate(userId, dto.totalListings());
        return ResponseEntity.ok(ownerProfileMapper.toDto(updatedProfile));
    }

    // UPDATE
    @PutMapping("/{userId}")
    public ResponseEntity<OwnerProfileResponseDTO> update(
            @PathVariable Long userId,
            @RequestBody OwnerProfileRequestDTO dto) {

        OwnerProfile entity = ownerProfileMapper.toEntity(dto);
        OwnerProfile updated = ownerProfileService.update(entity, userId);
        return ResponseEntity.ok(ownerProfileMapper.toDto(updated));
    }

    // GET BY ID
    @GetMapping("/{userId}")
    public ResponseEntity<OwnerProfileResponseDTO> findById(@PathVariable Long userId) {
        OwnerProfile profile = ownerProfileService.findById(userId);
        return ResponseEntity.ok(ownerProfileMapper.toDto(profile));
    }

    // GET ALL
    @GetMapping
    public ResponseEntity<Page<OwnerProfileResponseDTO>> findAll(
            @PageableDefault(size = 20, sort = "userId") Pageable pageable) {

        Page<OwnerProfile> page = ownerProfileService.findAll(pageable);
        return ResponseEntity.ok(page.map(ownerProfileMapper::toDto));
    }

    // DELETE
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteById(@PathVariable Long userId) {
        ownerProfileService.deleteById(userId);
        return ResponseEntity.noContent().build();
    }
}