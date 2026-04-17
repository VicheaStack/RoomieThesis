package org.roomrental.group.RoomieHub.service;

import org.roomrental.group.RoomieHub.entity.AuditLog;
import org.roomrental.group.RoomieHub.entity.Favorite;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface FavoriteService {

    Favorite create(Favorite favorite);
    Favorite update(Favorite favorite, Long id);
    Favorite findById(Long id);
    Page<Favorite> findAll(Pageable pageable);
    void deleteById(Long id);

}
