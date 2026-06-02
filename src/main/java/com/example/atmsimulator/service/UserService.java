package com.example.atmsimulator.service;

import com.example.atmsimulator.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<UserEntity> findByCNICNumber(String CNICNumber);
    String generateUniqueFormNumber();
    UserEntity save(UserEntity user);
    void deleteByCNICNumber(String CNICNumber);
}
