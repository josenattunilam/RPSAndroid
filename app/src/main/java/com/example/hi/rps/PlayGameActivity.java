package com.example.hi.rps;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

public class PlayGameActivity extends AppCompatActivity implements View.OnClickListener {
    public static  String send = null;
    public TextView choice;
    public Button gameButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);
        choice = (TextView)findViewById(R.id.Status);
        gameButton = (Button)findViewById(R.id.GameStatus);
        gameButton.setOnClickListener(this);
    }
    public class ResponseReceiver extends BroadcastReceiver {
        public static final String ACTION_RESP = "com.example.hi.DisplayActivity.rps.MESSAGE_PROCESSED";

        @Override
        public void onReceive(Context context, Intent intent) {

            Log.d("TAG", "choices");
            String text = intent.getStringExtra(TCPConnectionService.EXTRA_RESPONSE);
            choice.setText(text);

        }

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.Rock:

                choice.setText("Rock");
                break;
            case R.id.Paper:

                choice.setText("Paper");
                break;
            case R.id.Scissors:

                choice.setText("Scissors");
                break;
            case R.id.Submit:
                String s1="<Choice ";
                String st=choice.getText().toString();;
                String s2="/>";
                send= s1+st+s2;
                SubmitChoice(send);
                send = null;

                break;
            case R.id.GameStatus:
                String str3="<CheckGameStatus/>";
                Log.e("enter","Enter");
                Intent connectionIntent3 = new Intent(this, TCPConnectionService.class);
                connectionIntent3.putExtra(TCPConnectionService.EXTRA_PARAM1, "<CheckGameStatus/>");
                startService(connectionIntent3);
                break;
            case R.id.ExitGame:
                String str4="<Exit/>";
                Intent connectionIntent4 = new Intent(this, TCPConnectionService.class);
                connectionIntent4.putExtra(TCPConnectionService.EXTRA_PARAM1, "<ExitGame/>");
                startService(connectionIntent4);

        }
    }

    private void SubmitChoice(final String string) {

        final Handler handler = new Handler();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Socket s=SocketHandler.getSocket();
                    OutputStream out = s.getOutputStream();

                    PrintWriter output = new PrintWriter(out);

                    output.println(TCPConnectionService.userName);
                    output.flush();
                    BufferedReader input = new BufferedReader(new InputStreamReader(s.getInputStream()));

                    output.println(string);
                    output.flush();
                    final String st2 = input.readLine();

                    Log.d("con" , st2);


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }
}
