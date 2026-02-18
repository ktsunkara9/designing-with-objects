package inc.skt.designingwithobjects.stripe.upgrade;

public class CalculatorApp {

    public static void main(String[] args) {
        int currentPlanPrice = 10;
        int newPlanPrice = 30;
        int daysInMonth = 30;
        int dayOfUpgrade = 16;
        String userId = "12345";

        Calculator calculator = new Calculator();
        calculator.calculateBill(userId, currentPlanPrice, newPlanPrice, daysInMonth, dayOfUpgrade);
    }
}


/**
 * Basic Plan: $10 per month.
 *
 * Premium Plan: $30 per month.
 *
 * Billing Cycle: 30 days.
 *
 * Upgrade Day: Day 16 (The user has already paid for 30 days of Basic, but will use Premium for the final 15 days of the month).
 */
class Calculator {

    public Bill calculateBill(String userId, int oldPlanPrice, int newPlanPrice, int daysInMonth, int dayOfUpgrade) {

        double oldPlanRate = (double)(oldPlanPrice * 100)/daysInMonth;
        double chargeWithOldPlan = (daysInMonth - dayOfUpgrade + 1) * oldPlanRate;
        double newPlanRate = (double) (newPlanPrice * 100)/daysInMonth;
        double chargeWithNewPlan = (daysInMonth - dayOfUpgrade + 1) * newPlanRate;

        int totalBillInCents = Math.abs((int) ((chargeWithNewPlan - chargeWithOldPlan)));
        Bill bill = null;
        if(oldPlanPrice < newPlanPrice) {
            // User should pay extra
            int balanceToBePaidInCents = (int) Math.round((totalBillInCents - (oldPlanPrice*100)));
            bill = new Bill(userId, 0, totalBillInCents, oldPlanPrice, balanceToBePaidInCents);
        } else if(oldPlanPrice > newPlanPrice) {
            // User should receive credit
            int creditToUser = (int) Math.round(((oldPlanPrice *100) - totalBillInCents));
            bill = new Bill(userId, creditToUser, totalBillInCents, oldPlanPrice, 0);
        } else {
            bill = new Bill(userId, 0, oldPlanPrice, oldPlanPrice, 0);
        }
        return bill;
    }

}

class Bill {
    String userId;
    int credit;
    int totalBillingAmount;
    int billPaid;
    int balanceToBePaid;

    public Bill(String userId, int credit, int totalBillingAmount, int billPaid, int balanceToBePaid) {
        this.userId = userId;
        this.credit = credit;
        this.totalBillingAmount = totalBillingAmount;
        this.billPaid = billPaid;
        this.balanceToBePaid = balanceToBePaid;
    }
}