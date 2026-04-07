package com.pao.proiect.tema.model;

public interface Payment {
    boolean processPayment(double amount);
    boolean refund(double amount);
    PaymentStatus getStatus();
    String getTransactionID();
    double getAmount();
}