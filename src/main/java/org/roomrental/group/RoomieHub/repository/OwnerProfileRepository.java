package org.roomrental.group.RoomieHub.repository;

import org.roomrental.group.RoomieHub.entity.OwnerProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OwnerProfileRepository extends JpaRepository<OwnerProfile, Long> {
}
