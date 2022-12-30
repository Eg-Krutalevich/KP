package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import entities.FAQ;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class FAQChangeTheme extends LogInWindowController {

    @FXML
    private AnchorPane fontPane;

    @FXML
    private TableView<FAQ> AvailableFAQTableViewChangeTheme;

    @FXML
    private TableColumn<FAQ, String> FAQIDColumnChangeTheme;

    @FXML
    private TableColumn<FAQ, String> answerChangeTheme;

    @FXML
    private TableColumn<FAQ, String> questionChangeTheme;

    private ObservableList<FAQ> faqData = FXCollections.observableArrayList();

    @FXML
    private Button changeLanguage;

    @FXML
    private Button changeTheme;

    @FXML
    private Button changeFont;

    @FXML
    private Button goBack;

    @FXML
    private Button goBack1;

    @FXML
    void initialize() {
        try {
            Client.os.writeObject("getAllFAQChangeTheme");

            String id = (String) Client.is.readObject();
            String[] mp = id.split(";");

            int count = mp.length;

            for (int i = 0; i < count; ++i) {
                String[] tmp = mp[i].split(",");
                faqData.add(new FAQ(tmp[0], tmp[1], tmp[2]));
            }

            FAQIDColumnChangeTheme.setCellValueFactory(new PropertyValueFactory<FAQ, String>("id"));
            questionChangeTheme.setCellValueFactory(new PropertyValueFactory<FAQ, String>("question"));
            answerChangeTheme.setCellValueFactory(new PropertyValueFactory<FAQ, String>("answer"));
            AvailableFAQTableViewChangeTheme.setItems(faqData);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        changeLanguage.setOnMouseClicked(event -> {
            switchScene(event,"/client/FAQBel.fxml");
        });

        changeTheme.setOnMouseClicked(event -> {
            switchScene(event,"/client/FAQChangeTheme.fxml");
        });

        changeFont.setOnMouseClicked(event -> {
            changeFontSize("/client/styles/font.css");
        });

        goBack.setOnMouseClicked(event -> {
            switchScene(event,"/client/FAQ.fxml");
        });

        goBack1.setOnMouseClicked(event -> {
            switchScene(event,"/client/ClientMain.fxml");
        });

    }

    private void changeFontSize(String path) {
        ObservableList<String> styleSheets = fontPane.getStylesheets();
        String css = FAQChangeFontController.class.getResource(path).toExternalForm();
        styleSheets.add(css);
        fontPane.getStyleClass().add("column_1");
        fontPane.getStyleClass().add("column_2");
        fontPane.getStyleClass().add("column_3");
        fontPane.getStyleClass().add("column_4");
    }


}
