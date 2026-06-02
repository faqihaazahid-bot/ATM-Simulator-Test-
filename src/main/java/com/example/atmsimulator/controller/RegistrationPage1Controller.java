package com.example.atmsimulator.controller;

import com.example.atmsimulator.entity.UserEntity;
import com.example.atmsimulator.service.UserService;
import com.example.atmsimulator.util.JavafxUtil;
import com.example.atmsimulator.util.SessionData;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.example.atmsimulator.JavaFxApplication.alert;

@Controller
public class RegistrationPage1Controller implements Initializable {

    private final UserService userService;
    private final JavafxUtil javafxUtil;
    private final SessionData sessionData;

    public RegistrationPage1Controller(UserService userService, JavafxUtil javafxUtil, SessionData sessionData) {
        this.userService = userService;
        this.javafxUtil = javafxUtil;
        this.sessionData = sessionData;
    }

    @FXML
    private Text formNumberText;
    @FXML
    private TextField CNICNumberTextField;
    @FXML
    private TextField NameTextField;
    @FXML
    private TextField fatherNameTextField;
    @FXML
    private TextField EmailAddressTextField;
    @FXML
    private DatePicker BirthDatePicker;
    @FXML
    private TextField AddressTextField;
    @FXML
    private TextField CityTextField;
    @FXML
    private TextField StateTextField;
    @FXML
    private Button NextButton;
    @FXML
    private RadioButton genderMaleRadioButton;
    @FXML
    private ToggleGroup gender;
    private String genderValue = "";
    @FXML
    private RadioButton genderFemaleRadioButton;
    @FXML
    private RadioButton MarriedStatusMarriedRadioButton;
    @FXML
    private ToggleGroup MarriedStatus;
    private String marriedStatusValue = "";
    @FXML
    private RadioButton MarriedStatusUnmarriedRadioButton;
    @FXML
    private RadioButton MarriedStatusOtherRadioButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        if (sessionData.formNumber == null) {
            sessionData.formNumber = userService.generateUniqueFormNumber();
        }
        formNumberText.setText("APPLICATION FORM NO. " + sessionData.formNumber);
        CNICNumberTextField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null && !newVal.matches("\\d*")) {
                CNICNumberTextField.setText(oldVal);
            }
            if (newVal != null && newVal.length() > 13) {
                CNICNumberTextField.setText(oldVal);
            }
        });
        NameTextField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.matches("[a-zA-Z ]*")) {
                NameTextField.setText(newVal.replaceAll("[^a-zA-Z ]", ""));
            }
        });
        fatherNameTextField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.matches("[a-zA-Z ]*")) {
                fatherNameTextField.setText(newVal.replaceAll("[^a-zA-Z ]", ""));
            }
        });
        CityTextField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.matches("[a-zA-Z ]*")) {
                CityTextField.setText(newVal.replaceAll("[^a-zA-Z ]", ""));
            }
        });
        StateTextField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.matches("[a-zA-Z ]*")) {
                StateTextField.setText(newVal.replaceAll("[^a-zA-Z ]", ""));
            }
        });
        AddressTextField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.matches("[a-zA-Z0-9 ,/]*")) {
                AddressTextField.setText(newVal.replaceAll("[^a-zA-Z0-9 ,/]", ""));
            }
        });
        EmailAddressTextField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.matches("[a-zA-Z0-9@._-]*")) {
                EmailAddressTextField.setText(newVal.replaceAll("[^a-zA-Z0-9@._-]", ""));
            }
        });
        EmailAddressTextField.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) {
                String email = EmailAddressTextField.getText();
                if (email != null && !email.isBlank() && !email.contains("@")) {
                    EmailAddressTextField.setText(email + "@gmail.com");
                }
            }
        });
        gender.selectedToggleProperty().addListener((obs, o, n) -> {
            if (n == genderMaleRadioButton)
                genderValue = "Male";
            else if (n == genderFemaleRadioButton)
                genderValue = "Female";
        });
        MarriedStatus.selectedToggleProperty().addListener((obs, o, n) -> {
            if (n == MarriedStatusMarriedRadioButton)
                marriedStatusValue = "Married";
            else if (n == MarriedStatusUnmarriedRadioButton)
                marriedStatusValue = "Unmarried";
            else if (n == MarriedStatusOtherRadioButton)
                marriedStatusValue = "Other";
        });

        if (sessionData.tempUser != null) {
            UserEntity user = sessionData.tempUser;
            CNICNumberTextField.setText(user.getCNICNumber());
            if (sessionData.isEditing)
                CNICNumberTextField.setDisable(true);
            NameTextField.setText(user.getName());
            fatherNameTextField.setText(user.getFatherName());
            EmailAddressTextField.setText(user.getEmail());
            AddressTextField.setText(user.getAddress());
            CityTextField.setText(user.getCity());
            StateTextField.setText(user.getState());
            BirthDatePicker.setValue(user.getDateOfBirth());

            genderValue = user.getGender();
            marriedStatusValue = user.getMaritalStatus();

            if ("Male".equals(user.getGender())) {
                genderMaleRadioButton.setSelected(true);
            } else if ("Female".equals(user.getGender())) {
                genderFemaleRadioButton.setSelected(true);
            }

            if ("Married".equals(user.getMaritalStatus())) {
                MarriedStatusMarriedRadioButton.setSelected(true);
            } else if ("Unmarried".equals(user.getMaritalStatus())) {
                MarriedStatusUnmarriedRadioButton.setSelected(true);
            } else if ("Other".equals(user.getMaritalStatus())) {
                MarriedStatusOtherRadioButton.setSelected(true);
            }
            sessionData.currentCNIC = user.getCNICNumber();
        }
    }
    @FXML
    public void Next() throws IOException {
        if (NameTextField.getText().isBlank()) {
            NameTextField.requestFocus();
            alert.setContentText("Name field cannot be blank");
            alert.showAndWait();
            return;
        } else if (EmailAddressTextField.getText().isBlank()) {
            EmailAddressTextField.requestFocus();
            alert.setContentText("Email address field cannot be blank");
            alert.showAndWait();
            return;
        } else if (AddressTextField.getText().isBlank()) {
            AddressTextField.requestFocus();
            alert.setContentText("Address field cannot be blank");
            alert.showAndWait();
            return;
        } else if (CityTextField.getText().isBlank()) {
            CityTextField.requestFocus();
            alert.setContentText("City field cannot be blank");
            alert.showAndWait();
            return;
        } else if (CNICNumberTextField.getText().isBlank()) {
            CNICNumberTextField.requestFocus();
            alert.setContentText("CNIC Number field cannot be blank");
            alert.showAndWait();
            return;
        } else if (CNICNumberTextField.getText().length() < 13) {
            CNICNumberTextField.requestFocus();
            alert.setContentText("CNIC Number field cannot be less then 13 numbers");
            alert.showAndWait();
            return;
        }else if (StateTextField.getText().isBlank()) {
            StateTextField.requestFocus();
            alert.setContentText("State field cannot be blank");
            alert.showAndWait();
            return;
        } else if (BirthDatePicker.getValue() == null) {
            BirthDatePicker.requestFocus();
            alert.setContentText("Please select Date of Birth");
            alert.showAndWait();
            return;
        }
            Optional<UserEntity> existingUser = userService.findByCNICNumber(CNICNumberTextField.getText());
            if (existingUser.isPresent()) {
                if (sessionData.tempUser == null || !existingUser.get().getId().equals(sessionData.tempUser.getId())) {
                    CNICNumberTextField.requestFocus();
                    alert.setContentText("User with "+CNICNumberTextField.getText()+" already exists");
                    alert.showAndWait();
                    return;
                }
            }
            UserEntity user;
            if (sessionData.tempUser != null) {
                user = sessionData.tempUser;
            } else {
                user = new UserEntity();
                user.setCNICNumber(CNICNumberTextField.getText());
            }
            if (!sessionData.isEditing) {
                user.setCNICNumber(CNICNumberTextField.getText());
            }
            user.setName(NameTextField.getText());
            user.setFatherName(fatherNameTextField.getText());
            user.setAddress(AddressTextField.getText());
            user.setEmail(EmailAddressTextField.getText());
            user.setCity(CityTextField.getText());
            user.setState(StateTextField.getText());
            user.setGender(genderValue);
            user.setMaritalStatus(marriedStatusValue );
            user.setDateOfBirth(BirthDatePicker.getValue());
            user.setFormNumber(sessionData.formNumber);
            userService.save(user);
            sessionData.tempUser = user;
            sessionData.currentCNIC = user.getCNICNumber();
            javafxUtil.switchScene("RegistrationPage2");
        }
}
