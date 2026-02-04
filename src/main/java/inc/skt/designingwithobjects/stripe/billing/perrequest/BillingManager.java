package inc.skt.designingwithobjects.stripe.billing.perrequest;

import java.util.List;

class BillingManager {
    private static final int PRICE_CHANGE_DAY = 14;
    private static final int EARLY_MONTH_RATE_CENTS = 50;
    private static final int LATE_MONTH_RATE_CENTS = 30;
    private static final int MB_PER_GB = 1000;
    private static final int MINIMUM_MONTHLY_CHARGE = 500;

    public int generateBilling(List<BillingEvent> billingEventList) {
        long charge = 0;

        for (BillingEvent billingEvent : billingEventList) {
            charge += getCharge(billingEvent);
        }
        return Math.max(MINIMUM_MONTHLY_CHARGE, (int) charge);
    }

    private static long getCharge(BillingEvent billingEvent) {
        int rate = (billingEvent.dateOfUpload <= PRICE_CHANGE_DAY) ? EARLY_MONTH_RATE_CENTS : LATE_MONTH_RATE_CENTS;

        // calculate for whole GB's
        long wholeGBs = billingEvent.amountOfDataUploaded / MB_PER_GB;
        long costForWholeGBs = wholeGBs * rate;

        // calculate for remaining MB's
        long remainingMBs = billingEvent.amountOfDataUploaded % MB_PER_GB;
        long costForRemainingMBs = (remainingMBs * rate) / MB_PER_GB;

        return costForWholeGBs + costForRemainingMBs;
    }
}
