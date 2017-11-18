package com.example.hi.rps;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class PendingActivityCopy extends Activity implements AdapterView.OnClickListener, AdapterView.OnItemClickListener {

    ArrayList<String> pendingArray;
    ResponseReceiver receiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending);

        pendingArray=new ArrayList<>();

        IntentFilter filter=new IntentFilter(ResponseReceiver.ACTION_RESP);
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        receiver=new ResponseReceiver();
        registerReceiver(receiver,filter);

        Intent connectionIntent = new Intent(this, TCPConnectionService.class);
        connectionIntent.putExtra(TCPConnectionService.EXTRA_PARAM1, "<PlayRequestsForMe/>");
        startService(connectionIntent);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
        Log.d("TAG", pendingArray.get(i));
       // Button Accept = (Button) this.findViewById(R.id.Accept);
      //  Button Reject = (Button) this.findViewById(R.id.Reject);
    }

    public class ResponseReceiver extends BroadcastReceiver
    {

        public static final String ACTION_RESP = "com.example.hi.PendingActivity.rps.MESSAGE_PROCESSED";

        @Override
        public void onReceive(Context context, Intent intent)
        {

            pendingArray=intent.getStringArrayListExtra("response");
            UpdateListView();
        }
    }

    private void UpdateListView() {
       // ListView updateListView=(ListView) findViewById(R.id.PlayRequest);
       // updateListView.setOnItemClickListener(this);
        ArrayAdapter pendingAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,pendingArray);
       // updateListView.setAdapter(pendingAdapter);
    }
}
