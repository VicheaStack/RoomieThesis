package org.roomrental.group.RoomieHub.service;

import org.roomrental.group.RoomieHub.entity.Conversation;
import org.roomrental.group.RoomieHub.entity.Favorite;
import org.roomrental.group.RoomieHub.entity.Message;
import org.roomrental.group.RoomieHub.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MessageService {

    Message sendMessage(Long conversationId, Long senderId, String text);
    List<Message> getMessages(Long conversationId);
    void markAsRead(Long conversationId, Long userId);
}
