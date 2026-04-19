package org.roomrental.group.RoomieHub.repository;

import org.roomrental.group.RoomieHub.entity.SystemSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemSettingRepository extends JpaRepository<SystemSetting, Long> {
}
