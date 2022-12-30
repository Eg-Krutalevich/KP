
import tables.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class ServerThread extends Thread {
    private final int counter;
    private final ObjectOutputStream oos;
    private final ObjectInputStream ois;
    private final InetAddress address;
    private String clientMessage;
    private Statement statement;
    private UsersTable usersTable;

    private VacancyTable vacancyTable;
    private FlightsTable flightsTable;
    private FAQTable faqTable;
    private AirportTable airportTable;
    private ForbiddenStuffTable forbiddenStuffTable;

    private final DatabaseHandler databaseHandler;

    public ServerThread(Socket socket, int counter) throws IOException, SQLException, ClassNotFoundException {
        this.counter = counter;
        this.oos = new ObjectOutputStream(socket.getOutputStream());
        this.ois = new ObjectInputStream(socket.getInputStream());
        this.address = socket.getInetAddress();
        this.databaseHandler = DatabaseHandler.getInstance();

        Connection connection = databaseHandler.getDbConnection();
        try {
            this.statement = connection.createStatement();
            this.flightsTable = new FlightsTable(this.statement);
            this.usersTable = new UsersTable(this.statement);
            this.vacancyTable = new VacancyTable(this.statement);
            this.flightsTable = new FlightsTable(this.statement);
            this.faqTable = new FAQTable(this.statement);
            this.airportTable = new AirportTable(this.statement);
            this.forbiddenStuffTable = new ForbiddenStuffTable(this.statement);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void writeObj(String string) {
        this.clientMessage = string;
        try {
            this.oos.writeObject(this.clientMessage);
        } catch (IOException e) {
            System.err.println("--- I/О thread error: " + e + " ---");
        }
    }

    public void saveServerLog(Boolean bool, String text) {
        try (FileOutputStream fos = new FileOutputStream("D://upload//KP//serverLog.txt", bool)) {
            byte[] buffer = text.getBytes();
            fos.write(buffer, 0, buffer.length);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void run() {
        String messageToClient;
        String string;
        String ThreadStop = "";
        saveServerLog(false, "");
        try {
            System.out.println("--- Server is waiting for Client Actions ---");

            while (!ThreadStop.equals("Exit")) {
                countServerConnections();

                string = (String) this.ois.readObject();
                saveServerLog(true, string + " \r\n");
                String[] messageParts = string.split(",");

                switch (messageParts[0]) {
                    case "checkLoginClient":
                        String userLoginClient = messageParts[1];
                        String userPasswordClient = messageParts[2];
                        messageToClient = this.usersTable.CheckLoginClient(userLoginClient, userPasswordClient);
                        this.writeObj(messageToClient);
                        break;
                    case "checkLoginAdmin":
                        String userLoginAdmin = messageParts[1];
                        String userPasswordAdmin = messageParts[2];
                        messageToClient = this.usersTable.CheckLoginAdmin(userLoginAdmin, userPasswordAdmin);
                        this.writeObj(messageToClient);
                        break;
                    case "addClient":
                        this.usersTable.AddClient(messageParts[1], messageParts[2], messageParts[3], messageParts[4], messageParts[5], messageParts[6]);
                        break;
                    case "addInfoToUserData":
                        this.flightsTable.addInfoToUserData(messageParts[1], messageParts[2], messageParts[3]);
                        break;
                    case "сheckAvFl":
                        messageToClient = this.flightsTable.сheckAvFl(messageParts[1], messageParts[2]);
                        this.writeObj(messageToClient);
                        break;
                    case "getAvFlID":
                        messageToClient = this.flightsTable.getAvFlID(messageParts[1], messageParts[2]);
                        this.writeObj(messageToClient);
                        break;
                    case "checkUserInDB":
                        messageToClient = this.usersTable.checkUserInDB(messageParts[1]);
                        this.writeObj(messageToClient);
                        break;
                    case "sendData":
                        messageToClient = this.flightsTable.sendData(messageParts[1]);
                        this.writeObj(messageToClient);
                        break;
                    case "senddata":
                        messageToClient = this.flightsTable.senddata(messageParts[1], messageParts[2], messageParts[3]);
                        this.writeObj(messageToClient);
                        break;
                    case "getAvFl":
                        messageToClient = this.flightsTable.getAvFl(messageParts[2], messageParts[1]);
                        this.writeObj(messageToClient);
                        break;
                    case "getAllFlights":
                        messageToClient = this.flightsTable.getAllFlights();
                        this.writeObj(messageToClient);
                        break;
                    case "getAllFAQ":
                        messageToClient = this.faqTable.getAllFAQ();
                        this.writeObj(messageToClient);
                        break;
                    case "getAllAirport":
                        messageToClient = this.airportTable.getAllAirport();
                        this.writeObj(messageToClient);
                        break;
                    case "getAllStuff":
                        messageToClient = this.forbiddenStuffTable.getAllStuff();
                        this.writeObj(messageToClient);
                        break;
                    case "getAllFAQBel":
                        messageToClient = this.faqTable.getAllFAQ();
                        this.writeObj(messageToClient);
                        break;
                    case "getAllFAQChangeTheme":
                        messageToClient = this.faqTable.getAllFAQ();
                        this.writeObj(messageToClient);
                        break;
                    case "getAllUsers":
                        messageToClient = this.usersTable.getAllUsers();
                        this.writeObj(messageToClient);
                        break;
                    case "getAllVacancy":
                        messageToClient = this.vacancyTable.getAllVacancy();
                        this.writeObj(messageToClient);
                        break;
                    case "isBlocked":
                        messageToClient = this.usersTable.isBlocked(messageParts[1]);
                        this.writeObj(messageToClient);
                        break;
                    case "Blocked":
                        messageToClient = this.usersTable.Blocked(messageParts[1]);
                        this.writeObj(messageToClient);
                        break;
                    case "CountFreeSeatsAmount":
                        this.flightsTable.CountFreeSeatsAmount(messageParts[1]);
                        break;
                    case "addIDFtoUD":
                        this.flightsTable.addIDFtoUD(messageParts[1], messageParts[2]);
                        break;
                    case "getIDUserD":
                        messageToClient = this.flightsTable.getIDUserD(messageParts[1]);
                        this.writeObj(messageToClient);
                        break;
                    case "getTicketInfo":
                        messageToClient = this.flightsTable.getTicketInfo(messageParts[1]);
                        this.writeObj(messageToClient);
                        break;
                    case "getClientName":
                        messageToClient = this.flightsTable.getClientName(messageParts[1]);
                        this.writeObj(messageToClient);
                        break;
                    case "CalculateFullPrice":
                        messageToClient = this.flightsTable.CalculateFullPrice(messageParts[1], messageParts[2]);
                        this.writeObj(messageToClient);
                        break;
                    case "addInfoToTicket":
                        this.flightsTable.addInfoToTicket(messageParts[1], messageParts[2]);
                        break;
                    case "saveTicketToFile":
                        this.flightsTable.saveTicketToFile(messageParts[1], messageParts[2], messageParts[3], messageParts[4], messageParts[5]);
                        break;
                    case "deleteUserData":
                        this.flightsTable.deleteUserData(messageParts[1]);
                        break;
                    case "getDataForPie":
                        clientMessage = this.flightsTable.getDataForPie();
                        System.out.println(clientMessage);
                        this.writeObj(clientMessage);
                        break;
                    case "addFl":
                        this.flightsTable.addFl(messageParts[1], messageParts[2], messageParts[3], messageParts[4], messageParts[5], messageParts[6]);
                        break;
                    case "addVacancy":
                        this.vacancyTable.addVacancy(messageParts[1], messageParts[2], messageParts[3], messageParts[4]);
                        break;
                    case "addFAQ":
                        this.faqTable.addFAQ(messageParts[1], messageParts[2]);
                        break;
                    case "addTopAirport":
                        this.airportTable.addAirport(messageParts[1], messageParts[2], messageParts[3], messageParts[4]);
                        break;
                    case "addStuff":
                        this.forbiddenStuffTable.addStuff(messageParts[1], messageParts[2]);
                        break;
                    case "deleteFl":
                        this.flightsTable.deleteFl(messageParts[1], messageParts[2], messageParts[3]);
                        break;
                    case "addAdmin":
                        this.usersTable.addAdmin(messageParts[1], messageParts[2]);
                        break;
                    case "deleteAdmin":
                        this.usersTable.deleteAdmin(messageParts[1]);
                        break;
                    case "blockUser":
                        this.usersTable.blockUser(messageParts[1]);
                        break;
                    case "unblockUser":
                        this.usersTable.unblockUser(messageParts[1]);
                        break;
                }
            }
        } catch (Exception e) {
            System.err.println("--- Connection is closing ---");
        } finally {
            this.disconnect();
            countServerConnections(true);
        }
    }

    private void disconnect() {
        try {
            if (this.oos != null) this.oos.close();
            if (this.ois != null) this.ois.close();
            System.out.println("--- " + this.address.getHostName() + " --- Closing Client-" + this.counter);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            this.interrupt();
        }
    }

    private void countServerConnections(Boolean isNeedToBeDecreased) {
        if (isNeedToBeDecreased) Server.setCounter(Server.getCounter() - 1);
        System.out.println("--- Clients on Server: " + Server.getCounter());
    }

    private void countServerConnections() {
        System.out.println("--- Clients on Server: " + Server.getCounter());
    }
}

