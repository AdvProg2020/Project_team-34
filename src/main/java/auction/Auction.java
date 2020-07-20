package auction;

import account.Customer;
import account.Supplier;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import communications.Utils;
import product.Product;

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

    private Auction(String json) {
        JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
        this.identifier = jsonObject.get("identifier").getAsString();
        this.chatRoomIdentifier = jsonObject.get("chatRoomIdentifier").getAsString();
        this.product = Product.convertJsonStringToProduct(jsonObject.get("product").toString());
        this.supplier = Supplier.convertJsonStringToSupplier(jsonObject.get("supplier").toString());
        if (jsonObject.get("highestPromoter") instanceof JsonNull) {
            this.highestPromoter = null;
        } else {
            this.highestPromoter = Customer.convertJsonStringToCustomer(jsonObject.get("highestPromoter").toString());
        }
        if (jsonObject.get("highestPromotion").getAsString().equals("null")) {
            this.highestPromotion = null;
        } else {
            this.highestPromotion = Integer.parseInt(jsonObject.get("highestPromotion").getAsString());
        }
        this.end = new Date(Long.parseLong(jsonObject.get("end").getAsString()));
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getChatRoomIdentifier() {
        return chatRoomIdentifier;
    }

    public void setChatRoomIdentifier(String chatRoomIdentifier) {
        this.chatRoomIdentifier = chatRoomIdentifier;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Customer getHighestPromoter() {
        return highestPromoter;
    }

    public void setHighestPromoter(Customer highestPromoter) {
        this.highestPromoter = highestPromoter;
    }

    public Integer getHighestPromotion() {
        return highestPromotion;
    }

    public void setHighestPromotion(Integer highestPromotion) {
        this.highestPromotion = highestPromotion;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public int getWage() {
        return wage;
    }

    public void setWage(int wage) {
        this.wage = wage;
    }

    public String toJson() {
        JsonObject jsonObject = new JsonObject();
        JsonParser jsonParser = new JsonParser();
        jsonObject.add("identifier", jsonParser.parse(Utils.convertObjectToJsonString(identifier)));
        jsonObject.add("chatRoomIdentifier", jsonParser.parse(Utils.convertObjectToJsonString(chatRoomIdentifier)));
        jsonObject.add("product", jsonParser.parse(Utils.convertObjectToJsonString(product)));
        jsonObject.add("supplier", jsonParser.parse(Utils.convertObjectToJsonString(supplier)));
        jsonObject.add("highestPromoter", jsonParser.parse(Utils.convertObjectToJsonString(highestPromoter)));
        jsonObject.add("highestPromotion", jsonParser.parse(Utils.convertObjectToJsonString(String.valueOf(highestPromotion))));
        jsonObject.add("end", jsonParser.parse(Utils.convertObjectToJsonString(String.valueOf(end.getTime()))));
        jsonObject.add("wage", jsonParser.parse(Utils.convertObjectToJsonString(String.valueOf(wage))));
        return jsonObject.toString();
    }

    public static Auction convertJsonStringToAuction(String json) {
        if(json.equals("null"))
            return null;
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
