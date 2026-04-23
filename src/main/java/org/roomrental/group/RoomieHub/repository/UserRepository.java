package org.roomrental.group.RoomieHub.repository;

import org.roomrental.group.RoomieHub.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository <User, Long> {
    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);
}
