package cart;

import account.Customer;
import account.Supplier;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import database.CartDataBase;
import discount.CodedDiscount;
import discount.Sale;
import exceptionalMassage.ExceptionalMassage;
import product.Product;
import server.communications.Utils;
import state.State;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

/**
 * @author Aryan Ahadinia
 * @since 0.0.1
 */

public class Cart {
    private static final ArrayList<Cart> allCarts = new ArrayList<>();
    private static long countOfCartCreated = 0;

    private final String identifier;
    private Customer owner;
    private final ArrayList<ProductInCart> productsIn;
    private final HashMap<ProductInCart, Integer> productInCount;
    private final HashMap<ProductInCart, Sale> productInSale;
    private CodedDiscount codedDiscount;
    private ShippingInfo shippingInfo;

    //Constructor
    public Cart(Customer owner) {
        this.identifier = generateIdentifier();
        this.owner = owner;
        this.productsIn = new ArrayList<>();
        this.productInCount = new HashMap<>();
        this.productInSale = new HashMap<>();
        this.codedDiscount = null;
        this.shippingInfo = null;
        allCarts.add(this);
        countOfCartCreated++;
        if(owner != null)
            CartDataBase.add(this);
    }

    public Cart(String identifier, Customer owner, ArrayList<ProductInCart> productsIn, HashMap<ProductInCart, Integer> productInCount,
                HashMap<ProductInCart, Sale> productInSale, CodedDiscount codedDiscount, ShippingInfo shippingInfo) {
        this.identifier = identifier;
        this.owner = owner;
        this.productsIn = productsIn;
        this.productInCount = productInCount;
        this.productInSale = productInSale;
        this.codedDiscount = codedDiscount;
        this.shippingInfo = shippingInfo;
        allCarts.add(this);
        countOfCartCreated++;
    }

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
            this.codedDiscount = CodedDiscount.convertJsonStringToCodedDiscount(jsonObject.get("codedDiscount").getAsString());
        }
        if (jsonObject.get("shippingInfo") instanceof JsonNull) {
            this.shippingInfo = null;
        } else {
            this.shippingInfo = ShippingInfo.convertJsonStringToShippingInfo(jsonObject.get("shippingInfo").getAsString());
        }
    }

    //Getters:
    public String getIdentifier() {
        return identifier;
    }

    public Customer getOwner() {
        return owner;
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

    public ShippingInfo getShippingInfo() {
        return shippingInfo;
    }

    public static long getCountOfCartCreated() {
        return countOfCartCreated;
    }

    //Setters:
    public void setOwner(Customer owner) {
        if (owner != null) {
            if (this.owner != null) {
                this.owner = owner;
                CartDataBase.update(this);
            } else {
                this.owner = owner;
                CartDataBase.add(this);
            }
        } else {
            if (this.owner != null) {
                this.owner = null;
                CartDataBase.delete(this.getIdentifier());
            }
        }
    }

    private void setCodedDiscount(CodedDiscount codedDiscount) {
        this.codedDiscount = codedDiscount;
        if(owner!= null)
            CartDataBase.update(this);
    }

    private void setShippingInfo(ShippingInfo shippingInfo) {
        this.shippingInfo = shippingInfo;
        if(owner!= null)
            CartDataBase.update(this);
    }

    //Modeling methods:
    public void clear() {
        this.productsIn.clear();
        this.productInCount.clear();
        this.productInSale.clear();
        this.codedDiscount = null;
        this.shippingInfo = null;
    }

    public static String generateIdentifier() {
        return "T34CA" + String.format("%015d", countOfCartCreated + 1);
    }

    public boolean isCartClear() {
        return productsIn.size() == 0;
    }

    public ProductInCart getProductInCartObject(Product product, Supplier supplier) {
        for (ProductInCart productInCart : productsIn) {
            if (productInCart.getProduct().getProductId().equals(product.getProductId()) &&
                    productInCart.getSupplier().getUserName().equals(supplier.getUserName())) {
                return productInCart;
            }
        }
        return null;
        //modified to use username and id to check equality
    }

    public void addProductToCart(Product product, Supplier supplier) throws ExceptionalMassage {
        if (getProductInCartObject(product, supplier) != null) {
            increaseProductCount(product, supplier);
        } else {
            if (!product.getListOfSuppliers().contains(supplier))
                throw new ExceptionalMassage("This product with this supplier is currently out of stock.");
            if (product.getRemainedNumber(supplier) == 0)
                throw new ExceptionalMassage("This product with this supplier is currently out of stock.");
            ProductInCart productInCart = new ProductInCart(product, supplier);
            productsIn.add(productInCart);
            productInCount.put(productInCart, 1);
            productInSale.put(productInCart, Sale.getProductSale(product, supplier));
            if(owner!= null)
                CartDataBase.update(this);
        }
    }

    public void removeProductFromCart(Product product, Supplier supplier) throws ExceptionalMassage {
        if (getProductInCartObject(product, supplier) == null)
            throw new ExceptionalMassage("Product is not in cart yet.");
        ProductInCart productInCart = getProductInCartObject(product, supplier);
        productsIn.remove(productInCart);
        productInCount.remove(productInCart);
        productInSale.remove(productInCart);
        if(owner!= null)
            CartDataBase.update(this);
    }

    private void changeQuantityOfProductInCart(Product product, Supplier supplier, int newQuantity) throws ExceptionalMassage {
        if (newQuantity == 0) {
            removeProductFromCart(product, supplier);
        } else {
            ProductInCart productInCart = getProductInCartObject(product, supplier);
            if (product.getRemainedNumber(productInCart.getSupplier()) < newQuantity) {
                throw new ExceptionalMassage("This product is not available in this amount.");
            }
            productInCount.replace(productInCart, newQuantity);
        }
        if(owner!= null)
            CartDataBase.update(this);
    }

    public void increaseProductCount(Product product, Supplier supplier) throws ExceptionalMassage {
        ProductInCart productInCart = getProductInCartObject(product, supplier);
        if (productInCart == null)
            throw new ExceptionalMassage("Product not in cart.");
        changeQuantityOfProductInCart(product, supplier, productInCount.get(productInCart) + 1);
    }

    public void decreaseProductCount(Product product, Supplier supplier) throws ExceptionalMassage {
        ProductInCart productInCart = getProductInCartObject(product, supplier);
        if (productInCart == null)
            throw new ExceptionalMassage("Product not in cart.");
        changeQuantityOfProductInCart(product, supplier, productInCount.get(productInCart) - 1);
    }

    public void update() {
        for (ProductInCart productInCart : productsIn) {
            productInSale.replace(productInCart, Sale.getProductSale(productInCart.getProduct(), productInCart.getSupplier()));
        }
        CartDataBase.update(this);
    }

    public void applyCodedDiscount(String discountCode) throws ExceptionalMassage {
        if (owner == null) {
            throw new ExceptionalMassage("You should login before apply discount code.");
        }
        CodedDiscount discount = CodedDiscount.getCodedDiscountByCode(discountCode);
        if (discount == null) {
            throw new ExceptionalMassage("This Code is invalid.");
        }
        if (!discount.canCustomerUseCode(owner)) {
            throw new ExceptionalMassage("You can't use this code.");
        }
        setCodedDiscount(discount);
    }

    public void removeCodedDiscount(){
        setCodedDiscount(null);
    }

    public void submitShippingInfo(ShippingInfo shippingInfo) {
        setShippingInfo(shippingInfo);
    }

    public void removeShippingInfo() throws ExceptionalMassage {
        if (shippingInfo == null) {
            throw new ExceptionalMassage("ShippingInfo hasn't submitted yet.");
        }
        setShippingInfo(null);
    }

    public boolean isShippingInfoSubmitted() {
        return shippingInfo != null;
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

    public int getSupplierPurchase(Supplier supplier) {
        int totalPurchase = 0;
        if (productsIn.size() != 0)
            for (ProductInCart productInCart : productsIn)
                if (productInCart.getSupplier() == supplier)
                    totalPurchase += ((productInCart.getProduct()).getPrice(supplier)) * (productInCount.get(productInCart));
        return totalPurchase;
    }

    public int getSupplierSaleAmount(Supplier supplier) {
        //check
        int totalSale = 0;
        for (ProductInCart productInCart : productsIn) {
            if (productInCart.getSupplier().equals(supplier)) {
                Sale sale = productInSale.get(productInCart);
                if (sale != null) {
                    totalSale += (sale.discountAmountFor((productInCart.getProduct()).getPrice(supplier))) * productInCount.get(productInCart);
                }
            }
        }
        return totalSale;
    }

    public int getSupplierEarnedMoney(Supplier supplier) {
        return getSupplierPurchase(supplier) - getSupplierSaleAmount(supplier);
    }

    public ArrayList<Supplier> getAllSupplier() {
        ArrayList<Supplier> allSuppliers = new ArrayList<>();
        for (ProductInCart productInCart : productsIn) {
            if (!allSuppliers.contains(productInCart.getSupplier())) {
                allSuppliers.add(productInCart.getSupplier());
            }
        }
        return allSuppliers;
    }

    public boolean isProductInCart(Product product) {
        for (ProductInCart productInCart : productsIn) {
            if (productInCart.getProduct().equals(product))
                return true;
        }
        return false;
    }

    public boolean isProductInCart(Product product, Supplier supplier) {
        return getProductInCartObject(product, supplier) != null;
    }

    public static Cart getCartById(String identifier) {
        for (Cart cart : allCarts) {
            if (cart.getIdentifier().equals(identifier))
                return cart;
        }
        return null;
    }

    public int getCountOfProductInCart(ProductInCart productInCart) throws ExceptionalMassage {
        if (!productInCount.containsKey(productInCart)) {
            throw new ExceptionalMassage("Not found");
        }
        return productInCount.get(productInCart);
    }

    public ArrayList<Product> getFileProductsInCart(){
        ArrayList<Product> fileProducts = new ArrayList<>();
        Product product;
        for (ProductInCart productInCart : productsIn) {
            product = productInCart.getProduct();
            if(product.getFilePath() != null){
                fileProducts.add(product);
            }
        }
        return fileProducts;
    }
    public static Cart convertJsonStringToCart(String jsonString) {
        return new Cart(jsonString);
        //return (Cart) Utils.convertStringToObject(jsonString, "cart.Cart");
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
    public String toString() {
//        TODO update();
        StringBuilder cart = new StringBuilder("Cart, Identifier: " + identifier + " for <username: " + (owner == null ? "NA" : owner.getUserName()) + ">" + "\n");
        if (shippingInfo != null)
            cart.append(shippingInfo.toString()).append("\n");
        if (codedDiscount != null)
            cart.append(codedDiscount.getDiscountCode()).append("\n");
        cart.append("Sale: ").append(getAmountOfSale()).append("\n");
        cart.append("Coded Discount Amount: ").append(getAmountOfCodedDiscount()).append("\n");
        int i = 1;
        for (ProductInCart productInCart : productsIn) {
            cart.append("Product").append(i).append(". ").append(productInCart.getProduct().getProductId()).append(" from ").append(productInCart.getSupplier().getNameOfCompany()).append(" X ").append(productInCount.get(productInCart)).append("\n");
            i++;
        }
        return cart.toString();
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

    public String toAuctionString() {
//       TODO update();
        StringBuilder cart = new StringBuilder("Cart, Identifier: " + identifier + " for <username: " + (owner == null ? "NA" : owner.getUserName()) + ">" + "\n");
        if (shippingInfo != null)
            cart.append(shippingInfo.toString()).append("\n");
        if (codedDiscount != null)
            cart.append(codedDiscount.getDiscountCode()).append("\n");
        for (ProductInCart productInCart : productsIn) {
            cart.append("Auction").append(". ").append(productInCart.getProduct().getProductId()).append(" from ").append(productInCart.getSupplier().getNameOfCompany()).append(" X ").append(productInCount.get(productInCart)).append("\n");
        }
        return cart.toString();
    }
}
