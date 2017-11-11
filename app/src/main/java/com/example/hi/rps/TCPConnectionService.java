package com.example.hi.rps;

/**
 * Created by hi on 06-11-2017.
 */

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;


public class TCPConnectionService extends IntentService {

    public static final String EXTRA_PARAM1 = "com.example.hi.rps.extra.PARAM1";
    public static final String EXTRA_PARAM2 = "com.example.hi.rps.extra.PARAM2";
    public static final String EXTRA_RESPONSE = "response";
    public static final int BUFFER_SIZE = 10048;


    public static String ipAddress;
    public static String userName;


    private OutputStream out;
    private PrintWriter output;
    private BufferedReader input;
    private Socket clientSocket;
    private InetSocketAddress inetSocket;
    private String back = null;
    private StringBuilder stringBuilder = new StringBuilder();

    public TCPConnectionService() {
        super("TCPConnectionService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        // clientSocket = new Socket();
        clientSocket = SocketHandler.getSocket();
        String action = intent.getStringExtra(EXTRA_PARAM1);
        Log.d("TAG", "action: " + action + "  --------------------------");
        //int ipAddress;
        inetSocket = new InetSocketAddress(ipAddress, 4444);

        switch (action) {
           /* case "<ReadyToPlay/>":
                try {

                    clientSocket.connect(inetSocket, 100);
                    out = clientSocket.getOutputStream();
                    output = new PrintWriter(out);

                    output.println(intent.getStringExtra(TCPConnectionService.userName));
                    output.flush();

                    input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    String resultText = input.readLine();
                    Log.d("TAG", resultText);
                    Intent broadcastIntent = new Intent();
                    broadcastIntent.setAction(MainActivity.ResponseReceiver.ACTION_RESP);
                    broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
                    broadcastIntent.putExtra(EXTRA_RESPONSE, resultText);
                    //Bundle b=new Bundle();
                    // b.putString("OnlinePlayers", OnlinePlayers);
                    //intent.putExtra("arguments", b);

                    sendBroadcast(broadcastIntent);
                    output.close();
                    out.close();
                    clientSocket.close();


                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;*/
            case "<OnlinePlayers/>":

                Log.d("TAG", "case \"<OnlinePlayers/>\":");

                try {
                    /*Socket clientSocket=new Socket();
                    SocketHandler.setSocket(clientSocket);*/
                    // clientSocket.connect(inetSocket, 100);
                    out = clientSocket.getOutputStream();
                    output = new PrintWriter(out);

                    //output.println("UserName");
                    //output.flush();

                    output.println("<OnlinePlayers/>");
                    output.flush();

                    //goToDisplayActivity();
                    input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                    int charsRead = 0;
                    char[] buffer = new char[BUFFER_SIZE];
                    ArrayList<String> playersOnline = new ArrayList<>();

                    if ((charsRead = input.read(buffer)) != -1) {
                        back = new String(buffer).substring(0, charsRead);

                        String[] pla = back.split("\n");

                        for (int i = 0; i < pla.length; i++) {
                            String player = pla[i].substring(20, pla[i].length() - 3).split("\" status=\"")[0];
                            String status = pla[i].substring(20, pla[i].length() - 3).split("\" status=\"")[1];

                            playersOnline.add(player + "-" + status);
                            Log.d("TAG", player + "-" + status);
                        }



                    }

                    Intent broadcastIntent = new Intent();
                    broadcastIntent.setAction(DisplayActivity.ResponseReceiver.ACTION_RESP);

                    broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
                    broadcastIntent.putStringArrayListExtra("response", playersOnline);
                    sendBroadcast(broadcastIntent);


                   // output.close();
                   // out.close();
                    // clientSocket.close();
                    //goToDisplayActivity();


                } catch (IOException e) {
                    Log.d("TAG", "Error from <OnlinePlayers/>");
                    e.printStackTrace();
                }
                break;
            case "<PlayRequestsForMe/>":

                try {
                    Log.d("TAG", "playreq");
                    // clientSocket.connect(inetSocket, 100);
                    out = clientSocket.getOutputStream();
                    output = new PrintWriter(out);

                    output.println("<PlayRequestsForMe/>");
                    output.flush();

                    input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    int charsRead = 0;
                    char[] buffer = new char[BUFFER_SIZE];
                    ArrayList<String> PlayRequests = new ArrayList<>();

                    if ((charsRead = input.read(buffer)) != -1) {
                        back = new String(buffer).substring(0, charsRead);

                        String[] pla = back.split("\n");

                        for (int i = 0; i < pla.length; i++) {
                            String player = pla[i].substring(20, pla[i].length() - 3).split("\" status=\"")[0];
                            String status = pla[i].substring(20, pla[i].length() - 3).split("\" status=\"")[1];

                            PlayRequests.add(player + "-" + status);
                            Log.d("TAG", player + "-" + status);
                        }
                    }

                        Intent broadcastIntent = new Intent();
                        broadcastIntent.setAction(PendingActivity.ResponseReceiver.ACTION_RESP);

                        broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
                        broadcastIntent.putStringArrayListExtra("response", PlayRequests);
                        sendBroadcast(broadcastIntent);


                        output.close();
                        out.close();


                } catch (IOException e) {
                    Log.d("TAG", "error from playreq");
                    e.printStackTrace();
                }
                break;
            case "<MyPlayRequests/>":
                try {
                    Log.d("TAG", "in myreq");
                    // clientSocket.connect(inetSocket, 100);
                    out = clientSocket.getOutputStream();
                    output = new PrintWriter(out);

                    output.println("<MyPlayRequests/>");
                    output.flush();

                    input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    int charsRead = 0;
                    char[] buffer = new char[BUFFER_SIZE];
                    ArrayList<String> MyRequests = new ArrayList<>();
                    if ((charsRead = input.read(buffer)) != -1) {
                        back = new String(buffer).substring(0, charsRead);

                        String[] pla = back.split("\n");

                        for (int i = 0; i < pla.length; i++) {
                            String player = pla[i].substring(20, pla[i].length() - 3).split("\" status=\"")[0];
                            String status = pla[i].substring(20, pla[i].length() - 3).split("\" status=\"")[1];

                            MyRequests.add(player + "-" + status);
                            Log.d("TAG", player + "-" + status);
                        }
                    }

                    Intent broadcastIntent = new Intent();
                    broadcastIntent.setAction(SentRequestActivity.ResponseReceiver.ACTION_RESP);

                    broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
                    broadcastIntent.putStringArrayListExtra("response", MyRequests);
                    sendBroadcast(broadcastIntent);
                    output.close();
                    out.close();


                } catch (IOException e) {
                    Log.d("TAG", "error in myreq");
                    e.printStackTrace();
                }
                break;

        }
    }


}