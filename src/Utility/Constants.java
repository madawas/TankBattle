package Utility;

/**
 *
 * @author Madawa
 */
public class Constants {    
    private static final String DEFAULT_HOST = "127.0.0.1";    
    private static final int DEFAULT_WRITE_PORT = 6000;    
    private static final int DEFAULT_READ_PORT = 7000;
    
    public static final int SCREEN_WIDTH = 500;
    public static final int SCREEN_HEIGHT = 500;
    public static final int MAX_PLAYERS = 5;
    
    private static String host = null;
    private static int readPort = -1;
    private static int writePort = -1;
    
    public static String getHost() {
        if(host != null)
            return host;
        else
            return DEFAULT_HOST;
    }

    public static void setHost(String host) {
        Constants.host = host;
    }

    public static int getReadPort() {
        if(readPort != -1)
            return readPort;
        else
            return DEFAULT_READ_PORT;
    }

    public static void setReadPort(int readPort) {
        Constants.readPort = readPort;
    }

    public static int getWritePort() {
        if(writePort != -1)
            return writePort;
        else
            return DEFAULT_WRITE_PORT;
    }

    public static void setWritePort(int writePort) {
        Constants.writePort = writePort;
    }
    
}
