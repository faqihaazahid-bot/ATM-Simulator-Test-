package com.example.atmsimulator;

import com.example.atmsimulator.util.JavafxUtil;
import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;

public class JavaFxApplication extends Application {
    private ConfigurableApplicationContext springContext;
    public static Alert alert = new Alert(Alert.AlertType.ERROR);
    public static Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);

    @Override
    public void init() {

        springContext=springBootContext();
    }
    @Override
    public void start(Stage stage) throws IOException {
        JavafxUtil.setStage(stage);
        JavafxUtil util = springContext.getBean(JavafxUtil.class);
        util.switchScene("loginPage");
    }
    @Override
    public void stop() {
        springContext.close();
    }
    private ConfigurableApplicationContext springBootContext(){
        SpringApplicationBuilder builder = new SpringApplicationBuilder(BankingSystem.class);
        builder.headless(false);
        String[] args = getParameters().getRaw().toArray(new String[0]);
        return builder.run(args);
    }
}
