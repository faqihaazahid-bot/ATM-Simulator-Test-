package com.example.atmsimulator.service;

import com.example.atmsimulator.entity.TransactionEntity;
import com.example.atmsimulator.repository.TransactionRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepo repo;
        public TransactionServiceImpl(TransactionRepo repo) {
            this.repo = repo;
        }

  @Override
        public TransactionEntity save(TransactionEntity transaction) {
            return repo.save(transaction);
        }

  @Override
    public List<TransactionEntity> getUserTransactions(String cnic) {
        return repo.findByUser_CNICNumberOrderByDateTimeAsc(cnic);
    }
}

