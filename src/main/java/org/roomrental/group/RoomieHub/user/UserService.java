package org.roomrental.group.RoomieHub.user;

import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    User create(User user, UserRole role);
    User updateUser(Long id, User user);
    User processOAuthUser(OAuth2User oAuth2User, String provider);
    User findById(Long id);
    void deleteById(Long id);
}
