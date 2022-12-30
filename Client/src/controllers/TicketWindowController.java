package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class TicketWindowController extends LogInWindowController {

    @FXML
    private Button saveButton;

    @FXML
    private Label inAirLab;

    @FXML
    private Label dateLab;

    @FXML
    private Label outTimeLab;

    @FXML
    private Label inTimeLab;

    @FXML
    private Label fullPriceLab;

    @FXML
    private Label ClName;

    @FXML
    private Label ClSurname;

    @FXML
    void initialize() {
        try {
            String ids = (String) Client.is.readObject();
            String[] mparts = ids.split(";");
            String idf = "getTicketInfo," + mparts[1];
            Client.os.writeObject(idf);

            String tickInfo = (String) Client.is.readObject();
            String[] mp = tickInfo.split(",");
            inAirLab.setText(mp[0]);
            dateLab.setText(mp[1]);
            outTimeLab.setText(mp[2]);
            inTimeLab.setText(mp[3]);
            String NS = "getClientName," + mparts[0];
            Client.os.writeObject(NS);

            String ns = (String) Client.is.readObject();
            String[] namesurname = ns.split(",");
            ClName.setText(namesurname[0]);
            ClSurname.setText(namesurname[1]);
            String IDs = "CalculateFullPrice," + mparts[0] + "," + mparts[1];
            Client.os.writeObject(IDs);

            String fullprice = (String) Client.is.readObject();
            fullPriceLab.setText(fullprice);
            String clm = "addInfoToTicket," + mparts[0] + "," + fullprice;
            Client.os.writeObject(clm);

            saveButton.setOnAction(event ->
            {
                String save = "saveTicketToFile," + tickInfo + "," + fullprice;
                try {
                    Client.os.writeObject(save);
                    String del = "deleteUserData," + mparts[0];
                    Client.os.writeObject(del);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
