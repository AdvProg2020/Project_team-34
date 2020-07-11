package log;

import account.Customer;
import cart.Cart;

import java.util.Date;

/**
 * @author Aryan Ahadinia
 * @since 0.0.1
 */

public class CustomerLog {
    private String identifier;
    private Date date;
    private int paidAmount;
    private int codedDiscountAmount;
    private Customer customer;
    private Cart cart;
    private LogStatus deliveryStatus;

    public String getIdentifier() {
        return identifier;
    }

    public Date getDate() {
        return date;
    }

    public int getPaidAmount() {
        return paidAmount;
    }

    public int getCodedDiscountAmount() {
        return codedDiscountAmount;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Cart getCart() {
        return cart;
    }

    public LogStatus getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(LogStatus deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }
}
