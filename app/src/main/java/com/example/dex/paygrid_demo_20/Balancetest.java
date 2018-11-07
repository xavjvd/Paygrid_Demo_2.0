package com.example.dex.paygrid_demo_20;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class Balancetest extends AppCompatActivity {

    private TextView mValueView;
    private Firebase mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balancetest);

        Firebase.setAndroidContext(this);
        mValueView = findViewById(R.id.valueView);
        mRef = new Firebase("https://pg-demo-9a232.firebaseio.com/Users/jcnsFnZ7ztVbPA8hJCSTz1DEDjg1/balance");

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                mValueView.setText(value);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }
}
