package org.roomrental.group.RoomieHub.controller;

import org.roomrental.group.RoomieHub.entity.SystemSetting;
import org.roomrental.group.RoomieHub.service.SystemSettingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/settings")
public class SystemSettingController {

    private final SystemSettingService systemSettingService;

    public SystemSettingController(SystemSettingService systemSettingService) {
        this.systemSettingService = systemSettingService;
    }

    @GetMapping("/{key}/string")
    public ResponseEntity<String> getString(@PathVariable String key) {
        return ResponseEntity.ok(systemSettingService.getString(key));
    }

    @GetMapping("/{key}/int")
    public ResponseEntity<Integer> getInt(@PathVariable String key) {
        return ResponseEntity.ok(systemSettingService.getInt(key));
    }

    @GetMapping("/{key}/double")
    public ResponseEntity<Double> getDouble(@PathVariable String key) {
        return ResponseEntity.ok(systemSettingService.getDouble(key));
    }

    @GetMapping("/{key}/boolean")
    public ResponseEntity<Boolean> getBoolean(@PathVariable String key) {
        return ResponseEntity.ok(systemSettingService.getBoolean(key));
    }

    @PostMapping
    public ResponseEntity<SystemSetting> create(@RequestBody SystemSetting setting) {
        SystemSetting created = systemSettingService.create(setting);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{key}")
    public ResponseEntity<SystemSetting> update(
            @PathVariable String key,
            @RequestBody UpdateSettingRequest request) {
        SystemSetting updated = systemSettingService.update(key, request.getValue(), request.getUserId());
        return ResponseEntity.ok(updated);
    }

    static class UpdateSettingRequest {
        private String value;
        private Long userId;

        public String getValue() { return value; }
        public void setValue(String value) { this.value = value; }
        public Long getUserId() { return userId; }
        public void setUserId(Long userId) { this.userId = userId; }
    }
}