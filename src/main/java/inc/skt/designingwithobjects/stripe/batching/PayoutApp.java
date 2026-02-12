package inc.skt.designingwithobjects.stripe.batching;


import java.util.ArrayList;
import java.util.List;

/*
* Practice Problem: Payout Batching
Scenario: Stripe processes thousands of payouts to merchants every day. We send these payouts to a partner bank via an API. However, the bank has a strict limit on the total amount of money that can be sent in a single API request (a "batch").

The Task: Write a function that takes a list of pending payout amounts and a maximum limit per batch. Your function should group these payouts into batches in the order they are received, ensuring that the sum of amounts in any single batch does not exceed the limit.

Input:

payouts: An array of integers representing the amount of each payout (e.g., [20, 50, 10, 100]).

limit: An integer representing the maximum total amount allowed per batch.

Example:

Plaintext
Input:
payouts: [40, 10, 25, 60, 90]
limit: 100

Output:
[
  [40, 10, 25],  // Sum: 75. (Adding 60 would exceed 100, so we close this batch)
  [60],          // Sum: 60. (Adding 90 would exceed 100)
  [90]           // Sum: 90.
]
* */
public class PayoutApp {
    public static void main(String[] args) {
        PayoutBatcher payoutBatcher = new PayoutBatcher();
        List<Integer> payoutList = new ArrayList<>();
        payoutList.add(40);
        payoutList.add(10);
        payoutList.add(25);
        payoutList.add(60);
        payoutList.add(90);
        int limit = 100;
        List<List<Integer>> payoutLists= payoutBatcher.createBatch(payoutList, limit);
        System.out.println(payoutLists);

        /*
        * Input: [20, 30, 60, 10, 80]
        * Limit: 60
        * */

        List<Integer> payoutList2 = new ArrayList<>();
        payoutList2.add(20);
        payoutList2.add(30);
        payoutList2.add(65);
        payoutList2.add(10);
        payoutList2.add(80);
        int limit2 = 60;
        List<List<Integer>> payoutLists2= payoutBatcher.createBatch(payoutList2, limit2);
        System.out.println(payoutLists2);
    }
}

class PayoutBatcher {

    public List<List<Integer>> createBatch(List<Integer> payouts, Integer limit) {
        int sum = 0;
        List<List<Integer>> payoutList = new ArrayList<>();
        List<Integer> list = new ArrayList<>();
        for(int i = 0; i < payouts.size(); i++) {
            Integer payout = payouts.get(i);
            System.out.println(payout);
            if(payout > limit) {
                System.out.println("Payout "+ payout  +" can not be greater than limit " +limit );
                continue;
            } else if((sum + payout) > limit) {
                payoutList.add(list);
                sum = 0;
                list = new ArrayList<Integer>();
                list.add(payout);
                sum += payout;
            } else {
                list.add(payout);
                sum += payout;
            }
        }
        if(!list.isEmpty()) {
            payoutList.add(list);
        }
        return payoutList;
    }
}
