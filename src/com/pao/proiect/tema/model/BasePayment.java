package com.pao.proiect.tema.model;

import static java.util.UUID.randomUUID;


public abstract class BasePayment implements Payment {
    protected PaymentStatus status;
    protected double amount;
    protected String transactionId;
    protected double tips;

    public BasePayment(double amount) {
        this.status = PaymentStatus.PENDING;
        this.amount = amount;
        this.transactionId = randomUUID().toString().substring(0, 8).toUpperCase();
        this.tips = 0.0;
    }

    protected abstract String getPrefix();

    public void setTips(double amountTip){
        if(amountTip > 0 ){
            this.tips = amountTip;
        }
    }

    public double getTotalAmount(){
       return this.amount + this.tips;
    }

    @Override
    public PaymentStatus getStatus() {
        return this.status;
    }

    @Override
    public double getAmount() {
        return amount;
    }

    @Override
    public String getTransactionID() {
        return getPrefix() + "-" + transactionId;
    }

    @Override
    public boolean refund(double refund) {
        if (status == PaymentStatus.COMPLETED && refund > 0){
            if(refund > this.amount){
                System.out.println("Tips are non-refundable. Max refund is $" + this.amount);
                return false;
            }
            status = PaymentStatus.REFUNDED;
            return true;
        }
        return false;
    }
}