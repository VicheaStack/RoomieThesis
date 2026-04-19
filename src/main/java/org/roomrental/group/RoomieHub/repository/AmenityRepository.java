package org.roomrental.group.RoomieHub.repository;

import org.roomrental.group.RoomieHub.entity.Amenity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AmenityRepository extends JpaRepository<Amenity , Long> {
}
