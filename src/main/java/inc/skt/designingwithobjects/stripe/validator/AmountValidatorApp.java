package inc.skt.designingwithobjects.stripe.validator;

import java.util.List;
import java.util.stream.Collectors;

public class AmountValidatorApp {

    public static void main(String[] args) {

    }
}

/**
 * Each string is a comma-separated value (CSV) in the format: "TRANSACTION_ID,AMOUNT_IN_DOLLARS,STATUS"
 */
class Validator {

    private static long parseToUnits(String currency, String amount) {
        if(currency.equals("USD")) {
            if(Double.parseDouble(amount) > 0) {
                return Math.round(Double.parseDouble(amount) * 100);
            }
        }
        if(currency.equals("JPY")) {
            if(Double.parseDouble(amount) > 0) {
                return Math.round(Double.parseDouble(amount));
            }
        }
        return 0;
    }
    public long getValidatedAmount(List<String> transactions) {
        List<String> succededTransactions= transactions
                .stream()
                .filter(transaction -> transaction.contains("SUCCESS"))
                .collect(Collectors.toList());
        long totalAmount = 0;
        for(String transaction : succededTransactions) {
            String[] transactionDetails = transaction.split(",");
            if(transactionDetails.length == 4) {
                String amount = transactionDetails[1];
                String currency = transactionDetails[2];
                totalAmount += parseToUnits(currency, amount);
            }
        }
        return totalAmount;
    }
}
