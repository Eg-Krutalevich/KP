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
import entities.Airport;
import javafx.stage.Stage;

import java.io.IOException;

public class AirportsController {

    @FXML
    private TableView<Airport> AvailableAirportTableView;

    @FXML
    private TableColumn<Airport, String> AirportIDColumn;

    @FXML
    private TableColumn<Airport, String> name;

    @FXML
    private TableColumn<Airport, String> country;

    @FXML
    private TableColumn<Airport, String> city;

    @FXML
    private TableColumn<Airport, String> place;

    @FXML
    private Button goBack;

    private ObservableList<Airport> airportData = FXCollections.observableArrayList();

    @FXML
    void initialize() {
        try {
            Client.os.writeObject("getAllAirport");

            String id = (String) Client.is.readObject();
            String[] mp = id.split(";");

            int count = mp.length;

            for (int i = 0; i < count; ++i) {
                String[] tmp = mp[i].split(",");
                airportData.add(new Airport(tmp[0], tmp[1], tmp[2], tmp[3], tmp[4]));
            }

            AirportIDColumn.setCellValueFactory(new PropertyValueFactory<Airport, String>("id"));
            name.setCellValueFactory(new PropertyValueFactory<Airport, String>("name_airport"));
            country.setCellValueFactory(new PropertyValueFactory<Airport, String>("country"));
            city.setCellValueFactory(new PropertyValueFactory<Airport, String>("city"));
            place.setCellValueFactory(new PropertyValueFactory<Airport, String>("place"));
            AvailableAirportTableView.setItems(airportData);
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
