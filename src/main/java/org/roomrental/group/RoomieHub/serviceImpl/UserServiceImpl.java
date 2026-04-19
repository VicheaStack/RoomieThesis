package org.roomrental.group.RoomieHub.serviceImpl;

import org.roomrental.group.RoomieHub.entity.User;
import org.roomrental.group.RoomieHub.service.UserService;

import java.util.Optional;

public class UserServiceImpl implements UserService {
    @Override
    public User create(User user) {
        return null;
    }

    @Override
    public User updateUser(Long id, User user) {
        return null;
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public void deleteById(Long id) {

    }
}
