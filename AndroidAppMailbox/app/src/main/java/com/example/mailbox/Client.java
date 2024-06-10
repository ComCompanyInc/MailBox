package com.example.mailbox;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client implements Runnable{
    private String serverIP = "192.168.1.103";
    private int serverPort = 8189;
    private Socket socket;
    private PrintWriter out;
    public String response;

    public Client(String response)
    {
        this.response = (response).replaceAll("[\"\\n]", "");
    }

    public String connectToServer() {
        try {
            socket = new Socket(serverIP, serverPort);
            out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            //BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

            //String userString;
            /*while ((userString = userInput.readLine();/*) != null) {*/
            out.println(response);
            String answer = in.readLine();
            System.out.println("Server answer: " + answer);
            return answer;
            //}
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "0";
    }

    public void run() {
        response = connectToServer();
        //Log.d("MyThread", "Data in MyThread: " + response);
    }

    /*
    Client client = new Client("GETF_5");
                Thread thread = new Thread(client);
                thread.start();

                synchronized (thread) {
                    try {
                        thread.join(); // Ожидаем завершения работы потока

                        //System.out.println("qwe+"+client.response);

                        arrayThemes = client.response.split("_");
                        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<String>(Arrays.asList(arrayThemes)));
                        list.setAdapter(adapter);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
     */
}
