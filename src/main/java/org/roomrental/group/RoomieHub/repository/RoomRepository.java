package org.roomrental.group.RoomieHub.repository;

import org.roomrental.group.RoomieHub.entity.Room;
import org.roomrental.group.RoomieHub.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    boolean existsByOwnerAndTitle(User owner, String title);
}
