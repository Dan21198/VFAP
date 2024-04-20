package com.example.oopr3cv9.service;

import com.example.oopr3cv9.model.User;
import com.example.oopr3cv9.repository.UserRepository;
import com.example.oopr3cv9.token.TokenRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;

    @Override
    public User saveOrUpdateUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        return userOptional.orElse(null);
    }

    @Override
    public List<User> getAllUsers(boolean isAdmin) {
        if (isAdmin) {
            return userRepository.findAll();
        } else {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userEmail = authentication.getName();
            Optional<User> currentUser = userRepository.findByEmail(userEmail);

            return currentUser.map(Collections::singletonList).orElse(Collections.emptyList());
        }
    }

    @Override
    @Transactional
    public void deleteUserById(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);

        userOptional.ifPresent(user -> {
            tokenRepository.deleteByUserId(userId);
            userRepository.deleteById(userId);
        });
    }

}
