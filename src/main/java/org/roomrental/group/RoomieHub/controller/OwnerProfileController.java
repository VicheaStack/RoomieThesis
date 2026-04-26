package org.roomrental.group.RoomieHub.controller;

import org.roomrental.group.RoomieHub.entity.OwnerProfile;
import org.roomrental.group.RoomieHub.service.OwnerProfileService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/owner-profiles")
public class OwnerProfileController {

    private final OwnerProfileService ownerProfileService;

    public OwnerProfileController(OwnerProfileService ownerProfileService) {
        this.ownerProfileService = ownerProfileService;
    }

    @PostMapping("/{ownerId}/rate")
    public ResponseEntity<OwnerProfile> addRate(
            @PathVariable Long ownerId,
            @RequestParam double rating) {
        OwnerProfile updated = ownerProfileService.addRate(ownerId, rating);
        return ResponseEntity.ok(updated);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OwnerProfile> update(
            @PathVariable Long id,
            @RequestBody OwnerProfile ownerProfile) {
        OwnerProfile updated = ownerProfileService.update(ownerProfile, id);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OwnerProfile> findById(@PathVariable Long id) {
        OwnerProfile profile = ownerProfileService.findById(id);
        return ResponseEntity.ok(profile);
    }

    @GetMapping
    public ResponseEntity<Page<OwnerProfile>> findAll(
            @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<OwnerProfile> page = ownerProfileService.findAll(pageable);
        return ResponseEntity.ok(page);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        ownerProfileService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}