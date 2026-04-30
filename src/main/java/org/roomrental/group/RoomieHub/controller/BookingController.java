package org.roomrental.group.RoomieHub.controller;

import org.roomrental.group.RoomieHub.dto.BookingRequestDTO;
import org.roomrental.group.RoomieHub.dto.BookingResponseDTO;
import org.roomrental.group.RoomieHub.entity.Booking;
import org.roomrental.group.RoomieHub.mapper.BookingMapper;
import org.roomrental.group.RoomieHub.service.BookingService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/bookings")   // use lower-case, plural for REST
public class BookingController {

    private final BookingMapper bookingMapper;
    private final BookingService bookingService;

    public BookingController(BookingMapper bookingMapper,
                             BookingService bookingService) {
        this.bookingMapper = bookingMapper;
        this.bookingService = bookingService;
    }

    // Create a new booking
    @PostMapping
    public ResponseEntity<BookingResponseDTO> create(@RequestBody BookingRequestDTO requestDTO) {
        Booking entity = bookingMapper.toEntity(requestDTO);
        Booking booking = bookingService.create(entity, requestDTO.roomId(), requestDTO.renterId());  // ← pass IDs
        BookingResponseDTO dto = bookingMapper.toDTO(booking);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    // Update an existing booking
    @PutMapping("/{id}")
    public ResponseEntity<BookingResponseDTO> update(@PathVariable Long id,
                                                     @RequestBody BookingRequestDTO dto) {
        Booking entity = bookingMapper.toEntity(dto);
        Booking updated = bookingService.update(entity, id);
        return ResponseEntity.ok(bookingMapper.toDTO(updated));
    }

    // Get a booking by ID
    @GetMapping("/{id}")
    public ResponseEntity<BookingResponseDTO> findById(@PathVariable Long id) {
        Booking booking = bookingService.findById(id);
        return ResponseEntity.ok(bookingMapper.toDTO(booking));
    }

    // Get all bookings (with pagination)
    @GetMapping
    public ResponseEntity<Page<BookingResponseDTO>> findAll(Pageable pageable) {
        Page<BookingResponseDTO> page = bookingService.findAll(pageable)
                .map(bookingMapper::toDTO);
        return ResponseEntity.ok(page);
    }

    // Delete a booking
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        bookingService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}