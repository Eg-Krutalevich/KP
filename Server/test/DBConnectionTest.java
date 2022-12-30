import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;

public class DBConnectionTest {

    @Test
    public void init() {
        int actual = 0;
        int expected = 0;
        try {
            String url = "jdbc:mysql://localhost:3306/airport";
            String user = "Egor";
            String password = "Klientinvalid314";
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection myConnection = DriverManager.getConnection(url, user, password);
            System.out.println("URL: " + url + "\nUser: " + user + "\nPassword: " + password);
            System.out.println("\nTest successfully executed!");
        } catch (ClassNotFoundException | SQLException e) {
            actual = 1;
            System.out.println(e);
        }
        assertEquals(expected, actual);
    }
}