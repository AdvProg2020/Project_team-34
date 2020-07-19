package auction;

import account.ChatRoom;
import account.Customer;
import account.Supplier;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import product.Product;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class Auction {
    private static final ArrayList<Auction> ALL_AUCTIONS = new ArrayList<>();
    private static int allAuctionsCount = 0;
    private final String identifier;
    private final String chatRoomIdentifier;
    private final Product product;
    private final Supplier supplier;
    private final Customer highestPromoter;
    private final Integer highestPromotion;
    private final Date end;

    public Auction(Product product, Supplier supplier, long endLong) {
        this.identifier = Auction.generateIdentifier();
        this.chatRoomIdentifier = new ChatRoom().getChatRoomId();
        this.product = product;
        this.supplier = supplier;
        this.highestPromoter = null;
        this.highestPromotion = null;
        this.end = new Date(endLong);
        ALL_AUCTIONS.add(this);
        allAuctionsCount++;
    }

    public Auction(String identifier, String chatRoomIdentifier, Product product, Supplier supplier,
                   Customer highestPromoter, Integer highestPromotion, Date end) {
        this.identifier = identifier;
        this.chatRoomIdentifier = chatRoomIdentifier;
        this.product = product;
        this.supplier = supplier;
        this.highestPromoter = highestPromoter;
        this.highestPromotion = highestPromotion;
        this.end = end;
        ALL_AUCTIONS.add(this);
        allAuctionsCount++;
    }

    public Auction(String json) {
        this.identifier = identifier;
        this.chatRoomIdentifier = chatRoomIdentifier;
        this.product = product;
        this.supplier = supplier;
        this.highestPromoter = highestPromoter;
        this.highestPromotion = highestPromotion;
        this.end = end;
    }

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

    private static String generateIdentifier() {
        return "T34AC" + String.format("%015d", allAuctionsCount + 1);
    }

    public static Auction getAuctionByIdentifier(String identifier) {
        for (Auction auction : ALL_AUCTIONS) {
            if (auction.getIdentifier().equals(identifier)) {
                return auction;
            }
        }
        return null;
    }

    public void promote(Customer customer, int promotionAmount) {

    }

    public void end() {

    }

    public String toJson() {
        JsonObject jsonObject = new JsonObject();
        JsonParser jsonParser = new JsonParser();

        return jsonObject.toString();
    }

    public Auction convertJsonStringToAuction(String json) {
        return new Auction(json);
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
