package org.roomrental.group.RoomieHub.service;

import org.roomrental.group.RoomieHub.entity.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserService {

    User create(User user);
    User updateUser(Long id, User user);
    Optional<User> findById(Long id);
    void deleteById(Long id);
}
