package inc.skt.designingwithobjects.stripe.payouts;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TransferGeneratorApp {
    public static void main(String[] args) {

    }
}

class TransferGenerator {

    public List<Transfer> generateTransfers(Map<String, Integer> bankBalanceMap, Map<String, Integer> merchantRequestMap) {
        List<Transfer> transferList = new ArrayList();

        for(Map.Entry<String, Integer> merchantEntry: merchantRequestMap.entrySet()) {
            String merchantName = merchantEntry.getKey();
            int requiredAmount = merchantEntry.getValue();
            if(requiredAmount <= 0) continue;
            for(Map.Entry<String, Integer> bankEntry: bankBalanceMap.entrySet()) {
                int bankBalance = bankEntry.getValue();
                if(bankBalance <= 0) continue;
                if(bankBalance >= requiredAmount) {
                    transferList.add(new Transfer(bankEntry.getKey(), merchantName, requiredAmount));
                    bankEntry.setValue(bankBalance - requiredAmount);
                    break;
                } else {
                    transferList.add(new Transfer(bankEntry.getKey(), merchantName, bankBalance));
                    requiredAmount -= bankBalance;
                    bankEntry.setValue(0);
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