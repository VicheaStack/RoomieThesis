package org.roomrental.group.RoomieHub.repository;

import org.roomrental.group.RoomieHub.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    // ✅ Fixed: traverse conversation -> room -> roomId
    List<Message> findByConversationRoomRoomId(Long roomId);

    // ✅ OK – conversation has conversationId
    List<Message> findByConversationConversationIdOrderByCreatedAtAsc(Long conversationId);

    // ✅ OK (if you really need to fetch by sender AND receiver)
    List<Message> findBySenderUserIdAndReceiverUserId(Long senderUserId, Long receiverUserId);
}