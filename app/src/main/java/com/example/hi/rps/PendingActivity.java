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
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.internal.bind.ReflectiveTypeAdapterFactory;

import java.util.ArrayList;

public class PendingActivity extends Activity  {
    ListView listView;
    CustomAdapter customAdapter;
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

    private void UpdateListView() {
        listView =(ListView) findViewById(R.id.PlayRequest);
        setListData();
        customAdapter = new CustomAdapter(pendingActivity,customListViewValue);
      //  ArrayAdapter pendingAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,pendingArray);
        listView.setAdapter(customAdapter);
    }

    public void setListData()
    {

        for (int i = 0; i < pendingArray.size(); i++) {

            final PendingList sched = new PendingList();
           // String Name = pendingArray.get(i).substring(20, pendingArray.get(i).length() - 3).split("\" status=\"")[0];
          //  String Status = pendingArray.get(i).substring(20, pendingArray.get(i).length() - 3).split("\" status=\"")[0];
            Log.e("value",pendingArray.get(i));
            /******* Firstly take data in model object ******/
         //   sched.setName("Company "+i);
         //   sched.setStatus("image"+i);


            /******** Take Model Object in ArrayList **********/
            customListViewValue.add( sched );
        }

    }
}
