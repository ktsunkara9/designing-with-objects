package inc.skt.designingwithobjects.stripe.gateways;


import java.util.*;

/**
Input:
A list of Transactions (integers representing amounts).
A map/dictionary of Gateways where the key is the Gateway ID (string) and the value is the Remaining Capacity (integer).

Output:
A list of assignments indicating which gateway processed which transaction (e.g., "Transaction of 50 assigned to Gateway A").
If a transaction fails, indicate it (e.g., "Transaction of 500 failed").

Example:
Input:
transactions: [100, 50, 20, 150]
gateways: {"Gateway_A": 120, "Gateway_B": 200}

 */
public class GatewayMapperApp {
    public static void main(String[] args) {

        int maxRequests = 2;
        int[] timestamps = { 1, 2, 4, 8, 11, 25 };
        int[] windows = { 10, 20, 30, 40, 50, 60};
        System.out.println(extracted(maxRequests, timestamps, windows));

        List<Integer> transactionList = new ArrayList<>();
        transactionList.add(100);
        transactionList.add(50);
        transactionList.add(20);
        transactionList.add(150);
        System.out.println(transactionList);
        Map<String, Integer> gatewayMap = new TreeMap<>();
        gatewayMap.put("Gateway_A", 120);
        gatewayMap.put("Gateway_B", 200);
        System.out.println(gatewayMap);
        GatewayMapper mapper = new GatewayMapper();
        List<Assignment> assignmentList = mapper.map(transactionList, gatewayMap);
        for(Assignment assignment : assignmentList) {
            if(assignment.gatewayId != null) {
                System.out.println(assignment.transactionAmount + " -> " + assignment.gatewayId);
            } else {
                System.out.println("Transaction of " + assignment.transactionAmount + " failed");
            }
        }
    }

    private static List<String> extracted(int maxRequests, int[] timestamps, int[] windows) {
        Map<Integer, Integer> windowMap = new TreeMap<>();
        windowMap.put(10, maxRequests);
        windowMap.put(20, maxRequests);
        windowMap.put(30, maxRequests);
        windowMap.put(40, maxRequests);
        windowMap.put(50, maxRequests);
        windowMap.put(60, maxRequests);
        List<String> tranascationStatusList = new ArrayList<>();
        for(Integer timestamp : timestamps) {
            for(Integer window : windows) {
                int remainingTransactions = windowMap.get(window);
                if(timestamp < window &&  remainingTransactions > 0) {
                    windowMap.put(window, remainingTransactions - 1);
                    tranascationStatusList.add("Allowed");
                    break;
                } else if(timestamp < window &&  remainingTransactions == 0) {
                    tranascationStatusList.add("Dropped");
                    break;
                } else {
                    continue;
                }
            }
        }
        return tranascationStatusList;
    }
}


/**
 * Logic (One possible path):
 * Txn 100 -> Fits in Gateway_A? Yes (120 - 100 = 20 left). Assign to A.
 * Txn 50 -> Fits in Gateway_A? No (Only 20 left). Fits in Gateway_B? Yes (200 - 50 = 150 left). Assign to B.
 * Txn 20 -> Fits in Gateway_A? Yes (20 left - 20 = 0). Assign to A.
 * Txn 150 -> Fits in Gateway_A? No (0 left). Fits in Gateway_B? Yes (150 - 150 = 0). Assign to B.
 * Output:
 * "100 -> Gateway_A"
 * "50 -> Gateway_B"
 * "20 -> Gateway_A"
 * "150 -> Gateway_B"
 */
class GatewayMapper {

    public List<Assignment> map(List<Integer> transactions, Map<String, Integer> assignmentMap) {
        Map<String, Integer> assignmentBalanceMap = new TreeMap<>(assignmentMap);
        List<Assignment> assignmentList = new ArrayList<>();;
        for(Integer transactionAmount : transactions) {
            String transactionStatus = "Failed";
            for(Map.Entry<String, Integer> entrySet : assignmentMap.entrySet()) {
                Integer balance = assignmentBalanceMap.get(entrySet.getKey());
                if(balance >= transactionAmount) {
                    assignmentList.add(new Assignment(entrySet.getKey(), transactionAmount));
                    transactionStatus = "Succeeded";
                    assignmentBalanceMap.put(entrySet.getKey(), balance - transactionAmount);
                    break;
                }
            }
            if(transactionStatus.equals("Failed")) {
                assignmentList.add(new Assignment(transactionAmount, transactionStatus));
            }
        }
        return assignmentList;
    }
}

class Assignment {
    String gatewayId;
    Integer transactionAmount;
    String transactionStatus;

    public Assignment(String gatewayId, Integer transactionAmount) {
        this.gatewayId = gatewayId;
        this.transactionAmount = transactionAmount;
    }

    public Assignment(Integer transactionAmount, String transactionStatus) {
        this.transactionAmount = transactionAmount;
        this.transactionStatus = transactionStatus;
    }
}

