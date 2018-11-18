package com.example.dex.paygrid_demo_20;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {
    private static final String TAG = "ProfileActivity";

    private TextView profile;
    private TextView balance;
    private Button generate;
    private Button pay;
    private TextView balanceid;
    private Button logout;
    private Button profiledet;

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    DatabaseReference databaseUser;

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

        //initializing firebase authentication object
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        //if the user is not logged in
        //that means current user will return null
        if (user == null) {
            //closing this activity
            finish();
            //starting login activity
            startActivity(new Intent(this, LoginActivity.class));
        }

        // find current DB user
        Query query = FirebaseDatabase.getInstance().getReference("Users").orderByChild("email").equalTo(user.getEmail());

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot item : dataSnapshot.getChildren()) {
                        //if (item.child("/phone").toString().equals(user.getPhoneNumber())) { // can't use this because phone is not registered in firebaseAuth
                        Log.e(TAG, item.getValue().toString());
                        databaseUser = item.getRef();
                        balance.setText(item.child("/balance").getValue().toString());
                        //}
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, PaymentCodeActivity.class);
                intent.putExtra("userID", databaseUser.getKey());
                startActivity(intent);
            }
        });

        profiledet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, ProfileDetailsActivity.class));
            }
        });

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, PayActivity.class);
                intent.putExtra("userID", databaseUser.getKey());
                startActivity(intent);
            }
        });
    }


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