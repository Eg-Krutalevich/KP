package tables;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.*;

public class DatabaseHandler extends Configs {
    private static Connection dbConnection;
    private static DatabaseHandler databaseHandler;

    public static synchronized DatabaseHandler getInstance() {
        if (dbConnection == null) databaseHandler = new DatabaseHandler();
        return databaseHandler;
    }

    private DatabaseHandler() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("D://upload//KP//config.txt"));
            dbUser = reader.readLine();
            dbPass = reader.readLine();
            reader.close();
        } catch (Exception e) {
            dbUser = "Egor";
            dbPass = "Klientinvalid314";
            e.printStackTrace();
        }
    }


    public Connection getDbConnection() throws SQLException, ClassNotFoundException {
        String connectionConfiguration = "jdbc:mysql://localhost:3306/airport";
        Class.forName("com.mysql.cj.jdbc.Driver");
        dbConnection = DriverManager.getConnection(connectionConfiguration, dbUser, dbPass);
        return dbConnection;
    }

    public void close(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
