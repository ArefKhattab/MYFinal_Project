package com.example.myfinal_project.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.example.myfinal_project.R;
import com.example.myfinal_project.model.ConestantFirbase;
import com.example.myfinal_project.model.MyViewHolder;
import com.example.myfinal_project.model.Video;
import com.example.myfinal_project.model.ViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class VideosView extends AppCompatActivity {


    private RecyclerView recyclerView;
    private final String VIDEO_ROOT = "video";

       @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videos_view);


           getSupportActionBar().setTitle(R.string.title4_name);
           getSupportActionBar().setDisplayHomeAsUpEnabled(true);
           getSupportActionBar().setDisplayShowHomeEnabled(true);


        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.INTERNET, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE
        }, 100);


        setUpRecycelerView();
        setUpFirebaseConniction();


    }


    private void setUpFirebaseConniction() {
        ConestantFirbase._firebaseDatabase = FirebaseDatabase.getInstance();
        ConestantFirbase._databaseReference = ConestantFirbase._firebaseDatabase.getReference(VIDEO_ROOT);

    }

    private void setUpRecycelerView() {
        recyclerView = findViewById(R.id.video_recyceller);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(VideosView.this));

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Video, ViewHolder> adapter =
                new FirebaseRecyclerAdapter<Video, ViewHolder>(
                        Video.class,
                        R.layout.video_layout,
                        ViewHolder.class,
                        ConestantFirbase._databaseReference

                ) {

                    @Override
                    protected void populateViewHolder(ViewHolder mviewholder, Video video, int i) {
                        mviewholder.setVideo(getApplication(), video.getTitle(), video.getVideoUrl());

                    }


                };

        recyclerView.setAdapter(adapter);

    }
}
