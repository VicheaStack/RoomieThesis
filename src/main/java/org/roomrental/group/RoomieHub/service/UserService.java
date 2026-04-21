package org.roomrental.group.RoomieHub.service;

import org.roomrental.group.RoomieHub.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    User create(User user);
    User updateUser(Long id, User user);
    User findById(Long id);
    void deleteById(Long id);
}
