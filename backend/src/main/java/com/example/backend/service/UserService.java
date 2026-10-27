package com.example.backend.service;

import com.example.backend.dto.UserRequest;
import com.example.backend.model.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {

    void saveUser(UserRequest userRequest);

    List<User> fetchUserList();

    String updateUser(UserRequest userRequest, Integer userId);

    void deleteUserById(Integer userId);

    UserRequest findUserById(Integer userId);


}
