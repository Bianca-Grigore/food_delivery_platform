package com.pao.proiect.tema.model;

import com.pao.proiect.tema.exception.InvalidDetailsException;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


public class CardPayment extends BasePayment{

    private final CustomerCard card;

    public CardPayment(double amount, CustomerCard card) {
        super(amount);
        if(card == null){
            throw new IllegalArgumentException("Card details cannot be null.");
        }
        this.card = card;
    }

    @Override
    protected String getPrefix(){
        return "CARD";
    }

    private boolean validateDetails(){
        if(card.holderName() == null || card.holderName().trim().isEmpty()){
            throw new InvalidDetailsException("Card holder name cannot be empty.");
        }

        if(card.expirationDate() == null || card.expirationDate().trim().isEmpty() || !card.expirationDate().matches("(0[1-9]|1[0-2])/\\d{2}")){
            throw new InvalidDetailsException("Invalid expiration date format. Use MM/YY.");
        }

        try{
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yy");
            YearMonth expiryDate = YearMonth.parse(card.expirationDate(), formatter);
            YearMonth currentDate = YearMonth.now();
            if(expiryDate.isBefore(currentDate)){
                throw new InvalidDetailsException("Card has expired.");
            }
        }catch (DateTimeParseException e){
            throw new InvalidDetailsException("Error processing expiration date.");
        }

        if(card.cardNumber() == null || card.cardNumber().trim().isEmpty() || !card.cardNumber().matches("\\d{16}")){
            throw new InvalidDetailsException("Card number must have 16 digits.");
        }

        if(card.CVV() == null || !card.CVV().matches("\\d{3}")){
            throw new InvalidDetailsException("CVV must have 3 digits.");
        }
        return true;
    }

    @Override
    public boolean processPayment() {
        try{
            validateDetails();

            this.status = PaymentStatus.COMPLETED;
            System.out.println("Payment of $" + getTotalAmount() + " processed successfully. Transaction ID: " + getTransactionID());
            return true;

        }catch (InvalidDetailsException e){
            this.status = PaymentStatus.FAILED;
            System.out.println("Payment failed: " + e.getMessage());
            return false;
        }
    }
}