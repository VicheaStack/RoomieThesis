package org.roomrental.group.RoomieHub.serviceImpl;

import org.roomrental.group.RoomieHub.entity.Message;
import org.roomrental.group.RoomieHub.repository.MessageRepository;
import org.roomrental.group.RoomieHub.service.MessageService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;

    public MessageServiceImpl(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public Message create(Message message) {
        return null;
    }

    @Override
    public Message update(Message message, Long id) {
        return null;
    }

    @Override
    public Message findById(Long id) {
        return null;
    }

    @Override
    public Page<Message> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
