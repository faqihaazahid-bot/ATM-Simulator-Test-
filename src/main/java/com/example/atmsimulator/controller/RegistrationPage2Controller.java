package com.example.atmsimulator.controller;

import com.example.atmsimulator.entity.UserEntity;
import com.example.atmsimulator.service.UserService;
import com.example.atmsimulator.util.JavafxUtil;
import com.example.atmsimulator.util.SessionData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;

import static com.example.atmsimulator.JavaFxApplication.alert;
import static com.example.atmsimulator.JavaFxApplication.confirmAlert;

@Controller
public class RegistrationPage2Controller implements Initializable {
    private final UserService userService;
    private final SessionData sessionData ;
    public final JavafxUtil javafxUtil;

    public RegistrationPage2Controller(UserService userService, SessionData sessionData, JavafxUtil javafxUtil) {
        this.userService = userService;
        this.sessionData = sessionData;
        this.javafxUtil = javafxUtil;
    }

    @FXML
    private Button CancelButton;
    @FXML
    private Button BackButton;
    @FXML
    private Text formNumberText;
    @FXML
    private ChoiceBox<String> EducationalQualificationChoiceBox;
    @FXML
    private ChoiceBox<String> IncomeChoiceBox;
    @FXML
    private ChoiceBox<String> CategoryChoiceBox;
    @FXML
    private ChoiceBox<String> ReligionChoiceBox;
    @FXML
    private Button SubmitButton;
    @FXML
    private ChoiceBox<String> OccupationChoiceBox;
    @FXML
    private CheckBox SeniorCitizenCheckBox;
    @FXML
    private CheckBox ExistingAccountCheckBox;
    @FXML
    private CheckBox AtmCardCheckBox;
    @FXML
    private CheckBox ChequeBookCheckBox;
    @FXML
    private CheckBox EStatementCheckBox;
    @FXML
    private CheckBox InternetBankingCheckBox;
    @FXML
    private CheckBox MobileBankingCheckBox;
    @FXML
    private CheckBox EmailAlertCheckBox;

    ObservableList<String> EducationalQualification = FXCollections.observableArrayList(List.of("Non-Graduate", "Graduate", "Post-Graduate", "Doctrate", "Other"));
    ObservableList<String> Income = FXCollections.observableArrayList(List.of("Null", "<150,000", "<250,000", "500,000", "upto 1,000,000"));
    ObservableList<String> Category = FXCollections.observableArrayList(List.of("General", "Saving", "Fixed Deposit", "Recurring Deposit", "Other"));
    ObservableList<String> Religion = FXCollections.observableArrayList(List.of("Hindu", "Muslim", "Sikh", "Christian", "Other"));
    ObservableList<String> Occupation = FXCollections.observableArrayList(List.of("Salaried", "Self-Employed", "Business", "Student", "Retired", "Other"));

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        EducationalQualificationChoiceBox.setItems(EducationalQualification);
        IncomeChoiceBox.setItems(Income);
        CategoryChoiceBox.setItems(Category);
        ReligionChoiceBox.setItems(Religion);
        OccupationChoiceBox.setItems(Occupation);
        formNumberText.setText("FORM NO: " + SessionData.formNumber);

