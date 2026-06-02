package com.example.atmsimulator.repository;

import com.example.atmsimulator.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TransactionRepo extends JpaRepository<TransactionEntity, Long> {
    List<TransactionEntity> findByUser_CNICNumberOrderByDateTimeAsc(String cnic);
    }

