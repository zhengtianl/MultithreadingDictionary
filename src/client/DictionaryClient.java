package client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ConnectException;
import java.net.Socket;

import javax.swing.JOptionPane;

public class DictionaryClient {
    static Socket client = null;
    static BufferedReader reader;
    static BufferedWriter writer;

    public static void main(String[] args) throws IOException {
        ClientUI cui = new ClientUI();
        cui.run();
        int port = 8080;
        if (args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.out.println("Invalid port number: " + args[0]);
                JOptionPane.showMessageDialog(null, "Invalid port number: " + args[0]);
                System.exit(1);
            }
        }

        try {
            client = new Socket("localhost", port);
        } catch (ConnectException e) {
            JOptionPane.showMessageDialog(null, "Unable to connect to server. Please try again later.");
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true) {
            try {
                reader = new BufferedReader(new InputStreamReader(client.getInputStream(), "UTF-8"));
                writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream(), "UTF-8"));
                String receive = reader.readLine();
                System.out.println("Received response: " + receive);
                String que = "#1234message";
                if (receive.length() > que.length() && receive.substring(0, que.length()).equals(que)) {
                    String[] arrRec = receive.split("/");
                    ClientUI.textArea.setText(arrRec[1]);
                } else {
                    ClientUI.statusArea.setText(receive);
                }

            } catch (java.net.SocketException e) {
            	System.out.println("Server is killed, please retry");
                System.exit(0);
            	break;
            }
        }
    }
}
