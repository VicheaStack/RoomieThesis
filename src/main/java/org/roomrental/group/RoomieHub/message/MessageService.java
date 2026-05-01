package org.roomrental.group.RoomieHub.message;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MessageService {

    Message sendMessage(Long conversationId, Long senderId, String text);
    List<Message> getMessages(Long conversationId);
    void markAsRead(Long conversationId, Long userId);
}
