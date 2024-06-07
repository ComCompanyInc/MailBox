import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException {
        /*
        DBManager dbManager = new DBManager();
        dbManager.Start();
         */

        Server server = new Server();
        server.StartServer();
    }
}