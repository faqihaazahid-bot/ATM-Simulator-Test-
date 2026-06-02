package com.example.atmsimulator.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public class TransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @Column(name = "Type")
    private String type;

    @Column(name = "Amount")
    private double amount;

    @Column(name = "Balance")
    private double balance;

    @Column(name = "Date_Time")
    private LocalDateTime dateTime;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

        public Long getId() {
            return id;
        }
        public void setId(Long id) {
            this.id = id;
        }
        public String getType() {
            return type;
        }
        public void setType(String type) {
            this.type = type;
        }
        public UserEntity getUser() {return user;}
        public void setUser(UserEntity user) {this.user = user;}
        public double getAmount() {
            return amount;
        }
        public void setAmount(double amount) {
            this.amount = amount;
        }
        public double getBalance() {
            return balance;
        }
        public void setBalance(double balanceAfter) {
            this.balance = balanceAfter;
        }
        public LocalDateTime getDateTime() {
            return dateTime;
        }
        public void setDateTime(LocalDateTime dateTime) {
            this.dateTime = dateTime;
        }
}


