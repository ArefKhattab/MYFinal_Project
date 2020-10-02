package com.example.myfinal_project.model;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

public class ConestantFirbase {

    public static FirebaseAuth _mAuth;
    public static FirebaseUser _currentUser;
    public static FirebaseFirestore _firebaseFirestore;
    public static FirebaseDatabase _firebaseDatabase;
    public static DatabaseReference _databaseReference;

}
