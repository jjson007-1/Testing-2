package serialization;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import serialization.Date;

/**
 * The Invoice class represents payment information for rental orders.
 * Corresponds to the "invoice" table in the database.
 */
public class Invoice implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int invoiceId;
    private int rentalId;
    private int customerId;
    private double amountDue;
    private Date paymentDate;
    private String paymentMethod;
    
    // Default constructor
    public Invoice() {
        this.invoiceId = 0;
        this.rentalId = 0;
        this.customerId = 0;
        this.amountDue = 0.0;
        this.paymentDate = new Date();
        this.paymentMethod = "";
    }
    
    // Full constructor
    public Invoice(int invoiceId, int rentalId, int customerId, double amountDue, 
                 Date paymentDate, String paymentMethod) {
        this.invoiceId = invoiceId;
        this.rentalId = rentalId;
        this.customerId = customerId;
        this.amountDue = amountDue;
        this.paymentDate = paymentDate;
        this.paymentMethod = paymentMethod;
    }
    
    // Constructor without invoiceId (for new invoices)
    public Invoice(int rentalId, int customerId, double amountDue, 
                 Date paymentDate, String paymentMethod) {
        this.rentalId = rentalId;
        this.customerId = customerId;
        this.amountDue = amountDue;
        this.paymentDate = paymentDate;
        this.paymentMethod = paymentMethod;
    }
    
    // Getters and Setters
    public int getInvoiceId() {
        return invoiceId;
    }
    
    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }
    
    public int getRentalId() {
        return rentalId;
    }
    
    public void setRentalId(int rentalId) {
        this.rentalId = rentalId;
    }
    
    public int getCustomerId() {
        return customerId;
    }
    
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
    
    public double getAmountDue() {
        return amountDue;
    }
    
    public void setAmountDue(double amountDue) {
        this.amountDue = amountDue;
    }
    
    public Date getPaymentDate() {
        return paymentDate;
    }
    
    public void setPaymentDate(serialization.Date date) {
        this.paymentDate = date;
    }
    
    public String getPaymentMethod() {
        return paymentMethod;
    }
    
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
    
    // Check if invoice is paid
    public boolean isPaid() {
        return paymentDate != null && paymentMethod != null && !paymentMethod.isEmpty();
    }
    
    // Mark invoice as paid with current date
    public void markAsPaid(String paymentMethod) {
        this.paymentDate = new Date();
        this.paymentMethod = paymentMethod;
    }
    
    // Serialize invoices to file
    public static void saveInvoices(ArrayList<Invoice> invoiceList) {
        FileOutputStream fileOut = null;
        ObjectOutputStream objOut = null;
        
        try {
            fileOut = new FileOutputStream("./src/InvoicesDB.ser");
            objOut = new ObjectOutputStream(fileOut);
            
            for(Invoice invoice : invoiceList) {
                objOut.writeObject(invoice);
            }
            objOut.flush();
            objOut.close();
            System.out.printf("Serialized invoice data is saved \n");
        } catch (IOException i) {
            i.printStackTrace();
        }
    }
    
    // Deserialize invoices from file
    public static ArrayList<Invoice> readFromInvoicesFile() {
        ArrayList<Invoice> invoiceList = new ArrayList<>();
        FileInputStream fileIn = null;
        ObjectInputStream objIn = null;
        Invoice invoice;
        
        try {
            fileIn = new FileInputStream("./src/InvoicesDB.ser");
            objIn = new ObjectInputStream(fileIn);
            while(true) {
                try {
                    invoice = (Invoice) objIn.readObject();
                    if(invoice != null) {
                        invoiceList.add(invoice);
                    } else if(invoice == null) {
                        break;
                    }
                } catch (ClassNotFoundException e) {
                    System.out.println("Object could not be converted to Invoice");
                } catch (EOFException e) {
                    break;
                }
            }
        } catch (IOException i) {
            i.printStackTrace();
        }
        return invoiceList;
    }
    
    // Find invoices by customer ID
    public static ArrayList<Invoice> findByCustomerId(int customerId) {
        ArrayList<Invoice> allInvoices = readFromInvoicesFile();
        ArrayList<Invoice> customerInvoices = new ArrayList<>();
        
        for (Invoice invoice : allInvoices) {
            if (invoice.getCustomerId() == customerId) {
                customerInvoices.add(invoice);
            }
        }
        
        return customerInvoices;
    }
    
    // Find invoice by rental ID
    public static Invoice findByRentalId(int rentalId) {
        ArrayList<Invoice> allInvoices = readFromInvoicesFile();
        
        for (Invoice invoice : allInvoices) {
            if (invoice.getRentalId() == rentalId) {
                return invoice;
            }
        }
        
        return null;
    }
    
    // Calculate total revenue from all invoices
    public static double calculateTotalRevenue() {
        ArrayList<Invoice> allInvoices = readFromInvoicesFile();
        double totalRevenue = 0.0;
        
        for (Invoice invoice : allInvoices) {
            totalRevenue += invoice.getAmountDue();
        }
        
        return totalRevenue;
    }
    
    @Override
    public String toString() {
        return "Invoice [invoiceId=" + invoiceId + ", rentalId=" + rentalId + 
               ", customerId=" + customerId + ", amountDue=" + amountDue + 
               ", paymentDate=" + paymentDate + ", paymentMethod=" + paymentMethod + "]";
    }
    
    // Generate formatted receipt
    public String generateReceipt() {
        StringBuilder receipt = new StringBuilder();
        receipt.append("=============== INVOICE RECEIPT ===============\n");
        receipt.append("Invoice ID: ").append(invoiceId).append("\n");
        receipt.append("Customer ID: ").append(customerId).append("\n");
        receipt.append("Rental ID: ").append(rentalId).append("\n");
        receipt.append("Amount Due: $").append(String.format("%.2f", amountDue)).append("\n");
        receipt.append("Payment Date: ").append(paymentDate).append("\n");
        receipt.append("Payment Method: ").append(paymentMethod).append("\n");
        receipt.append("Status: ").append(isPaid() ? "PAID" : "UNPAID").append("\n");
        receipt.append("==============================================\n");
        return receipt.toString();
    }
}