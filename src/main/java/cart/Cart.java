package cart;

import account.Customer;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import communications.Utils;
import discount.CodedDiscount;
import discount.Sale;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

/**
 * @author Aryan Ahadinia
 * @since 0.0.1
 */

public class Cart {
    private String identifier;
    private Customer owner;
    private ArrayList<ProductInCart> productsIn;
    private HashMap<ProductInCart, Integer> productInCount;
    private HashMap<ProductInCart, Sale> productInSale;
    private CodedDiscount codedDiscount;
    private ShippingInfo shippingInfo;

    public Cart(String json) {
        JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
        this.identifier = jsonObject.get("identifier").getAsString();
        if (jsonObject.get("owner") instanceof JsonNull) {
            this.owner = null;
        } else {
            this.owner = Customer.convertJsonStringToCustomer(jsonObject.get("owner").toString());
        }
        this.productsIn = Utils.convertJsonElementToProductInCartArrayList(jsonObject.get("productsIn"));
        this.productInCount = Utils.convertJsonElementToProductInCartToIntegerHashMap(jsonObject.get("productInCount"));
        this.productInSale = Utils.convertJsonElementToProductInCartToSaleHashMap(jsonObject.get("productInSale"));
        if (jsonObject.get("codedDiscount") instanceof JsonNull) {
            this.codedDiscount = null;
        } else {
            this.codedDiscount = CodedDiscount.convertJsonStringToCodedDiscount(jsonObject.get("codedDiscount").toString());
        }
        if (jsonObject.get("shippingInfo") instanceof JsonNull) {
            this.shippingInfo = null;
        } else {
            this.shippingInfo = ShippingInfo.convertJsonStringToShippingInfo(jsonObject.get("shippingInfo").toString());
        }
    }

    public String getIdentifier() {
        return identifier;
    }

    public Customer getOwner() {
        return owner;
    }

    public void setOwner(Customer owner) {
        this.owner = owner;
    }

    public ArrayList<ProductInCart> getProductsIn() {
        return productsIn;
    }

    public HashMap<ProductInCart, Integer> getProductInCount() {
        return productInCount;
    }

    public HashMap<ProductInCart, Sale> getProductInSale() {
        return productInSale;
    }

    public CodedDiscount getCodedDiscount() {
        return codedDiscount;
    }

    public void setCodedDiscount(CodedDiscount codedDiscount) {
        this.codedDiscount = codedDiscount;
    }

    public ShippingInfo getShippingInfo() {
        return shippingInfo;
    }

    public void setShippingInfo(ShippingInfo shippingInfo) {
        this.shippingInfo = shippingInfo;
    }

    public static Cart convertJsonStringToCart(String jsonString){
        return new Cart(jsonString);
        //return (Cart) Utils.convertStringToObject(jsonString, "cart.Cart");
    }

    public int getValueOfCartWithoutDiscounts() {
        int totalAmount = 0;
        for (ProductInCart productInCart : productsIn) {
            totalAmount += (productInCart.getProduct()).getPrice(productInCart.getSupplier()) * (productInCount.get(productInCart));
        }
        return totalAmount;
    }

    public int getAmountOfSale() {
        int saleAmount = 0;
        for (ProductInCart productInCart : productsIn) {
            Sale productSale = productInSale.get(productInCart);
            if (productSale != null) {
                saleAmount += productSale.discountAmountFor((productInCart.getProduct()).getPrice(productInCart.getSupplier())) *
                        productInCount.get(productInCart);
            }
        }
        return saleAmount;
    }

    public int getAmountOfCodedDiscount() {
        if (codedDiscount == null)
            return 0;
        return codedDiscount.discountAmountFor(getValueOfCartWithoutDiscounts() - getAmountOfSale());
    }

    public int getBill() {
        return getValueOfCartWithoutDiscounts() - (getAmountOfSale() + getAmountOfCodedDiscount());
    }

    public String toJson() {
        JsonObject jsonObject = new JsonObject();
        JsonParser jsonParser = new JsonParser();
        jsonObject.add("identifier", jsonParser.parse(Utils.convertObjectToJsonString(identifier)));
        jsonObject.add("owner", jsonParser.parse(Utils.convertObjectToJsonString(owner)));
        jsonObject.add("productsIn", Utils.convertProductInCartArrayListToJsonElement(productsIn));
        jsonObject.add("productInCount", Utils.convertProductInCartToIntegerHashMapToJsonElement(productInCount));
        jsonObject.add("productInSale", Utils.convertProductInCartToSaleHashMapToJsonElement(productInSale));
        jsonObject.add("codedDiscount", jsonParser.parse(Utils.convertObjectToJsonString(codedDiscount)));
        jsonObject.add("shippingInfo", jsonParser.parse(Utils.convertObjectToJsonString(shippingInfo)));
        return jsonObject.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cart cart = (Cart) o;
        return Objects.equals(identifier, cart.identifier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier);
    }


}
