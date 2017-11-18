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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SentRequestActivity extends Activity  {
    ArrayList<String> MyRequestsArray;
    ResponseReceiver receiver;
    CustomAdapterSentRequest customAdapterSentRequest;
    RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    public ArrayList<SentRequestList> customListViewValue = new ArrayList<SentRequestList>();
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
        recyclerView = (RecyclerView)findViewById(R.id.recyclerSend);
        layoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        customAdapterSentRequest = new CustomAdapterSentRequest(customListViewValue, getApplicationContext(), new ItemClickListner() {
            @Override
            public void onItemClick(View view, int position) {
                Log.e("clicked",customListViewValue.get(position).getStatus());
                if (customListViewValue.get(position).getStatus().contains("accepted")){
                    Intent intent = new Intent(getApplicationContext(),PlayGameActivity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(getApplicationContext(),customListViewValue.get(position).getStatus().toString(),Toast.LENGTH_SHORT).show();
                }
            }
        });
        setListData();
        recyclerView.setAdapter(customAdapterSentRequest);
    }

    private void setListData() {
        for (int i = 0; i < MyRequestsArray.size(); i++) {

            final SentRequestList sched = new SentRequestList();
            Log.d("value",MyRequestsArray.get(i));
            String[] str_array = MyRequestsArray.get(i).split(",");
            String Name = str_array[0];
            String Status = str_array[1];
            Log.d("Name",Name);
            String st=MyRequestsArray.get(i);
            sched.setName(Name);
            sched.setStatus(Status);

            customListViewValue.add( sched );
        }
        customAdapterSentRequest.setlist(customListViewValue);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }
}
