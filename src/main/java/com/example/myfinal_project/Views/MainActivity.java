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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {


    private EditText email_ed, pass_ed;
    private TextView ed_reg;
    private TextView button;
    FirebaseAuth auth;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {

            startActivity(new Intent(getApplicationContext(), ContentActivity.class));
        } else {
            setContentView(R.layout.activity_main);
            email_ed = findViewById(R.id.email_login);
            pass_ed = findViewById(R.id.pass_login);
            ed_reg = findViewById(R.id.textView);
            button = findViewById(R.id.button_login);
            progressBar = findViewById(R.id.login_progress);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    progressBar.setVisibility(View.VISIBLE);
                    String email = email_ed.getText().toString();
                    String pass = pass_ed.getText().toString();
                    if (!email.equals("") && !pass.equals("")) {
                        auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(MainActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.GONE);

                                    startActivity(new Intent(getApplicationContext(), ContentActivity.class));

                                } else {
                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(MainActivity.this, "wrong email or password ", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }else{
                        Toast.makeText(MainActivity.this, "empty email or password ", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);

                    }

                }
            });

            ed_reg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getApplicationContext(), Activity_signup.class));
                    finish();

                }
            });

        }


    }
}
