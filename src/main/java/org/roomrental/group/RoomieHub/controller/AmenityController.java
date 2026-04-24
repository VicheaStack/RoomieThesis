package org.roomrental.group.RoomieHub.controller;

import org.roomrental.group.RoomieHub.dto.AmenityRequestDTO;
import org.roomrental.group.RoomieHub.dto.AmenityResponseDTO;
import org.roomrental.group.RoomieHub.entity.Amenity;
import org.roomrental.group.RoomieHub.mapper.AmenityMapper;
import org.roomrental.group.RoomieHub.service.AmenityService;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.DatagramSocket;

@RestController
@RequestMapping("/api/v1/Amenity")
public class AmenityController {

    private final AmenityService amenityService;
    private final AmenityMapper amenityMapper;

    public AmenityController(AmenityService amenityService,
                             AmenityMapper amenityMapper) {
        this.amenityService = amenityService;
        this.amenityMapper = amenityMapper;
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody AmenityRequestDTO amenityRequestDTO){
        Amenity entity = amenityMapper.toEntity(amenityRequestDTO);
        Amenity amenity = amenityService.create(entity);
        AmenityResponseDTO dto = amenityMapper.toDTO(amenity);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody AmenityRequestDTO amenityRequestDTO, @PathVariable Long id){
        Amenity entity = amenityMapper.toEntity(amenityRequestDTO);
        Amenity update = amenityService.update(entity, id);
        AmenityResponseDTO dto = amenityMapper.toDTO(update);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AmenityResponseDTO> findById(@PathVariable Long id){
        Amenity byId = amenityService.findById(id);
        AmenityResponseDTO dto = amenityMapper.toDTO(byId);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/id")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        amenityService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
