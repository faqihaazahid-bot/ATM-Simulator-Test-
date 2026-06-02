package com.example.atmsimulator.repository;

import com.example.atmsimulator.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface UserRepo extends JpaRepository<UserEntity,Long> {
    List<UserEntity> findAllByName(String name);
    Optional<UserEntity> findByCNICNumber(String CNICNumber);
    Optional<UserEntity> findByFormNumber(String formNumber);
    void deleteByCNICNumber(String CNICNumber);
}
