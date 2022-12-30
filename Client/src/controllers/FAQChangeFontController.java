package controllers;

import entities.FAQ;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class FAQChangeFontController extends LogInWindowController{

    @FXML
    public AnchorPane fontPane;

    @FXML
    private TableView<FAQ> AvailableFAQTableViewChangeFont;

    @FXML
    private TableColumn<FAQ, String> FAQIDColumnChangeFont;

    @FXML
    private TableColumn<FAQ, String> questionChangeFont;
    @FXML
    private TableColumn<FAQ, String> answerChangeFont;

    private ObservableList<FAQ> faqData = FXCollections.observableArrayList();

    @FXML
    private Button goBack;

    @FXML
    private Button goBack1;

    @FXML
    private Button changeLanguage;

    @FXML
    private Button changeFont;

    @FXML
    private Button changeTheme;

    @FXML
    void initialize() {
        try {
            Client.os.writeObject("getAllFAQChangeFont");

            String id = (String) Client.is.readObject();
            String[] mp = id.split(";");

            int count = mp.length;

            for (int i = 0; i < count; ++i) {
                String[] tmp = mp[i].split(",");
                faqData.add(new FAQ(tmp[0], tmp[1], tmp[2]));
            }

            FAQIDColumnChangeFont.setCellValueFactory(new PropertyValueFactory<FAQ, String>("id"));
            questionChangeFont.setCellValueFactory(new PropertyValueFactory<FAQ, String>("question"));
            answerChangeFont.setCellValueFactory(new PropertyValueFactory<FAQ, String>("answer"));
            AvailableFAQTableViewChangeFont.setItems(faqData);
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
    }
}
