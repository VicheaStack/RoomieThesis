package org.roomrental.group.RoomieHub.photos;

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
        Photo created = photoService.create(entity, photoRequestDTO.roomId());
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