package com.example.atmsimulator.controller;

import com.example.atmsimulator.service.UserService;
import com.example.atmsimulator.util.JavafxUtil;
import com.example.atmsimulator.util.SessionData;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.Optional;

import static com.example.atmsimulator.JavaFxApplication.alert;
import static com.example.atmsimulator.JavaFxApplication.confirmAlert;

@Controller
public class ManageAccountPageController {
    private final JavafxUtil javafxUtil;
    private final SessionData sessionData;
    private final UserService userService;

    @FXML
    private Button UPDATEAccountButton;
    @FXML
    private Button DELETEACCOUNTButton;
    @FXML
    private Button BACKButton;

    public ManageAccountPageController(JavafxUtil javafxUtil, SessionData sessionData, UserService userService) {
        this.javafxUtil = javafxUtil;
        this.sessionData = sessionData;
        this.userService = userService;
    }

    @FXML
    public void updateAccount() throws IOException {
        sessionData.isEditing = true;
        sessionData.tempUser = userService.findByCNICNumber(sessionData.currentCNIC).orElseThrow();
        javafxUtil.switchScene("RegistrationPage1");
    }

    @FXML
    public void deleteAccount() throws IOException {
        confirmAlert.setTitle("Confirm Deletion");
        confirmAlert.setHeaderText("Delete Account");
        confirmAlert.setContentText("Are you sure you want to delete this account?");
        Optional<ButtonType> result = confirmAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
        if (sessionData.currentCNIC != null) {
            userService.deleteByCNICNumber(sessionData.currentCNIC);

            confirmAlert.setAlertType(Alert.AlertType.INFORMATION);
            confirmAlert.setContentText("Account is deleted successfully!");
            confirmAlert.showAndWait();
        } else {
            alert.setContentText("No user found to delete!");
            alert.showAndWait();
        }
        sessionData.currentCNIC = null;
        sessionData.tempUser = null;
        sessionData.formNumber = null;
        sessionData.isEditing = false;

        javafxUtil.switchScene("loginPage");
    }}
    @FXML
    public void back() throws IOException {
        if(sessionData.isEditing){
            javafxUtil.switchScene("LoginPage");
        }else {
            javafxUtil.switchScene("ATM");
        }

    }
}
