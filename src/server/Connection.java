package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

public class Connection extends Thread{
	Socket socket = null;
	ConcurrentHashMap<String, String>dict;
	BufferedReader input = null;
	BufferedWriter output = null;
	
	@Override
	public void run() {
		try {
			input = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return;
		}
		String request;
		while (true) {
		    try {
		        request = input.readLine();
		        System.out.println("Received request: " + request);
		   
		        if (request == null) {
		            socket.close();
		            System.out.println("app is closed!\n");
		            System.exit(0);
		            return;
		        } else {
		            String[] reqArray = request.split("/");
		            String method = reqArray[0];
		            String text = reqArray[1];
		            String meaning = null;
		            if (reqArray.length == 3) {
		                meaning = reqArray[2];
		            }
		            // switch to trigger different function on out dictionary based on client's quest
		            switch (method) {
		                case "ADD":
		                    if (dict.get(text) != null) {
		                        try {
		                            output.write(text + " exists exists!\n");
		                            output.flush();
		                        } catch (Exception e) {
		                            e.printStackTrace();
		                        }
		                        break;
		                    }
		                    dict.put(text, meaning);
		                    try {
		                        DicServer.writeDictionary();
		                        output.write("Success!\n");
		                        output.flush();
		                    } catch (Exception e) {
		                        // TODO: handle exception
		                        e.printStackTrace();
		                    }
		                    break;
		                case "DELETE":
		                    // delete the word
		                    // did not find the words
		                    if (dict.get(text) == null) {
		                        try {
		                            output.write(text + " no found\n");
		                            output.flush();
		                        } catch (Exception e) {
		                            e.printStackTrace();
		                        }
		                        break;
		                    }
		                    // find the text
		                    dict.remove(text);
		                    try {
		                        DicServer.writeDictionary();
		                        output.write("Delete successfully\n");
		                        output.flush();
		                    } catch (Exception e) {
		                        // TODO: handle exception
		                        e.printStackTrace();
		                    }

		                    break;
		                case "UPDATE":
		                    if (dict.get(text) == null) {
		                        try {
		                            output.write("you cannot update something that doesn't exist.\n");
		                            output.flush();
		                        } catch (Exception e) {
		                            e.printStackTrace();
		                        }
		                        break;
		                    }
		                    dict.remove(text);
		                    dict.put(text, meaning);
		                    try {
		                        DicServer.writeDictionary();
		                        output.write("update successfully.\n");
		                        output.flush();

		                    } catch (Exception e) {
		                        e.printStackTrace();
		                    }
		                    break;

		                case "QUERY":
		                    if (dict.get(text) == null) {
		                        try {
		                            output.write(text + " no found\n");
		                            output.flush();
		                        } catch (Exception e) {
		                            e.printStackTrace();
		                        }
		                        break;
		                    }
		                    String wordMeaning = dict.get(text);
		                    try {
		                        output.write("#1234message" + "/" + wordMeaning + "\n");
		                        output.flush();
		                    } catch (Exception e) {
		                        e.printStackTrace();
		                    }
		                    break;
		            }
		        }
		    } catch (java.net.SocketException e) {
		    	System.out.println("Connection reset it means one client be closed");
		    	break;
		    } catch (IOException e) {
	            System.out.println("An IO error occurred");
	            return;
	        }
		}
	}
	public Connection(Socket socket, ConcurrentHashMap<String, String>dict)throws IOException{
		this.socket = socket;
		this.dict = dict;
		
}
}
