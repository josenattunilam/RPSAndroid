package com.example.hi.rps;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by HP on 15-11-2017.
 */

public class CustomAdapterPending extends RecyclerView.Adapter<CustomAdapterPending.MyViewHolder>  {
    List<PendingList> playRqstList = new ArrayList<>();
    Context context;
    private boolean AcceptOrReject = false;
    private String clientName = null;
    public static  String send = null;
    public CustomAdapterPending(List<PendingList> playRqstList,Context context) {


        this.context = context;
    }
    public void setlist(List<PendingList> playerRqstSendList)
    {
        this.playRqstList.addAll(playerRqstSendList);
        notifyDataSetChanged();
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.tabitem,parent,false);
        final MyViewHolder myViewHolder = new MyViewHolder(itemView);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final PendingList playrqstlisting = playRqstList.get(position);
        holder.txtRqstName.setText(playrqstlisting.getName());
        holder.txtRqstStatus.setText(playrqstlisting.getStatus());
        holder.btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG",playrqstlisting.getName());
                AcceptOrReject = true;
                clientName = playrqstlisting.getName();
                Log.d("TAG",clientName);
                Toast.makeText(context,"send",Toast.LENGTH_SHORT).show();
                String s="<AcceptPlayRequest ";
                String s1="/>";
                //Log.d("TAG",  clientName);
                String st=clientName;
                //String[] separated=st.split("-");
                //Log.d("TAG",separated[0]);
                //String s1= separated[0]+"/>";
                send=s+st+s1;
                Log.d("TAG",send);
                MessageFunction();
                nextActivity(PlayGameActivity.class);


            }
        });
        holder.btnReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG",playrqstlisting.getStatus());
                AcceptOrReject = false;
                clientName = playrqstlisting.getName();
                Log.d("TAG",clientName);
                Toast.makeText(context,"send",Toast.LENGTH_SHORT).show();
                String s="<RejectPlayRequest ";
                String s1="/>";
                //Log.d("TAG",  clientName);
                String st=clientName;
                //String[] separated=st.split("-");
                //Log.d("TAG",separated[0]);
                //String s1= separated[0]+"/>";
                send=s+st+s1;
                Log.d("TAG",send);
                MessageFunction();
                nextActivity(SecondActiityN.class);
            }
        });

    }

    private void nextActivity(final Class<?extends Activity> activity1){
        Intent intent = new Intent(context,activity1);
        context.startActivity(intent);

    }


    private void MessageFunction() {
        final Handler handler = new Handler();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {

                    Socket s=SocketHandler.getSocket();

                    OutputStream out = s.getOutputStream();

                    PrintWriter output = new PrintWriter(out);

                    output.println(send);

                    output.flush();

                    BufferedReader input2 = new BufferedReader(new InputStreamReader(s.getInputStream()));

                    final String st3 = input2.readLine();

                    //showToast(st3);



                } catch (IOException e) {
                    Log.d("TAG","error in recycler");
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }

    @Override
    public int getItemCount() {
        return playRqstList.size();
    }



    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView txtRqstName,txtRqstStatus;
        private Button btnAccept,btnReject;
        public MyViewHolder(View itemView) {
            super(itemView);
            txtRqstName = (TextView)itemView.findViewById(R.id.txtRqstFrmName);
            txtRqstStatus = (TextView)itemView.findViewById(R.id.txtStatusShown);
            btnAccept = (Button)itemView.findViewById(R.id.btnAccept);
            btnReject = (Button)itemView.findViewById(R.id.btnReject);

        }
    }
}