        if (sessionData.tempUser != null) {
            UserEntity user = sessionData.tempUser;
            EducationalQualificationChoiceBox.setValue(user.getEducation());
            IncomeChoiceBox.setValue(user.getIncome());
            CategoryChoiceBox.setValue(user.getCategory());
            ReligionChoiceBox.setValue(user.getReligion());
            OccupationChoiceBox.setValue(user.getOccupation());
            SeniorCitizenCheckBox.setSelected("Yes".equals(user.getSeniorCitizen()));
            ExistingAccountCheckBox.setSelected("Yes".equals(user.getExistingAccount()));

            if (user.getServices() != null) {
                String services = user.getServices();
                AtmCardCheckBox.setSelected(services.contains("ATM Card"));
                MobileBankingCheckBox.setSelected(services.contains("Mobile Banking"));
                InternetBankingCheckBox.setSelected(services.contains("Internet Banking"));
                EmailAlertCheckBox.setSelected(services.contains("Email Alert"));
                EStatementCheckBox.setSelected(services.contains("E-Statements"));
                ChequeBookCheckBox.setSelected(services.contains("Cheque Book"));
            }
        }
    }
    private String generatePassword() {
        Random random = new Random();
        int number = 1000 + random.nextInt(9000);
        return String.valueOf(number);
    }

    @FXML
     public void Cancel() throws IOException {

        if (sessionData.currentCNIC != null) {
            confirmAlert.setTitle("Confirm Registration Cancel");
            confirmAlert.setHeaderText("Registration Cancel");
            confirmAlert.setContentText("Are you sure you want to cancel registration?");
            Optional<ButtonType> result = confirmAlert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                userService.deleteByCNICNumber(sessionData.currentCNIC);
                confirmAlert.setAlertType(Alert.AlertType.INFORMATION);
                confirmAlert.setContentText("Your registration is canceled successfully!");
                confirmAlert.showAndWait();
            }
        } else {
            alert.setContentText("No user found to cancelling registration!");
            alert.showAndWait();
        }
        sessionData.currentCNIC = null;
        sessionData.tempUser = null;
        sessionData.formNumber = null;
        sessionData.isEditing = false;
        javafxUtil.switchScene("loginPage");
    }
    private String buildServices() {
        StringBuilder services = new StringBuilder();

        if (AtmCardCheckBox.isSelected()) {
            services.append("ATM Card, ");
        }
        if (MobileBankingCheckBox.isSelected()) {
            services.append("Mobile Banking, ");
        }
        if (InternetBankingCheckBox.isSelected()) {
            services.append("Internet Banking, ");
        }
        if (EmailAlertCheckBox.isSelected()) {
            services.append("Email Alert, ");
        }
        if (EStatementCheckBox.isSelected()) {
            services.append("E-Statements, ");
        }
        if (ChequeBookCheckBox.isSelected()) {
            services.append("Cheque Book, ");
        }
        return services.toString();
    }
  @FXML
  public void Submit() throws IOException {

      if (EducationalQualificationChoiceBox.getValue() == null) {
          EducationalQualificationChoiceBox.requestFocus();
          alert.setContentText("EducationalQualification cannot be blank");
          alert.showAndWait();
          return;
      } else if (IncomeChoiceBox.getValue() == null) {
          IncomeChoiceBox.requestFocus();
          alert.setContentText("Income cannot be blank");
          alert.showAndWait();
          return;
      } else if (CategoryChoiceBox.getValue() == null) {
          CategoryChoiceBox.requestFocus();
          alert.setContentText("Category cannot be blank");
          alert.showAndWait();
          return;
      } else if (ReligionChoiceBox.getValue() == null) {
          ReligionChoiceBox.requestFocus();
          alert.setContentText("Religion cannot be blank");
          alert.showAndWait();
          return;
      } else if (OccupationChoiceBox.getValue() == null) {
          OccupationChoiceBox.requestFocus();
          alert.setContentText("Occupation cannot be blank");
          alert.showAndWait();
          return;
      }

      if (sessionData.isEditing) {
          UserEntity user = sessionData.tempUser;
          user.setEducation(EducationalQualificationChoiceBox.getValue());
          user.setIncome(IncomeChoiceBox.getValue());
          user.setCategory(CategoryChoiceBox.getValue());
          user.setReligion(ReligionChoiceBox.getValue());
          user.setOccupation(OccupationChoiceBox.getValue());
          if (SeniorCitizenCheckBox.isSelected()) {
              user.setSeniorCitizen("Yes");
          } else {
              user.setSeniorCitizen("No");
          }
          if (ExistingAccountCheckBox.isSelected()) {
              user.setExistingAccount("Yes");
          } else {
              user.setExistingAccount("No");
          }
          user.setServices(buildServices());
          userService.save(user);
          alert.setAlertType(Alert.AlertType.INFORMATION);
          alert.setContentText("Your account has been updated successfully!");
          alert.showAndWait();
          javafxUtil.switchScene("ATM");
      } else {
          UserEntity user = sessionData.tempUser;
          user.setEducation(EducationalQualificationChoiceBox.getValue());
          user.setIncome(IncomeChoiceBox.getValue());
          user.setCategory(CategoryChoiceBox.getValue());
          user.setReligion(ReligionChoiceBox.getValue());
          user.setOccupation(OccupationChoiceBox.getValue());
          if (SeniorCitizenCheckBox.isSelected()) {
              user.setSeniorCitizen("Yes");
          } else {
              user.setSeniorCitizen("No");
          }
          if (ExistingAccountCheckBox.isSelected()) {
              user.setExistingAccount("Yes");
          } else {
              user.setExistingAccount("No");
          }
          user.setServices(buildServices());
          String pin = generatePassword();
          user.setPin(pin);
          userService.save(user);
          alert.setAlertType(Alert.AlertType.INFORMATION);
          alert.setContentText("Your Account is Created!\nPIN: " + pin);
          alert.showAndWait();
          javafxUtil.switchScene("depositPage");
      }
  }
        @FXML
        public void Back() throws IOException {

        if(!sessionData.isEditing) {
            sessionData.cnicAvalibleWhileReg=true;
        }
        if (sessionData.tempUser != null) {
             UserEntity user = sessionData.tempUser;
             user.setEducation(EducationalQualificationChoiceBox.getValue());
             user.setIncome(IncomeChoiceBox.getValue());
             user.setCategory(CategoryChoiceBox.getValue());
             user.setReligion(ReligionChoiceBox.getValue());
             user.setOccupation(OccupationChoiceBox.getValue());
                if (SeniorCitizenCheckBox.isSelected()) {
                    user.setSeniorCitizen("Yes");
                } else {
                    user.setSeniorCitizen("No");
                }
                if (ExistingAccountCheckBox.isSelected()) {
                    user.setExistingAccount("Yes");
                } else {
                    user.setExistingAccount("No");
                }
                user.setServices(buildServices());
            }
            javafxUtil.switchScene("RegistrationPage1");
        }
}

