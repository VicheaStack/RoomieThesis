package org.roomrental.group.RoomieHub.controller;

import org.roomrental.group.RoomieHub.dto.PhotoRequestDTO;
import org.roomrental.group.RoomieHub.dto.PhotoResponseDTO;
import org.roomrental.group.RoomieHub.entity.Photo;
import org.roomrental.group.RoomieHub.mapper.PhotoMapper;
import org.roomrental.group.RoomieHub.service.PhotoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/photos")
public class PhotoController {

    private final PhotoService photoService;
    private final PhotoMapper photoMapper;

    public PhotoController(PhotoService photoService, PhotoMapper photoMapper) {
        this.photoService = photoService;
        this.photoMapper = photoMapper;
    }

    @PostMapping
    public ResponseEntity<PhotoResponseDTO> create(@RequestBody PhotoRequestDTO photoRequestDTO) {
        Photo entity = photoMapper.toEntity(photoRequestDTO);
        Photo created = photoService.create(entity);
        PhotoResponseDTO dto = photoMapper.toDTO(created);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Photo> update(@PathVariable Long id, @RequestBody Photo photo) {
        Photo updated = photoService.update(photo, id);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        photoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}