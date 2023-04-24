package server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;
import javax.swing.JOptionPane;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class DicServer{
    static String fileName;
    static ConcurrentHashMap<String, String> dict = new ConcurrentHashMap<String, String>();
    static volatile boolean serverRunning = true;
    private static final int NUMBER_OF_THREADS = 10;
    private static CustomThreadPool threadPool;
    
    public static void main(String[] args) {
    	int port = 8080;
    	ServerSocket server = null;
        Socket request = null;
        if (args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.out.println("Invalid port number: " + args[0]);
                JOptionPane.showMessageDialog(null, "Invalid port number: " + args[0]);
                System.exit(1);
            }
        }
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            fileName = "input.txt";
            System.out.println("Server started on port " + port);
            threadPool = new CustomThreadPool(NUMBER_OF_THREADS);
        } catch (IOException e) {
            System.out.println("Unable to create ServerSocket object: " + e.getMessage());
            System.exit(1);
        }
        
        // read dictionary.txt if it exists
        try {
            readDictionary();
        } catch (Exception e) {
            System.out.println("Unable to create ServerSocket object: " + e.getMessage());
        }
        // create a thread pool with 10 worker threads
        
        // thread per connection
        while (serverRunning) {
            // this is the socket from the client
            try {
                request = serverSocket.accept();
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
            // wait and accept connection from the client
            // create the connection and thread for this connection
            try {
                new Connection(request, dict).start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        threadPool.shutdown();
        // Write the dictionary to a file after the server stops running
        writeDictionary();
    }
    

    // read the dictionary.txt
    public static void readDictionary() throws IOException {
        File file = new File(fileName);
        if (file.isFile() && file.exists()) {
            try (InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(file), "UTF-8");
                 BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {

                // store the dictionary file
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    String[] lines = line.split(":");
                    dict.put(lines[0], lines[1]);
                }
            } catch (Exception e) {
                System.out.println("File not found, or unsupported encoding type!");
            }
        }
    }

    // close server write the dictionary.txt
    // write the hashmap to the dictionary.txt file
    public static void writeDictionary() {
        File file = new File(fileName);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (java.util.Map.Entry<String, String> entry : dict.entrySet()) {
                writer.write(entry.getKey() + ":" + entry.getValue() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Cannot write to file");
        }
    }


	public void start() {
		// TODO Auto-generated method stub
		
	}
}
