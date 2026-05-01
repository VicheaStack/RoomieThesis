package org.roomrental.group.RoomieHub.adminProfile;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/AdminProfile")
public class AdminProfilleController {

    private final AdminProfileService adminProfileService;
    private final AdminProfileMapper adminProfileMapper;

    public AdminProfilleController(AdminProfileService adminProfileService,
                                    AdminProfileMapper adminProfileMapper) {
        this.adminProfileService = adminProfileService;
        this.adminProfileMapper = adminProfileMapper;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping ("/create")
    public ResponseEntity<AdminProfileResponseDTO> createAdminProfile(@RequestBody AdminProfileRequestDTO adminProfileRequestDTO) {
        AdminProfile entity = adminProfileMapper.toEntity(adminProfileRequestDTO);
        AdminProfile result = adminProfileService.create(entity);
        AdminProfileResponseDTO dto = adminProfileMapper.toDTO(result);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/Update/{id}")
    public ResponseEntity<AdminProfileResponseDTO> updateAdminProfile(@RequestBody AdminProfileRequestDTO adminProfileRequestDTO, @PathVariable Long id) {
        AdminProfile entity = adminProfileMapper.toEntity(adminProfileRequestDTO);
        AdminProfile update = adminProfileService.update(entity, id);
        AdminProfileResponseDTO dto = adminProfileMapper.toDTO(update);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<AdminProfileResponseDTO> findbyId(@PathVariable Long id) {
        AdminProfile byId = adminProfileService.findById(id);
        AdminProfileResponseDTO dto = adminProfileMapper.toDTO(byId);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/getAll")
    public ResponseEntity<Page<AdminProfileResponseDTO>> findAll(Pageable pageable) {
        Page<AdminProfileResponseDTO> map = adminProfileService.findAll(pageable)
                .map(adminProfileMapper::toDTO);
        return ResponseEntity.ok(map);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id ){
        adminProfileService.deleteById(id);
        return ResponseEntity.noContent().build();
    }



}
