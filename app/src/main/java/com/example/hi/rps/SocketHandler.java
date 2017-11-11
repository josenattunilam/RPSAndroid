package com.example.hi.rps;

import java.net.Socket;

/**
 * Created by hi on 05-11-2017.
 */

public class SocketHandler {
    public static Socket clientSocket;

    public static synchronized Socket getSocket(){
        return clientSocket;
    }
    public static synchronized void setSocket(Socket socket){
        SocketHandler.clientSocket = socket;
    }
}
