package org.roomrental.group.RoomieHub.message;

import org.roomrental.group.RoomieHub.room.Room;
import org.roomrental.group.RoomieHub.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Long> {

    Optional<Conversation> findByRenterAndOwnerAndRoom(
            User renter,
            User owner,
            Room room
    );
}