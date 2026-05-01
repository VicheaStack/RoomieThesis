package org.roomrental.group.RoomieHub.user;

import lombok.extern.slf4j.Slf4j;
import org.roomrental.group.RoomieHub.room.RoomRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
            throw new RuntimeException("User with email " + user.getEmail() + " already exists");
        }

        if(userRepository.existsByPhoneNumber(user.getPhoneNumber())){
            throw new RuntimeException("User with phone number " + user.getPhoneNumber() + " already exists");
        }

        User save = userRepository.save(user);
        log.debug("User created: {}", save);
        return save;
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
            throw new RuntimeException("User with id " + id + " not found");
        }
        log.debug("User deleted: {}", id);
        userRepository.deleteById(id);
    }
}
