package org.roomrental.group.RoomieHub.service;

import org.roomrental.group.RoomieHub.entity.User;
import org.roomrental.group.RoomieHub.entity.UserRole;
import org.springframework.stereotype.Service;

import javax.management.relation.Role;

@Service
public interface UserService {

    User create(User user, UserRole role);
    User updateUser(Long id, User user);
    User findById(Long id);
    void deleteById(Long id);
}
