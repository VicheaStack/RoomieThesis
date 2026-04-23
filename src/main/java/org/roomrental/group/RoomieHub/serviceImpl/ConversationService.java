package org.roomrental.group.RoomieHub.serviceImpl;

import org.roomrental.group.RoomieHub.entity.Conversation;
import org.roomrental.group.RoomieHub.entity.Room;
import org.roomrental.group.RoomieHub.entity.User;
import org.roomrental.group.RoomieHub.repository.ConversationRepository;
import org.springframework.stereotype.Service;

@Service
public class ConversationService {

    private final ConversationRepository conversationRepository;

    public ConversationService(ConversationRepository conversationRepository) {
        this.conversationRepository = conversationRepository;
    }

    public Conversation getOrCreate(User renter, User owner, Room room) {

        return conversationRepository
                .findByRenterAndOwnerAndRoom(renter, owner, room)
                .orElseGet(() -> {

                    Conversation conversation = Conversation.builder()
                            .renter(renter)
                            .owner(owner)
                            .room(room)
                            .build();

                    return conversationRepository.save(conversation);
                });
    }
}