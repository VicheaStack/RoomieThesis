package org.roomrental.group.RoomieHub.message;

import org.roomrental.group.RoomieHub.room.Room;
import org.roomrental.group.RoomieHub.user.User;
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