package inc.skt.designingwithobjects.stripe.debtresolver;

import java.util.*;

/*
* Example Data
* Balances:
USD: -5000 (Negative $50.00)
EUR: 8000 (Positive €80.00)
GBP: 2000 (Positive £20.00)

Sample Output (One possible solution):
From: EUR, To: USD, Amount: 4545 (Covering roughly 5000 USD using EUR)
Resulting State: USD: 0, EUR: 3455, GBP: 2000
* */
public class DebtResolverApp {
    public static void main(String[] args) {
        Map<String, Integer> balanceMap = new HashMap<>();
        balanceMap.put("USD", -5000);
        balanceMap.put("EUR", 8000);
        balanceMap.put("GBP", 2000);
        Map<String, Double> conversionMap = new HashMap<>();
        conversionMap.put("USD", 1.0);
        conversionMap.put("EUR", 1.10);
        conversionMap.put("GBP", 1.30);
    }
}

class DebtResolver {

    public static double convertToUSD(int amount, String exchange, Map<String, Double> conversionMap) {
        return conversionMap.get(exchange) * amount;
    }

    public static double convertUSDtoExchange(double amount, double exchangeRate) {
        return amount / exchangeRate;
    }
    public List<Transfer> resolveDebt(Map<String, Integer> balanceMap, Map<String, Double> conversionMap) {
        List<String> debtorsList = new ArrayList<>();
        List<String> creditorsList = new ArrayList<>();
        List<Transfer> transferList = new ArrayList<>();

        for(Map.Entry<String, Integer> entry : balanceMap.entrySet()) {
            if(entry.getValue() < 0) {
                debtorsList.add(entry.getKey());
            }
            if(entry.getValue() > 0) {
                creditorsList.add(entry.getKey());
            }
        }

        Map<String, Integer> creditorsMap = new HashMap();
        for(String creditor : creditorsList) {
            creditorsMap.put(creditor, balanceMap.get(creditor));
        }

        /*
        * Rates (to USD):
        USD: 1.0
        EUR: 1.10 (1 EUR = 1.10 USD)
        GBP: 1.30 (1 GBP = 1.30 USD)
        * */
        for(String debtor : debtorsList) {
            int debtRequiredInCents = Math.abs(balanceMap.get(debtor));
            for(String creditor : creditorsList) {
                Double creditorBalanceInUSD = creditorsMap.get(creditor) * conversionMap.get(creditor);
                if(creditorBalanceInUSD >= debtRequiredInCents) {
                    transferList.add(new Transfer(creditor, debtor, (int)convertUSDtoExchange(debtRequiredInCents, conversionMap.get(creditor))));
                    creditorBalanceInUSD -= debtRequiredInCents;
                    creditorsMap.put(creditor, (int)Math.ceil(convertUSDtoExchange(creditorBalanceInUSD, conversionMap.get(creditor))));
                    debtRequiredInCents = 0;
                    break;
                } else if (creditorBalanceInUSD < debtRequiredInCents) {
                    int amountInNative = (int) Math.ceil(convertUSDtoExchange(creditorBalanceInUSD, conversionMap.get(creditor)));
                    transferList.add(new Transfer(creditor, debtor, amountInNative));
                    creditorsMap.put(creditor, 0);
                    debtRequiredInCents -= creditorBalanceInUSD;
                }
            }
        }

        return transferList;
    }
}



class Transfer {
    String from;
    String to;
    int amount;

    public Transfer(String from, String to, int amount) {
        this.from = from;
        this.to = to;
        this.amount = amount;
    }
}