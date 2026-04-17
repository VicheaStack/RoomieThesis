package org.roomrental.group.RoomieHub.service;

import org.roomrental.group.RoomieHub.entity.Photo;
import org.springframework.stereotype.Service;

@Service
public interface PhotoService {

    Photo create(Photo photo);
    Photo update(Photo photo, Long id);
    void deleteById(Long id);
}
