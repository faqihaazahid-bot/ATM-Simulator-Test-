package com.example.atmsimulator.controller;

import com.example.atmsimulator.entity.UserEntity;
import com.example.atmsimulator.service.UserService;
import com.example.atmsimulator.util.JavafxUtil;
import com.example.atmsimulator.util.SessionData;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.Optional;

import static com.example.atmsimulator.JavaFxApplication.alert;

@Controller
public class LoginController {
    private final UserService userService;
    private final JavafxUtil javafxUtil;
    private final SessionData sessionData;
    public  boolean isPasswordVisibleForLogin =true;

    @FXML
    private PasswordField PINNumberField;
    @FXML
    private Button CreateAccountButton;
    @FXML
    private TextField PINNumberTextField;
    @FXML
    private Button loginButton;
    @FXML
    private TextField CNICTextField;
    @FXML
    public Button ShowButton;

    public LoginController(UserService userService, JavafxUtil javafxUtil, SessionData sessionData) {
        this.userService = userService;
        this.javafxUtil = javafxUtil;
        this.sessionData = sessionData;
    }

    @FXML
    public void Login() throws IOException {
        if(CNICTextField.getText().isBlank()){
            CNICTextField.requestFocus();
            alert.setContentText("CNIC Number cannot be blank");
            alert.showAndWait();
            return;

        }
        if (PINNumberField.getText().isBlank()) {
            PINNumberField.requestFocus();
            alert.setContentText("PIN cannot be blank");
            alert.showAndWait();
            return;
        }

         Optional<UserEntity> user =userService.findByCNICNumber(CNICTextField.getText());
        if(user.isEmpty()){
            CNICTextField.requestFocus();
            alert.setContentText("CNIC Number/PIN incorrect");
            alert.showAndWait();
        }else{
            UserEntity currentUser = user.get();
            if (!PINNumberField.getText().equals(currentUser.getPin())) {
                alert.setContentText("CNIC Number/PIN incorrect");
                alert.showAndWait();
            } else {
                sessionData.currentCNIC = currentUser.getCNICNumber();
                javafxUtil.switchScene("ATM");
            }

        }
    }
    @FXML
    public void CreateAccount() throws IOException{
        sessionData.tempUser = null;
        sessionData.isEditing = false;
        sessionData.currentCNIC = null;
        javafxUtil.switchScene("RegistrationPage1");

    }
    @FXML
    public void ShowHide(){
        show();
    }

    public void show() {
        if (isPasswordVisibleForLogin) {
            PINNumberTextField.setText(PINNumberField.getText());
            PINNumberTextField.setVisible(true);
            PINNumberField.setVisible(false);
            ShowButton.setText("Hide ");
            isPasswordVisibleForLogin = false;
        } else {
            PINNumberField.setText(PINNumberTextField.getText());
            PINNumberField.setVisible(true);
            PINNumberTextField.setVisible(false);
            ShowButton.setText("Show");
            isPasswordVisibleForLogin = true;
        }
    }

}

