package org.roomrental.group.RoomieHub;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.roomrental.group.RoomieHub.exception.AppException;
import org.roomrental.group.RoomieHub.ownerProfile.OwnerProfile;
import org.roomrental.group.RoomieHub.ownerProfile.OwnerProfileRepository;
import org.roomrental.group.RoomieHub.ownerProfile.OwnerProfileServiceImpl;
import org.roomrental.group.RoomieHub.user.User;
import org.roomrental.group.RoomieHub.user.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@Disabled("Reason for disabling (optional)")
@ExtendWith(MockitoExtension.class)
class OwnerProfileServiceImplTest {

    @Mock
    private OwnerProfileRepository ownerProfileRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private OwnerProfileServiceImpl ownerProfileService;

    // ==================== CREATE ====================

    @Test
    void create_Success_WhenUserExistsAndProfileDoesNotExist() {
        Long userId = 1L;
        User user = new User();
        user.setUserId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(ownerProfileRepository.existsById(userId)).thenReturn(false);
        when(ownerProfileRepository.save(any(OwnerProfile.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        OwnerProfile result = ownerProfileService.create(userId);

        assertNotNull(result);
        assertEquals(0, result.getTotalListings());
        assertEquals(0, result.getRatingCount());
        assertEquals(0.0, result.getTotalRating());

        ArgumentCaptor<OwnerProfile> captor = ArgumentCaptor.forClass(OwnerProfile.class);
        verify(ownerProfileRepository).save(captor.capture());
        assertEquals(user, captor.getValue().getUser());

        verify(userRepository).findById(userId);
        verify(ownerProfileRepository).existsById(userId);
    }

    @Test
    void create_UserNotFound_ThrowsRuntimeException() {
        Long userId = 99L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> ownerProfileService.create(userId));
        assertEquals("User not found: " + userId, ex.getMessage());

        verify(userRepository).findById(userId);
        verify(ownerProfileRepository, never()).existsById(any());
        verify(ownerProfileRepository, never()).save(any());
    }

    @Test
    void create_ProfileAlreadyExists_ThrowsAppException() {
        Long userId = 1L;
        User user = new User();
        user.setUserId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(ownerProfileRepository.existsById(userId)).thenReturn(true);

        AppException ex = assertThrows(AppException.class,
                () -> ownerProfileService.create(userId));
        assertEquals("OwnerProfile already exists for user: " + userId, ex.getMessage());

        verify(userRepository).findById(userId);
        verify(ownerProfileRepository).existsById(userId);
        verify(ownerProfileRepository, never()).save(any());
    }

    // ==================== ADD RATE ====================

    @Test
    void addRate_ProfileExists_UpdatesRating() {
        Long userId = 1L;
        double newRating = 4.5;

        OwnerProfile existingProfile = new OwnerProfile();
        existingProfile.setRatingCount(5);
        existingProfile.setTotalRating(20.0);

        when(ownerProfileRepository.findById(userId)).thenReturn(Optional.of(existingProfile));
        when(ownerProfileRepository.save(existingProfile)).thenReturn(existingProfile);

        OwnerProfile result = ownerProfileService.addRate(userId, newRating);

        assertNotNull(result);
        assertEquals(6, existingProfile.getRatingCount());
        assertEquals(24.5, existingProfile.getTotalRating());

        verify(ownerProfileRepository).findById(userId);
        verify(ownerProfileRepository).save(existingProfile);
        verify(userRepository, never()).findById(any());
    }

    @Test
    void addRate_ProfileDoesNotExist_UserExists_CreatesAndAddsRating() {
        Long userId = 1L;
        double newRating = 3.0;

        User user = new User();
        user.setUserId(userId);

        when(ownerProfileRepository.findById(userId)).thenReturn(Optional.empty());
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Capture ratingCount at the time of each save
        List<Integer> ratingCounts = new ArrayList<>();
        when(ownerProfileRepository.save(any(OwnerProfile.class)))
                .thenAnswer(invocation -> {
                    OwnerProfile arg = invocation.getArgument(0);
                    ratingCounts.add(arg.getRatingCount()); // save current value
                    return arg; // no modification
                });

        OwnerProfile result = ownerProfileService.addRate(userId, newRating);

        // Final state after adding rating
        assertNotNull(result);
        assertEquals(1, result.getRatingCount());
        assertEquals(newRating, result.getTotalRating());

        // Verify two saves occurred
        verify(ownerProfileRepository, times(2)).save(any(OwnerProfile.class));

        // Assert ratingCount at each save
        assertEquals(2, ratingCounts.size());
        assertEquals(0, ratingCounts.get(0).intValue()); // first save: creation
        assertEquals(1, ratingCounts.get(1).intValue()); // second save: update

        // Optional: also verify user and default fields if needed
        // (you can capture the actual objects if you copy them, but this is enough)
    }

    @Test
    void addRate_ProfileDoesNotExist_UserNotFound_ThrowsRuntimeException() {
        Long userId = 99L;
        when(ownerProfileRepository.findById(userId)).thenReturn(Optional.empty());
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> ownerProfileService.addRate(userId, 4.0));
        assertEquals("User not found: " + userId, ex.getMessage());

        verify(ownerProfileRepository).findById(userId);
        verify(userRepository).findById(userId);
        verify(ownerProfileRepository, never()).save(any());
    }

    // ==================== UPDATE ====================

    @Test
    void update_Success() {
        Long userId = 1L;
        OwnerProfile existing = new OwnerProfile();
        existing.setTotalListings(5);

        OwnerProfile updatePayload = new OwnerProfile();
        updatePayload.setTotalListings(10);

        when(ownerProfileRepository.findById(userId)).thenReturn(Optional.of(existing));
        when(ownerProfileRepository.save(existing)).thenReturn(existing);

        OwnerProfile result = ownerProfileService.update(updatePayload, userId);

        assertNotNull(result);
        assertEquals(10, existing.getTotalListings());

        verify(ownerProfileRepository).findById(userId);
        verify(ownerProfileRepository).save(existing);
    }

    @Test
    void update_ProfileNotFound_ThrowsRuntimeException() {
        Long userId = 99L;
        when(ownerProfileRepository.findById(userId)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> ownerProfileService.update(new OwnerProfile(), userId));
        assertEquals("OwnerProfile not found for user: " + userId, ex.getMessage());

        verify(ownerProfileRepository).findById(userId);
        verify(ownerProfileRepository, never()).save(any());
    }

    // ==================== FIND BY ID ====================

    @Test
    void findById_Success() {
        Long userId = 1L;
        OwnerProfile profile = new OwnerProfile();
        profile.setTotalListings(5);

        when(ownerProfileRepository.findById(userId)).thenReturn(Optional.of(profile));

        OwnerProfile result = ownerProfileService.findById(userId);

        assertNotNull(result);
        assertEquals(5, result.getTotalListings());

        verify(ownerProfileRepository).findById(userId);
    }

    @Test
    void findById_NotFound_ThrowsRuntimeException() {
        Long userId = 99L;
        when(ownerProfileRepository.findById(userId)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> ownerProfileService.findById(userId));
        assertEquals("OwnerProfile not found for user: " + userId, ex.getMessage());

        verify(ownerProfileRepository).findById(userId);
    }

    // ==================== FIND ALL ====================

    @Test
    void findAll_Success() {
        Pageable pageable = PageRequest.of(0, 10);
        List<OwnerProfile> profiles = List.of(new OwnerProfile(), new OwnerProfile());
        Page<OwnerProfile> page = new PageImpl<>(profiles, pageable, profiles.size());

        when(ownerProfileRepository.findAll(pageable)).thenReturn(page);

        Page<OwnerProfile> result = ownerProfileService.findAll(pageable);

        assertNotNull(result);
        assertEquals(2, result.getTotalElements());

        verify(ownerProfileRepository).findAll(pageable);
    }

    // ==================== DELETE ====================

    @Test
    void deleteById_Success_WhenProfileExists() {
        Long userId = 1L;
        when(ownerProfileRepository.existsById(userId)).thenReturn(true);

        assertDoesNotThrow(() -> ownerProfileService.deleteById(userId));

        verify(ownerProfileRepository).existsById(userId);
        verify(ownerProfileRepository).deleteById(userId);
    }

    @Test
    void deleteById_ThrowsAppException_WhenProfileNotFound() {
        Long userId = 99L;
        when(ownerProfileRepository.existsById(userId)).thenReturn(false);

        AppException ex = assertThrows(AppException.class,
                () -> ownerProfileService.deleteById(userId));
        assertEquals("OwnerProfile not found", ex.getMessage());

        verify(ownerProfileRepository).existsById(userId);
        verify(ownerProfileRepository, never()).deleteById(any());
    }
}