package org.roomrental.group.RoomieHub.service;

import org.roomrental.group.RoomieHub.entity.Favorite;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface FavoriteService {

    Favorite addFavorite(Long renterId, Long roomId);
    void removeFavorite(Long id);
    void removeFavorite(Long renterId, Long roomId); // overload for convenience
    Favorite findById(Long id);
    Page<Favorite> findAll(Pageable pageable);
    Page<Favorite> findAllByRenter(Long renterId, Pageable pageable);
    boolean isFavorited(Long renterId, Long roomId);
}
