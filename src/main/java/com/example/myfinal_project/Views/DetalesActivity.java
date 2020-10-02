package com.example.myfinal_project.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.myfinal_project.R;

public class DetalesActivity extends AppCompatActivity {


    private int posision;
    private TextView tvCases, tvRecovered, tvCritical, tvActive, tvTodayCases, tvTotalDeaths, tvTodayDeaths, tvAffectedCountries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detales);

        Intent intent = getIntent();
        posision = intent.getIntExtra("position", 0);

        getSupportActionBar().setTitle("Details " + ListOfCountry.countryModelsList.get(posision).getCountry());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        tvCases = findViewById(R.id.tvCases);
        tvRecovered = findViewById(R.id.tvRecovered);
        tvCritical = findViewById(R.id.tvCritical);
        tvActive = findViewById(R.id.tvActive);
        tvTodayCases = findViewById(R.id.tvTodayCases);
        tvTotalDeaths = findViewById(R.id.tvTotalDeaths);
        tvTodayDeaths = findViewById(R.id.tvTodayDeaths);
        tvAffectedCountries = findViewById(R.id.tvAffectedCountries);

        loadData();

    }

    private void loadData() {
        new Thread(new Runnable() {
            public void run() {
                tvCases.setText(ListOfCountry.countryModelsList.get(posision).getCases());
                tvRecovered.setText(ListOfCountry.countryModelsList.get(posision).getRecovered());
                tvAffectedCountries.setText(ListOfCountry.countryModelsList.get(posision).getCountry());
                tvCritical.setText(ListOfCountry.countryModelsList.get(posision).getCritical());
                tvActive.setText(ListOfCountry.countryModelsList.get(posision).getActive());
                tvTodayCases.setText(ListOfCountry.countryModelsList.get(posision).getTodayCases());
                tvTotalDeaths.setText(ListOfCountry.countryModelsList.get(posision).getDeaths());
                tvTodayDeaths.setText(ListOfCountry.countryModelsList.get(posision).getTodayDeaths());

            }
        }).start();


    }

}
