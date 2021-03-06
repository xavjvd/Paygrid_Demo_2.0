package com.example.dex.paygrid_demo_20;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PayCodeViewActivity extends AppCompatActivity {

    private TextView code;
    private TextView amount;
    private Button profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_code_view);


        code = findViewById(R.id.tvPayCode);
        amount = findViewById(R.id.tvAmount);
        profile = findViewById(R.id.btnProfile);

        String requestCode = getIntent().getStringExtra("paymentcode");
        String requestAmount = getIntent().getStringExtra("amount");

        code.setText(requestCode);
        amount.setText(requestAmount);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PayCodeViewActivity.this, ProfileActivity.class));
            }
        });
    }
}
