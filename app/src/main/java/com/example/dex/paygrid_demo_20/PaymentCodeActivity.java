package com.example.dex.paygrid_demo_20;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
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

import java.util.Random;

public class PaymentCodeActivity extends AppCompatActivity {
    EditText payamount;
    TextView discription;
    TextView paymentCode;
    Button generate;
    Button payrequest;
    ProgressDialog progressDialog;

    //Firebase
    DatabaseReference databasePaymentRequest;
    FirebaseAuth mAuth;

    String userID;



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

        databasePaymentRequest = FirebaseDatabase.getInstance().getReference("RequestPayment");

        //initializing firebase authentication object
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();


        payrequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPaymentRequest();
            }
        });
    }

    public void generate(View view){
        Random rand = new Random();
        int number = rand.nextInt(10000);
        TextView code = findViewById(R.id.tvCode);
        String myString = String.valueOf(number);
        code.setText(myString);

    }

    private void addPaymentRequest(){

        String amount = payamount.getText().toString().trim();
        String paymentcode = paymentCode.getText().toString().trim();

        if(!TextUtils.isEmpty(amount)){


            String id = databasePaymentRequest.push().getKey();
            PaymentRequest paymentRequest = new PaymentRequest(id, amount, paymentcode);
            databasePaymentRequest.child(id).setValue(paymentRequest);
            Toast.makeText(this,"PaymentRequest added", Toast.LENGTH_LONG).show();
            startActivity(new Intent(PaymentCodeActivity.this, PayCodeViewActivity.class));
            finish();

        }else{
            Toast.makeText(this,"Please enter Amount", Toast.LENGTH_LONG).show();
        }
    }
}





