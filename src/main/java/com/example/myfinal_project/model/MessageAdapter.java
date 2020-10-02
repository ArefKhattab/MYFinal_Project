package com.example.myfinal_project.model;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfinal_project.R;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageAdapterViewHolder> {

    Context context;
    List<Message> messages;
    DatabaseReference messagedb;
    int adapterPosision;

    public MessageAdapter(Context context, List<Message> messages, DatabaseReference messagedb) {
        this.context = context;
        this.messages = messages;
        this.messagedb = messagedb;
    }

    @NonNull
    @Override
    public MessageAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_message, parent, false);
        return new MessageAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapterViewHolder holder, int position) {
        Message message = messages.get(position);
        if (message.getName().equals(AllMethods.name)) {
            holder.tvTitle.setText("you : \n" + message.getMessage());
            holder.tvTitle.setGravity(Gravity.START);
            holder.linearLayout.setBackgroundColor(Color.parseColor("#EF9E73"));

        } else {
            holder.tvTitle.setText(message.getName() + ": \n" + message.getMessage());
            holder.del.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class MessageAdapterViewHolder extends RecyclerView.ViewHolder {


        TextView tvTitle;
        ImageView del;
        LinearLayout linearLayout;

        public MessageAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTilte);
            del = itemView.findViewById(R.id.delet);
            linearLayout = itemView.findViewById(R.id.l1message);

            del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    adapterPosision = getAdapterPosition();
                    DeletDialog(adapterPosision);
                }
            });
        }
    }

    private void DeletDialog(int adapterposision) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("delet message From every one ?")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        messagedb.child(messages.get(adapterposision).getKey()).removeValue();


                    }

                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }

                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
