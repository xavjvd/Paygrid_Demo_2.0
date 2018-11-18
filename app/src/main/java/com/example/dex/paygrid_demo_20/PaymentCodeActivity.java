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

        userID = getIntent().getStringExtra("userID");

        payrequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPaymentRequest();
            }
        });
    }

    public void generate(View view) {
        TextView code = findViewById(R.id.tvCode);
        String chars = "0123456789QWERTYUIOPASDFGHJKLZXCVBNM";
        int length = 10;
        final Random random = new Random();
        final StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; ++i) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }

        code.setText(sb.toString());
    }

    private void addPaymentRequest() {

        String amount = payamount.getText().toString().trim();
        String paymentcode = paymentCode.getText().toString().trim();

        if (!TextUtils.isEmpty(amount)) {
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("PaymentRequests");
            ref.push().setValue(new PaymentRequest(userID, amount, paymentcode));

            Toast.makeText(this, "PaymentRequest added", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(PaymentCodeActivity.this, PayCodeViewActivity.class);
            intent.putExtra("amount", amount);
            intent.putExtra("paymentcode", paymentcode);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Please enter Amount", Toast.LENGTH_LONG).show();
        }
    }
}





