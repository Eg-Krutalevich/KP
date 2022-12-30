package controllers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class MainMenuGUI extends Application {


    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(MainMenuGUI.class.getResource("/client/LogInWindow.fxml"));
        String css = Objects.requireNonNull(MainMenuGUI.class.getResource("/client/styles/font.css")).toExternalForm();
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        scene.getStylesheets().add(css);
        stage.setScene(scene);
        stage.show();
    }

    public void run() {
        Application.launch();
    }
}