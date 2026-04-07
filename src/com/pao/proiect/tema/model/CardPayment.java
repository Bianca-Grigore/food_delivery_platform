package com.pao.proiect.tema.model;

import com.pao.proiect.tema.exception.InvalidDetailsException;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


public class CardPayment extends BasePayment{

    private final String holderName;
    private final String expirationDate;
    private final String CVV;
    private final String cardNumber;

    public CardPayment(double amount, String holder, String exp, String ccv, String number){
        super(amount);
        this.holderName = holder;
        this.expirationDate = exp;
        this.CVV = ccv;
        this.cardNumber = number;
    }

    @Override
    protected String getPrefix(){
        return "CARD";
    }

    private boolean validateDetails(){
        if(holderName == null || holderName.trim().isEmpty()){
            throw new InvalidDetailsException("Card holder name cannot be empty.");
        }

        if(expirationDate == null || expirationDate.trim().isEmpty() || !expirationDate.matches("(0[1-9]|1[0-2])/\\d{2}")){
            throw new InvalidDetailsException("Invalid expiration date format. Use MM/YY.");
        }

        try{
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yy");
            YearMonth expiryDate = YearMonth.parse(expirationDate, formatter);
            YearMonth currentDate = YearMonth.now();
            if(expiryDate.isBefore(currentDate)){
                throw new InvalidDetailsException("Card has expired.");
            }
        }catch (DateTimeParseException e){
            throw new InvalidDetailsException("Error processing expiration date.");
        }

        if(cardNumber == null || cardNumber.trim().isEmpty() || !cardNumber.matches("\\d{16}")){
            throw new InvalidDetailsException("Card number must have 16 digits.");
        }

        if(CVV == null || !CVV.matches("\\d{3}")){
            throw new InvalidDetailsException("CVV must have 3 digits.");
        }
        return true;
    }

    @Override
    public boolean processPayment(double amountProcess) {
        try{
            validateDetails();
            if(amountProcess < getTotalAmount()){
                throw new InvalidDetailsException("Insufficient amount provided for this transaction.");
            }

            this.status = PaymentStatus.COMPLETED;
            System.out.println("Payment of $" + amountProcess + " processed successfully. Transaction ID: " + getTransactionID());
            return true;

        }catch (InvalidDetailsException e){
            this.status = PaymentStatus.FAILED;
            System.out.println("Payment failed: " + e.getMessage());
            return false;
        }
    }
}