package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import entities.Vacancy;
import javafx.stage.Stage;

import java.io.IOException;

public class VacancyController {
    @FXML
    private TableView<Vacancy> AvailableEmployeeTableView;

    @FXML
    private TableColumn<Vacancy, String> EmployeeIDColumn;

    @FXML
    private TableColumn<Vacancy, String> surname;

    @FXML
    private TableColumn<Vacancy, String> name;

    @FXML
    private TableColumn<Vacancy, String> post;

    @FXML
    private TableColumn<Vacancy, String> salary;

    @FXML
    private Button goBack;

    private ObservableList<Vacancy> vacancyData = FXCollections.observableArrayList();

    @FXML
    void initialize() {
        try {
            Client.os.writeObject("getAllVacancy");

            String id = (String) Client.is.readObject();
            String[] mp = id.split(";");

            int count = mp.length;

            for (int i = 0; i < count; ++i) {
                String[] tmp = mp[i].split(",");
                vacancyData.add(new Vacancy(tmp[0], tmp[1], tmp[2], tmp[3], tmp[4]));
            }

            EmployeeIDColumn.setCellValueFactory(new PropertyValueFactory<Vacancy, String>("id"));
            surname.setCellValueFactory(new PropertyValueFactory<Vacancy, String>("surname"));
            name.setCellValueFactory(new PropertyValueFactory<Vacancy, String>("name"));
            post.setCellValueFactory(new PropertyValueFactory<Vacancy, String>("post"));
            salary.setCellValueFactory(new PropertyValueFactory<Vacancy, String>("salary"));
            AvailableEmployeeTableView.setItems(vacancyData);
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
