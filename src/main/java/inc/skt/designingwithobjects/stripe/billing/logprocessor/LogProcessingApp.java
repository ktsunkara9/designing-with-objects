package inc.skt.designingwithobjects.stripe.billing.logprocessor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LogProcessingApp {

    public static void main(String[] args) {
        // <=100 requests charge is 0

        // 101 requests (Expected: 1)

        // 1001 requests (Expected: 900.5, which rounds to 901 or truncates to 900 depending on your choice)
    }
}

class BillingProcessor {
    Map<String, Integer> customerRequestMap = new HashMap<>();
    Map<String, Integer> billingMap = new HashMap<>();
    public Map<String, Integer> prepareCustomerMap(List<String> logs) {
        for(String log : logs) {
            // extract customerId from log
            LogProcessor logProcessor = new LogProcessor();
            String customerId = logProcessor.extractCustomerId(log);
            // store the customerId in a map with number of transactions as value
            customerRequestMap.put(customerId, customerRequestMap.getOrDefault(customerId, 0) + 1);
        }
        return customerRequestMap;
    }

    // calculate bill based on usage using the map earlier to see number of transactions
    // return bill for each customer in a map
    public Map<String, Integer> prepareBillingMap() {
        for(Map.Entry<String, Integer> entrySet : customerRequestMap.entrySet()) {
            double charge = 0;
            int numberOfRequests = entrySet.getValue();
            if(numberOfRequests == 0) {
                charge = 0;
            } else if(numberOfRequests > 100) {
                // check if it's greater than 1000
                if(numberOfRequests > 1000) {
                    // calculate for above 100 to 1000
                    charge = ((0.01*100) * 900);
                    // calculate for above 1000
                    int aboveThousandRequests = numberOfRequests - 1000;
                    // calculate for remaining above 1000
                    charge = charge + (aboveThousandRequests * (0.005*100));
                } else {
                    charge = ((0.01*100) * (numberOfRequests - 100));
                }
            } else {
                charge = 0;
            }
            billingMap.put(entrySet.getKey(), (int)Math.round(charge));
        }
        return billingMap;
    }

    public Map<String, Integer> getBillingMap(List<String> logs) {
            prepareCustomerMap(logs);
            return prepareBillingMap();
    }
}
class LogProcessor {
    public String extractCustomerId(String log) {
        String[] entries = log.split(",");
        return entries[1];
    }
}