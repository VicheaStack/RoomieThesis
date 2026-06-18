package org.roomrental.group.RoomieHub;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.roomrental.group.RoomieHub.exception.AppException;
import org.roomrental.group.RoomieHub.user.User;
import org.roomrental.group.RoomieHub.user.UserRepository;
import org.roomrental.group.RoomieHub.user.UserRole;
import org.roomrental.group.RoomieHub.user.UserServiceImpl;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@Disabled("Reason for disabling (optional)")
@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    // ==================== CREATE ====================

    @Test
    void create_Success() {
        User user = new User();
        user.setEmail("john@example.com");
        user.setPhoneNumber("+1234567890");
        user.setPassword("secret123");
        user.setFirstName("John");
        user.setLastName("Doe");

        UserRole role = UserRole.OWNER;

        User savedUser = new User();
        savedUser.setUserId(1L);
        savedUser.setEmail(user.getEmail());
        savedUser.setRole(role);

        when(userRepository.existsByEmail(user.getEmail())).thenReturn(false);
        when(userRepository.existsByPhoneNumber(user.getPhoneNumber())).thenReturn(false);
        when(userRepository.save(user)).thenReturn(savedUser);

        User result = userService.create(user, role);

        assertNotNull(result);
        assertEquals(1L, result.getUserId());
        assertEquals(role, result.getRole());
        assertEquals(user.getEmail(), result.getEmail());

        // verify role was set before save
        assertEquals(role, user.getRole());

        verify(userRepository).existsByEmail(user.getEmail());
        verify(userRepository).existsByPhoneNumber(user.getPhoneNumber());
        verify(userRepository).save(user);
    }

    @Test
    void create_PasswordBlank_ThrowsIllegalArgumentException() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword(""); // blank

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> userService.create(user, UserRole.RENTER));
        assertEquals("Password cannot be empty", ex.getMessage());

        verifyNoInteractions(userRepository);
    }

    @Test
    void create_PasswordNull_ThrowsIllegalArgumentException() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword(null);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> userService.create(user, UserRole.RENTER));
        assertEquals("Password cannot be empty", ex.getMessage());

        verifyNoInteractions(userRepository);
    }

    @Test
    void create_EmailAlreadyExists_ThrowsAppException() {
        User user = new User();
        user.setEmail("duplicate@example.com");
        user.setPhoneNumber("+1234567890");
        user.setPassword("secret");

        when(userRepository.existsByEmail(user.getEmail())).thenReturn(true);

        AppException ex = assertThrows(AppException.class,
                () -> userService.create(user, UserRole.RENTER));
        assertEquals("User with email " + user.getEmail() + " already exists", ex.getMessage());

        verify(userRepository).existsByEmail(user.getEmail());
        verify(userRepository, never()).existsByPhoneNumber(any());
        verify(userRepository, never()).save(any());
    }

    @Test
    void create_PhoneAlreadyExists_ThrowsAppException() {
        User user = new User();
        user.setEmail("unique@example.com");
        user.setPhoneNumber("+1234567890");
        user.setPassword("secret");

        when(userRepository.existsByEmail(user.getEmail())).thenReturn(false);
        when(userRepository.existsByPhoneNumber(user.getPhoneNumber())).thenReturn(true);

        AppException ex = assertThrows(AppException.class,
                () -> userService.create(user, UserRole.RENTER));
        assertEquals("User with phone number " + user.getPhoneNumber() + " already exists", ex.getMessage());

        verify(userRepository).existsByEmail(user.getEmail());
        verify(userRepository).existsByPhoneNumber(user.getPhoneNumber());
        verify(userRepository, never()).save(any());
    }

    // ==================== PROCESS OAUTH USER ====================

    @Test
    void processOAuthUser_ExistingByProviderAndProviderId_UpdatesAndReturns() {
        String registrationId = "google";
        String providerId = "12345";
        String email = "oauth@example.com";
        String name = "Jane Doe";
        String picture = "http://pic.com/jane.jpg";

        OAuth2User oAuth2User = mock(OAuth2User.class);
        when(oAuth2User.getAttribute("email")).thenReturn(email);
        when(oAuth2User.getAttribute("name")).thenReturn(name);
        when(oAuth2User.getAttribute("picture")).thenReturn(picture);
        when(oAuth2User.getName()).thenReturn(providerId);

        User existingUser = new User();
        existingUser.setUserId(1L);
        existingUser.setFirstName("Old");
        existingUser.setLastName("Name");
        existingUser.setProvider(registrationId);
        existingUser.setProviderId(providerId);

        when(userRepository.findByProviderAndProviderId(registrationId, providerId))
                .thenReturn(Optional.of(existingUser));

        User updatedUser = new User();
        updatedUser.setUserId(1L);
        updatedUser.setFirstName("Jane");
        updatedUser.setLastName("Doe");
        updatedUser.setProfilePhotoUrl(picture);
        when(userRepository.save(existingUser)).thenReturn(updatedUser);

        User result = userService.processOAuthUser(oAuth2User, registrationId);

        assertNotNull(result);
        assertEquals("Jane", result.getFirstName());
        assertEquals("Doe", result.getLastName());
        assertEquals(picture, result.getProfilePhotoUrl());

        verify(userRepository).findByProviderAndProviderId(registrationId, providerId);
        verify(userRepository).save(existingUser);
        verify(userRepository, never()).findByEmail(any());
    }

    @Test
    void processOAuthUser_ExistingByEmail_LinksAndReturns() {
        String registrationId = "github";
        String providerId = "abcde";
        String email = "githubuser@example.com";
        String name = "Alice Smith";

        OAuth2User oAuth2User = mock(OAuth2User.class);
        when(oAuth2User.getAttribute("email")).thenReturn(email);
        when(oAuth2User.getAttribute("name")).thenReturn(name);
        when(oAuth2User.getName()).thenReturn(providerId);

        // No existing by provider
        when(userRepository.findByProviderAndProviderId(registrationId, providerId))
                .thenReturn(Optional.empty());

        // Existing by email
        User existingUser = new User();
        existingUser.setUserId(2L);
        existingUser.setEmail(email);
        existingUser.setIsVerified(false); // not verified yet
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(existingUser));

        when(userRepository.save(existingUser)).thenReturn(existingUser);

        User result = userService.processOAuthUser(oAuth2User, registrationId);

        assertNotNull(result);
        assertEquals(registrationId, existingUser.getProvider());
        assertEquals(providerId, existingUser.getProviderId());
        assertTrue(existingUser.getIsVerified());

        verify(userRepository).findByProviderAndProviderId(registrationId, providerId);
        verify(userRepository).findByEmail(email);
        verify(userRepository).save(existingUser);
    }

    @Test
    void processOAuthUser_BrandNew_CreatesAndReturns() {
        String registrationId = "google";
        String providerId = "new123";
        String email = "newuser@example.com";
        String name = "Bob Johnson";

        OAuth2User oAuth2User = mock(OAuth2User.class);
        when(oAuth2User.getAttribute("email")).thenReturn(email);
        when(oAuth2User.getAttribute("name")).thenReturn(name); // optional
        when(oAuth2User.getName()).thenReturn(providerId);

        // No existing by provider
        when(userRepository.findByProviderAndProviderId(registrationId, providerId))
                .thenReturn(Optional.empty());
        // No existing by email
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        User savedUser = new User();
        savedUser.setUserId(3L);
        savedUser.setEmail(email);
        savedUser.setProvider(registrationId);
        savedUser.setProviderId(providerId);
        savedUser.setRole(UserRole.RENTER);
        savedUser.setIsVerified(true);
        savedUser.setIsActive(true);
        savedUser.setFirstName("Bob");
        savedUser.setLastName("Johnson");

        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        User result = userService.processOAuthUser(oAuth2User, registrationId);

        assertNotNull(result);
        assertEquals(3L, result.getUserId());
        assertEquals(email, result.getEmail());
        assertEquals(registrationId, result.getProvider());
        assertEquals(providerId, result.getProviderId());
        assertEquals(UserRole.RENTER, result.getRole());
        assertTrue(result.getIsVerified());
        assertTrue(result.getIsActive());
        assertEquals("Bob", result.getFirstName());
        assertEquals("Johnson", result.getLastName());

        // Capture the new user to verify fields
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());
        User captured = userCaptor.getValue();
        assertEquals(email, captured.getEmail());
        assertEquals(registrationId, captured.getProvider());
        assertEquals(providerId, captured.getProviderId());
        assertEquals(UserRole.RENTER, captured.getRole());
        assertTrue(captured.getIsVerified());
        assertTrue(captured.getIsActive());
        assertEquals("Bob", captured.getFirstName());
        assertEquals("Johnson", captured.getLastName());
    }

    @Test
    void processOAuthUser_NameWithNoLastName_SetsLastNameEmpty() {
        String registrationId = "facebook";
        String providerId = "fb123";
        String email = "fb@example.com";
        String name = "Mona"; // single name

        OAuth2User oAuth2User = mock(OAuth2User.class);
        when(oAuth2User.getAttribute("email")).thenReturn(email);
        when(oAuth2User.getAttribute("name")).thenReturn(name);
        when(oAuth2User.getName()).thenReturn(providerId);

        when(userRepository.findByProviderAndProviderId(registrationId, providerId))
                .thenReturn(Optional.empty());
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        User savedUser = new User();
        savedUser.setUserId(4L);
        savedUser.setFirstName("Mona");
        savedUser.setLastName("");
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        User result = userService.processOAuthUser(oAuth2User, registrationId);
        assertNotNull(result);
        assertEquals("Mona", result.getFirstName());
        assertEquals("", result.getLastName());
    }

    // ==================== UPDATE USER ====================

    @Test
    void updateUser_Success() {
        Long userId = 1L;
        User existingUser = new User();
        existingUser.setUserId(userId);
        existingUser.setFirstName("OldFirst");
        existingUser.setLastName("OldLast");
        existingUser.setPassword("oldpass");
        existingUser.setEmail("old@example.com");
        existingUser.setPhoneNumber("+111");
        existingUser.setProfilePhotoUrl("old.jpg");

        User updatePayload = new User();
        updatePayload.setFirstName("NewFirst");
        updatePayload.setLastName("NewLast");
        updatePayload.setPassword("newpass");
        updatePayload.setEmail("new@example.com");
        updatePayload.setPhoneNumber("+222");
        updatePayload.setProfilePhotoUrl("new.jpg");

        User updatedUser = new User();
        updatedUser.setUserId(userId);
        updatedUser.setFirstName("NewFirst");
        updatedUser.setLastName("NewLast");
        updatedUser.setPassword("newpass");
        updatedUser.setEmail("new@example.com");
        updatedUser.setPhoneNumber("+222");
        updatedUser.setProfilePhotoUrl("new.jpg");

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(existingUser)).thenReturn(updatedUser);

        User result = userService.updateUser(userId, updatePayload);

        assertNotNull(result);
        assertEquals("NewFirst", result.getFirstName());
        assertEquals("NewLast", result.getLastName());
        assertEquals("newpass", result.getPassword());
        assertEquals("new@example.com", result.getEmail());
        assertEquals("+222", result.getPhoneNumber());
        assertEquals("new.jpg", result.getProfilePhotoUrl());

        verify(userRepository).findById(userId);
        verify(userRepository).save(existingUser);
    }

    @Test
    void updateUser_NotFound_ThrowsRuntimeException() {
        Long userId = 99L;
        User updatePayload = new User();

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> userService.updateUser(userId, updatePayload));
        assertEquals("User with id " + userId + " not found", ex.getMessage());

        verify(userRepository).findById(userId);
        verify(userRepository, never()).save(any());
    }

    // ==================== FIND BY ID ====================

    @Test
    void findById_Success() {
        Long userId = 1L;
        User user = new User();
        user.setUserId(userId);
        user.setEmail("test@example.com");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        User result = userService.findById(userId);

        assertNotNull(result);
        assertEquals(userId, result.getUserId());
        assertEquals("test@example.com", result.getEmail());

        verify(userRepository).findById(userId);
    }

    @Test
    void findById_NotFound_ThrowsRuntimeException() {
        Long userId = 99L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> userService.findById(userId));
        assertEquals("User with id " + userId + " not found", ex.getMessage());

        verify(userRepository).findById(userId);
    }

    // ==================== DELETE ====================

    @Test
    void deleteById_Success_WhenUserExists() {
        Long userId = 1L;

        when(userRepository.existsById(userId)).thenReturn(true);

        assertDoesNotThrow(() -> userService.deleteById(userId));

        verify(userRepository).existsById(userId);
        verify(userRepository).deleteById(userId);
    }

    @Test
    void deleteById_ThrowsAppException_WhenUserNotFound() {
        Long userId = 99L;

        when(userRepository.existsById(userId)).thenReturn(false);

        AppException ex = assertThrows(AppException.class,
                () -> userService.deleteById(userId));
        assertEquals("User with id " + userId + " not found", ex.getMessage());

        verify(userRepository).existsById(userId);
        verify(userRepository, never()).deleteById(any());
    }
}