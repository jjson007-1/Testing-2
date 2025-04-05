package model;

import java.io.Serializable;

public class Invoice implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -192380170824695030L;
	
	private int invoiceId;
    private int rentalId;
    private int customerId;
    private double amountDue;
    private Date paymentDate;
    private String paymentMethod;
    
    
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
	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}
	public String getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	
	
	public boolean isPaid() {
        return paymentDate != null && paymentMethod != null && !paymentMethod.isEmpty();
    }
	
	
	public void markAsPaid(String paymentMethod) {
		this.paymentDate = new Date();
		this.paymentMethod = paymentMethod;
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
