package com.example.myfinal_project.Views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myfinal_project.R;
import com.example.myfinal_project.model.ConestantFirbase;
import com.example.myfinal_project.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Activity_signup extends AppCompatActivity {

    private EditText name, email, pass;
    private TextView reg_btn, lin;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        ConestantFirbase._mAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.regester_progress);


        email = findViewById(R.id.email_reg);
        name = findViewById(R.id.name_reg);
        pass = findViewById(R.id.password_reg);
        reg_btn = findViewById(R.id.reg_button);
        lin = findViewById(R.id.lin);


        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference().child("Users");


        reg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegesterUser();
            }
        });
        lin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            }
        });
    }

    private void RegesterUser() {
        progressBar.setVisibility(View.VISIBLE);
        final String n = name.getText().toString();
        final String e = email.getText().toString();
        final String p = pass.getText().toString();

        if (!n.equals("") && !email.equals("") && !p.equals("")) {
            auth.createUserWithEmailAndPassword(e, p).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {

                        FirebaseUser firbaseuser = auth.getCurrentUser();
                        User u = new User();
                        u.setName(n);
                        u.setName(e);

                        reference.child(firbaseuser.getUid()).setValue(u).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if (task.isSuccessful()) {
                                    Toast.makeText(Activity_signup.this, "Success create Account", Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.GONE);
                                    finish();
                                    startActivity(new Intent(getApplicationContext(), ContentActivity.class));

                                } else {
                                    Toast.makeText(Activity_signup.this, "Failer create this Account", Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.GONE);
                                }
                            }
                        });

                    }
                }
            });

        } else {
            Toast.makeText(this, "some input empty", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);

        }

    }


}
