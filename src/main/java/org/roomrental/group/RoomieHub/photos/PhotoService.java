package org.roomrental.group.RoomieHub.photos;

public interface PhotoService {

    Photo create(Photo photo, Long roomId);
    Photo update(Photo photo, Long id);
    void deleteById(Long id);
}
