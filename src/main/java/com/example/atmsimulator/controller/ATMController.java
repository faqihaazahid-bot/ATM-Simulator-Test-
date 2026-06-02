package com.example.atmsimulator.controller;

import com.example.atmsimulator.util.JavafxUtil;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;

import java.io.IOException;

@Controller
public class ATMController  {
    private final ApplicationContext springContext;

    @FXML
    private Button DEPOSITButton;
    @FXML
    private Button PINCHANGEButton;
    @FXML
    private Button CASHWITHDRAWButton;
    @FXML
    private Button MINISTATEMENTButton;
    @FXML
    private Button BALANCEENQUIRYButton;
    @FXML
    private Button LOGOUTButton;
    @FXML
    private BorderPane borderPane;
    @FXML
    private Button MANAGEACCOUNTButton;

    public ATMController(ApplicationContext springContext) {
        this.springContext = springContext;
    }

    @FXML
    public void Logout(){
        switchView("loginPage");
    }
    @FXML
    public void Deposit(){
        switchView("depositPage");
    }
    @FXML
    public void PinChange(){
       switchView("pinChangePage");
    }
    @FXML
    public void CashWithdraw(){
        switchView("withdrawPage");
    }
    @FXML
    public void MiniStatement(){
        switchView("miniStatementPage");
    }
    @FXML
    public void BalanceEnquiry(){
        switchView("balanceEnquiryPage");
    }
    @FXML
    public void ManageAccount(){
        switchView("manageAccountPage");
    }
    private void switchView(String ViewName) {
        try {
            FXMLLoader loader = new FXMLLoader(JavafxUtil.class.getResource("/fxml/" + ViewName + ".fxml"));
            loader.setControllerFactory(springContext::getBean);
            Parent page=loader.load();
            JavafxUtil.getStage().setTitle("ATM");
            borderPane.setCenter(page);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
