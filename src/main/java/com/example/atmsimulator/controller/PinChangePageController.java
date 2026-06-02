package com.example.atmsimulator.controller;

import com.example.atmsimulator.entity.UserEntity;
import com.example.atmsimulator.service.UserService;
import com.example.atmsimulator.util.JavafxUtil;
import com.example.atmsimulator.util.SessionData;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Controller;

import java.io.IOException;

import static com.example.atmsimulator.JavaFxApplication.alert;

@Controller
public class PinChangePageController {

    private final JavafxUtil javafxUtil;
    private final UserService userService;
    private final SessionData sessionData;

    @FXML
    private TextField newPINTextField;
    @FXML
    private TextField reEnterPINTextField;
    @FXML
    private Button PINChangeButton;
    @FXML
    private Button BACKButton;

    public PinChangePageController(UserService userService, JavafxUtil javafxUtil, SessionData sessionData) {
        this.userService = userService;
        this.javafxUtil = javafxUtil;
        this.sessionData = sessionData;
    }

    @FXML
    public void back() throws IOException {
        javafxUtil.switchScene("ATM");
    }

    @FXML
    public void changePIN() {

        if (newPINTextField.getText().isBlank() ||  reEnterPINTextField.getText().isBlank()) {
            alert.setContentText("PIN fields cannot be empty");
            alert.showAndWait();
            return;
        }

        if (!newPINTextField.getText().equals( reEnterPINTextField.getText())) {
            alert.setContentText("PINs do not match");
            alert.showAndWait();
            return;
        }

        if (newPINTextField.getText().length() != 4) {
            alert.setContentText("PIN must be 4 digits");
            alert.showAndWait();
            return;
        }
        UserEntity user = userService.findByCNICNumber(sessionData.currentCNIC).orElseThrow(() -> new RuntimeException("User not found"));
        user.setPin(newPINTextField.getText());
        userService.save(user);
        alert.setAlertType(Alert.AlertType.INFORMATION);
        alert.setContentText("PIN changed successfully!");
        alert.showAndWait();
        newPINTextField.clear();
        reEnterPINTextField.clear();
    }

}
