package org.roomrental.group.RoomieHub.repository;

import org.roomrental.group.RoomieHub.entity.AdminProfile;
import org.roomrental.group.RoomieHub.entity.Amenity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AmenityRepository extends JpaRepository<Amenity , Long> {
}
