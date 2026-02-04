package inc.skt.designingwithobjects.stripe.billing.perrequest;

class BillingEvent {
    int dateOfUpload;
    long amountOfDataUploaded;

    public BillingEvent(int dateOfUpload, long amountOfDataUploaded) {
        this.dateOfUpload = dateOfUpload;
        this.amountOfDataUploaded = amountOfDataUploaded;
    }
}
