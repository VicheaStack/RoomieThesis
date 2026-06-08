package org.roomrental.group.RoomieHub.user;

import lombok.extern.slf4j.Slf4j;
import org.roomrental.group.RoomieHub.exception.AppException;
import org.roomrental.group.RoomieHub.room.RoomRepository;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(RoomRepository roomRepository,
                           UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User create(User user, UserRole role) {
        if (user.getPassword() == null || user.getPassword().isBlank()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }

        user.setRole(role);

        if(userRepository.existsByEmail(user.getEmail())){
            throw AppException.of("User with email " + user.getEmail() + " already exists");
        }

        if(userRepository.existsByPhoneNumber(user.getPhoneNumber())){
            throw AppException.of("User with phone number " + user.getPhoneNumber() + " already exists");
        }

        User save = userRepository.save(user);
        log.debug("User created: {}", save);
        return save;
    }

    @Transactional
    public User processOAuthUser(OAuth2User oAuth2User, String registrationId) {
        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");
        String providerId = oAuth2User.getName();   // or getAttribute("sub") for OpenID

        // 1. Try by provider + providerId
        Optional<User> existing = userRepository.findByProviderAndProviderId(registrationId, providerId);
        if (existing.isPresent()) {
            return updateExistingFromOAuth(existing.get(), oAuth2User);
        }

        // 2. Try to link by email (local account that hasn't logged in via OAuth yet)
        Optional<User> userByEmail = userRepository.findByEmail(email);
        if (userByEmail.isPresent()) {
            User user = userByEmail.get();
            user.setProvider(registrationId);
            user.setProviderId(providerId);
            user.setIsVerified(true);
            // optionally update name/avatar from OAuth
            return userRepository.save(user);
        }

        // 3. Brand new OAuth user
        User newUser = new User();
        newUser.setEmail(email);
        newUser.setProvider(registrationId);
        newUser.setProviderId(providerId);
        newUser.setRole(UserRole.RENTER);           // as before
        newUser.setIsVerified(true);
        newUser.setIsActive(true);

        if (name != null) {
            String[] parts = name.split(" ", 2);
            newUser.setFirstName(parts[0]);
            newUser.setLastName(parts.length > 1 ? parts[1] : "");
        }
        return userRepository.save(newUser);
    }

    // Helper to update existing user from fresh OAuth data (name, picture, etc.)
    private User updateExistingFromOAuth(User user, OAuth2User oAuth2User) {
        String name = oAuth2User.getAttribute("name");
        if (name != null) {
            String[] parts = name.split(" ", 2);
            user.setFirstName(parts[0]);
            user.setLastName(parts.length > 1 ? parts[1] : "");
        }
        user.setProfilePhotoUrl(oAuth2User.getAttribute("picture"));
        // preserve isVerified, isActive, role, etc.
        return userRepository.save(user);
    }

    @Transactional
    @Override
    public User updateUser(Long id, User user) {
        User update = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User with id " + id + " not found"));

        update.setFirstName(user.getFirstName());
        update.setLastName(user.getLastName());
        update.setPassword(user.getPassword());
        update.setEmail(user.getEmail());
        update.setPhoneNumber(user.getPhoneNumber());
        update.setProfilePhotoUrl(user.getProfilePhotoUrl());
        User save = userRepository.save(update);
        log.debug("User updated: {}", save);

        return save;
    }

    @Transactional(readOnly = true)
    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("User with id " + id + " not found"));
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        if(!userRepository.existsById(id)){
            throw AppException.of("User with id " + id + " not found");
        }
        log.debug("User deleted: {}", id);
        userRepository.deleteById(id);
    }
}
