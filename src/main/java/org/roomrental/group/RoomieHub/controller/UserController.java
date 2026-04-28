package org.roomrental.group.RoomieHub.controller;

import org.roomrental.group.RoomieHub.dto.UserRequestDTO;
import org.roomrental.group.RoomieHub.dto.UserResponseDTO;
import org.roomrental.group.RoomieHub.entity.User;
import org.roomrental.group.RoomieHub.mapper.UserMapper;
import org.roomrental.group.RoomieHub.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService,
                          UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    // Create a new user
    @PostMapping
    public ResponseEntity<UserResponseDTO> create(@RequestBody UserRequestDTO userRequestDTO) {
        User entity = userMapper.toEntity(userRequestDTO);
        User created = userService.create(entity);
        UserResponseDTO dto = userMapper.toDTO(created);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    // Get a user by ID
    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id) {
        User user = userService.findById(id);
        userMapper.toDTO(user);
        return ResponseEntity.ok(user);
    }

    // Update an existing user
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long id, @RequestBody UserRequestDTO userRequestDTO) {
        User entity = userMapper.toEntity(userRequestDTO);
        User updated = userService.updateUser(id, entity);
        UserResponseDTO dto = userMapper.toDTO(updated);
        return ResponseEntity.ok(dto);
    }

    // Delete a user by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}