package org.roomrental.group.RoomieHub.system;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/settings")
public class SystemSettingController {

    private final SystemSettingService systemSettingService;
    private final SystemSettingMapper systemSettingMapper;

    public SystemSettingController(SystemSettingService systemSettingService,
                                   SystemSettingMapper systemSettingMapper) {
        this.systemSettingService = systemSettingService;
        this.systemSettingMapper = systemSettingMapper;
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
    public ResponseEntity<SystemSettingResponseDTO> create(
            @RequestBody SystemSettingRequestDTO dto) {

        SystemSetting entity = systemSettingMapper.toEntity(dto);

        SystemSetting created = systemSettingService.create(entity);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(systemSettingMapper.toDto(created));
    }

    @PutMapping("/{key}")
    public ResponseEntity<SystemSettingResponseDTO> update(
            @PathVariable String key,
            @RequestBody UpdateSettingRequest request) {

        SystemSetting updated = systemSettingService.update(
                key,
                request.getValue(),
                request.getUserId()
        );

        return ResponseEntity.ok(systemSettingMapper.toDto(updated));
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