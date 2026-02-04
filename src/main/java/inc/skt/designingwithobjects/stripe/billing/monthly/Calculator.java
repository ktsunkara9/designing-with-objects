package inc.skt.designingwithobjects.stripe.billing.monthly;

/*
** Write a function calculate_monthly_charge(plan_price, days_active, total_days_in_month) that returns the cost for a user's usage.
* Inputs:
* plan_price: The total cost of the plan (e.g., 5000 for $50.00). Remember: Use integers (cents) to avoid floating-point errors.
* days_active: How many days the user was on the plan.
* total_days_in_month: Usually 28, 30, or 31.
* The Goal: Return the exact amount to charge for that period.
*/
class Calculator {
    public int calculate_monthly_charge(int plan_price, int days_active, int total_days_in_month) {
        if(days_active == total_days_in_month) {
            return plan_price;
        }
        long totalCharge = (long) plan_price * days_active / total_days_in_month;
        return (int) totalCharge;
    }
}
