package inc.skt.designingwithobjects.stripe.payoutrequest;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PayoutRequestApp {
    public static void main(String[] args) {
        PayoutRequest payoutRequest = new PayoutRequest(800, "USD");
        Map<String, Long> currentBalances = new HashMap<>();
        currentBalances.put("USD", 500L);
        currentBalances.put("EUR", 200L);
        currentBalances.put("GBP", 200L);
        Payout payout = new Payout();
        List<Transaction> transactionList = payout.pay(payoutRequest, currentBalances);
        System.out.println(transactionList);
    }
}

class PayoutRequest {
    int amount;
    String currency;

    public PayoutRequest(int amount, String currency) {
        this.amount = amount;
        this.currency = currency;
    }
}

class Payout {
    static Map<String, BigDecimal> exchangeRateMap;
    static {
        exchangeRateMap = new HashMap<>();
        exchangeRateMap.put("EUR", new BigDecimal("1.10"));
        exchangeRateMap.put("GBP", new BigDecimal("1.25"));
    }

    public List<Transaction> pay(PayoutRequest targetPayout, Map<String, Long> currentBalances) {
        String targetCurrency = targetPayout.currency;
        long targetAmount = targetPayout.amount;
        long targetAmountInCents = targetAmount * 100;
        Map<String, Long> balanceMapInCents = new HashMap<>();
        for(Map.Entry<String, Long> entrySet: currentBalances.entrySet()) {
            balanceMapInCents.put(entrySet.getKey(), entrySet.getValue()*100);
        }

        List<Transaction> transactionList = new ArrayList<>();

        if(balanceMapInCents.containsKey(targetCurrency)) {
            long availableBalanceInSameCurrency = balanceMapInCents.get(targetCurrency);
            if(availableBalanceInSameCurrency >= targetAmountInCents) {
                balanceMapInCents.put(targetCurrency, availableBalanceInSameCurrency - targetAmountInCents);
                transactionList.add(new Transaction(targetCurrency, targetAmountInCents/100L));
                return transactionList;
            } else {
                targetAmountInCents -= availableBalanceInSameCurrency;
                transactionList.add(new Transaction(targetCurrency, availableBalanceInSameCurrency/100L));
                balanceMapInCents.remove(targetCurrency);
            }
        }

        for(Map.Entry<String, Long> entrySet : balanceMapInCents.entrySet()) {
            if(targetAmountInCents == 0) break;
            String exchange = entrySet.getKey();
            long avialableExchangeInCents = entrySet.getValue();
           long balanceInUSDCents = convertExchangeToUSD(exchange, avialableExchangeInCents);
           if(balanceInUSDCents >= targetAmountInCents) {
               transactionList.add(new Transaction(exchange, convertUSDToExchange(exchange, targetAmountInCents)/100L));
               targetAmountInCents = 0;
               return transactionList;
           } else {
               targetAmountInCents -= balanceInUSDCents;
               transactionList.add(new Transaction(exchange, avialableExchangeInCents/100L));
           }
        }
        if(targetAmountInCents > 0) throw new IllegalArgumentException("Insufficient Funds");
        return transactionList;
    }
    public long convertExchangeToUSD(String currency, long amount) {
        if(currency.equals("USD")) return amount;
        BigDecimal exchangeAmount = BigDecimal.valueOf(amount);
        BigDecimal rate = exchangeRateMap.get(currency);
        return exchangeAmount.multiply(rate).setScale(0, RoundingMode.HALF_UP).longValue();
    }

    public long convertUSDToExchange(String currency, long amount) {
        if(currency.equals("USD")) return amount;
        BigDecimal usdAmount = BigDecimal.valueOf(amount);
        BigDecimal rate = exchangeRateMap.get(currency);
        return usdAmount.divide(rate, 0, RoundingMode.HALF_UP).longValue();
    }
}

class Transaction {
    String from;
    Long amount;

    public Transaction(String from, Long amount) {
        this.from = from;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "from='" + from + '\'' +
                ", amount=" + amount +
                '}';
    }
}