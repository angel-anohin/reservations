package com.example.backend.service;

import com.example.backend.dto.UserRequest;
import com.example.backend.model.User;
import com.example.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void saveUser(@RequestBody UserRequest userRequest){
        User user = new User();
        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());
        userRepository.save(user);
    }

    @Override
    @Cacheable(value = "users")
    public List<User> fetchUserList() {
        return (List<User>)userRepository.findAll();
    }

    @Override
    @CachePut(value = "users", key = "#userId")
    public String updateUser(UserRequest userRequest, Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());
        userRepository.save(user);
        return "User updated";
    }

    @Override
    @CacheEvict(value = "users", key = "#userId")
    public void deleteUserById(Integer userId) {
        userRepository.deleteById(userId);
    }

    @Override
    @Cacheable(value = "users", key = "#a0")
    public UserRequest findUserById(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        return new UserRequest(user.getId(), user.getName(), user.getEmail());
    }
}
