package org.roomrental.group.RoomieHub.controller;

import org.roomrental.group.RoomieHub.dto.ReviewRequestDTO;
import org.roomrental.group.RoomieHub.dto.ReviewResponseDTO;
import org.roomrental.group.RoomieHub.entity.Review;
import org.roomrental.group.RoomieHub.mapper.ReviewMapper;
import org.roomrental.group.RoomieHub.service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;
    private final ReviewMapper reviewMapper;

    public ReviewController(ReviewService reviewService,
                            ReviewMapper reviewMapper) {
        this.reviewService = reviewService;
        this.reviewMapper = reviewMapper;
    }

    @PostMapping
    public ResponseEntity<ReviewResponseDTO> create(@RequestBody ReviewRequestDTO review) {
        Review entity = reviewMapper.toEntity(review);
        Review created = reviewService.create(entity);
        ReviewResponseDTO dto = reviewMapper.toDTO(created);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReviewResponseDTO> update(@PathVariable Long id, @RequestBody ReviewRequestDTO review) {
        Review entity = reviewMapper.toEntity(review);
        Review updated = reviewService.update(entity, id);
        ReviewResponseDTO dto = reviewMapper.toDTO(updated);
        return ResponseEntity.ok(dto);
    }
}