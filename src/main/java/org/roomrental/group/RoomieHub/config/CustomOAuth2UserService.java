package org.roomrental.group.RoomieHub.config;


import org.roomrental.group.RoomieHub.user.User;
import org.roomrental.group.RoomieHub.user.UserRepository;
import org.roomrental.group.RoomieHub.user.UserRole;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    public CustomOAuth2UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        Map<String, Object> attributes = oAuth2User.getAttributes();
        String email = (String) attributes.get("email");
        String name = (String) attributes.get("name");

        User user = userRepository.findByEmail(email)
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setEmail(email);
                    newUser.setFirstName(name.split(" ")[0]);
                    newUser.setLastName(name.split(" ").length > 1 ? name.split(" ")[1] : "");
                    newUser.setRole(UserRole.RENTER); // default role
                    newUser.setIsVerified(true);
                    newUser.setIsActive(true);
                    return userRepository.save(newUser);
                });

        attributes.put("userId", user.getUserId());
        attributes.put("role", user.getRole().name());

        return new DefaultOAuth2User(
                oAuth2User.getAuthorities(),
                attributes,
                "email"
        );
    }
}