package controllers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    public static String clientMessage;
    public static Socket socket;
    public static ObjectOutputStream os;
    public static ObjectInputStream is;
    public static boolean connected = false;

    public static void Connect() {
        clientMessage = "";

        try {
            socket = new Socket(InetAddress.getLocalHost(), 8070);
            os = new ObjectOutputStream(socket.getOutputStream());
            is = new ObjectInputStream(socket.getInputStream());
            connected = true;
        } catch (UnknownHostException e) {
            connected = false;
            System.err.println("Address not available" + e);
        } catch (IOException e) {
            connected = false;
            System.err.println("I/О thread error" + e);
        }
    }

    public static void Disconnect() {
        try {
            if (is != null) is.close();
            if (os != null) os.close();
            if (socket != null) socket.close();

            connected = false;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        MainMenuGUI mainMenuGUI = new MainMenuGUI();
        mainMenuGUI.run();
    }
}
