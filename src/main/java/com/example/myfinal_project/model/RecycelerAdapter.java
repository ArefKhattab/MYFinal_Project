package com.example.myfinal_project.model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfinal_project.R;
import com.example.myfinal_project.Views.View_Activity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecycelerAdapter extends RecyclerView.Adapter<MyViewHolder> {
     View_Activity view_activity;
    ArrayList<CoronaClass> arrayList;

    public RecycelerAdapter(View_Activity view_activity, ArrayList<CoronaClass> arrayList) {
        this.view_activity = view_activity;
        this.arrayList = arrayList;
     }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(view_activity.getBaseContext());
        View view = layoutInflater.inflate(R.layout.custom_list_layout, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.title.setText(arrayList.get(position).getTitle());
        holder.description.setText(arrayList.get(position).getDescription());
        Picasso.get().load(arrayList.get(position).getUrl()).into(holder.imageView);


    }

    @Override
    public int getItemCount() {
         return arrayList.size();

     }

}
