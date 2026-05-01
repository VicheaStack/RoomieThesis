package org.roomrental.group.RoomieHub.user;

import org.springframework.stereotype.Service;

@Service
public interface UserService {

    User create(User user, UserRole role);
    User updateUser(Long id, User user);
    User findById(Long id);
    void deleteById(Long id);
}
