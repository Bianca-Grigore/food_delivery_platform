package com.pao.proiect.tema.model;

public interface Payment {
    boolean processPayment();
    boolean refund(double amount);
    PaymentStatus getStatus();
    String getTransactionID();
    double getAmount();
}