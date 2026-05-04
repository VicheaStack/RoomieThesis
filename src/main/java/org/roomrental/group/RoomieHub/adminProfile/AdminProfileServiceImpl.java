package org.roomrental.group.RoomieHub.adminProfile;

import lombok.extern.slf4j.Slf4j;
import org.roomrental.group.RoomieHub.exception.AppException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
public class AdminProfileServiceImpl implements AdminProfileService {

    private final AdminProfileRepository adminProfileRepository;

    public AdminProfileServiceImpl(AdminProfileRepository adminProfileRepository) {
        this.adminProfileRepository = adminProfileRepository;
    }

    /**
     * imlpement security check
     *
     * **/
    @Override
    public AdminProfile create(AdminProfile adminProfile) {

        AdminProfile save = adminProfileRepository.save(adminProfile);
        log.info("AdminProfile Create successfully: " + save);
        return save;
    }

    @Transactional
    @Override
    public AdminProfile update(AdminProfile adminProfile, Long id) {

        AdminProfile profile = adminProfileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Profile already exist: " + adminProfile.getUser()));

        adminProfile.setUsername(profile.getUsername());

        AdminProfile save = adminProfileRepository.save(profile);
        log.info("profile create successfully ");
        return save;
    }

    @Transactional(readOnly = true)
    @Override
    public AdminProfile findById(Long id) {
        return adminProfileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("admin profile not found "));
    }

    @Transactional(readOnly = true)
    @Override
    public Page<AdminProfile> findAll(Pageable pageable) {
        log.debug("Fetching all admin profile with pagination: page {}, size {}",
                pageable.getPageNumber(), pageable.getPageSize());
        return adminProfileRepository.findAll(pageable);
    }

    @Override
    public void deleteById(Long id) {
        if(!adminProfileRepository.existsById(id)){
            throw AppException.of("admin profile don't exist");
        }
        adminProfileRepository.deleteById(id);
    }
}
