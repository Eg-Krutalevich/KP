package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class InformationController extends LogInWindowController implements Initializable {
    @FXML
    private Label count;

    @FXML
    private Label count1;

    @FXML
    private Label count2;

    @FXML
    private TextField textField;

    @FXML
    private Button goBack;

    @FXML
    void textFieldTyped() {
        textField.getText();
        textField.getText().split("\\s+");
    }

    @FXML
    void labelTyped() {
        count.setWrapText(true);
        count1.setWrapText(true);
        count2.setWrapText(true);

        count.setVisible(true);
        count1.setVisible(true);
        count2.setVisible(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println(textField.getText());
        textField.setVisible(true);

        labelTyped();

        goBack.setOnMouseClicked(event -> {
            switchScene(event,"/client/ClientMain.fxml");
        });

    }


}
