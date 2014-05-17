/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Sockets;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

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
                Logger.getLogger(ServerConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (UnknownHostException ex) {
            Logger.getLogger(ServerConnection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ServerConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void createSocket() throws UnknownHostException, IOException {
        clientSocket = new Socket("localhost", 6000);
    }

    public void closeConnection() {
        try {
            clientSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(ServerConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
