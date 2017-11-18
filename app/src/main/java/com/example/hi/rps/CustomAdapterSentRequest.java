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
 * Created by hi on 16-11-2017.
 */

public class CustomAdapterSentRequest extends RecyclerView.Adapter<CustomAdapterSentRequest.MyViewHolder> {
    List<SentRequestList> RqstList = new ArrayList<>();
    Context context;
    //private boolean AcceptOrReject = false;
    private String clientName = null;
    public static String send = null;
    ItemClickListner clickListner;

    public CustomAdapterSentRequest(List<SentRequestList> RqstList, Context context,ItemClickListner clickListner) {

        this.context = context;
        this.clickListner = clickListner;
    }

    public void setlist(List<SentRequestList> playerRqstSendList) {
        this.RqstList.addAll(playerRqstSendList);
        notifyDataSetChanged();
    }

    @Override
    public CustomAdapterSentRequest.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.tabitemsendrqst, parent, false);
        final CustomAdapterSentRequest.MyViewHolder myViewHolder = new CustomAdapterSentRequest.MyViewHolder(itemView);
         itemView.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 clickListner.onItemClick(v,myViewHolder.getPosition());
             }
         });
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(CustomAdapterSentRequest.MyViewHolder holder, int position) {
        final SentRequestList playrqstlisting = RqstList.get(position);
        holder.txtRqstName.setText(playrqstlisting.getName());
        holder.txtRqstStatus.setText(playrqstlisting.getStatus());
        holder.btnDiscard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clientName = playrqstlisting.getName();
                Log.d("TAG", clientName);
                Toast.makeText(context, "send", Toast.LENGTH_SHORT).show();
                String s = "<DiscardPlayRequest ";
                String s1 = "/>";
                //Log.d("TAG",  clientName);
                String st = clientName;
                //String[] separated=st.split("-");
                //Log.d("TAG",separated[0]);
                //String s1= separated[0]+"/>";
                send = s + st + s1;
                Log.d("TAG", send);
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
                    Socket s = SocketHandler.getSocket();
                    OutputStream out = s.getOutputStream();
                    PrintWriter output = new PrintWriter(out);
                    output.println(send);
                    output.flush();
                    BufferedReader input2 = new BufferedReader(new InputStreamReader(s.getInputStream()));
                    final String st3 = input2.readLine();
                    //showToast(st3);

                    Log.d("con", st3);

                    //output1.close();
                    //out1.close();

                } catch (IOException e) {
                    Log.d("TAG", "error in recycler");
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }

    @Override
    public int getItemCount() {
        return RqstList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView txtRqstName, txtRqstStatus;
        private Button btnDiscard;
        public MyViewHolder(View itemView) {
            super(itemView);
            txtRqstName = (TextView) itemView.findViewById(R.id.txtRqstSendName);
            txtRqstStatus = (TextView) itemView.findViewById(R.id.txtStatusSendShown);
            btnDiscard = (Button) itemView.findViewById(R.id.btndiscard);
            btnDiscard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            //btnReject = (Button) itemView.findViewById(R.id.btnReject);
        }
    }

   /* @Override
    public CustomAdapterSentRequest.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.tabitem, parent, false);
        final CustomAdapterSentRequest.MyViewHolder myViewHolder = new CustomAdapterSentRequest.MyViewHolder(itemView);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(CustomAdapterSentRequest.MyViewHolder holder, final int position) {
        final SentRequestList playrqstlisting = RqstList.get(position);
        holder.txtRqstName.setText(playrqstlisting.getName());
        holder.txtRqstStatus.setText(playrqstlisting.getStatus());
        //holder.btnDiscard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG", playrqstlisting.getName());
                //AcceptOrReject = true;
                //Discard=true;
                clientName = playrqstlisting.getName();
                Log.d("TAG", clientName);
                Toast.makeText(context, "send", Toast.LENGTH_SHORT).show();
                String s = "<DiscardPlayRequest ";
                String s1 = "/>";
                //Log.d("TAG",  clientName);
                String st = clientName;
                //String[] separated=st.split("-");
                //Log.d("TAG",separated[0]);
                //String s1= separated[0]+"/>";
                String send = s + st + s1;
                Log.d("TAG", send);
                MessageFunction();


            }
        });
    }
        holder.btnReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG",playrqstlisting.getStatus());
                //AcceptOrReject = false;
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
                String send=s+st+s1;
                Log.d("TAG",send);
                MessageFunction();
            }
        });

    }

    private void MessageFunction() {
        final Handler handler = new Handler();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Log.d("TAG", "from recycler");
                    Socket s = SocketHandler.getSocket();
                    Log.d("TAG", "recycler1");
                    OutputStream out = s.getOutputStream();
                    Log.d("TAG", "recycler2");
                    PrintWriter output = new PrintWriter(out);
                    Log.d("TAG", "recycler3");
                    Log.d("send", send.toString());
                    Log.d("TAG", "recycler4");
                    output.println(send);
                    Log.d("TAG", "recycler5");
                    output.flush();
                    Log.d("TAG", "recycler6");
                    BufferedReader input2 = new BufferedReader(new InputStreamReader(s.getInputStream()));
                    Log.d("TAG", "recycler7");
                    final String st3 = input2.readLine();
                    Log.d("TAG", "recycler8");
                    //showToast(st3);

                    Log.d("con", st3);

                    //output1.close();
                    //out1.close();

                } catch (IOException e) {
                    Log.d("TAG", "error in recycler");
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }

    @Override
    public int getItemCount() {
        return RqstList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView txtRqstName, txtRqstStatus;
        private Button btnAccept, btnReject;

        public MyViewHolder(View itemView) {
            super(itemView);
            txtRqstName = (TextView) itemView.findViewById(R.id.txtRqstFrmName);
            txtRqstStatus = (TextView) itemView.findViewById(R.id.txtStatusShown);
            //btnDiscard = (Button) itemView.findViewById(R.id.btnDiscard);
            //btnReject = (Button) itemView.findViewById(R.id.btnReject);

        }
    }

    @Override
    public int getItemCount() {
        return 0;
    }*/

}
