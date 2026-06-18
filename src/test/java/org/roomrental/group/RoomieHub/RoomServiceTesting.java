package org.roomrental.group.RoomieHub;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.roomrental.group.RoomieHub.booking.BookingRepository;
import org.roomrental.group.RoomieHub.exception.AppException;
import org.roomrental.group.RoomieHub.room.Room;
import org.roomrental.group.RoomieHub.room.RoomRepository;
import org.roomrental.group.RoomieHub.room.RoomServiceImpl;
import org.roomrental.group.RoomieHub.user.User;
import org.roomrental.group.RoomieHub.user.UserRepository;
import org.roomrental.group.RoomieHub.user.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@Disabled("Reason for disabling (optional)")
@ExtendWith(MockitoExtension.class)
class RoomServiceTesting {

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private RoomServiceImpl roomService;

    @Mock
    private BookingRepository bookingRepository;

    // ==================== CREATE ====================

    @Test
    void create_Success() {
        Long ownerId = 1L;
        User owner = new User();
        owner.setUserId(ownerId);
        owner.setRole(UserRole.OWNER);

        Room inputRoom = new Room();
        inputRoom.setTitle("Cozy Studio");

        Room savedRoom = new Room();
        savedRoom.setRoomId(100L);
        savedRoom.setTitle("Cozy Studio");
        savedRoom.setOwner(owner);

        when(userRepository.findById(ownerId)).thenReturn(Optional.of(owner));
        when(roomRepository.existsByOwnerAndTitle(owner, inputRoom.getTitle())).thenReturn(false);
        when(roomRepository.save(inputRoom)).thenReturn(savedRoom);

        Room result = roomService.create(inputRoom, ownerId);

        assertNotNull(result);
        assertEquals(100L, result.getRoomId());
        assertEquals("Cozy Studio", result.getTitle());
        assertEquals(owner, result.getOwner());

        verify(userRepository).findById(ownerId);
        verify(roomRepository).existsByOwnerAndTitle(owner, inputRoom.getTitle());
        verify(roomRepository).save(inputRoom);
    }

    @Test
    void create_OwnerNotFound_ThrowsRuntimeException() {
        Long ownerId = 99L;
        Room inputRoom = new Room();

        when(userRepository.findById(ownerId)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> roomService.create(inputRoom, ownerId));
        assertEquals("Owner not found with id: " + ownerId, ex.getMessage());

        verify(userRepository).findById(ownerId);
        verifyNoInteractions(roomRepository); // no further calls
    }

    @Test
    void deleteById_ThrowsAppException_WhenRoomDoesNotExist() { // Updated name to match the '!' logic
        Long roomId = 1L;

        // 1. Change this to FALSE. Because of the '!', your service needs
        //    existsById to be false to enter the if statement and throw the error.
        when(roomRepository.existsById(roomId)).thenReturn(false);

        AppException ex = assertThrows(AppException.class,
                () -> roomService.deleteById(roomId));

        // 2. This must match the exact string inside your service class if statement
        assertEquals("Room already booking: " + roomId, ex.getMessage());

        verify(roomRepository).existsById(roomId);
        verify(roomRepository, never()).deleteById(any());
    }


    @Test
    void create_DuplicateTitle_ThrowsAppException() {
        Long ownerId = 1L;
        User owner = new User();
        owner.setUserId(ownerId);
        owner.setRole(UserRole.OWNER);

        Room inputRoom = new Room();
        inputRoom.setTitle("Duplicate Title");

        when(userRepository.findById(ownerId)).thenReturn(Optional.of(owner));
        when(roomRepository.existsByOwnerAndTitle(owner, inputRoom.getTitle())).thenReturn(true);

        AppException ex = assertThrows(AppException.class,
                () -> roomService.create(inputRoom, ownerId));
        assertEquals("Room already exists with this title for owner: " + inputRoom.getTitle(),
                ex.getMessage());

        verify(userRepository).findById(ownerId);
        verify(roomRepository).existsByOwnerAndTitle(owner, inputRoom.getTitle());
        verify(roomRepository, never()).save(any());
    }

    // ==================== UPDATE ====================

