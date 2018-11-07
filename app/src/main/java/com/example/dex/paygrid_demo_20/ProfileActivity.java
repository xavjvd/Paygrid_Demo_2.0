package com.example.dex.paygrid_demo_20;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {

    private TextView profile;
    private TextView balance;
    private Button generate;
    private Button pay;
    private TextView balanceid;
    private FirebaseAuth mAuth;
    private Button logout;
    private Button profiledet;
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profile = findViewById(R.id.etProfile);
        balance = findViewById(R.id.tvBalance);
        balanceid = findViewById(R.id.tvBident);
        generate = findViewById(R.id.btnGen);
        pay = findViewById(R.id.btnPay);
        profiledet = findViewById(R.id.btnProfileDet);

        mDatabase = FirebaseDatabase.getInstance().getReference();



        //initializing firebase authentication object
        mAuth = FirebaseAuth.getInstance();

        //if the user is not logged in
        //that means current user will return null
        if (mAuth.getCurrentUser() == null) {
            //closing this activity
            finish();
            //starting login activity
            startActivity(new Intent(this, LoginActivity.class));


        }



        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, PaymentCodeActivity.class));
            }
        });

        profiledet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this,ProfileDetailsActivity.class));
            }
        });

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, PayActivity.class));
            }
        });
    }
    ValueEventListener user = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            int balance = dataSnapshot.child("user").child("balance").getValue(Integer.class);
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            // Getting Post failed, log a message
        }
    };











        @Override
    protected void onStart() {
        super.onStart();
        findViewById(R.id.btnLogout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();

                Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);

                startActivity(intent);
            }
        });
    }
}