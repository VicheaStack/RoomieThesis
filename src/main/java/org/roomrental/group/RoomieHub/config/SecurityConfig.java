package org.roomrental.group.RoomieHub.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter,
                          CustomOAuth2UserService customOAuth2UserService,
                          OAuth2SuccessHandler oAuth2SuccessHandler) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.customOAuth2UserService = customOAuth2UserService;
        this.oAuth2SuccessHandler = oAuth2SuccessHandler;
    }

    // ✅ Required bean for stateless OAuth2
    @Bean
    public CookieAuthorizationRequestRepository cookieAuthorizationRequestRepository() {
        return new CookieAuthorizationRequestRepository();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/oauth2/**", "/login/**").permitAll()
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/users/tenant", "/api/users/owner").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/room/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/reviews/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/owner-profiles/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/Amenity/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/photos/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/favorites/check").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/users/**").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/api/users/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/api/users/**").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/v1/bookings/**").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/api/v1/bookings/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/bookings/**").authenticated()
                        .requestMatchers("/api/conversations/**").authenticated()
                        .requestMatchers("/api/notifications/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/v1/bookings").hasRole("RENTER")
                        .requestMatchers(HttpMethod.POST, "/api/reviews").hasRole("RENTER")
                        .requestMatchers(HttpMethod.PUT, "/api/reviews/**").hasRole("RENTER")
                        .requestMatchers(HttpMethod.DELETE, "/api/reviews/**").hasRole("RENTER")
                        .requestMatchers("/api/favorites/**").hasRole("RENTER")
                        .requestMatchers(HttpMethod.POST, "/api/owner-profiles/*/rate").hasRole("RENTER")
                        .requestMatchers(HttpMethod.POST, "/api/v1/room").hasRole("OWNER")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/room/**").hasRole("OWNER")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/room/**").hasRole("OWNER")
                        .requestMatchers(HttpMethod.POST, "/api/cdn/upload").hasRole("OWNER")
                        .requestMatchers(HttpMethod.POST, "/api/photos").hasRole("OWNER")
                        .requestMatchers(HttpMethod.PUT, "/api/photos/**").hasRole("OWNER")
                        .requestMatchers(HttpMethod.DELETE, "/api/photos/**").hasRole("OWNER")
                        .requestMatchers(HttpMethod.PUT, "/api/owner-profiles/**").hasRole("OWNER")
                        .requestMatchers(HttpMethod.DELETE, "/api/owner-profiles/**").hasRole("OWNER")
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        .requestMatchers("/api/settings/**").hasRole("ADMIN")
                        .requestMatchers("/api/v1/AdminProfile/**").hasRole("ADMIN")
                        .requestMatchers("/api/v1/AuditLog/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/v1/Amenity/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/Amenity/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/Amenity/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/owner-profiles/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/owner-profiles/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/reviews/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/photos/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/photos/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth2 -> oauth2
                        .authorizationEndpoint(auth -> auth
                                .authorizationRequestRepository(cookieAuthorizationRequestRepository())
                        )
                        .redirectionEndpoint(redir -> redir
                                .baseUri("/login/oauth2/code/*")
                        )
                        .userInfoEndpoint(userInfo -> userInfo.userService(customOAuth2UserService))
                        .successHandler(oAuth2SuccessHandler)
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}