package com.example.hi.rps;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SentRequestActivity extends Activity implements AdapterView.OnItemClickListener {
    ArrayList<String> MyRequestsArray;
    ResponseReceiver receiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sent_request);
        MyRequestsArray=new ArrayList<>();

        IntentFilter filter = new IntentFilter(SentRequestActivity.ResponseReceiver.ACTION_RESP);
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        receiver = new ResponseReceiver();
        registerReceiver(receiver, filter);

        Intent connectionIntent = new Intent(this, TCPConnectionService.class);
        connectionIntent.putExtra(TCPConnectionService.EXTRA_PARAM1, "<MyPlayRequests/>");
        startService(connectionIntent);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
        Log.d("TAG",MyRequestsArray.get(i));
    }

    public class ResponseReceiver extends BroadcastReceiver
    {

        public static final String ACTION_RESP = "com.example.hi.SentRequestActivity.rps.MESSAGE_PROCESSED";

        @Override
        public void onReceive(Context context, Intent intent)
        {
            MyRequestsArray=intent.getStringArrayListExtra("response");
            UpdateListView();


        }
    }

    private void UpdateListView() {
        ListView updateListView2= (ListView) findViewById(R.id.SentRequest);
        updateListView2.setOnItemClickListener(this);
        ArrayAdapter MyRequestsAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,MyRequestsArray);
        updateListView2.setAdapter(MyRequestsAdapter);
    }
}
