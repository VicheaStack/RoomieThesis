package org.roomrental.group.RoomieHub.system;

import org.roomrental.group.RoomieHub.exception.AppException;
import org.roomrental.group.RoomieHub.user.User;
import org.roomrental.group.RoomieHub.user.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class SystemSettingServiceImpl implements SystemSettingService {

    private final SystemSettingRepository repository;
    private final UserRepository userRepository;

    public SystemSettingServiceImpl(SystemSettingRepository repository,
                                    UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    // 🔹 GETTERS

    @Override
    public String getString(String key) {
        return getSetting(key).getSettingValue();
    }

    @Override
    public Integer getInt(String key) {
        return Integer.parseInt(getSetting(key).getSettingValue());
    }

    @Override
    public Double getDouble(String key) {
        return Double.parseDouble(getSetting(key).getSettingValue());
    }

    @Override
    public Boolean getBoolean(String key) {
        return Boolean.parseBoolean(getSetting(key).getSettingValue());
    }

    private SystemSetting getSetting(String key) {
        return repository.findBySettingKey(key)
                .orElseThrow(() -> new RuntimeException("Setting not found: " + key));
    }

    // 🔹 CREATE

    @Override
    public SystemSetting create(SystemSetting setting) {

        if (repository.existsBySettingKey(setting.getSettingKey())) {
            throw AppException.of("Setting already exists: " + setting.getSettingKey());
        }

        return repository.save(setting);
    }

    // 🔹 UPDATE

    @Override
    public SystemSetting update(String key, String value, Long userId) {

        SystemSetting setting = repository.findBySettingKey(key)
                .orElseThrow(() -> new RuntimeException("Setting not found: " + key));

        setting.setSettingValue(value);

        if (userId != null) {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            setting.setUpdatedBy(user);
        }

        return repository.save(setting);
    }
}