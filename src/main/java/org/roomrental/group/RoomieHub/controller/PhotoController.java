package org.roomrental.group.RoomieHub.controller;

import org.roomrental.group.RoomieHub.entity.Photo;
import org.roomrental.group.RoomieHub.service.PhotoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/photos")
public class PhotoController {

    private final PhotoService photoService;

    public PhotoController(PhotoService photoService) {
        this.photoService = photoService;
    }

    // Create a new photo
    @PostMapping
    public ResponseEntity<Photo> create(@RequestBody Photo photo) {
        Photo created = photoService.create(photo);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // Update an existing photo
    @PutMapping("/{id}")
    public ResponseEntity<Photo> update(@PathVariable Long id, @RequestBody Photo photo) {
        Photo updated = photoService.update(photo, id);
        return ResponseEntity.ok(updated);
    }

    // Delete a photo by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        photoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}