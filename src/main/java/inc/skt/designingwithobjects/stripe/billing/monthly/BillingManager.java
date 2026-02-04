package inc.skt.designingwithobjects.stripe.billing.monthly;

import java.util.List;

class BillingManager {
    int upfrontPayment;
    List<BillingEvent> billingEventList;
    Calculator calculator;
    public BillingManager(int upfrontPayment, List<BillingEvent> billingEventList, Calculator calculator) {
        this.upfrontPayment = upfrontPayment;
        this.billingEventList = billingEventList;
        this.calculator = new Calculator();
    }

    public int generateFinalBill() {
        int calculatedBill = 0;
        for(BillingEvent billingEvent : billingEventList) {
            calculatedBill += calculator.calculate_monthly_charge(billingEvent.planPrice, billingEvent.daysActive, billingEvent.totalDaysInMonth);
        }
        return calculatedBill - upfrontPayment;
    }
}

