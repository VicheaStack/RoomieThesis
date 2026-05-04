package org.roomrental.group.RoomieHub.favorite;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.roomrental.group.RoomieHub.exception.AppException;
import org.roomrental.group.RoomieHub.room.Room;
import org.roomrental.group.RoomieHub.user.User;
import org.roomrental.group.RoomieHub.room.RoomRepository;
import org.roomrental.group.RoomieHub.user.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class FavoriteServiceImpl implements FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;

    @Override
    public Favorite addFavorite(Long renterId, Long roomId) {
        // Check if already favorited
        if (favoriteRepository.existsByRenterUserIdAndRoomRoomId(renterId, roomId)) {
            throw new IllegalStateException("Room already favorited by this user");
        }

        User renter = userRepository.findById(renterId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + renterId));
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found with id: " + roomId));

        Favorite favorite = new Favorite(renter, room);
        Favorite saved = favoriteRepository.save(favorite);
        log.info("Favorite added: renterId={}, roomId={}", renterId, roomId);
        return saved;
    }

    @Override
    public void removeFavorite(Long id) {
        if (!favoriteRepository.existsById(id)) {
            throw AppException.of("Favorite not found with id: " + id);
        }
        favoriteRepository.deleteById(id);
        log.info("Favorite removed with id: {}", id);
    }

    @Override
    public void removeFavorite(Long renterId, Long roomId) {
        Favorite favorite = favoriteRepository.findByRenterUserIdAndRoomRoomId(renterId, roomId)
                .orElseThrow(() -> new RuntimeException(
                        String.format("Favorite not found for renterId=%d and roomId=%d", renterId, roomId)));
        favoriteRepository.delete(favorite);
        log.info("Favorite removed: renterId={}, roomId={}", renterId, roomId);
    }

    @Override
    @Transactional(readOnly = true)
    public Favorite findById(Long id) {
        return favoriteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Favorite not found with id: " + id));
    }

    @Override
    public Page<Favorite> findAll(Pageable pageable) {
        return favoriteRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Favorite> findAllByRenter(Long renterId, Pageable pageable) {
        return favoriteRepository.findAllByRenterUserId(renterId, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isFavorited(Long renterId, Long roomId) {
        return favoriteRepository.existsByRenterUserIdAndRoomRoomId(renterId, roomId);
    }
}