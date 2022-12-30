import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;

public class Server {
    private static int counter = 0;

    public static int getCounter() {
        return counter;
    }

    public static void setCounter(int counter) {
        Server.counter = counter;
    }

    public Server() {
    }


    public static void main(String[] args) {
        try {

            ServerSocket serverSocket = new ServerSocket(8070);
            System.out.println("--- Server started ---");

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("--- LINKING: " + socket.getInetAddress().getHostName() + " ---");
                System.out.println("--- Client-" + ++counter + " is connected ---");

                ServerThread thread = null;
                try {
                    thread = new ServerThread(socket, counter);
                } catch (SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
                thread.start();
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}
