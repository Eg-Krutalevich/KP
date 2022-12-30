package controllers;

import entities.Flight;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;

public class FlightsController {
    @FXML
    private TableView<Flight> AvailableFlyTableView;

    @FXML
    private TableColumn<Flight, String> FlIDColumn;

    @FXML
    private TableColumn<Flight, String> inAirport;

    @FXML
    private TableColumn<Flight, String> date;

    @FXML
    private TableColumn<Flight, String> outTime;

    @FXML
    private TableColumn<Flight, String> inTime;

    @FXML
    private TableColumn<Flight, String> seatsAmount;

    @FXML
    private TableColumn<Flight, String> price;

    @FXML
    private TextField FlyToAirportField;

    @FXML
    private TextField FlyOutDateTF;

    @FXML
    private Button finder;

    @FXML
    private Button goBack;


    private ObservableList<Flight> flData = FXCollections.observableArrayList();

    @FXML
    void initialize() {
        try {
            Client.os.writeObject("getAllFlights");

            String id = (String) Client.is.readObject();
            String[] mp = id.split(";");

            int count = mp.length;

            for (int i = 0; i < count; ++i) {
                String[] tmp = mp[i].split(",");
                flData.add(new Flight(tmp[0], tmp[1], tmp[2], tmp[3], tmp[4], tmp[5], tmp[6]));
            }

            FlIDColumn.setCellValueFactory(new PropertyValueFactory<Flight, String>("id"));
            inAirport.setCellValueFactory(new PropertyValueFactory<Flight, String>("Airport"));
            date.setCellValueFactory(new PropertyValueFactory<Flight, String>("date"));
            outTime.setCellValueFactory(new PropertyValueFactory<Flight, String>("outTime"));
            inTime.setCellValueFactory(new PropertyValueFactory<Flight, String>("inTime"));
            seatsAmount.setCellValueFactory(new PropertyValueFactory<Flight, String>("seatsAmount"));
            price.setCellValueFactory(new PropertyValueFactory<Flight, String>("price"));
            AvailableFlyTableView.setItems(flData);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        finder.setOnAction(event ->
        {
            String airport = FlyToAirportField.getText().trim();
            String Date = FlyOutDateTF.getText().trim();

            String fl = airport + "," + Date;
            String clM = "сheckAvFl," + fl;

            try {
                Client.os.writeObject(clM);
                String str = (String) Client.is.readObject();
                if (str.equals("success")) {
                    String clM1 = "getAvFlID," + fl;
                    Client.os.writeObject(clM1);
                    String ids = (String) Client.is.readObject();
                    String clM2 = "senddata," + ids + "," + " ";
                    Client.os.writeObject(clM2);
                } else if (str.equals("fail")) System.out.println("No available flights");
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            try {
                String id = (String) Client.is.readObject();
                String[] mp = id.split(",");
                String id1 = mp[0] + "," + mp[1];
                String clm = "getAvFl," + id1;
                Client.os.writeObject(clm);
                String data = (String) Client.is.readObject();
                String[] count = id.split(",");
                String[] messParts = data.split(";");
                flData.clear();
                for (int i = 0; i < Integer.parseInt(mp[0]); i++) {
                    String[] mesParts = messParts[i].split(",");
                    if (!mesParts[5].equals("0"))
                        flData.add(new Flight(mesParts[0], mesParts[1], mesParts[2], mesParts[3], mesParts[4], mesParts[5], mesParts[6]));
                }
                AvailableFlyTableView.setItems(flData);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });


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