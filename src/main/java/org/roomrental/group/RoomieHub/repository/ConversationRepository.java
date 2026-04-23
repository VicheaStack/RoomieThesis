package org.roomrental.group.RoomieHub.repository;

import org.roomrental.group.RoomieHub.entity.Conversation;
import org.roomrental.group.RoomieHub.entity.Room;
import org.roomrental.group.RoomieHub.entity.User;
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