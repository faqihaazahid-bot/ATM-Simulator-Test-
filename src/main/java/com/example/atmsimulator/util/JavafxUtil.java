package com.example.atmsimulator.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JavafxUtil {
    private final ApplicationContext springContext;
    private static Stage stage;
    public static Stage getStage() {
        return stage;
    }
    public static void setStage(Stage stage) {

        JavafxUtil.stage = stage;
    }
    public JavafxUtil(ApplicationContext springContext) {

        this.springContext = springContext;
    }
    public void switchScene(String sceneName) throws IOException {
        FXMLLoader loader = new FXMLLoader(JavafxUtil.class.getResource("/fxml/" + sceneName + ".fxml"));
        loader.setControllerFactory(springContext::getBean);
        Parent root=loader.load();
        stage.setTitle("welcome page");
        Scene scene=new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
