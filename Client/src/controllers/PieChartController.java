package controllers;

import java.io.EOFException;
import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class PieChartController {

    @FXML
    private PieChart dayTimeStat;

    @FXML
    private Button goBack;

    @FXML
    void initialize() {
        String clm = "getDataForPie";
        int cm = 0;
        int cd = 0;
        int ce = 0;
        int cn = 0;
        try {
            try {
                Client.os.writeObject(clm);
                String data = (String) Client.is.readObject();
                String[] count = data.split(",");
                cm = Integer.parseInt(count[0]);
                cd = Integer.parseInt(count[1]);
                ce = Integer.parseInt(count[2]);
                cn = Integer.parseInt(count[3]);
            } catch (EOFException e) {
                System.out.println("");
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Утpо", cm),
                new PieChart.Data("День", cd),
                new PieChart.Data("Вечеp", ce),
                new PieChart.Data("Ночь", cn)
        );


        dayTimeStat.setData(pieChartData);
        dayTimeStat.visibleProperty();

        goBack.setOnMouseClicked(event -> {
            openNewSceneExit("/client/AdminMain.fxml");
        });
    }

    public void openNewSceneExit(String window) {
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
