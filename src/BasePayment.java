public abstract class BasePayment implements Payment {
    protected PaymentStatus status;
    protected double amount;
    protected String transactionId;

    public BasePayment(){
        this.status = PaymentStatus.PENDING;
        this.amount = 0.0;
        this.transactionId  = null;
    }

    @Override
    public PaymentStatus getStatus() {
        return this.status;
    }

    @Override
    public double getAmount(){
        return amount;
    }

    @Override
    public boolean refund(double refund){
        return true;
    }


}
