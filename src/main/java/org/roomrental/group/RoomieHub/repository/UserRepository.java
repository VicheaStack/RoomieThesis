package org.roomrental.group.RoomieHub.repository;

import org.roomrental.group.RoomieHub.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository <User, Long> {
}
