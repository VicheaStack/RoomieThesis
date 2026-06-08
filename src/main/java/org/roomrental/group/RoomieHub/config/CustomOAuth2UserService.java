package org.roomrental.group.RoomieHub.config;

import org.roomrental.group.RoomieHub.user.User;
import org.roomrental.group.RoomieHub.user.UserServiceImpl;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserServiceImpl userService;   // now inject the SERVICE, not the repo

    public CustomOAuth2UserService(UserServiceImpl userService) {
        this.userService = userService;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        Map<String, Object> attributes = oAuth2User.getAttributes();

        String registrationId = userRequest.getClientRegistration().getRegistrationId(); // "google"
        User user = userService.processOAuthUser(oAuth2User, registrationId);

        // Add custom attributes your JWT handler needs
        attributes.put("userId", user.getUserId());
        attributes.put("role", user.getRole().name());

        return new DefaultOAuth2User(
                oAuth2User.getAuthorities(),
                attributes,
                "email"   // nameAttributeKey – must be a unique field
        );
    }
}