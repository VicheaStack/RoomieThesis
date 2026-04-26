package org.roomrental.group.RoomieHub.controller;

import org.roomrental.group.RoomieHub.dto.BookingRequestDTO;
import org.roomrental.group.RoomieHub.dto.BookingResponseDTO;
import org.roomrental.group.RoomieHub.entity.Booking;
import org.roomrental.group.RoomieHub.mapper.BookingMapper;
import org.roomrental.group.RoomieHub.service.BookingService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Pageable;


@RestController
@RequestMapping("api/v1/AuditLog")
public class BookingController {

    private final BookingMapper bookingMapper;
    private final BookingService bookingService;

    public BookingController(BookingMapper bookingMapper,
                             BookingService bookingService) {
        this.bookingMapper = bookingMapper;
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody BookingRequestDTO bookingRequestDTO){
        Booking entity = bookingMapper.toEntity(bookingRequestDTO);
        Booking booking = bookingService.create(entity);
        BookingResponseDTO dto = bookingMapper.toDTO(booking);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<?> update(@RequestBody BookingRequestDTO bookingRequestDTO, @PathVariable Long id){
        Booking entity = bookingMapper.toEntity(bookingRequestDTO);
        Booking update = bookingService.update(entity, id);
        BookingResponseDTO dto = bookingMapper.toDTO(update);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<?> findById(@PathVariable Long id){
        Booking update = bookingService.findById(id);
        BookingResponseDTO dto = bookingMapper.toDTO(update);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<?> findAll(Pageable pageable){
        Page<BookingResponseDTO> map = bookingService.findAll(pageable)
                .map(bookingMapper::toDTO);
        return ResponseEntity.ok(map);
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@PathVariable Long id){
        bookingService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
