package org.roomrental.group.RoomieHub.favorite;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    // ✅ Fixed: use RoomRoomId because Room has roomId, not id
    boolean existsByRenterUserIdAndRoomRoomId(Long userId, Long roomId);

    Optional<Favorite> findByRenterUserIdAndRoomRoomId(Long userId, Long roomId);

    void deleteByRenterUserIdAndRoomRoomId(Long userId, Long roomId);

    // ✅ Already correct: User has userId, not id
    Page<Favorite> findAllByRenterUserId(Long userId, Pageable pageable);
}