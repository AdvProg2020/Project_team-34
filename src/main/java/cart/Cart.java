package cart;

import account.Customer;
import account.Supplier;
import discount.CodedDiscount;
import discount.Sale;
import exceptionalMassage.ExceptionalMassage;
import log.ShippingInfo;
import product.Product;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Aryan Ahadinia
 * @since 0.0.1
 */

public class Cart {
    private static long countOfCartCreated = 0;

    private String identifier;
    private Customer owner;
    private ArrayList<ProductInCart> productsIn;
    private HashMap<ProductInCart, Integer> productInCount;
    private HashMap<ProductInCart, Sale> productInSale;
    private CodedDiscount codedDiscount;
    private ShippingInfo shippingInfo;

    //Constructor
    public Cart(Customer owner) {
        this.identifier = generateIdentifier();
        countOfCartCreated++;
        this.owner = owner;
        this.productsIn = new ArrayList<>();
        this.productInCount = new HashMap<>();
        this.productInSale = new HashMap<>();
        this.codedDiscount = null;
        this.shippingInfo = null;
        //file modification required if <owner != null>
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

    public void setProductsIn(ArrayList<ProductInCart> productsIn) {
        this.productsIn = productsIn;
        //file modification required <owner != null>
    }

    public void setProductInCount(HashMap<ProductInCart, Integer> productInCount) {
        this.productInCount = productInCount;
        //file modification required <owner != null>
    }

    public void setProductInSale(HashMap<ProductInCart, Sale> productInSale) {
        this.productInSale = productInSale;
        //file modification required <owner != null>
    }

    //Modeling methods:
    public String generateIdentifier() {
        return "T34CA" + String.format("%015d", countOfCartCreated + 1);
    }

    public void clearCart() {
        setCodedDiscount(null);
        setShippingInfo(null);
        setProductsIn(new ArrayList<>());
        setProductInCount(new HashMap<>());
        setProductInSale(new HashMap<>());
    }

    public boolean isCartClear() {
        return productsIn.size() == 0;
    }

    public ProductInCart getProductInCartObject(Product product, Supplier supplier) {
        if (productsIn.size() != 0) {
            for (ProductInCart productInCart : productsIn) {
                if (productInCart.getProduct() == product && productInCart.getSupplier() == supplier)
                    return productInCart;
            }
        }
        return null;
    }

    public void addProductToCart(Product product, Supplier supplier) throws ExceptionalMassage {
        if (getProductInCartObject(product, supplier) != null) {
            increaseProductCount(product, supplier);
        } else {
            if (product.getRemainedNumber(supplier) == 0) {
                throw new ExceptionalMassage("This product with this supplier is currently out of stock.");
            }
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

    public void changeQuantityOfProductInCart(Product product, Supplier supplier, int newQuantity) throws ExceptionalMassage {
        if (getProductInCartObject(product, supplier) == null)
            throw new ExceptionalMassage("Product not in cart.");
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
        changeQuantityOfProductInCart(product, supplier, productInCount.get(productInCart) + 1);
    }

    public void decreaseProductCount(Product product, Supplier supplier) throws ExceptionalMassage {
        ProductInCart productInCart = getProductInCartObject(product, supplier);
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
            totalAmount += (productInCart.getProduct()).getPrice(productInCart.getSupplier());
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
        return codedDiscount.discountAmountFor(getValueOfCartWithoutDiscounts() - getAmountOfSale());
    }

    public int getBill() {
        return getValueOfCartWithoutDiscounts() - (getAmountOfSale() + getAmountOfCodedDiscount());
    }

    public int getSupplierPurchase(Supplier supplier) {
        int totalPurchase = 0;
        if (productsIn.size() != 0) {
            for (ProductInCart productInCart : productsIn) {
                if (productInCart.getSupplier() == supplier) {
                    totalPurchase += (productInCart.getProduct()).getPrice(supplier);
                }
            }
        }
        return totalPurchase;
    }

    public int getSupplierSaleAmount(Supplier supplier) {
        int totalSale = 0;
        if (productsIn.size() != 0) {
            for (ProductInCart productInCart : productsIn) {
                if (productInCart.getSupplier() == supplier) {
                    totalSale += (productInSale.get(productInCart)).discountAmountFor((productInCart.getProduct()).getPrice(supplier));
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
        if (productsIn.size() != 0) {
            for (ProductInCart productInCart : productsIn) {
                if (productInCart.getProduct() == product)
                    return true;
            }
        }
        return false;
    }

    public boolean isProductInCart(Product product, Supplier supplier) {
        return getProductInCartObject(product, supplier) != null;
    }
}

class ProductInCart {
    private static long numberOfObjectCreated;

    private final String identifier;
    private final Product product;
    private final Supplier supplier;

    public ProductInCart(Product product, Supplier supplier) {
        this.identifier = generateIdentifier();
        this.product = product;
        this.supplier = supplier;
        numberOfObjectCreated++;
    }

    public String getIdentifier() {
        return identifier;
    }

    public Product getProduct() {
        return product;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public String generateIdentifier() {
        return "T34PC" + String.format("%015d", numberOfObjectCreated + 1);
    }
}