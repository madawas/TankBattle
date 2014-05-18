/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Sockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import Utility.Constants;

/**
 *
 * @author Madawa
 */
public class ServerRead implements Runnable {

    private DataHandler gameDataHandler;

    public ServerRead() {
        gameDataHandler = new DataHandler();
    }

    public void readFromServer() throws IOException {
        int i = 0;
        ServerSocket inSocket = new ServerSocket(Constants.getReadPort());
        while (true) {
            Socket connectionSocket = inSocket.accept();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
            String message = reader.readLine();
            gameDataHandler.parseMessage(message);
            
            System.out.println(message);
        }
    }

    @Override
    public void run() {
        try {
            readFromServer();
        } catch (IOException ex) {
            System.out.println("IO Exception "+ex.getMessage());
        }
    }
}
