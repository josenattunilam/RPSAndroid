package com.example.hi.rps;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.internal.bind.ReflectiveTypeAdapterFactory;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PendingActivity extends Activity  {
    ListView listView;
    CustomAdapter customAdapter;
    CustomAdapterPending customAdapterPending;
    RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    public  PendingActivity pendingActivity = null;
    public  ArrayList<PendingList> customListViewValue = new ArrayList<PendingList>();

    ArrayList<String> pendingArray;
    ResponseReceiver receiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending);

        pendingArray=new ArrayList<>();
        pendingActivity = this;

        IntentFilter filter=new IntentFilter(ResponseReceiver.ACTION_RESP);
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        receiver=new ResponseReceiver();
        registerReceiver(receiver,filter);

        Intent connectionIntent = new Intent(this, TCPConnectionService.class);
        connectionIntent.putExtra(TCPConnectionService.EXTRA_PARAM1, "<PlayRequestsForMe/>");
        startService(connectionIntent);
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

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

    private void UpdateListView() {
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        customAdapterPending = new CustomAdapterPending(customListViewValue,getApplicationContext());
        setListData();
        recyclerView.setAdapter(customAdapterPending);
    }

    public void setListData()
    {

        for (int i = 0; i < pendingArray.size(); i++) {

            final PendingList sched = new PendingList();
            Log.d("value",pendingArray.get(i));
            String[] str_array = pendingArray.get(i).split(",");
            String Name = str_array[0];
            String Status = str_array[1];
            Log.d("Name",Name);
            String st=pendingArray.get(i);
            sched.setName(Name);
            sched.setStatus(Status);

            customListViewValue.add( sched );
        }
        customAdapterPending.setlist(customListViewValue);

    }
}
