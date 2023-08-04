package com.example.runge_kutta_lotka_volterra;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

//scene.getStylesheets().add("path/stylesheet.css");
public class HelloApplication extends Application {
    public static Scene scene;

    @Override
    public void start(Stage stage) throws Exception {

        scene = new Scene(loadFXML("hello-view"), 1150, 510);
        scene.getStylesheets().add("java/resources/stylesheet.css");
        stage.setScene(scene);
        stage.setTitle("Lotka Volterra equation RK4 solver");
        stage.show();

    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}
