import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.UUID;

import static java.util.UUID.randomUUID;

public class CardPayment extends BasePayment implements Payment {

    private String holderName;
    private String expirationDate;
    private String CVV;
    private String cardNumber;

    public CardPayment(String holder, String exp, String ccv, String number){
        super();
        this.holderName = holder;
        this.expirationDate = exp;
        this.CVV = ccv;
        this.cardNumber = number;
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
    public boolean processPayment(double amount) {
        try{
            validateDetails();
            this.amount = amount;
            this.status = PaymentStatus.COMPLETED;
            this.transactionId = randomUUID().toString().substring(0, 8).toUpperCase();

            System.out.println("Payment of $" + amount + " processed successfully. Transaction ID: " + transactionId);
            return true;
        }catch (InvalidDetailsException e){
            this.status = PaymentStatus.FAILED;
            System.out.println("Payment failed: " + e.getMessage());
            return false;
        }
    }

    @Override
    public PaymentStatus getStatus() {
        return this.status;
    }

    @Override
    public String getTransactionID() {
        return this.transactionId;
    }
}
