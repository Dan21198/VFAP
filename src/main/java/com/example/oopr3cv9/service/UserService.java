package com.example.opr3cv9.service;



import com.example.opr3cv9.model.User;

import java.util.List;

public interface UserService {
    User saveOrUpdateUser(User user);

    User getUserById(Long userId);

    List<User> getAllUsers();

    void deleteUserById(Long userId);
}