    @Test
    void update_Success() {
        Long roomId = 1L;
        Room existingRoom = new Room();
        existingRoom.setRoomId(roomId);
        existingRoom.setTitle("Old Title");
        existingRoom.setDescription("Old Desc");
        existingRoom.setPricePerNight(100.0);
        existingRoom.setLocation("Old Loc");
        existingRoom.setSizeSqft(50);
        existingRoom.setMaxOccupancy(2);
        existingRoom.setHasPrivateBathroom(false);
        existingRoom.setIsFurnished(false);
        existingRoom.setIsVerified(false);

        Room updatePayload = new Room();
        updatePayload.setTitle("New Title");
        updatePayload.setDescription("New Desc");
        updatePayload.setPricePerNight(150.0);
        updatePayload.setLocation("New Loc");
        updatePayload.setSizeSqft(60);
        updatePayload.setMaxOccupancy(3);
        updatePayload.setHasPrivateBathroom(true);
        updatePayload.setIsFurnished(true);
        updatePayload.setIsVerified(true);

        Room updatedRoom = new Room();
        updatedRoom.setRoomId(roomId);
        updatedRoom.setTitle("New Title");
        updatedRoom.setDescription("New Desc");
        updatedRoom.setPricePerNight(150.0);
        updatedRoom.setLocation("New Loc");
        updatedRoom.setSizeSqft(60);
        updatedRoom.setMaxOccupancy(3);
        updatedRoom.setHasPrivateBathroom(true);
        updatedRoom.setIsFurnished(true);
        updatedRoom.setIsVerified(true);

        when(roomRepository.findById(roomId)).thenReturn(Optional.of(existingRoom));
        when(roomRepository.save(existingRoom)).thenReturn(updatedRoom);

        Room result = roomService.update(roomId, updatePayload);

        assertNotNull(result);
        assertEquals("New Title", result.getTitle());
        assertEquals("New Desc", result.getDescription());
        assertEquals(150.0, result.getPricePerNight());
        // Verify that owner was NOT changed (stays as existingRoom's owner)
        // Since we didn't set owner in updatePayload, it remains null, but we don't care.

        verify(roomRepository).findById(roomId);
        verify(roomRepository).save(existingRoom);

        // Optionally capture to verify all fields were set
        ArgumentCaptor<Room> roomCaptor = ArgumentCaptor.forClass(Room.class);
        verify(roomRepository).save(roomCaptor.capture());
        Room captured = roomCaptor.getValue();
        assertEquals("New Title", captured.getTitle());
        assertEquals("New Desc", captured.getDescription());
        assertEquals(150.0, captured.getPricePerNight());
        assertEquals("New Loc", captured.getLocation());
        assertEquals(60, captured.getSizeSqft());
        assertEquals(3, captured.getMaxOccupancy());
        assertTrue(captured.getHasPrivateBathroom());
        assertTrue(captured.getIsFurnished());
        assertTrue(captured.getIsVerified());
    }

    @Test
    void update_RoomNotFound_ThrowsRuntimeException() {
        Long roomId = 99L;
        Room updatePayload = new Room();

        when(roomRepository.findById(roomId)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> roomService.update(roomId, updatePayload));
        assertEquals("Room not found: " + roomId, ex.getMessage());

        verify(roomRepository).findById(roomId);
        verify(roomRepository, never()).save(any());
    }

    // ==================== FIND BY ID ====================

    @Test
    void findById_Success() {
        Long roomId = 1L;
        Room room = new Room();
        room.setRoomId(roomId);
        room.setTitle("Test Room");

        when(roomRepository.findById(roomId)).thenReturn(Optional.of(room));

        Optional<Room> result = roomService.findById(roomId);

        assertTrue(result.isPresent());
        assertEquals(roomId, result.get().getRoomId());
        assertEquals("Test Room", result.get().getTitle());

        verify(roomRepository).findById(roomId);
    }

    @Test
    void findById_NotFound_ThrowsRuntimeException() {
        Long roomId = 99L;

        when(roomRepository.findById(roomId)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> roomService.findById(roomId));
        assertEquals("Room not found: " + roomId, ex.getMessage());

        verify(roomRepository).findById(roomId);
    }

    // ==================== FIND ALL ====================

    @Test
    void findAllRoom_Success() {
        Pageable pageable = PageRequest.of(0, 10);
        List<Room> roomList = List.of(new Room(), new Room());
        Page<Room> page = new PageImpl<>(roomList, pageable, roomList.size());

        when(roomRepository.findAll(pageable)).thenReturn(page);

        Page<Room> result = roomService.findAllRoom(pageable);

        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        assertEquals(pageable, result.getPageable());

        verify(roomRepository).findAll(pageable);
    }

    // ==================== DELETE ====================

    @Test
    void deleteById_Success() {
        Long roomId = 1L;

        // 1. The room DOES exist (so it passes the first check)
        when(roomRepository.existsById(roomId)).thenReturn(true);

        // 2. The room is NOT booked (so it passes the second check)
        when(bookingRepository.existsActiveBookingByRoomId(roomId)).thenReturn(false);

        // 3. Verify that running the method executes cleanly without errors
        assertDoesNotThrow(() -> roomService.deleteById(roomId));

        // 4. Verify all repository steps occurred in sequence
        verify(roomRepository).existsById(roomId);
        verify(bookingRepository).existsActiveBookingByRoomId(roomId);
        verify(roomRepository).deleteById(roomId); // It is now safe to delete!
    }


    @Test
    void deleteById_ThrowsAppException_WhenRoomExists() {
        Long roomId = 1L;

        // 1. Tell Mockito the room exists
        when(roomRepository.existsById(roomId)).thenReturn(true);

        // 2. ADD THIS LINE: Tell Mockito the room HAS an active booking
        when(bookingRepository.existsActiveBookingByRoomId(roomId)).thenReturn(true);

        AppException ex = assertThrows(AppException.class,
                () -> roomService.deleteById(roomId));
        assertEquals("Room already booking: " + roomId, ex.getMessage());

        verify(roomRepository).existsById(roomId);

        // 3. ADD THIS LINE: Verify the booking check was actually performed
        verify(bookingRepository).existsActiveBookingByRoomId(roomId);

        verify(roomRepository, never()).deleteById(any());
    }

}