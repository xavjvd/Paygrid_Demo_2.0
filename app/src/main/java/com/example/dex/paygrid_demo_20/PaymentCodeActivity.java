package com.example.dex.paygrid_demo_20;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.Random;

public class PaymentCodeActivity extends AppCompatActivity {
    private static final String TAG = "PaymentCodeActivity";

    EditText payamount;
    TextView discription;
    TextView paymentCode;
    Button generate;
    Button payrequest;
    ProgressDialog progressDialog;

    //Firebase
    DatabaseReference databaseUser;
    FirebaseAuth mAuth;

    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        payamount = findViewById(R.id.etPayAmount);
        discription = findViewById(R.id.tvDiscrp);
        paymentCode = findViewById(R.id.tvCode);
        generate = findViewById(R.id.btnPay);
        payrequest = findViewById(R.id.btnRequestPayment);
        progressDialog = new ProgressDialog(this);

        //initializing firebase authentication object
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        Query query = FirebaseDatabase.getInstance().getReference("Users").orderByChild("email").equalTo(user.getEmail());

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot item : dataSnapshot.getChildren()) {
                        //if (item.child("/phone").toString().equals(user.getPhoneNumber())) { // can't use this because phone is not registered in firebaseAuth
                            Log.e(TAG, item.getValue().toString());
                            databaseUser = item.getRef();
                        //}
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        payrequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPaymentRequest();
            }
        });
    }

    public void generate(View view) {
        Random rand = new Random();
        int number = rand.nextInt(10000);
        TextView code = findViewById(R.id.tvCode);
        String myString = String.valueOf(number);
        code.setText(myString);

    }

    private void addPaymentRequest() {

        String amount = payamount.getText().toString().trim();
        String paymentcode = paymentCode.getText().toString().trim();

        if (!TextUtils.isEmpty(amount)) {
            if (databaseUser != null) {
                String id = databaseUser.child("/payment-requests").push().getKey();
                PaymentRequest paymentRequest = new PaymentRequest(id, amount, paymentcode);
                databaseUser.child("/payment-requests/" + id).setValue(paymentRequest);

                Toast.makeText(this, "PaymentRequest added", Toast.LENGTH_LONG).show();
                startActivity(new Intent(PaymentCodeActivity.this, PayCodeViewActivity.class));
                finish();
            }

        } else {
            Toast.makeText(this, "Please enter Amount", Toast.LENGTH_LONG).show();
        }
    }
}





