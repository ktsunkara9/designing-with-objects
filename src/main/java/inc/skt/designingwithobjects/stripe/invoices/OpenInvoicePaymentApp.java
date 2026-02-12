package inc.skt.designingwithobjects.stripe.invoices;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/*
* Background Stripe Billing allows merchants to send invoices to their customers. Sometimes, a customer will make a single large "lump sum" payment via wire transfer to pay off multiple outstanding invoices at once.

The Goal Write a function that takes a payment_amount and a list of open_invoices.
The function should automatically allocate the payment amount to the invoices.

Business Rules
Priority: The money should be applied to invoices based on their due_date, starting with the oldest (earliest) invoice first.
Partial Payments: If the remaining payment amount is not enough to fully pay an invoice,
apply whatever is left to that invoice (marking it as partially paid).

Overage: If the payment amount exceeds the total of all invoices, the remaining balance should be identified as "unallocated credit."
Sample Input
Payment Amount: $350
Open Invoices:
Invoice A: Amount $100, Due 2024-01-05
Invoice B: Amount $50, Due 2024-01-01
Invoice C: Amount $300, Due 2024-01-10

Sample Output Your function should return a structure indicating how much was applied to each invoice and the final status.

Invoice B (Oldest): Paid $50 (Status: PAID)
Invoice A (Next Oldest): Paid $100 (Status: PAID)
Invoice C (Newest): Paid $200 (Status: PARTIAL, Remaining Owed: $100)

Unallocated Credit: $0
* */
public class OpenInvoicePaymentApp {
    public static void main(String[] args) {

    }
}

/*
*
* */
class PaymentManager {
    public Result allocatePayment(int paymentAmount, List<Invoice> openInvoices) {
       Collections.sort(openInvoices);
       Map<String, AllocationDetails> invoiceStatusMap = new HashMap<>();
       int remainingAmount = paymentAmount;
       for(Invoice invoice: openInvoices) {
           if(invoice.invoiceAmount <= remainingAmount) {
               AllocationDetails allocationDetails =  new AllocationDetails(invoice.invoiceAmount, "Paid");
               invoiceStatusMap.put(invoice.invoiceId, allocationDetails);
               remainingAmount -= invoice.invoiceAmount;
           } else {
               if(remainingAmount == 0) break;
               System.out.println("remaining amount is not sufficient");
               AllocationDetails allocationDetails =  new AllocationDetails(remainingAmount, "Partially Paid");
               remainingAmount = 0;
               invoiceStatusMap.put(invoice.invoiceId, allocationDetails);
               break;
           }
       }
       int unallocatedCredit = 0;
       if(remainingAmount > 0) {
           unallocatedCredit = remainingAmount;
       }
       Result result =  new Result(invoiceStatusMap, unallocatedCredit);
       return result;
    }
}

class AllocationDetails {
    int amountPaid;
    String status;

    public AllocationDetails(int amountPaid, String status) {
        this.amountPaid = amountPaid;
        this.status = status;
    }
}

class Result {
    Map<String, AllocationDetails> invoiceStatusMap;
    int unallocatedCredit;

    public Result(Map<String, AllocationDetails> invoiceStatusMap, int unallocatedCredit) {
        this.invoiceStatusMap = invoiceStatusMap;
        this.unallocatedCredit = unallocatedCredit;
    }
}

class Invoice implements Comparable<Invoice> {
    String invoiceId;
    int invoiceAmount;
    Date dueDate;

    public Invoice(String invoiceId, int invoiceAmount, Date dueDate) {
        this.invoiceId = invoiceId;
        this.invoiceAmount = invoiceAmount;
        this.dueDate = dueDate;
    }

    @Override
    public int compareTo(Invoice o) {
        return this.dueDate.compareTo(o.dueDate);
    }
}