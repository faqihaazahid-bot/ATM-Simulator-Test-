package com.example.atmsimulator.controller;

import com.example.atmsimulator.entity.TransactionEntity;
import com.example.atmsimulator.entity.UserEntity;
import com.example.atmsimulator.service.TransactionService;
import com.example.atmsimulator.service.UserService;
import com.example.atmsimulator.util.JavafxUtil;
import com.example.atmsimulator.util.SessionData;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Controller;
import java.time.LocalDateTime;
import java.io.IOException;
import static com.example.atmsimulator.JavaFxApplication.alert;

@Controller
public class DepositPageController {
    private final JavafxUtil javafxUtil;
    private final UserService userService;
    private final SessionData sessionData;
    private final TransactionService transactionService;

    @FXML
    private TextField depositTextField;
    @FXML
    private Button DepositButton;
    @FXML
    private Button BackButton;

    public DepositPageController(JavafxUtil javafxUtil, UserService userService, SessionData sessionData, TransactionService transactionService) {
        this.javafxUtil = javafxUtil;
        this.userService = userService;
        this.sessionData = sessionData;
        this.transactionService = transactionService;
    }

    @FXML
    public void initialize() {
        depositTextField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.matches("\\d*(\\.\\d*)?")) {
                depositTextField.setText(oldVal);
            }
        });
    }

    @FXML
    void Deposit() {
        if (depositTextField.getText().isBlank()) {
            alert.setContentText("Enter amount");
            alert.showAndWait();
            return;
        }
        double amount = Double.parseDouble(depositTextField.getText());
        UserEntity user = userService.findByCNICNumber(sessionData.currentCNIC).orElseThrow();
        user.setBalance(user.getBalance() + amount);
        userService.save(user);
        TransactionEntity Transaction = new TransactionEntity();
        Transaction.setUser(user);
        Transaction.setType("DEPOSIT");
        Transaction.setAmount(amount);
        Transaction.setBalance(user.getBalance());
        Transaction.setDateTime(LocalDateTime.now());
        transactionService.save(Transaction);
        alert.setAlertType(Alert.AlertType.INFORMATION);
        alert.setContentText("Deposit Successful!");
        alert.showAndWait();
        depositTextField.clear();
    }
    @FXML
    void Back() throws IOException {
        javafxUtil.switchScene("ATM");
    }

}
