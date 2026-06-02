package com.example.atmsimulator.service;

import com.example.atmsimulator.entity.TransactionEntity;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface TransactionService {
        TransactionEntity save(TransactionEntity transaction);
        List<TransactionEntity> getUserTransactions(String cnic);
}

