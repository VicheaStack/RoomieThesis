package org.roomrental.group.RoomieHub.controller;

import org.roomrental.group.RoomieHub.dto.FavoriteResponseDTO;
import org.roomrental.group.RoomieHub.entity.Favorite;
import org.roomrental.group.RoomieHub.mapper.FavroiteMapper;
import org.roomrental.group.RoomieHub.service.FavoriteService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/favorites")
public class FavoriteController {

    private final FavoriteService favoriteService;
    private final FavroiteMapper favroiteMapper;

    public FavoriteController(FavoriteService favoriteService,
                              FavroiteMapper favroiteMapper) {
        this.favoriteService = favoriteService;
        this.favroiteMapper = favroiteMapper;
    }

    @PostMapping
    public ResponseEntity<Favorite> addFavorite( @PathVariable Long renterId,
                                                 @PathVariable Long roomId) {

        Favorite favorite = favoriteService.addFavorite(renterId, roomId);
        return ResponseEntity.status(HttpStatus.CREATED).body(favorite);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeFavoriteById(@PathVariable Long id) {
        favoriteService.removeFavorite(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> removeFavoriteByRenterAndRoom(
            @RequestParam Long renterId,
            @RequestParam Long roomId) {
        favoriteService.removeFavorite(renterId, roomId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<FavoriteResponseDTO> findById(@PathVariable Long id) {
        Favorite favorite = favoriteService.findById(id);
        FavoriteResponseDTO dto = favroiteMapper.toDTO(favorite);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<Page<Favorite>> findAllByRenter(
            @RequestParam Long renterId,
            @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Favorite> page = favoriteService.findAllByRenter(renterId, pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/check")
    public ResponseEntity<Boolean> isFavorited(
            @RequestParam Long renterId,
            @RequestParam Long roomId) {
        boolean favorited = favoriteService.isFavorited(renterId, roomId);
        return ResponseEntity.ok(favorited);
    }
}