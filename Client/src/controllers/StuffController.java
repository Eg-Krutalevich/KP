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
import javafx.scene.control.cell.PropertyValueFactory;
import entities.ForbiddenStuff;
import javafx.stage.Stage;

import java.io.IOException;

public class StuffController extends LogInWindowController{
    @FXML
    private TableView<ForbiddenStuff> AvailableStuffTableView;

    @FXML
    private TableColumn<ForbiddenStuff, String> StuffIDColumn;

    @FXML
    private TableColumn<ForbiddenStuff, String> stuff;

    @FXML
    private TableColumn<ForbiddenStuff, String> punishment;

    private ObservableList<ForbiddenStuff> forbiddenStuffData = FXCollections.observableArrayList();

    @FXML
    private Button goBack;

    @FXML
    void initialize() {
        try {
            Client.os.writeObject("getAllStuff");

            String id = (String) Client.is.readObject();
            String[] mp = id.split(";");

            int count = mp.length;

            for (int i = 0; i < count; ++i) {
                String[] tmp = mp[i].split(",");
                forbiddenStuffData.add(new ForbiddenStuff(tmp[0], tmp[1], tmp[2]));
            }

            StuffIDColumn.setCellValueFactory(new PropertyValueFactory<ForbiddenStuff, String>("id"));
            stuff.setCellValueFactory(new PropertyValueFactory<ForbiddenStuff, String>("stuff"));
            punishment.setCellValueFactory(new PropertyValueFactory<ForbiddenStuff, String>("punishment"));
            AvailableStuffTableView.setItems(forbiddenStuffData);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

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
}
