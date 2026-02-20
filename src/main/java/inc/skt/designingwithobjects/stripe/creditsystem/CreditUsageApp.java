package inc.skt.designingwithobjects.stripe.creditsystem;

import java.text.DateFormat;
import java.time.LocalDate;
import java.util.*;

public class CreditUsageApp {
    public static void main(String[] args) {
        List<Invoice> invoices = Arrays.asList(new Invoice("Inv_B", 60, LocalDate.of(2026,02,20)),
                new Invoice("Inv_A", 40, LocalDate.of(2026,2, 10)));
        Collections.sort(invoices);

        List<Credit> creditList = Arrays.asList(new Credit(50, LocalDate.of(2026, 3, 1)),
                new Credit(30, LocalDate.of(2026, 2, 15)));
        CreditUsage creditUsage = new CreditUsage();
        List<PaymentSummary> paymentSummaries = creditUsage.payInvoice(invoices, creditList);
        System.out.println(paymentSummaries);
    }
}

class CreditUsage {
    public List<PaymentSummary> payInvoice(List<Invoice> invoiceList, List<Credit> creditList) {
        List<Credit> updatedCreditList = creditList;
        Iterator<Credit> itr = updatedCreditList.iterator();
        Credit credit;
        List<PaymentSummary> paymentSummaries = new ArrayList<>();
        if(itr.hasNext()) {
            credit = itr.next();
        } else {
            return paymentSummaries;
        }

        for(Invoice invoice : invoiceList) {
            int invoiceAmount = invoice.invoiceAmount;
            if(credit.expiryDate.isEqual(invoice.invoiceDate) ||  credit.expiryDate.isAfter(invoice.invoiceDate)) {
                if(credit.creditAmount == 0) {
                    if(itr.hasNext()) {
                        credit = itr.next();
                    } else {
                        break;
                    }
                }
                while(invoiceAmount > 0) {
                    if(credit.creditAmount == 0) break;
                    if(credit.creditAmount >= invoiceAmount) {
                        PaymentSummary summary = new PaymentSummary(invoice.invoiceId, invoiceAmount, 0);
                        credit.creditAmount -= invoiceAmount;
                        paymentSummaries.add(summary);
                        invoiceAmount = 0;
                    } else if(credit.creditAmount < invoiceAmount) {
                        PaymentSummary summary = new PaymentSummary(invoice.invoiceId,
                                credit.creditAmount, invoiceAmount - credit.creditAmount);
                        paymentSummaries.add(summary);
                        invoiceAmount -= credit.creditAmount;
                        credit.creditAmount = 0;
                    }
                }

            }
        }
        return paymentSummaries;
    }
}

class PaymentSummary {
    String invoiceId;
    int amountPaid;
    int balance;

    public PaymentSummary(String invoiceId, int amountPaid, int balance) {
        this.invoiceId = invoiceId;
        this.amountPaid = amountPaid;
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "PaymentSummary{" +
                "invoiceId='" + invoiceId + '\'' +
                ", amountPaid=" + amountPaid +
                ", balance=" + balance +
                '}';
    }
}
class Credit {
    int creditAmount;
    LocalDate expiryDate;

    public Credit() {
    }

    public Credit(int creditAmount, LocalDate expiryDate) {
        this.creditAmount = creditAmount;
        this.expiryDate = expiryDate;
    }
}

class Invoice implements Comparable {
    String invoiceId;
    int invoiceAmount;
    LocalDate invoiceDate;

    public Invoice(String invoiceId, int invoiceAmount, LocalDate invoiceDate) {
        this.invoiceId = invoiceId;
        this.invoiceAmount = invoiceAmount;
        this.invoiceDate = invoiceDate;
    }

    @Override
    public int compareTo(Object o) {
        if(this.invoiceDate.isBefore(((Invoice) o).invoiceDate)) {
            return -1;
        } else if(this.invoiceDate.isAfter(((Invoice) o).invoiceDate)) {
            return  1;
        } else {
            return 0;
        }
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "invoiceId='" + invoiceId + '\'' +
                ", invoiceAmount=" + invoiceAmount +
                ", invoiceDate=" + invoiceDate +
                '}';
    }
}
