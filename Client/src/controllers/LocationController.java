package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LocationController extends LogInWindowController{

    @FXML
    private Button goBack;

    @FXML
    public void initialize() {
        goBack.setOnMouseClicked(event -> {
            //openNewSceneExit("/client/ClientMainWindow.fxml");
            switchScene(event,"/client/ClientMain.fxml");
        });
    }

    public void openNewSceneExit(String window) {
        goBack.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(window));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
