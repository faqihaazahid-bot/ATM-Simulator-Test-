package com.example.atmsimulator.controller;

import com.example.atmsimulator.entity.TransactionEntity;
import com.example.atmsimulator.entity.UserEntity;
import com.example.atmsimulator.service.TransactionService;
import com.example.atmsimulator.service.UserService;
import com.example.atmsimulator.util.JavafxUtil;
import com.example.atmsimulator.util.SessionData;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.List;

@Controller
public class MiniStatementPageController {

    private final TransactionService transactionService;
    private final UserService userService;
    private final SessionData sessionData;
    private final JavafxUtil javafxUtil;

    @FXML
    private TextArea statementTextArea;
    @FXML
    private Text nameText;
    @FXML
    private Text CNICNumberText;
    @FXML
    private Text remainingBalanceText;

    public MiniStatementPageController(TransactionService transactionService, UserService userService, SessionData sessionData, JavafxUtil javafxUtil) {
        this.transactionService = transactionService;
        this.userService = userService;
        this.sessionData = sessionData;
        this.javafxUtil = javafxUtil;
    }

    @FXML
    public void initialize() {

        UserEntity user = userService.findByCNICNumber(sessionData.currentCNIC).orElseThrow(() -> new RuntimeException("User not found"));
        nameText.setText("Name: " + user.getName());
        CNICNumberText.setText("CNIC: " + user.getCNICNumber());
        remainingBalanceText.setText("YOUR REMAINING BALANCE IS Rs " + user.getBalance());
        List<TransactionEntity> transactions = transactionService.getUserTransactions(sessionData.currentCNIC);
        StringBuilder sb = new StringBuilder();
        sb.append("----- MINI STATEMENT -----\n\n");
        for (TransactionEntity tx : transactions) {
            sb.append(tx.getType()).append(" | ").append("\n");
            sb.append("Rs ").append(tx.getAmount()).append("\n");
            sb.append("Bal: ").append(tx.getBalance()).append("\n");
            sb.append(tx.getDateTime()).append("\n");
            sb.append("------------------------\n");
        }
        statementTextArea.setText(sb.toString());
    }
    @FXML
    public void Back() throws IOException {
        javafxUtil.switchScene("ATM");
    }
}
