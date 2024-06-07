import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Server {
    DBManager dbManager = new DBManager();

    public void StartServer() throws IOException, SQLException, ClassNotFoundException {
        while(true) {

            try (ServerSocket serverSocket = new ServerSocket(8189);
                 Socket socket = serverSocket.accept();
                 Scanner scanner = new Scanner(socket.getInputStream())) //input data for server out client)
            {
                PrintWriter writer = new PrintWriter(socket.getOutputStream()); // output data for server in client

                //writer.println("Hello World");
                //while(scanner.hasNextLine())
                //{
                String clientRequest = scanner.nextLine(); //client request here !!!
                System.out.println("Client: " + clientRequest);
                //}

                RequestProcessing(clientRequest);
            }
        }
    }

    public void RequestProcessing(String clientRequest) throws SQLException, ClassNotFoundException {
        String[] request = clientRequest.split(">>");

        for (int i = 0; i < request.length; i++) {
            System.out.println("request["+i+"] = "+request[i]);
        }

        //ENTERSYS>>com.company.inc@gmail.com>>123
        if(request[0].equals("ENTERSYS")) // 0
        {
            dbManager.Start(0, new String[]{
                    request[1], //login
                    request[2] //password
            });
        }

        //SHOWMESS>>com.company.inc@gmail.com
        if(request[0].equals("SHOWMESS")) // 1
        {
            dbManager.Start(1, new String[]{
                    request[1] //out adress
            });
        }

        //SEARCHPEOPLE>>co
        if(request[0].equals("SEARCHPEOPLE")) // 2
        {
            dbManager.Start(2, new String[]{
                    request[1] // reciver address
            });
        }

        //SHOWACCOUNT>>com.company.inc@gmail.com
        if(request[0].equals("SHOWACCOUNT")) // 3
        {
            dbManager.Start(3, new String[]{
                    request[1] // address for watching
            });
        }

        //SENDMESS>>its my first mail for myself!>>2013-05-09>>com.company.inc@gmail.com_com.company.inc@gmail.com
        if(request[0].equals("SENDMESS")) // 4
        {
            dbManager.Start(4, new String[]{
                    request[1], //description message
                    request[2], //date of send
                    request[3], //sender address
                    request[4], // reciver address
            });
        }

        //OPENMESS>>com.company.inc@gmail.com_1
        if(request[0].equals("OPENMESS")) // 5
        {
            dbManager.Start(5, new String[]{
                    request[1], //address with number
            });
        }
    }
}
