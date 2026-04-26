package org.roomrental.group.RoomieHub.serviceImpl;

import org.roomrental.group.RoomieHub.entity.Conversation;
import org.roomrental.group.RoomieHub.entity.Message;
import org.roomrental.group.RoomieHub.entity.User;
import org.roomrental.group.RoomieHub.repository.ConversationRepository;
import org.roomrental.group.RoomieHub.repository.MessageRepository;
import org.roomrental.group.RoomieHub.repository.UserRepository;
import org.roomrental.group.RoomieHub.service.MessageService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final ConversationRepository conversationRepository;
    private final UserRepository userRepository;

    public MessageServiceImpl(MessageRepository messageRepository, ConversationRepository conversationRepository, UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.conversationRepository = conversationRepository;
        this.userRepository = userRepository;
    }

    public Message sendMessage(Long conversationId, Long senderId, String text) {

        Conversation conversation = conversationRepository.findById(conversationId)
                .orElseThrow(() -> new RuntimeException("Conversation not found"));

        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Message message = Message.builder()
                .conversation(conversation)
                .sender(sender)
                .messageText(text)
                .isRead(false)
                .build();

        return messageRepository.save(message);
    }

    public List<Message> getMessages(Long conversationId) {
        return messageRepository
                .findByConversationConversationIdOrderByCreatedAtAsc(conversationId);
    }

    public void markAsRead(Long conversationId, Long userId) {

        List<Message> messages = messageRepository
                .findByConversationConversationIdOrderByCreatedAtAsc(conversationId);

        for (Message m : messages) {
            if (!m.getSender().getUserId().equals(userId)) {
                m.setIsRead(true);
            }
        }

        messageRepository.saveAll(messages);
    }
}