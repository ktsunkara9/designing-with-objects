package inc.skt.designingwithobjects.stripe.billing.payout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PayoutApp {

    public static void main(String[] args) {
        /*
        * * You are given a list of Transactions and a PayoutThreshold (e.g., 5000 cents).
        */
        int threshold = 5000;
        List<Transaction> transactionList = new ArrayList<>();
        transactionList.add(new Transaction("A", 6000, Status.SETTLED));
        transactionList.add(new Transaction("B", 3000, Status.SETTLED));
        transactionList.add(new Transaction("B", -4000, Status.SETTLED));
        transactionList.add(new Transaction("C", 7000, Status.SETTLED));

        PayoutMapper mapper = new PayoutMapper();
        PayoutResult result = mapper.getPayout(threshold, transactionList);
        System.out.println(result);
    }
}

class PayoutMapper {
    public PayoutResult getPayout(int treshold, List<Transaction> transactionList) {
        Map<String, Integer> payoutMap = new HashMap<>();
        Map<String, Integer> aggregationMap = new HashMap<>();
        for(Transaction transaction: transactionList) {
            if(transaction.status == Status.SETTLED) {
                aggregationMap.put(transaction.accountId, aggregationMap.getOrDefault(transaction.accountId, 0) + transaction.amount);
            }
        }
        for(Map.Entry<String, Integer> entry : aggregationMap.entrySet()) {
            if(entry.getValue() >= treshold) {
                payoutMap.put(entry.getKey(), entry.getValue());
            }
        }
        Map<String, Integer> accountBalanceMap = new HashMap<>();
        for(Transaction transaction: transactionList) {
            if(transaction.status == Status.SETTLED) {
                accountBalanceMap.put(
                        transaction.accountId,
                        accountBalanceMap.getOrDefault(transaction.accountId,0) + transaction.amount
                );
            }
        }
        Map<String, Integer> deficitAccountMap = new HashMap<>();
        Map<String, Integer> surplusAccountMap = new HashMap<>();

        for(Map.Entry<String, Integer> entry:accountBalanceMap.entrySet()) {
            if(entry.getValue() < 0) {
                deficitAccountMap.put(entry.getKey(), entry.getValue());
            }
        }

        for(Map.Entry<String, Integer> entry:accountBalanceMap.entrySet()) {
            if(entry.getValue() > 5000) {
                surplusAccountMap.put(entry.getKey(), entry.getValue());
            }
        }

        List<Transfer> transferList = new ArrayList<>();
        for(String deficitId: deficitAccountMap.keySet()) {
            int deficitAmount = Math.abs(deficitAccountMap.get(deficitId));
            for(String surplusId: surplusAccountMap.keySet()) {
                int surplusAmount = surplusAccountMap.get(surplusId);
                int transferableAmount = surplusAmount - 5000;

                if(transferableAmount > 0) {
                    int amountToMove = Math.min(deficitAmount, transferableAmount);
                    transferList.add(new Transfer(surplusId, deficitId, amountToMove));
                    surplusAccountMap.put(surplusId, surplusAmount - amountToMove);
                    deficitAmount -= amountToMove;
                }
            }
        }


        PayoutResult result = new PayoutResult(payoutMap, transferList);
        return result;
    }
}

class PayoutResult {
    Map<String, Integer> payouts;
    List<Transfer> transferList;
    public PayoutResult(Map<String, Integer> payouts, List<Transfer> transferList) {
        this.payouts = payouts;
        this.transferList = transferList;
    }

    @Override
    public String toString() {
        return "PayoutResult{" +
                "payouts=" + payouts +
                ", transferList=" + transferList +
                '}';
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

    @Override
    public String toString() {
        return "Transfer{" +
                "from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", amount=" + amount +
                '}';
    }
}

/*
* Each transaction has an account_id, an amount (in cents), and a status ("settled" or "pending").
* */
class Transaction {
    String accountId;
    int amount;
    Status status;

    public Transaction() {
    }

    public Transaction(String accountId, int amount, Status status) {
        this.accountId = accountId;
        this.amount = amount;
        this.status = status;
    }
}

enum Status {
    SETTLED,
    PENDING
}