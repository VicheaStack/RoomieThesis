package org.roomrental.group.RoomieHub.review;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;
    private final ReviewMapper reviewMapper;

    public ReviewController(ReviewService reviewService, ReviewMapper reviewMapper) {
        this.reviewService = reviewService;
        this.reviewMapper = reviewMapper;
    }

    // CREATE
    @PostMapping
    public ResponseEntity<ReviewResponseDTO> create(@RequestBody ReviewRequestDTO requestDTO) {
        Review entity = reviewMapper.toEntity(requestDTO);
        Review review = reviewService.create(entity, requestDTO.renterId(), requestDTO.roomId(), requestDTO.bookingId());
        ReviewResponseDTO dto = reviewMapper.toDTO(review);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<ReviewResponseDTO> update(@PathVariable Long id,
                                                    @RequestBody ReviewRequestDTO requestDTO) {
        Review entity = reviewMapper.toEntity(requestDTO);
        Review review = reviewService.update(entity, id);
        ReviewResponseDTO dto = reviewMapper.toDTO(review);
        return ResponseEntity.ok(dto);
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<ReviewResponseDTO> findById(@PathVariable Long id) {
        Review review = reviewService.findById(id);
        ReviewResponseDTO dto = reviewMapper.toDTO(review);
        return ResponseEntity.ok(dto);
    }

    // GET ALL
    @GetMapping
    public ResponseEntity<Page<ReviewResponseDTO>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Review> reviews = reviewService.findAll(pageable);
        Page<ReviewResponseDTO> dtoPage = reviews.map(reviewMapper::toDTO);
        return ResponseEntity.ok(dtoPage);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        reviewService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}