package org.roomrental.group.RoomieHub.repository;

import org.roomrental.group.RoomieHub.entity.Message;
import org.roomrental.group.RoomieHub.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByRoomRoomId(Long roomId);


    List<Message> findByConversationConversationIdOrderByCreatedAtAsc(Long conversationId);
    List<Message> findBySenderAndReceiver(User sender, User receiver);
}
