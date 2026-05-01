package org.roomrental.group.RoomieHub.ownerProfile;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OwnerProfileRepository extends JpaRepository<OwnerProfile, Long> {
}
