package org.roomrental.group.RoomieHub.room;

import org.roomrental.group.RoomieHub.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    boolean existsByOwnerAndTitle(User owner, String title);
}
