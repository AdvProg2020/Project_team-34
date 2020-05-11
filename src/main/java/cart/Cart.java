package cart;

import account.Customer;
import account.Supplier;
import discount.CodedDiscount;
import discount.Sale;
import exceptionalMassage.ExceptionalMassage;
import product.Product;

import java.util.ArrayList;
import java.util.HashMap;

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
        //file modification required if <owner != null>
    }

    // Added by rpirayadi


    public Cart(String identifier, Customer owner, ArrayList<ProductInCart> productsIn, HashMap<ProductInCart, Integer> productInCount,
                HashMap<ProductInCart, Sale> productInSale, CodedDiscount codedDiscount, ShippingInfo shippingInfo) {
        this.identifier = identifier;
        this.owner = owner;
        this.productsIn = productsIn;
        this.productInCount = productInCount;
        this.productInSale = productInSale;
        this.codedDiscount = codedDiscount;
        this.shippingInfo = shippingInfo;
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
        this.owner = owner;
        //file modification required
    }

    private void setCodedDiscount(CodedDiscount codedDiscount) {
        this.codedDiscount = codedDiscount;
        //file modification required <owner != null>
    }

    private void setShippingInfo(ShippingInfo shippingInfo) {
        this.shippingInfo = shippingInfo;
        //file modification required <owner != null>
    }

    //Modeling methods:
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
            //file modification required if <owner != null>
        }
    }

    public void removeProductFromCart(Product product, Supplier supplier) throws ExceptionalMassage {
        if (getProductInCartObject(product, supplier) == null)
            throw new ExceptionalMassage("Product is not in cart yet.");
        ProductInCart productInCart = getProductInCartObject(product, supplier);
        productsIn.remove(productInCart);
        productInCount.remove(productInCart);
        productInSale.remove(productInCart);
        //file modification required if <owner != null>
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
        //file modification required if <owner != null>
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
        //check for time
        setCodedDiscount(discount);
    }

    public void removeCodedDiscount() throws ExceptionalMassage {
        if (codedDiscount == null) {
            throw new ExceptionalMassage("Code hasn't applied yet.");
        }
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
        //cart must be updated before
        int saleAmount = 0;
        for (ProductInCart productInCart : productsIn) {
            Sale productSale = productInSale.get(productInCart);
            if (productSale != null) {
                saleAmount += productSale.discountAmountFor((productInCart.getProduct()).getPrice(productInCart.getSupplier()));
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
        if (productsIn.size() != 0) {
            for (ProductInCart productInCart : productsIn) {
                if (productInCart.getSupplier() == supplier) {
                    Sale sale = productInSale.get(productInCart);
                    totalSale += sale.discountAmountFor((productInCart.getProduct()).getPrice(supplier));
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
            if (productInCart.getProduct() == product)
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
}

