package com.example.myfinal_project.Views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.myfinal_project.R;
import com.example.myfinal_project.model.Adapter;
import com.example.myfinal_project.model.ConestantFirbase;
import com.example.myfinal_project.model.CoronaClass;
import com.example.myfinal_project.model.MyInfoClass;
import com.example.myfinal_project.model.RecycelerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class TrueInfo extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<MyInfoClass> list;
    private Adapter adapter;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_true_info);

        getSupportActionBar().setTitle(R.string.title5_name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        progressBar = findViewById(R.id.progressBar2);

        list = new ArrayList<>();
        setUpRecycelerView();
        setUpFireBase();
        LoadData();

    }

    private void LoadData() {
        progressBar.setVisibility(View.VISIBLE);
        if (list.size() > 0)
            list.clear();
        ConestantFirbase._firebaseFirestore.collection("helpinfo")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull final Task<QuerySnapshot> task) {


                                for (DocumentSnapshot snapshot : task.getResult()) {
                                    MyInfoClass aClass = new MyInfoClass(snapshot.getString("description"));
                                    list.add(aClass);
                                }


                        adapter = new Adapter(TrueInfo.this, list);
                        recyclerView.setAdapter(adapter);
                        progressBar.setVisibility(View.GONE);
                    }


                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(TrueInfo.this, "error read data", Toast.LENGTH_SHORT).show();
                        Log.d("error", e.getMessage());
                        progressBar.setVisibility(View.GONE);

                    }
                });
    }
    private void setUpFireBase() {
        ConestantFirbase._firebaseFirestore = FirebaseFirestore.getInstance();
    }


    private void setUpRecycelerView() {
        recyclerView = findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(TrueInfo.this));

    }


}


