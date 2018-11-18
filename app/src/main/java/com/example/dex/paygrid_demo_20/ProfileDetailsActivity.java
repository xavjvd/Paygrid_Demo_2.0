package com.example.dex.paygrid_demo_20;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.firebase.client.DataSnapshot;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class ProfileDetailsActivity extends AppCompatActivity {
    private static final String TAG = "ViewDatabase";


    //add Firebase Database stuff
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private String userID;

    private ListView mListview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_details);

        mListview = findViewById(R.id.balance_view);

        //declare the database reference object. This is what we use to access the database.
        //NOTE: Unless you are signed in, this will not be useable.
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());

                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");

                }
                // ...
            }
        };

        myRef.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot) {
                showData(dataSnapshot);
                
            }

            private void showData(com.google.firebase.database.DataSnapshot dataSnapshot) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

     

            }
        });
    }

    private void showData(DataSnapshot dataSnapshot) {
        for(DataSnapshot ds : dataSnapshot.getChildren()){
//            User uInfo = new User();
//            uInfo.setName(ds.child(userID).getValue(User.class).getName()); //set the name
//            uInfo.setEmail(ds.child(userID).getValue(User.class).getEmail()); //set the email
//            uInfo.setPhone(ds.child(userID).getValue(User.class).getPhone()); //set the phone_num
//            uInfo.setBalance(ds.child(userID).getValue(User.class).getBalance()); //set the balance
//
//            //display all the information
//            Log.d(TAG, "showData: name: " + uInfo.getName());
//            Log.d(TAG, "showData: email: " + uInfo.getEmail());
//            Log.d(TAG, "showData: phone_num: " + uInfo.getPhone());
//            Log.d(TAG, "showData: balance: " + uInfo.getBalance());
//
//            ArrayList<String> array  = new ArrayList<>();
//            array.add(uInfo.getName());
//            array.add(uInfo.getEmail());
//            array.add(uInfo.getPhone());
//            array.add(uInfo.getBalance());
//            ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,array);
//            mListview.setAdapter(adapter);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }

    }
}
