package inc.skt.designingwithobjects.stripe.billing.monthly;

import java.util.ArrayList;
import java.util.List;

/*
* Context: Stripe is a subscription-heavy platform. When a user changes their plan in the middle of a month,
* we need to calculate how much they should be credited or charged.
* */
public class SubscriptionApp {
    public static void main(String[] args) {
        BillingEvent billingEvent1 = new BillingEvent(15000, 15, 31);
        BillingEvent billingEvent2 = new BillingEvent(10000, 10, 31);
        BillingEvent cancelledPeriod = new BillingEvent(0, 6, 31);
        List<BillingEvent> billingEventList = new ArrayList<>();
        billingEventList.add(billingEvent1);
        billingEventList.add(billingEvent2);
        billingEventList.add(cancelledPeriod);
        Calculator calculator = new Calculator();
        BillingManager billingManager = new BillingManager(5000, billingEventList, calculator);
        int finalBill = billingManager.generateFinalBill();
        System.out.println(finalBill);
    }
}