package controllers;

import java.io.EOFException;
import java.io.IOException;
import java.util.Objects;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class LogInWindowController {

    protected Parent root;
    protected Stage stage;
    protected Scene scene;

    @FXML
    private TextField LoginField;

    @FXML
    private PasswordField PasswordField;

    @FXML
    private Button AuthSignUpButton;

    @FXML
    private Button LoginSignUpButton;

    @FXML
    private RadioButton AdminLogInWindow;

    @FXML
    private RadioButton UserLogInWindow;

    @FXML
    private Label CautionLabel;

    public String message;

    @FXML
    void initialize() {
        Client.Connect();

        AuthSignUpButton.setOnMouseClicked(event ->
        {
            String loginText = LoginField.getText().trim();
            String loginPassword = PasswordField.getText().trim();

            try {
                try {
                    if (loginText.equals("") || loginPassword.equals(""))
                        CautionLabel.setText("Пожалуйста, проверьте корректность ввода!");
                    else {
                        String clm = "isBlocked," + loginText;
                        Client.os.writeObject(clm);
                        message = (String) Client.is.readObject();
                    }
                } catch (EOFException e) {
                    System.out.println("");
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            if (message != null) {
                if (message.equals("no")) {
                    if (UserLogInWindow.isSelected() && !AdminLogInWindow.isSelected()) {
                        String clientMessage = "checkLoginClient," + loginText + "," + loginPassword;

                        try {
                            Client.os.writeObject(clientMessage);
                            message = (String) Client.is.readObject();
                        } catch (IOException | ClassNotFoundException e) {
                            e.printStackTrace();
                        }

                        if (message.equals("successClient")) {
                            String clM = "sendData," + loginText;

                            try {
                                Client.os.writeObject(clM);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            //openNewScene("/client/ClientMainWindow.fxml");
                            switchScene(event,"/client/ClientMain.fxml");
                        } else if (message.equals("fail"))
                            CautionLabel.setText("Пользователь с такими данными не найден!");
                    } else if (AdminLogInWindow.isSelected() && !UserLogInWindow.isSelected()) {
                        String clientMessage = "checkLoginAdmin," + loginText + "," + loginPassword;

                        try {
                            Client.os.writeObject(clientMessage);
                            message = (String) Client.is.readObject();
                        } catch (IOException | ClassNotFoundException e) {
                            e.printStackTrace();
                        }

                        if (message.equals("successAdmin")) {
                            String clM = "sendData," + loginText;

                            try {
                                Client.os.writeObject(clM);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            //openNewScene("/client/AdminMain.fxml");
                            switchScene(event, "/client/AdminMain.fxml");
                        } else if (message.equals("fail"))
                            CautionLabel.setText("Администратор с такими данными не найден!");
                    } else CautionLabel.setText("Пожалуйста, выберите роль!");
                } else AuthSignUpButton.getScene().getWindow().hide();
            }
        });

        LoginSignUpButton.setOnMouseClicked(event ->
        {
            if (UserLogInWindow.isSelected() && !AdminLogInWindow.isSelected())
                //openNewScene("/client/RegisterWindow.fxml");
                switchScene(event,"/client/RegisterWindow.fxml");
            else if (AdminLogInWindow.isSelected() && !UserLogInWindow.isSelected())
                CautionLabel.setText("Вы можете зарегистрироваться только как пользователь!");
            else CautionLabel.setText("Пожалуйста, выберите роль!");
        });
    }

    public void switchScene(MouseEvent event, String path) {
        try {
            root = FXMLLoader.load(Objects.requireNonNull(MainMenuGUI.class.getResource(path)));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAdminLogInWindow() {
        if (AdminLogInWindow.isSelected()) UserLogInWindow.setSelected(false);
    }

    @FXML
    private void handleUserLogInWindow() {
        if (UserLogInWindow.isSelected()) AdminLogInWindow.setSelected(false);
    }
}

