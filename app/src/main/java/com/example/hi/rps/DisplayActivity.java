package com.example.hi.rps;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;

public class DisplayActivity extends Activity implements AdapterView.OnItemClickListener {

    ArrayList<String> playerArray;
    public static  String send = null;
   public ResponseReceiver receiver = new ResponseReceiver();
  public Context context;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        context = getApplicationContext();


        playerArray = new ArrayList<>();


        IntentFilter filter = new IntentFilter(DisplayActivity.ResponseReceiver.ACTION_RESP);
        filter.addCategory(Intent.CATEGORY_DEFAULT);

        registerReceiver(receiver, filter);

        Intent connectionIntent = new Intent(this, TCPConnectionService.class);
        connectionIntent.putExtra(TCPConnectionService.EXTRA_PARAM1, "<OnlinePlayers/>");
        startService(connectionIntent);
    }

    public class ResponseReceiver extends BroadcastReceiver{
        public static final String ACTION_RESP = "com.example.hi.DisplayActivity.rps.MESSAGE_PROCESSED";

        @Override
        public void onReceive(Context context, Intent intent) {

            playerArray = intent.getStringArrayListExtra("response");
            UpdateTheListView();

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

    private void UpdateTheListView() {
        ListView updateListView1 = (ListView) findViewById(R.id.OnlinePlayersList);
        updateListView1.setOnItemClickListener(this);
        ArrayAdapter playerOnlineAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, playerArray);

        updateListView1.setAdapter(playerOnlineAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        String s="<PlayRequest ";
        String s1="/>";
        Log.d("TAG",  playerArray.get(i));
        String st=playerArray.get(i);
        String[] separated=st.split("-");
        Log.d("TAG",separated[0]);
        //String s1= separated[0]+"/>";
        send=s+separated[0]+s1;
        Log.d("TAG",send);
        MessageFunction();


    }

    private void MessageFunction() {

        final Handler handler = new Handler();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Socket s1=SocketHandler.getSocket();

                    OutputStream out1 = s1.getOutputStream();
                    PrintWriter output1 = new PrintWriter(out1);
                    Log.e("send",send.toString());
                    output1.println(send);
                    output1.flush();
                    BufferedReader input1 = new BufferedReader(new InputStreamReader(s1.getInputStream()));
                    final String st3 = input1.readLine();
                    showToast(st3);

                    Log.d("con" , st3);
                    //output1.close();
                    //out1.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }

    public void showToast(final String toast)
    {
        runOnUiThread(new Runnable() {
            public void run()
            {
                Toast.makeText(DisplayActivity.this, toast, Toast.LENGTH_SHORT).show();
            }
        });
    }
}


