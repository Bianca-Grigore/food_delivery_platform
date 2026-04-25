package com.pao.proiect.tema.model;

import com.pao.proiect.tema.exception.InvalidDetailsException;
import java.util.Optional;


public class CashPayment extends BasePayment {
    private boolean needsChange;
    private double clientAmount;
    private double deliveryChange;
    private String note;

    public CashPayment(double amount, boolean needsChange, double clientAmount, String note) {
        super(amount);
        this.needsChange = needsChange;
        this.clientAmount = clientAmount;
        this.note = note;

        if (needsChange && clientAmount > amount) {
            this.deliveryChange = clientAmount - amount;
        } else {
            this.deliveryChange = 0.0;
        }
    }

    public Optional<String> getNote() {
        return Optional.ofNullable(note);
    }

    @Override
    protected String getPrefix() {
        return "CASH";
    }

    @Override
    public boolean processPayment() {
        try {
            this.status = PaymentStatus.COMPLETED;
            System.out.println("Payment successful. Transaction ID: " + getTransactionID());
            if(this.deliveryChange > 0){
                System.out.println("Delivery change to give: $" + deliveryChange);
            }
            return true;

        } catch (InvalidDetailsException e) {
            this.status = PaymentStatus.FAILED;
            System.out.println("Payment failed: " + e.getMessage());
            return false;
        }
    }

    @Override
    public void setTips(double tipAmount){
        super.setTips(tipAmount);
        if (needsChange && clientAmount > getTotalAmount()) {
            this.deliveryChange = clientAmount - getTotalAmount();
        } else {
            this.deliveryChange = 0.0;
        }
    }
}