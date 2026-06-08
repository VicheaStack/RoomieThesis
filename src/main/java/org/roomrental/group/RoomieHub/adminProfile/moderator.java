package org.roomrental.group.RoomieHub.adminProfile;

import org.roomrental.group.RoomieHub.ownerProfile.OwnerProfileRepository;
import org.roomrental.group.RoomieHub.ownerProfile.OwnerProfileService;
import org.roomrental.group.RoomieHub.room.Room;
import org.roomrental.group.RoomieHub.room.RoomMapper;
import org.roomrental.group.RoomieHub.room.RoomResponseDTO;
import org.roomrental.group.RoomieHub.room.RoomService;
import org.roomrental.group.RoomieHub.user.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping
@RestController("/Moderator")
public class moderator {

    private final OwnerProfileService ownerProfileService;
    private final RoomService roomService;
    private final RoomMapper roomMapper;

    public moderator(OwnerProfileService ownerProfileService,
                     RoomService roomService,
                     RoomMapper roomMapper) {
        this.ownerProfileService = ownerProfileService;
        this.roomService = roomService;
        this.roomMapper = roomMapper;
    }

    // find room by id
    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<RoomResponseDTO> findById(@PathVariable Long id) {
        Room room = roomService.findById(id)
                .orElseThrow(() -> new RuntimeException("Room not found with id: " + id));
        RoomResponseDTO dto = roomMapper.toDTO(room);
        return ResponseEntity.ok(dto);
    }

    // seach all room user
    // GET ALL WITH PAGINATION
    @GetMapping("/getAll")
    public ResponseEntity<Page<RoomResponseDTO>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Room> rooms = roomService.findAllRoom(pageable);
        Page<RoomResponseDTO> dtoPage = rooms.map(roomMapper::toDTO);
        return ResponseEntity.ok(dtoPage);
    }

    // DELETE Room user if something gone wrong
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        roomService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
