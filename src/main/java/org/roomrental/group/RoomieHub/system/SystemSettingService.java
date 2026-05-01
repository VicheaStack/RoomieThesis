package org.roomrental.group.RoomieHub.system;

public interface SystemSettingService {
    String getString(String key);
    Integer getInt(String key);
    Double getDouble(String key);
    Boolean getBoolean(String key);
    SystemSetting create(SystemSetting setting);
    SystemSetting update(String key, String value, Long userId);
}
