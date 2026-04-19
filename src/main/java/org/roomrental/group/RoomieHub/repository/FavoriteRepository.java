package org.roomrental.group.RoomieHub.repository;

import org.roomrental.group.RoomieHub.entity.Favorite;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    boolean existsByRenterIdAndRoomId(Long renterId, Long roomId);

    Optional<Favorite> findByRenterIdAndRoomId(Long renterId, Long roomId);

    // Paginated version
    Page<Favorite> findAllByRenterId(Long renterId, Pageable pageable);

    // Optional: Non-paginated version if you need it elsewhere
    // List<Favorite> findAllByRenterId(Long renterId);

    void deleteByRenterIdAndRoomId(Long renterId, Long roomId);
}