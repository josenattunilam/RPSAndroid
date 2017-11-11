package com.example.hi.rps;

import android.app.LauncherActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;

public class SecondActiityN extends AppCompatActivity implements View.OnClickListener {


    ListView myList;
    ArrayList<String> arrayList;

    public String st = "";
    public String s = "";
    ResponseReceiver receiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);


        IntentFilter filter = new IntentFilter(SecondActiityN.ResponseReceiver.ACTION_RESP);
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        receiver = new ResponseReceiver();
        registerReceiver(receiver, filter);

    }

    public class ResponseReceiver extends BroadcastReceiver {

        public static final String ACTION_RESP = "com.example.hi.rps.SecondActiityN.MESSAGE_PROCESSED";

        @Override
        public void onReceive(Context context, Intent intent) {

            String newString = intent.getStringExtra("response");
            Log.d("TAG", newString);
            Log.d("TAG", "Entered");
            TextView result = (TextView) findViewById(R.id.myTextView);
            //result.setText( );
            Bundle b = intent.getBundleExtra(TCPConnectionService.EXTRA_RESPONSE);

            String s = b.getString("userName");
            Intent intent1 = new Intent(SecondActiityN.this, DisplayActivity.class);
            intent1.putExtra("userName", s);
            startActivity(intent1);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ViewOnlinePlayers:
                Log.d("TAG", "R.id.ViewOnlinePlayers: " + TCPConnectionService.EXTRA_PARAM1);

                String str = "<OnlinePlayers/>";


                Intent disPlayAct = new Intent(this, DisplayActivity.class);
                disPlayAct.putExtra("runOnlinePlayers","<OnlinePlayers/>" );
                startActivity(disPlayAct);


                Log.d("TAG", "RECEIVING FROM SERVICE");

                break;
            case R.id.PendingPlayRequests:
                Log.d("TAG", "R.id.PendingPlayRequests:");

                Intent Pending = new Intent(this, PendingActivity.class);
                Pending.putExtra("PlayRequests","<PlayRequestsForMe/>" );
                startActivity(Pending);
                break;
            case R.id.SentPlayRequest:
                Log.d("TAG", "R.id.SentPlayRequest:");
                Intent MyRequests = new Intent(this, SentRequestActivity.class);
                MyRequests.putExtra("MyPlayRequests","<MyPlayRequests/>" );
                startActivity(MyRequests);
                //Intent connectionIntent2 = new Intent(this, TCPConnectionService.class);
                //connectionIntent2.putExtra(TCPConnectionService.EXTRA_PARAM1, "<MyPlayRequests/>");
                //startActivity(myIntent2);
             /*case R.id.ExitGame:
                 Log.d("TAG", "R.id.SentPlayRequest:");

                 goToMainActivity();*/
                break;
        }
    }
}