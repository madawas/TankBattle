/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Sockets;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import Utility.Constants;

/**
 *
 * @author Madawa
 */
public class ServerConnection implements Runnable {

    private DataOutputStream outToServer;
    private Socket clientSocket;

    public void joinGame() throws UnknownHostException, IOException, InterruptedException {
        createSocket();
        outToServer = new DataOutputStream(clientSocket.getOutputStream());
        outToServer.writeBytes("JOIN#");
        closeConnection();

    }

    public void run() {
        try {
            try {
                joinGame();
            } catch (InterruptedException ex) {
                System.out.println("InterruptedException "+ex.getMessage());
            }
        } catch (UnknownHostException ex) {
            System.out.println("UnknownHost "+ex.getMessage());
        } catch (IOException ex) {
            System.out.println("IOException "+ex.getMessage());
        }
    }

    public void createSocket() throws UnknownHostException, IOException {
        clientSocket = new Socket(Constants.getHost(), Constants.getWritePort());
    }

    public void closeConnection() {
        try {
            clientSocket.close();
        } catch (IOException ex) {
            System.out.println("IOException "+ex.getMessage());
        }
    }
}
