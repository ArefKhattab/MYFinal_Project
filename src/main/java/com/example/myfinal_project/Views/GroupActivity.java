package com.example.myfinal_project.Views;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.myfinal_project.R;
import com.example.myfinal_project.model.AllMethods;
import com.example.myfinal_project.model.Message;
import com.example.myfinal_project.model.MessageAdapter;
import com.example.myfinal_project.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class GroupActivity extends AppCompatActivity {

    /**
     * auth.
     */
    private FirebaseAuth auth;
    /**
     * database.
     */
    private FirebaseDatabase database;
    /**
     * messagedb.
     */
    private DatabaseReference messagedb;
    /**
     * messageAdapter.
     */
    private MessageAdapter messageAdapter;
    /**
     * u.
     */
    private User u;
    /**
     * messages.
     */
    private List<Message> messages;
    /**
     * rvMessage.
     */
    private RecyclerView rvMessage;
    /**
     * edMessage.
     */
    private EditText edMessage;
    /**
     * send.
     */
    private ImageButton send;


    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);

        getSupportActionBar().setTitle("Public room");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        init();
    }

    private void init() {
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        u = new User();
        rvMessage = findViewById(R.id.recyclerView);
        edMessage = findViewById(R.id.editText_message);
        send = findViewById(R.id.button);
        messages = new ArrayList<>();

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if (!TextUtils.isEmpty(edMessage.getText().toString())) {
                    Message message = new Message(edMessage.getText().toString(), u.getName());

                    edMessage.setText("");
                    messagedb.push().setValue(message);
                } else {
                    Toast.makeText(GroupActivity.this, "Empty message", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.meun, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull final MenuItem item) {
        if (item.getItemId() == R.id.logout) {
            auth.signOut();
            finish();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        final FirebaseUser currentuser = auth.getCurrentUser();
        u.setUid(currentuser.getUid());
        u.setEmail(currentuser.getEmail());
        database.getReference("Users").child(currentuser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                u = dataSnapshot.getValue(User.class);
                assert u != null;
                u.setUid(currentuser.getUid());
                AllMethods.name = u.getName();
            }

            @Override
            public void onCancelled(@NonNull final DatabaseError databaseError) {

            }
        });

        messagedb = database.getReference("messages");
        messagedb.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull final DataSnapshot dataSnapshot, @Nullable final String s) {

                Message message = dataSnapshot.getValue(Message.class);
                message.setKey(dataSnapshot.getKey());
                messages.add(message);
                displayMessages(messages);

            }

            @Override
            public void onChildChanged(@NonNull final DataSnapshot dataSnapshot, @Nullable final String s) {

                Message message = dataSnapshot.getValue(Message.class);
                message.setKey(dataSnapshot.getKey());
                List<Message> newMessages = new ArrayList<>();
                for (Message m : messages) {
                    if (m.getKey().equals(message.getKey())) {

                        newMessages.add(message);

                    } else {
                        newMessages.add(m);
                    }

                }
                messages = newMessages;
                displayMessages(messages);
            }

            @Override
            public void onChildRemoved(@NonNull final DataSnapshot dataSnapshot) {

                Message message = dataSnapshot.getValue(Message.class);
                message.setKey(dataSnapshot.getKey());
                List<Message> newMessages = new ArrayList<>();
                for (Message m : messages) {
                    if (!m.getKey().equals(message.getKey())) {
                        newMessages.add(m);
                    }
                }
                messages = newMessages;
                displayMessages(messages);
            }

            @Override
            public void onChildMoved(@NonNull final DataSnapshot dataSnapshot, @Nullable final String s) {

            }

            @Override
            public void onCancelled(@NonNull final DatabaseError databaseError) {

            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();

        messages = new ArrayList<>();
    }

    private void displayMessages(final List<Message> messages) {
        rvMessage.setLayoutManager(new LinearLayoutManager(GroupActivity.this));
        messageAdapter = new MessageAdapter(GroupActivity.this, messages, messagedb);
        rvMessage.setAdapter(messageAdapter);
        messageAdapter.notifyDataSetChanged();
    }

}
