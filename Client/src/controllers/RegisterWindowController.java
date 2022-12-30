package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class RegisterWindowController extends LogInWindowController{
    @FXML
    private TextField LoginField;

    @FXML
    private PasswordField PasswordField;

    @FXML
    private Button SignUpButton;

    @FXML
    private TextField SignUpName;

    @FXML
    private TextField SignUpSurname;

    @FXML
    private RadioButton SignUpMale;

    @FXML
    private RadioButton SignUpFemale;

    @FXML
    private TextField SignUpCountry;

    @FXML
    private RadioButton SignUpOther;

    @FXML
    private Button goBack;

    @FXML
    void initialize() {
        SignUpButton.setOnMouseClicked(event ->
        {
            String Gender;
            if (SignUpMale.isSelected()) Gender = "Мужской";
            else if (SignUpFemale.isSelected()) Gender = "Женский";
            else Gender = "Дpугой";
            String Name = SignUpName.getText();
            String Surname = SignUpSurname.getText();
            String userName = LoginField.getText();
            String Password = PasswordField.getText();
            String Country = SignUpCountry.getText();

            String clM = "checkUserInDB," + userName;
            try {
                Client.os.writeObject(clM);
                String mes = (String) Client.is.readObject();
                if (mes.equals("success")) {
                    String clientMessage = "addClient," + Name + "," + Surname + "," + Gender + "," + Country + "," + userName + "," + Password;
                    try {
                        Client.os.writeObject(clientMessage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    switchScene(event,"/client/LogInWindow.fxml");
                } else if (mes.equals("fail")) {
                    LoginField.clear();
                    LoginField.setPromptText("Пользователь с таким логином уже существует!");
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        goBack.setOnMouseClicked(event -> {
            switchScene(event,"/client/LogInWindow.fxml");
        });

    }

    @FXML
    private void handleSignUpMale() {
        if (SignUpMale.isSelected()) {
            SignUpFemale.setSelected(false);
            SignUpOther.setSelected(false);
        }
    }

    @FXML
    private void handleSignUpFemale() {
        if (SignUpFemale.isSelected()) {
            SignUpMale.setSelected(false);
            SignUpOther.setSelected(false);
        }
    }

    @FXML
    private void handleSignUpOther() {
        if (SignUpOther.isSelected()) {
            SignUpMale.setSelected(false);
            SignUpFemale.setSelected(false);
        }
    }
}
