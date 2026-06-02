package com.example.atmsimulator.service;

import com.example.atmsimulator.entity.UserEntity;
import com.example.atmsimulator.repository.UserRepo;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import com.example.atmsimulator.util.FormNumberGeneratorUtil;

import java.util.List;
import java.util.Optional;
@Service
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    public UserServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }
    @Override
    public Optional<UserEntity> findByCNICNumber(String CNICNumber) {
        return userRepo.findByCNICNumber(CNICNumber);
    }
    @Override
    public UserEntity save(UserEntity user) {
        return userRepo.save(user);
    }
    @Override
    @Transactional
    public void deleteByCNICNumber(String cnic) {
        userRepo.deleteByCNICNumber(cnic);
    }

    public String generateUniqueFormNumber() {
        String formNumber;
        do {
            formNumber = FormNumberGeneratorUtil.generate5DigitFormNumber();
        } while (userRepo.findByFormNumber(formNumber).isPresent());

        return formNumber;
    }
}
