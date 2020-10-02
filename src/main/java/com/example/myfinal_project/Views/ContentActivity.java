package com.example.myfinal_project.Views;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;

import com.example.myfinal_project.R;

import java.util.ArrayList;

public class ContentActivity extends AppCompatActivity {
    GridLayout mainGrid;
    Intent intent;

    Switch aSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);


        mainGrid = (GridLayout) findViewById(R.id.mainGrid);

        //Set Event
        setSingleEvent(mainGrid);


    }

    private void setToggleEvent(GridLayout mainGrid) {
        //Loop all child item of Main Grid
        for (int i = 0; i < mainGrid.getChildCount(); i++) {
            //You can see , all child item is CardView , so we just cast object to CardView
            final CardView cardView = (CardView) mainGrid.getChildAt(i);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (cardView.getCardBackgroundColor().getDefaultColor() == -1) {
                        //Change background color
                        cardView.setCardBackgroundColor(Color.parseColor("#FF6F00"));
                        Toast.makeText(getApplicationContext(), "State : True", Toast.LENGTH_SHORT).show();

                    } else {
                        //Change background color
                        cardView.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                        Toast.makeText(getApplicationContext(), "State : False", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void setSingleEvent(GridLayout mainGrid) {
        //Loop all child item of Main Grid
        for (int i = 0; i < mainGrid.getChildCount(); i++) {
            //You can see , all child item is CardView , so we just cast object to CardView
            CardView cardView = (CardView) mainGrid.getChildAt(i);
            final int finalI = i;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    switch (finalI) {
                        case 0:
                            intent = new Intent(getApplicationContext(), View_Activity.class);
                            startActivity(intent);

                            break;
                        case 1:
                            intent = new Intent(getApplicationContext(), GlobalApi.class);
                            startActivity(intent);

                            break;
                        case 2:
                            intent = new Intent(getApplicationContext(), ListOfCountry.class);
                            startActivity(intent);

                            break;
                        case 3:
                            intent = new Intent(getApplicationContext(), VideosView.class);
                            startActivity(intent);

                            break;
                        case 4:
                            intent = new Intent(getApplicationContext(), TrueInfo.class);
                            startActivity(intent);

                            break;

                        case 5:
                            intent = new Intent(getApplicationContext(), GroupActivity.class);
                            startActivity(intent);
                            break;

                        default:
                            break;
                    }

                }
            });
        }

    }
}
