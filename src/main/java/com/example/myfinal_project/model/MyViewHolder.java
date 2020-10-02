package com.example.myfinal_project.model;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfinal_project.R;


public class MyViewHolder extends RecyclerView.ViewHolder {

    public TextView title;
    public TextView description;
    public ImageView imageView;


    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.title_text);
        description = itemView.findViewById(R.id.info_text);
        imageView = itemView.findViewById(R.id.image_view);


    }

}
