package com.example.dex.paygrid_demo_20;

public class PaymentRequest {
    public String amount;
    public String paymentCode;
    public String userID;

    public PaymentRequest(String userID, String amount, String paymentCode) {
        this.userID = userID;
        this.amount = amount;
        this.paymentCode = paymentCode;
    }
}
