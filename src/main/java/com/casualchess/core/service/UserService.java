package com.casualchess.core.service;

import com.casualchess.core.entity.UserEntity;
import com.casualchess.core.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity addUser(String username) {
        return userRepository.save(new UserEntity(username));
    }

    public Optional<UserEntity> findById(long userId) {
        return userRepository.findById(userId);
    }

    public Optional<UserEntity> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
