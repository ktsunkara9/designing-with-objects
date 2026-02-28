package inc.skt.designingwithobjects.interview;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class InterviewApp {

    public static void main(String[] args) {
        String transactionCsv1 = "id,reference,amount,currency,date,merchant_id,buyer_country,transaction_type,payment_provider,status\n" +
                "py_1,1,1000,eur,2024-12-24,acct_1,ie,payment,card,payment_completed\n" +
                "py_2,2,2500,eur,2024-12-24,acct_2,ie,payment,card,payment_failed\n" +
                "py_3,3,3400,eur,2024-12-25,acct_2,ie,payment,klarna,payment_completed";
        String[] lines = transactionCsv1.split("\n");

        List<Transaction> transactionList = new ArrayList<>();
        for(int i = 1; i < lines.length; i++) {

            Transaction transaction = convertLineToTransaction(lines[i]);
            transactionList.add(transaction);
        }

        PaymentProcessor processor = new PaymentProcessor();
        List<Fee> feeList = processor.processPayment(transactionList);
        System.out.println(feeList);



    }

    private static Transaction convertLineToTransaction(String line) {
        String[] values = line.split(",");
        Transaction transaction = new Transaction();

            transaction.id = values[0];
            transaction.reference = values[1] ; // reference for the transaction

            transaction.amount = Integer.parseInt(values[2]); // amount in minor currency unit (e.g. cents, pennies)

            transaction.currency = values[3]; // ISO format of the currency

            transaction.date = values[4]; // date of the transaction completion

            transaction.merchant_id = values[5]; // transaction merchant

        transaction.buyer_country = values[6]; // country where the buyer paid, ISO code

        transaction.transaction_type = values[7]; // payment, refund, dispute

        transaction.payment_provider = values[8]; //  the way the buyer pays for the transaction. Example: card, klarna.

        transaction.status = values[9]; // payment_completed, payment_failed, payment_pending, dispute_won, dispute_lost, refund_completed, refund_failed, refund_pending
        return transaction;
    }

    static void check(Object actual, Object expected) {
        if (!Objects.equals(actual, expected)) {
            throw new RuntimeException("Test Failed! Expected [" + expected + "] but got [" + actual + "]");
        }
        System.out.println("Assertion Passed: " + actual);
    }

}

class PaymentProcessor {

    public List<Fee> processPayment(List<Transaction> transactionList) {
        List<Fee> feeList = new ArrayList<>();
        for(Transaction transaction : transactionList) {
            if(transaction.status.equals("payment_completed")) {
                // calculate Fee as 2.1% of paymentAmount + 30
              int calculatedFee =  (int)(Math.round((transaction.amount * 2.1)/100) + 30);
              Fee fee = new Fee(transaction.id, transaction.transaction_type, transaction.payment_provider, calculatedFee);
              feeList.add(fee);
            } else if(transaction.status.equals("dispute_lost")) {
                Fee fee = new Fee(transaction.id, transaction.transaction_type, transaction.payment_provider, 15);
                feeList.add(fee);
            } else if(transaction.status.equals("dispute_won")) {
                if(transaction.payment_provider.equals("card")) {
                    Fee fee = new Fee(transaction.id, transaction.transaction_type, transaction.payment_provider, 15);
                    feeList.add(fee);
                } else {
                    Fee fee = new Fee(transaction.id, transaction.transaction_type, transaction.payment_provider, 0);
                    feeList.add(fee);
                }
            } else {
                Fee fee = new Fee(transaction.id, transaction.transaction_type, transaction.payment_provider, 0);
                feeList.add(fee);
            }
        }
        return feeList;
    }
}

class Transaction {

    String id; // identifier for the transaction

    String reference; // reference for the transaction

    int amount; // amount in minor currency unit (e.g. cents, pennies)

    String currency; // ISO format of the currency

    String date; // date of the transaction completion

    String merchant_id; // transaction merchant

    String buyer_country; // country where the buyer paid, ISO code

    String transaction_type; // payment, refund, dispute

    String payment_provider; //  the way the buyer pays for the transaction. Example: card, klarna.

    String status; // payment_completed, payment_failed, payment_pending, dispute_won, dispute_lost, refund_completed, refund_failed, refund_pending

    public Transaction() {
    }

    public Transaction(String id, String reference, int amount, String currency, String date, String merchant_id, String buyer_country, String transaction_type, String payment_provider, String status) {
        this.id = id;
        this.reference = reference;
        this.amount = amount;
        this.currency = currency;
        this.date = date;
        this.merchant_id = merchant_id;
        this.buyer_country = buyer_country;
        this.transaction_type = transaction_type;
        this.payment_provider = payment_provider;
        this.status = status;
    }
}

 class Fee {

        String id; // transaction id

        String transaction_type; // payment, refund, dispute

        String payment_provider; // card, klarna

        int fee;

     public Fee(String id, String transaction_type, String payment_provider, int fee) {
         this.id = id;
         this.transaction_type = transaction_type;
         this.payment_provider = payment_provider;
         this.fee = fee;
     }

     @Override
     public String toString() {
         return "Fee{" +
                 "id='" + id + '\'' +
                 ", transaction_type='" + transaction_type + '\'' +
                 ", payment_provider='" + payment_provider + '\'' +
                 ", fee=" + fee +
                 '}';
     }
 }