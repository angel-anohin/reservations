package com.example.backend.controller;

import com.example.backend.dto.UserRequest;
import com.example.backend.model.User;
import com.example.backend.repository.UserRepository;
import com.example.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "http://127.0.0.1:3001")
@RestController
@RequestMapping("/user")
public class UserController {


    private UserRepository userRepository;
    private UserService userService;
    private UserRequest toDto(User user) {
        UserRequest dto = new UserRequest();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        return dto;
    }

    public UserController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @PostMapping("/add")
    public ResponseEntity<String>saveUser(@RequestBody UserRequest userRequest) {
        userService.saveUser(userRequest);
        return ResponseEntity.ok("Saved");
    }

    @GetMapping("/all")
    public Iterable<User> getAllusers() {
        return userService.fetchUserList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserRequest> get(@PathVariable("id") Integer userId) {
        return ResponseEntity.ok(userService.findUserById(userId));
}

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@RequestBody UserRequest userRequest, @PathVariable Integer id) {
       userService.updateUser(userRequest, id);
       return ResponseEntity.ok("User updated");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable("id") Integer userId) {
        userService.deleteUserById(userId);
        return ResponseEntity.ok( "Deleted successfully");
    }
}
