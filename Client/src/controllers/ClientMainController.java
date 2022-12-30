package controllers;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ClientMainController extends LogInWindowController{

    public Button locationButton;

    public Button importantButton;
    public Button goWebButton;
    public Button bannedButton;
    public Button faqButton;
    @FXML
    private TextField FirstTF;

    @FXML
    private TextField SecondTF;

    @FXML
    private ComboBox<String> PassengerAgeComboBox;

    @FXML
    private Button LetsFlyButton;

    @FXML
    private ComboBox<String> LaggageComboBox;


    @FXML
    private Button goBack;


    @FXML
    void initialize() {

        PassengerAgeComboBox.getItems().addAll("Взpослые(16+ лет)", "Подpостки(12-15 лет)", "Дети(2-14 лет)", "Младенцы(0-2 года)");
        LaggageComboBox.getItems().addAll("Pучная кладь", "Дополнительная pучная кладь", "Основной багаж", "Дополнительный основной багаж", "Кpупногабаритный багаж");

        locationButton.setOnMouseClicked(event -> {
            switchScene(event,"/client/Location.fxml");
        });

        LetsFlyButton.setOnMouseClicked(event ->
        {
            String userName;
            String airport = SecondTF.getText().trim();
            String date = FirstTF.getText().trim();
            String age = PassengerAgeComboBox.getValue();
            String laggage = LaggageComboBox.getValue();
            try {
                userName = (String) Client.is.readObject();
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

            String data = age + "," + laggage + "," + userName;
            String clientMessage1 = "addInfoToUserData," + data;

            try {
                Client.os.writeObject(clientMessage1);
            } catch (IOException e) {
                e.printStackTrace();
            }

            String fl = airport + "," + date;
            String clM = "сheckAvFl," + fl;
            System.out.println(clM);

            try {
                Client.os.writeObject(clM);
                String str = (String) Client.is.readObject();
                System.out.println("Data received on MainClintWindow with CheckFL:" + str);
                if (str.equals("success")) {
                    String clM1 = "getAvFlID," + fl;
                    Client.os.writeObject(clM1);
                    System.out.println("Send to get ID:" + clM1);
                    String ids = (String) Client.is.readObject();
                    System.out.println("" + ids);
                    String clM2 = "senddata," + ids + "," + userName;
                    System.out.println(clM2);
                    Client.os.writeObject(clM2);

                    switchScene(event,"/client/AvailableFlightsWindow.fxml");

                } else if (str.equals("fail")) System.out.println("No available flights");
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        goWebButton.setOnMouseClicked(event -> {
            switchScene(event,"/client/ContactInformation.fxml");
        });

        importantButton.setOnMouseClicked(event -> {
            switchScene(event,"/client/Information.fxml");
        });

        bannedButton.setOnMouseClicked(event -> {
            switchScene(event,"/client/Stuff.fxml");
        });

        faqButton.setOnMouseClicked(event -> {
            switchScene(event,"/client/FAQ.fxml");
        });

        goBack.setOnMouseClicked(event -> {
            switchScene(event,"/client/LogInWindow.fxml");
        });

    }

}
