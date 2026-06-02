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

import java.io.IOException;
import java.time.LocalDateTime;

import static com.example.atmsimulator.JavaFxApplication.alert;

@Controller
public class WithdrawPageController {

    private final UserService userService;
    private final TransactionService transactionService;
    private final JavafxUtil javafxUtil;
    private final SessionData sessionData;

    @FXML
    private TextField withdrawAmountTextField;
    @FXML
    private Button withdrowButton;
    @FXML
    private Button backButton;

    public WithdrawPageController(UserService userService, TransactionService transactionService, JavafxUtil javafxUtil, SessionData sessionData) {
        this.userService = userService;
        this.transactionService = transactionService;
        this.javafxUtil = javafxUtil;
        this.sessionData = sessionData;
    }

    @FXML
    public void initialize() {

        withdrawAmountTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null && !newValue.isEmpty()){
                try{
                    Float.parseFloat(newValue);
                }catch(NumberFormatException e){
                    withdrawAmountTextField.setText(oldValue);
                }
            }
        });
        withdrawAmountTextField.textProperty().addListener((obs, oldVal, newVal) -> {
            withdrawAmountTextField.setText(String.valueOf(newVal));
        });
    }

    @FXML
    void Withdrow() {

        if (withdrawAmountTextField.getText().isBlank()) {
            alert.setContentText("Enter amount");
            alert.showAndWait();
            return;
        }
        double amount = Double.parseDouble(withdrawAmountTextField.getText());
        UserEntity user = userService.findByCNICNumber(sessionData.currentCNIC).orElseThrow(() -> new RuntimeException("Session expired"));

        if (user.getBalance() < amount) {
            alert.setContentText("Insufficient balance!");
            alert.showAndWait();
            return;
        }

        user.setBalance(user.getBalance() - amount);
        userService.save(user);
        TransactionEntity text = new TransactionEntity();
        text.setUser(user);
        text.setType("WITHDRAW");
        text.setAmount(amount);
        text.setBalance(user.getBalance());
        text.setDateTime(LocalDateTime.now());
        transactionService.save(text);
        alert.setAlertType(Alert.AlertType.INFORMATION);
        alert.setContentText("Withdrawal Successful!");
        alert.showAndWait();
        withdrawAmountTextField.clear();
    }
    @FXML
    void Back() throws IOException {
        javafxUtil.switchScene("ATM");
    }
}
