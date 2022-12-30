package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.net.URI;
import java.util.ResourceBundle;

public class ContactInformationController extends LogInWindowController implements Initializable {

    @FXML
    private Button hyperlink;

    @FXML
    private Button goBack;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        hyperlink.setOnAction(event -> {
            try {
                hyperLinkPage();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        goBack.setOnMouseClicked(event -> {
            switchScene(event,"/client/ClientMain.fxml");
        });

    }

    public void openNewSceneExit(String window) {
        goBack.getScene();

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

    public void hyperLinkPage() throws Exception {
        java.awt.Desktop.getDesktop().browse(URI.create("https://airport.by/"));
    }
}
