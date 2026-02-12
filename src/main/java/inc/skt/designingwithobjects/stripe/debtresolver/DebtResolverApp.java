package inc.skt.designingwithobjects.stripe.debtresolver;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/*
* Example Data
* Balances:
USD: -5000 (Negative $50.00)
EUR: 8000 (Positive €80.00)
GBP: 2000 (Positive £20.00)
Rates (to USD):
USD: 1.0
EUR: 1.10 (1 EUR = 1.10 USD)
GBP: 1.30 (1 GBP = 1.30 USD)
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
    public Map<String, Integer> resolveDebt(Map<String, Integer> balanceMap, Map<String, Double> conversionMap) {
        Map<String, Integer> resolutionMap = new HashMap<>();
        Map<String, Integer> resultMap = new HashMap<>();
        Iterator itr = conversionMap.entrySet().iterator();

        for(Map.Entry<String, Integer> entry : balanceMap.entrySet()) {
            if(entry.getValue() < 0) {
                resolutionMap.put(entry.getKey(), entry.getValue());
            }
        }
        return resultMap;
    }
}

class Transfer {
    String from;
    String to;
    int amount;


}