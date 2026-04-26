package org.roomrental.group.RoomieHub.controller;

import org.roomrental.group.RoomieHub.entity.Review;
import org.roomrental.group.RoomieHub.service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    // Create a new review
    @PostMapping
    public ResponseEntity<Review> create(@RequestBody Review review) {
        Review created = reviewService.create(review);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // Update an existing review
    @PutMapping("/{id}")
    public ResponseEntity<Review> update(@PathVariable Long id, @RequestBody Review review) {
        Review updated = reviewService.update(review, id);
        return ResponseEntity.ok(updated);
    }
}