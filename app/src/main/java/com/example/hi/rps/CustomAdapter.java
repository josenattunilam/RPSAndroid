package com.example.hi.rps;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by HP on 11-11-2017.
 */

public class CustomAdapter extends BaseAdapter implements View.OnClickListener {
    private Activity activity;
    private ArrayList data;
    private static LayoutInflater inflater=null;
    PendingList tempValues=null;
    int i=0;

    /*************  CustomAdapter Constructor *****************/
    public CustomAdapter(Activity a, ArrayList d) {

        /********** Take passed values **********/
        activity = a;
        data=d;


        /***********  Layout inflator to call external xml layout () ***********/
        inflater = ( LayoutInflater )activity.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }
    @Override
    public void onClick(View v) {

    }

    @Override
    public int getCount() {
        if(data.size()<=0)
            return 1;
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /********* Create a holder Class to contain inflated xml file elements *********/
    public static class ViewHolder{

        public TextView textFrom;
        public TextView textStatus;
        public Button btnAccept,btnReject;

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        ViewHolder holder;

        if(convertView==null){

            /****** Inflate tabitem.xml file for each row ( Defined below ) *******/
            vi = inflater.inflate(R.layout.tabitem, null);

            /****** View Holder Object to contain tabitem.xml file elements ******/

            holder = new ViewHolder();
            /*holder.textFrom = (TextView) vi.findViewById(R.id.txtRqstFrmName);
            holder.textStatus=(TextView)vi.findViewById(R.id.txtStatusShown);
            holder.btnAccept = (Button) vi.findViewById(R.id.btnAccept);
            holder.btnReject = (Button) vi.findViewById(R.id.btnReject);*/
            TextView textFrom = (TextView) vi.findViewById(R.id.txtRqstFrmName);
            TextView textStatus=(TextView)vi.findViewById(R.id.txtStatusShown);
            Button btnAccept = (Button) vi.findViewById(R.id.btnAccept);
            Button btnReject = (Button) vi.findViewById(R.id.btnReject);

            tempValues = ( PendingList ) data.get( position );

            /************  Set Model values in Holder elements ***********/
            Log.d("adapter",tempValues.getName());
            Log.d("adapter",tempValues.getStatus());
            textFrom.setText( tempValues.getName() );
            textStatus.setText( tempValues.getStatus() );
            /************  Set holder with LayoutInflater ************/
            vi.setTag( holder );
        }
       /* else {
            holder = (ViewHolder) vi.getTag();
        }

        if(data.size()<=0)
        {
           // holder.text.setText("No Data");

        }
        else
        {
            *//***** Get each Model object from Arraylist ********//*
            tempValues=null;
            tempValues = ( PendingList ) data.get( position );

            *//************  Set Model values in Holder elements ***********//*
            Log.d("adapter",tempValues.getName());
            Log.d("adapter",tempValues.getStatus());
            holder.textFrom.setText( tempValues.getName() );
            holder.textStatus.setText( tempValues.getStatus() );


            *//******** Set Item Click Listner for LayoutInflater for each row *******//*

          //  vi.setOnClickListener(new AdapterView.OnItemClickListener( position ));
        }*/
        return vi;
    }


}
