package com.example.dex.paygrid_demo_20;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class PayActivity extends AppCompatActivity {
    private static final String TAG = "PayActivity";
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);

        userID = getIntent().getStringExtra("userID");

        View btnPay = findViewById(R.id.btPay);
        EditText paymentCode = findViewById(R.id.etPaymentCode);

        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendPayment();
            }
        });

        paymentCode.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                getPaymentInfo();
            }
        });
    }

    private void getPaymentInfo() {
        EditText paymentcode = findViewById(R.id.etPaymentCode);
        String code = paymentcode.getText().toString();

        final TextView amount = findViewById(R.id.tvAmount);
        final TextView username = findViewById(R.id.username);

        Log.e(TAG, code);
        Query query = FirebaseDatabase.getInstance().getReference("PaymentRequests").orderByChild("paymentCode").equalTo(code.toUpperCase());

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot item : dataSnapshot.getChildren()) {
                        Log.e(TAG, item.getValue().toString());
                        amount.setText(item.child("/amount").getValue().toString());

                        String userID = item.child("/userID").getValue().toString();
                        FirebaseDatabase.getInstance().getReference("Users").child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    username.setText(dataSnapshot.child("/name").getValue().toString());
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                } else {
                    amount.setText("");
                    username.setText("");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void sendPayment() {
        EditText paymentcode = findViewById(R.id.etPaymentCode);
        String code = paymentcode.getText().toString();

        Query query = FirebaseDatabase.getInstance().getReference("PaymentRequests").orderByChild("paymentCode").equalTo(code.toUpperCase());

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot item : dataSnapshot.getChildren()) {
                        String receiverID = item.child("/userID").getValue().toString();
                        final Float addBalance = Float.parseFloat(item.child("/amount").getValue().toString());

                        // add to balance of receiver
                        final DatabaseReference receiver = FirebaseDatabase.getInstance().getReference("Users").child(receiverID);

                        receiver.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    Float oldBalance = Float.parseFloat(dataSnapshot.child("/balance").getValue().toString());
                                    dataSnapshot.child("/balance").getRef().setValue(oldBalance + addBalance);
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                        // remove from balance of sender
                        final DatabaseReference sender = FirebaseDatabase.getInstance().getReference("Users").child(userID);

                        sender.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    Float oldBalance = Float.parseFloat(dataSnapshot.child("/balance").getValue().toString());
                                    dataSnapshot.child("/balance").getRef().setValue(oldBalance - addBalance);
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                        // remove request
                        item.getRef().removeValue();

                        startActivity(new Intent(PayActivity.this, ProfileActivity.class));
                        finish();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
