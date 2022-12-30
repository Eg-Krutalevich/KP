package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import entities.User;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;

public class UsersController {
    @FXML
    private TableView<User> UsersTableView;

    @FXML
    private TableColumn<User, String> FlIDColumn;

    @FXML
    private TableColumn<User, String> login;

    @FXML
    private TableColumn<User, String> password;

    @FXML
    private TableColumn<User, String> role;

    @FXML
    private TableColumn<User, String> status;

    @FXML
    private Button goBack;

    private ObservableList<User> data = FXCollections.observableArrayList();

    @FXML
    void initialize() {
        try {
            Client.os.writeObject("getAllUsers");

            String id = (String) Client.is.readObject();
            String[] mp = id.split(";");

            int count = mp.length;
            for (int i = 0; i < count; ++i) {
                String[] tmp = mp[i].split(",");
                data.add(new User(tmp[0], tmp[1], tmp[2], tmp[3], tmp[4]));
            }

            FlIDColumn.setCellValueFactory(new PropertyValueFactory<User, String>("id"));
            login.setCellValueFactory(new PropertyValueFactory<User, String>("login"));
            password.setCellValueFactory(new PropertyValueFactory<User, String>("password"));
            role.setCellValueFactory(new PropertyValueFactory<User, String>("role"));
            status.setCellValueFactory(new PropertyValueFactory<User, String>("status"));
            UsersTableView.setItems(data);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        goBack.setOnAction(event -> {
            openNewSceneExit("/client/AdminMain.fxml");
        });
    }

    private void openNewSceneExit(String window) {
        goBack.getScene().getWindow().hide();

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
