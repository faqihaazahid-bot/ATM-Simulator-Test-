package com.example.atmsimulator.controller;

import com.example.atmsimulator.entity.UserEntity;
import com.example.atmsimulator.service.UserService;
import com.example.atmsimulator.util.JavafxUtil;
import com.example.atmsimulator.util.SessionData;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import org.springframework.stereotype.Controller;

import java.io.IOException;

import static com.example.atmsimulator.JavaFxApplication.alert;


@Controller
public class BalanceEnquiryPageController {
    private final JavafxUtil javafxUtil;
    private final UserService userService;
    private final SessionData sessionData;

    @FXML
    private Text currentBalanceText;
    @FXML
    private Button BACKButton;

    public BalanceEnquiryPageController(JavafxUtil javafxUtil, UserService userService1, SessionData sessionData) {
        this.javafxUtil = javafxUtil;
        this.userService = userService1;
        this.sessionData = sessionData;
    }

    @FXML
    public void initialize() {
        UserEntity user = userService.findByCNICNumber(sessionData.currentCNIC).orElseThrow(() -> new RuntimeException("User not found"));
        currentBalanceText.setText("YOUR CURRENT ACCOUNT BALANCE is Rs : " + user.getBalance());
    }

    @FXML
    public void Back() throws IOException {
        javafxUtil.switchScene("ATM");
    }
}
