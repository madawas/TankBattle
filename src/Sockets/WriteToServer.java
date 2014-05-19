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
import Utility.Constants;

/**
 *
 * @author Madawa
 */
public class WriteToServer {

    private DataOutputStream write;
    private Socket clientSocket;

    public void writeCommand(String s) throws UnknownHostException, IOException, InterruptedException {
        Thread.sleep(1050);
        System.out.println(s);
        createSocket();
        write = new DataOutputStream(clientSocket.getOutputStream());
        write.writeBytes(s);
        closeConnection();

    }

    public void createSocket() throws UnknownHostException, IOException {
        clientSocket = new Socket(Constants.getHost(), Constants.getWritePort());
    }

    public void closeConnection() {
        try {
            clientSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(WriteToServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
