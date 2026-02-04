package inc.skt.designingwithobjects.stripe.billing.monthly;

class BillingEvent {
    int planPrice;
    int daysActive;
    int totalDaysInMonth;

    public BillingEvent(int planPrice, int daysActive, int totalDaysInMonth) {
        this.planPrice = planPrice;
        this.daysActive = daysActive;
        this.totalDaysInMonth = totalDaysInMonth;
    }
}

