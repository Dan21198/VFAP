package com.example.oopr3cv9.service;



import com.example.oopr3cv9.model.User;

import java.util.List;

public interface UserService {
    User saveOrUpdateUser(User user);

    User getUserById(Long userId);

    List<User> getAllUsers(boolean isAdmin);

    void deleteUserById(Long userId);
}
