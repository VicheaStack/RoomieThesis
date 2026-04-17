package org.roomrental.group.RoomieHub.service;

import org.roomrental.group.RoomieHub.entity.Favorite;
import org.roomrental.group.RoomieHub.entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface MessageService {

    Message create(Message message);
    Message update(Message message, Long id);
    Message findById(Long id);
    Page<Message> findAll(Pageable pageable);
    void deleteById(Long id);
}
