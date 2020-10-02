package com.example.myfinal_project.Views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.myfinal_project.R;
import com.example.myfinal_project.model.ConestantFirbase;
import com.example.myfinal_project.model.CoronaClass;
import com.example.myfinal_project.model.RecycelerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class View_Activity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private ArrayList<CoronaClass> arrayList;
    private RecycelerAdapter recycelerAdapter;
    private ProgressBar progressBar;
    private DocumentSnapshot lastposotion;
    boolean ISscroling;
    boolean ISLastItem;
    LinearLayoutManager linearLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_);
        progressBar = findViewById(R.id.progressBar);

        getSupportActionBar().setTitle(R.string.title_name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        setUpRecycelerView();
        setUpFireBase();
        loadData();

        //description,information

    }

    private void loadData() {

        Query query = ConestantFirbase._firebaseFirestore.collection("information").limit(2);
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    arrayList = new ArrayList<>();
                    for (DocumentSnapshot snapshot : task.getResult()) {
                        CoronaClass coronaClass = new CoronaClass(snapshot.getString("title"),
                                snapshot.getString("description"), snapshot.getString("url"));
                        arrayList.add(coronaClass);
                    }


                    recycelerAdapter = new RecycelerAdapter(View_Activity.this, arrayList);
                    recyclerView.setAdapter(recycelerAdapter);
                    progressBar.setVisibility(View.GONE);
                    lastposotion = task.getResult().getDocuments().get(task.getResult().getDocuments().size() - 1);
                    Toast.makeText(View_Activity.this, "First page loaded", Toast.LENGTH_SHORT).show();

                    RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
                        @Override
                        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                            super.onScrollStateChanged(recyclerView, newState);
                            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                                ISscroling = true;
                            }
                        }

                        @Override
                        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                            super.onScrolled(recyclerView, dx, dy);
//
//                            if(recyclerView.canScrollVertically(RecyclerView.FOCUS_DOWN) == false){
//                                Toast.makeText(getApplicationContext(), "   Scroled", Toast.LENGTH_LONG).show();
//                            }


                            int firstItemVesebl = linearLayoutManager.findFirstCompletelyVisibleItemPosition();
                            int vesebleItemCount = linearLayoutManager.getChildCount();
                            int totalItemCount = linearLayoutManager.getItemCount();

                            if (ISscroling && (firstItemVesebl + vesebleItemCount == totalItemCount) && !ISLastItem) {
                                ISscroling = false;

                                fetchDataScrol();


                            }
                        }

                        private void fetchDataScrol() {
                            progressBar.setVisibility(View.VISIBLE);
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    if (recyclerView.canScrollVertically(RecyclerView.FOCUS_DOWN) == false) {

                                        Query Nextquery = ConestantFirbase._firebaseFirestore
                                                .collection("information")
                                                .startAfter(lastposotion)
                                                .limit(2);
                                        Nextquery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                progressBar.setVisibility(View.GONE);

                                                for (DocumentSnapshot snapshot : task.getResult()) {
                                                    CoronaClass coronaClass = new CoronaClass(snapshot.getString("title"),
                                                            snapshot.getString("description"), snapshot.getString("url"));
                                                    arrayList.add(coronaClass);


                                                }

                                                recycelerAdapter.notifyDataSetChanged();
                                                lastposotion = task.getResult().getDocuments().get(task.getResult().getDocuments().size() - 1);

                                                Toast.makeText(View_Activity.this, "Next page loaded", Toast.LENGTH_SHORT).show();

                                                if (task.getResult().size() < 2) {
                                                    ISLastItem = true;


                                                }
                                                progressBar.setVisibility(View.GONE);

                                            }
                                        });


                                    }
                                }

                            }, 5000);
                        }
                    };

                    recyclerView.addOnScrollListener(onScrollListener);
                }
            }
        });
    }

    private void setUpFireBase() {
        ConestantFirbase._firebaseFirestore = FirebaseFirestore.getInstance();
    }


    private void setUpRecycelerView() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(View_Activity.this);
        recyclerView.setLayoutManager(linearLayoutManager);

    }


}
