package com.example.hi.rps;

        import android.content.BroadcastReceiver;
        import android.content.Context;
        import android.content.Intent;
        import android.content.IntentFilter;
        import android.os.Handler;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.widget.EditText;
        import android.widget.TextView;
        import android.widget.Spinner;
        import android.widget.AdapterView;
        import android.widget.ArrayAdapter;
        import android.widget.AdapterView.OnItemSelectedListener;
        import java.io.BufferedReader;
        import java.io.IOException;
        import java.io.InputStreamReader;
        import java.io.OutputStream;
        import java.io.PrintWriter;
        import java.net.InetSocketAddress;
        import java.net.Socket;
        import java.util.ArrayList;
        import java.util.List;


        import static android.util.Log.*;

public class MainActivity extends AppCompatActivity {
    Socket clientSocket;
   /* public class ResponseReceiver extends BroadcastReceiver
    {
        public static final String ACTION_RESP = "com.example.hi.rps.MESSAGE_PROCESSED";


        @Override
        public void onReceive(Context context, Intent intent)
        {
            TextView result = (TextView) findViewById(R.id.myTextView);
            String text = intent.getStringExtra(TCPConnectionService.EXTRA_RESPONSE);
            result.setText(text);
        }
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*IntentFilter filter = new IntentFilter(ResponseReceiver.ACTION_RESP);
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        ResponseReceiver receiver = new ResponseReceiver();
        registerReceiver(receiver, filter);*/
    }



    public void onClick(View v) throws IOException {

        EditText ipText = (EditText) findViewById(R.id.ipAddress);
        //ipText.setText("192.168.56.1");
        //String ipAddress = ipText.getText().toString();

        EditText UserText = (EditText) findViewById(R.id.userName);
        TCPConnectionService.ipAddress = ipText.getText().toString();
        //String userName = UserText.getText().toString();
        TCPConnectionService.userName = UserText.getText().toString();
        //Log.d("lala", ipAddress + " " + userName);


        switch (v.getId()) {
            case R.id.loginButton:
                //String ipAddress = ipText.getText().toString();
                //String userName = UserText.getText().toString();

                //String ipAddress = TCPConnectionService.ipAddress;
                //String userName = TCPConnectionService.userName;
                Intent connectionIntent = new Intent(this, TCPConnectionService.class);
                //connectionIntent.putExtra(TCPConnectionService.EXTRA_PARAM1, ipAddress);
                //connectionIntent.putExtra(TCPConnectionService.EXTRA_PARAM2, userName);
                //LoginFunction( ipAddress, userName);
                LoginFunction();


               // goToSecondActivity();

                break;
        }
    }

    private void goToSecondActivity(){
        Intent intent=new Intent(MainActivity.this,SecondActiityN.class);
        startActivity(intent);
    }

    //private void LoginFunction(final String ipAddress, final String userName) {
    private void LoginFunction() {

        final Handler handler = new Handler();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Socket s=new Socket();
                    //Socket s = new Socket(ipAddress, 4444);
                    InetSocketAddress socketAddress = new InetSocketAddress(TCPConnectionService.ipAddress, 4444);
                    SocketHandler.setSocket(s);
                    s.connect(socketAddress);
                    OutputStream out = s.getOutputStream();

                    PrintWriter output = new PrintWriter(out);

                    output.println(TCPConnectionService.userName);
                    output.flush();
                    BufferedReader input = new BufferedReader(new InputStreamReader(s.getInputStream()));
                    final String st = input.readLine();
                    Log.d("con" , st);

                    output.println("<ReadyToPlay/>");
                    output.flush();
                    final String st2 = input.readLine();

                    Log.d("con" , st2);
                    goToSecondActivity();



                    //Log.d("lala", st);
                    //Log.d("lala", st2);
                   //output.close();
                    //out.close();
                   // s.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }
}
