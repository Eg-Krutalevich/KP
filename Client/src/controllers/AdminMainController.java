package controllers;

import java.io.EOFException;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class AdminMainController extends LogInWindowController {

    @FXML
    private MenuItem addFlMenu;

    @FXML
    private MenuItem addEmployeeMenu;

    @FXML
    private MenuItem addAdmin;

    @FXML
    private MenuItem deleteFl;

    @FXML
    private MenuItem deleteAdmin;

    @FXML
    private MenuItem infoFl;

    @FXML
    private MenuItem infoUsers;

    @FXML
    private TextField FirstTF;

    @FXML
    private TextField SecondTF;

    @FXML
    private TextField ThirdTF;

    @FXML
    private TextField SixthTF;

    @FXML
    private TextField FourthTF;

    @FXML
    private TextField FifthTF;

    @FXML
    private Label KeyLabel;

    @FXML
    private Button KeyButton;
    @FXML
    private Label adminlabel;

    @FXML
    private MenuItem blockUser;

    @FXML
    private MenuItem unblockUser;

    @FXML
    private MenuItem infoEmployee;

    @FXML
    private MenuItem top;

    @FXML
    private MenuItem addTopAirport;

    @FXML
    private MenuItem addStuff;

    @FXML
    private MenuItem addFAQ;

    @FXML
    private Button statistics;

    @FXML
    private Button goBack;


    @FXML
    void initialize() {
        try {
            try {
                adminlabel.setText((String) Client.is.readObject());
            } catch (EOFException e) {
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        Visibility(false, false, false, false, false, false, false, false);

        addFlMenu.setOnAction(event ->
        {
            Visibility(true, true, true, true, true, true, true, true);
            setText("Аэропорт прибытия", "Дата", "Время вылета", "Время прибытия", "Цена", "Количество мест", "Добавить", "Введите данные полета:");
            KeyButton.setOnAction(event1 ->
            {
                String clm = "addFl," + FirstTF.getText() + "," + SecondTF.getText() + "," + ThirdTF.getText() + "," + FourthTF.getText() + "," + FifthTF.getText() + "," + SixthTF.getText();
                try {
                    Client.os.writeObject(clm);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        });

        addEmployeeMenu.setOnAction(event ->
        {
            Visibility(false, false, true, true, true, true, true, true);
            setText("", "", "Фамилия", "Имя", "Должность", "Зарплата", "Добавить", "Введите данные сотрудника:");
            KeyButton.setOnAction(event1 ->
            {
                String clm1 = "addEmployee," + ThirdTF.getText() + "," + FourthTF.getText() + "," + FifthTF.getText() + "," + SixthTF.getText();
                try {
                    Client.os.writeObject(clm1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        });

        addAdmin.setOnAction(event ->
        {
            Visibility(false, false, true, true, false, false, true, true);
            setText("", "", "Логин", "Пароль", "", "", "Добавить", "Введите данные администратора:");
            KeyButton.setOnAction(event1 ->
            {
                String clm1 = "addAdmin," + ThirdTF.getText() + "," + FourthTF.getText();
                try {
                    Client.os.writeObject(clm1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        });


        addTopAirport.setOnAction(event -> {
            Visibility(false, false, true, true, true, true, true, true);
            setText("", "", "Название", "Страна", "Город", "Место", "Добавить", "Введите топ-лучших аэропортов:");
            KeyButton.setOnAction(event1 ->
            {
                String clm1 = "addTopAirport," + ThirdTF.getText() + "," + FourthTF.getText() + "," + FifthTF.getText() + "," + SixthTF.getText();
                try {
                    Client.os.writeObject(clm1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        });

        addStuff.setOnAction(event ->
        {
            Visibility(false, false, true, true, false, false, true, true);
            setText("", "", "Предмет", "Наказание", "", "", "Добавить", "Введите запрещенные предметы:");
            KeyButton.setOnAction(event1 ->
            {
                String clm1 = "addStuff," + ThirdTF.getText() + "," + FourthTF.getText();
                try {
                    Client.os.writeObject(clm1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        });

        addFAQ.setOnAction(event ->
        {
            Visibility(false, false, true, true, false, false, true, true);
            setText("", "", "Вопрос", "Ответ", "", "", "Добавить", "Введите вопросы клиентов:");
            KeyButton.setOnAction(event1 ->
            {
                String clm1 = "addFAQ," + ThirdTF.getText() + "," + FourthTF.getText();
                try {
                    Client.os.writeObject(clm1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        });

        deleteFl.setOnAction(event ->
        {
            Visibility(false, true, true, true, false, false, true, true);
            setText("", "Аэропорт прибытия", "Дата", "Время вылета", "", "", "Удалить", "Введите данные полета:");
            KeyButton.setOnAction(event1 ->
            {
                String clm2 = "deleteFl," + SecondTF.getText() + "," + ThirdTF.getText() + "," + FourthTF.getText();
                try {
                    Client.os.writeObject(clm2);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        });

        deleteAdmin.setOnAction(event ->
        {
            Visibility(false, false, true, false, false, false, true, true);
            setText("", "", "Логин", "", "", "", "Удалить", "Введите логин администратора:");
            KeyButton.setOnAction(event1 ->
            {
                String clm3 = "deleteAdmin," + ThirdTF.getText();
                try {
                    Client.os.writeObject(clm3);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        });

        blockUser.setOnAction(event ->
        {
            Visibility(false, false, true, false, false, false, true, true);
            setText("", "", "Логин", "", "", "", "Блокиpовать", "Введите логин пользователя:");
            KeyButton.setOnAction(event1 ->
            {
                String clm4 = "blockUser," + ThirdTF.getText();
                try {
                    Client.os.writeObject(clm4);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        });

        unblockUser.setOnAction(event ->
        {
            Visibility(false, false, true, false, false, false, true, true);
            setText("", "", "Логин", "", "", "", "Разблокиpовать", "Введите логин пользователя:");
            KeyButton.setOnAction(event1 ->
            {
                String clm4 = "unblockUser," + ThirdTF.getText();
                try {
                    Client.os.writeObject(clm4);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        });
        statistics.setOnMouseClicked(event -> {
            switchScene(event,"/client/PieChart.fxml");
        });
        infoFl.setOnAction(event -> {
            openNewScene("/client/Flights.fxml");
        });
        top.setOnAction(event -> {
            openNewScene("/client/Airports.fxml");
        });
        infoUsers.setOnAction(event -> {
            openNewScene("/client/Users.fxml");
        });
        infoEmployee.setOnAction(event -> {
            openNewScene("/client/Vacancy.fxml");
        });
        goBack.setOnMouseClicked(event -> {
            switchScene(event,"/client/LogInWindow.fxml");
        });

    }

    private void openNewScene(String window) {
        statistics.getScene().getWindow().hide();

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

    private void openNewSceneExit(String window) {
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

    public void Visibility(Boolean one, Boolean two, Boolean three, Boolean four, Boolean five, Boolean six, Boolean butt, Boolean lab) {
        FirstTF.setVisible(one);
        SecondTF.setVisible(two);
        ThirdTF.setVisible(three);
        FourthTF.setVisible(four);
        FifthTF.setVisible(five);
        SixthTF.setVisible(six);
        KeyButton.setVisible(butt);
        KeyLabel.setVisible(lab);
    }

    public void setText(String one, String two, String three, String four, String five, String six, String butt, String lab) {
        FirstTF.setPromptText(one);
        SecondTF.setPromptText(two);
        ThirdTF.setPromptText(three);
        FourthTF.setPromptText(four);
        FifthTF.setPromptText(five);
        SixthTF.setPromptText(six);
        KeyButton.setText(butt);
        KeyLabel.setText(lab);
    }

}
