package auction;

import account.Customer;
import account.Supplier;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import exceptionalMassage.ExceptionalMassage;
import log.CustomerLog;
import product.Product;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class Auction {
    private String identifier;
    private String chatRoomIdentifier;
    private Product product;
    private Supplier supplier;
    private Customer highestPromoter;
    private Integer highestPromotion;
    private Date end;
    private int wage;

    public String getIdentifier() {
        return identifier;
    }

    public String getChatRoomIdentifier() {
        return chatRoomIdentifier;
    }

    public Product getProduct() {
        return product;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public Customer getHighestPromoter() {
        return highestPromoter;
    }

    public Integer getHighestPromotion() {
        return highestPromotion;
    }

    public Date getEnd() {
        return end;
    }

    public int getWage() {
        return wage;
    }

    public Auction convertJsonStringToAuction(String json) {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Auction auction = (Auction) o;
        return Objects.equals(identifier, auction.identifier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier);
    }
}
