package com.example.myfinal_project.model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfinal_project.R;
import com.example.myfinal_project.Views.TrueInfo;

import java.io.Serializable;
import java.util.List;

import io.grpc.Context;

public class Adapter extends RecyclerView.Adapter<AdapterViewHolder> {

    TrueInfo context;
    private List<MyInfoClass> list;

    public Adapter(TrueInfo context, List<MyInfoClass> list) {
        this.context = context;
        this.list = list;

    }

    @NonNull
    @Override
    public AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.info_true_layout, null, false);

        return new AdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterViewHolder holder, int position) {
        holder.text_description.setText(list.get(position).getDescInfo());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class AdapterViewHolder extends RecyclerView.ViewHolder {

    public TextView title, text_description;

    public AdapterViewHolder(@NonNull View itemView) {
        super(itemView);
        text_description = itemView.findViewById(R.id.desc_info);

    }
}

