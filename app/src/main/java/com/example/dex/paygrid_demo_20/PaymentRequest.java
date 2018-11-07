package com.example.dex.paygrid_demo_20;

public class PaymentRequest {

    String requestId;
    String amount;
    String paymentcode;

    public PaymentRequest(){

    }

    public PaymentRequest(String requestId, String amount, String paymentcode){
        this.requestId = requestId;
        this.amount = amount;
        this.paymentcode = paymentcode;


    }

    public String getAmount() {
        return amount;
    }

    public  String getPaymentcode() {
        return paymentcode;
    }

}
