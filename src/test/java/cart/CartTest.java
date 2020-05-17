package cart;

import account.Customer;
import account.Supplier;
import discount.CodedDiscount;
import exceptionalMassage.ExceptionalMassage;
import org.junit.Assert;
import org.junit.Test;
import product.Product;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class CartTest {
    private static final Customer owner = new Customer("aryanahadinia24", "Aryan", "Ahadinia",
            "aryanahadinia24@gmail.com", "09306926009", "0000", 1000);
    private static final Cart cart = owner.getCart();
    private static final Supplier supplier1 = new Supplier("sup1", "fs1", "ls1",
            "1@sup.com", "09111111111", "1111", 111, "c1");
    private static final Supplier supplier2 = new Supplier("sup2", "fs2", "ls2",
            "2@sup.com", "09222222222", "2222", 222, "c2");
    private static final Product product1 = new Product(supplier1, "p1", "b1", 100,
            10, "A good product1", null, null);
    private static final Product product2 = new Product(supplier2, "p2", "b2", 200,
            20, "A good Product2", null, null);
    private static final Product product3 = new Product(supplier2, "p3", "b3", 1000,
            0, "A good Product3", null, null);
    private static final ShippingInfo shippingInfo = new ShippingInfo("aryan", "ahadinia",
            "tehran", "d5", "1477996171", "09306926009");
    private static final CodedDiscount codedDiscount1 = new CodedDiscount("cartTester1",
            new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis() + 10000), 50,
            10000, getUsageHashMap());
    private static final CodedDiscount codedDiscount2 = new CodedDiscount("cartTester2",
            new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis() + 10000), 50,
            100, getUsageHashMap());

    private static HashMap<Customer, Integer> getUsageHashMap() {
        HashMap<Customer, Integer> customerIntegerHashMap = new HashMap<>();
        customerIntegerHashMap.put(owner, 100);
        return customerIntegerHashMap;
    }

    @Test
    public void testGetOwner() {
        Assert.assertEquals(owner, cart.getOwner());
    }

    @Test
    public void testSetOwner() {
        cart.setOwner(null);
        Assert.assertNull(cart.getOwner());
        cart.setOwner(owner);
        Assert.assertEquals(owner, cart.getOwner());
    }

    @Test
    public void testIdentifier() {
        Assert.assertEquals("T34CA" + String.format("%015d", Cart.getCountOfCartCreated()), cart.getIdentifier());
    }

    @Test
    public void testGetCartByIdentifier1() {
        Assert.assertNull(Cart.getCartById("T34CA" + String.format("%015d", Cart.getCountOfCartCreated() + 1)));
    }

    @Test
    public void testGetCartByIdentifier2() {
        Assert.assertEquals(cart, Cart.getCartById("T34CA" + String.format("%015d", Cart.getCountOfCartCreated())));
    }

    @Test
    public void testGetProductIn1() {
        Assert.assertEquals(0, cart.getProductsIn().size());
    }

    @Test
    public void testGetProductInCount1() {
        Assert.assertEquals(0, cart.getProductInCount().size());
    }

    @Test
    public void testGetProductInSale() {
        Assert.assertEquals(0, cart.getProductInSale().size());
    }

    @Test
    public void testGetCodedDiscount1() {
        Assert.assertNull(cart.getCodedDiscount());
    }

    @Test
    public void testGetShippingInfo1() {
        Assert.assertNull(cart.getShippingInfo());
    }

    @Test
    public void testIsCartClear1() {
        Assert.assertTrue(cart.isCartClear());
    }

    @Test
    public void testGetProductInCartObject1() {
        Assert.assertNull(cart.getProductInCartObject(product1, supplier1));
    }

    @Test
    public void testAddProductToCart1() throws ExceptionalMassage {
        cart.addProductToCart(product1, supplier1);
        Assert.assertTrue(cart.isProductInCart(product1, supplier1));
        cart.removeProductFromCart(product1, supplier1);
    }

    @Test
    public void testAddProductToCart2() throws ExceptionalMassage {
        cart.addProductToCart(product1, supplier1);
        cart.addProductToCart(product1, supplier1);
        ProductInCart productInCart = cart.getProductInCartObject(product1, supplier1);
        Assert.assertEquals(2, cart.getProductInCount().get(productInCart).intValue());
        Assert.assertEquals(1, cart.getProductsIn().size());
        cart.removeProductFromCart(product1, supplier1);
    }

    @Test
    public void testAddProductToCart3() {
        String massage = "";
        try {
            cart.addProductToCart(product1, supplier2);
        } catch (ExceptionalMassage e) {
            massage = e.getMessage();
        }
        Assert.assertEquals("This product with this supplier is currently out of stock.", massage);
    }

    @Test
    public void testAddProductToCart4() {
        String massage = "";
        try {
            cart.addProductToCart(product3, supplier2);
        } catch (ExceptionalMassage e) {
            massage = e.getMessage();
        }
        Assert.assertEquals("This product with this supplier is currently out of stock.", massage);
    }

    @Test
    public void testRemoveProductFromCart1() throws ExceptionalMassage {
        cart.addProductToCart(product1, supplier1);
        cart.removeProductFromCart(product1, supplier1);
        Assert.assertFalse(cart.isProductInCart(product1, supplier1));
    }

    @Test
    public void testRemoveProductFromCart2() throws ExceptionalMassage {
        String massage = "";
        try {
            cart.removeProductFromCart(product1, supplier1);
        } catch (ExceptionalMassage e) {
            massage = e.getMessage();
        }
        Assert.assertEquals("Product is not in cart yet.", massage);
    }

    @Test
    public void testIncreaseProductCount1() throws ExceptionalMassage {
        cart.addProductToCart(product1, supplier1);
        cart.increaseProductCount(product1, supplier1);
        ProductInCart productInCart = cart.getProductInCartObject(product1, supplier1);
        Assert.assertEquals(2, cart.getProductInCount().get(productInCart).intValue());
        cart.removeProductFromCart(product1, supplier1);
    }

    @Test
    public void testIncreaseProductCount2() throws ExceptionalMassage {
        cart.addProductToCart(product1, supplier1);
        cart.increaseProductCount(product1, supplier1);
        cart.increaseProductCount(product1, supplier1);
        cart.increaseProductCount(product1, supplier1);
        ProductInCart productInCart = cart.getProductInCartObject(product1, supplier1);
        Assert.assertEquals(4, cart.getProductInCount().get(productInCart).intValue());
        cart.removeProductFromCart(product1, supplier1);
    }

    @Test
    public void testIncreaseProductCount3() throws ExceptionalMassage {
        cart.addProductToCart(product1, supplier1);
        String massage = "";
        for (int i = 0; i < 10; i++) {
            try {
                cart.increaseProductCount(product1, supplier1);
            } catch (ExceptionalMassage e) {
                massage = e.getMessage();
            }
        }
        Assert.assertEquals("This product is not available in this amount.", massage);
        cart.removeProductFromCart(product1, supplier1);
    }

    @Test
    public void testIncreaseProductCount4() {
        String massage = "";
        try {
            cart.increaseProductCount(product1, supplier1);
        } catch (ExceptionalMassage e) {
            massage = e.getMessage();
        }
        Assert.assertEquals("Product not in cart.", massage);
    }

    @Test
    public void testDecreaseProductCount1() throws ExceptionalMassage {
        cart.addProductToCart(product1, supplier1);
        cart.increaseProductCount(product1, supplier1);
        cart.decreaseProductCount(product1, supplier1);
        ProductInCart productInCart = cart.getProductInCartObject(product1, supplier1);
        Assert.assertEquals(1, cart.getProductInCount().get(productInCart).intValue());
        cart.removeProductFromCart(product1, supplier1);
    }

    @Test
    public void testDecreaseProductCount2() throws ExceptionalMassage {
        cart.addProductToCart(product1, supplier1);
        cart.decreaseProductCount(product1, supplier1);
        Assert.assertTrue(cart.isCartClear());
    }

    @Test
    public void testDecreaseProductCount3() throws ExceptionalMassage {
        String massage = "";
        try {
            cart.decreaseProductCount(product1, supplier1);
        } catch (ExceptionalMassage e) {
            massage = e.getMessage();
        }
        Assert.assertEquals("Product not in cart.", massage);
    }

    @Test
    public void testSubmitShippingInfo() throws ExceptionalMassage {
        cart.submitShippingInfo(shippingInfo);
        Assert.assertEquals(shippingInfo, cart.getShippingInfo());
        Assert.assertTrue(cart.isShippingInfoSubmitted());
        cart.removeShippingInfo();
        Assert.assertFalse(cart.isShippingInfoSubmitted());
    }

    @Test
    public void testRemoveShippingInfo() {
        String massage = "";
        try {
            cart.removeShippingInfo();
        } catch (ExceptionalMassage e) {
            massage = e.getMessage();
        }
        Assert.assertEquals("ShippingInfo hasn't submitted yet.", massage);
    }

    @Test
    public void testIsProductInCart() throws ExceptionalMassage {
        Assert.assertFalse(cart.isProductInCart(product2));
        Assert.assertFalse(cart.isProductInCart(product1));
        cart.addProductToCart(product1, supplier1);
        Assert.assertFalse(cart.isProductInCart(product2));
        Assert.assertTrue(cart.isProductInCart(product1));
        cart.removeProductFromCart(product1, supplier1);
        Assert.assertFalse(cart.isProductInCart(product2));
        Assert.assertFalse(cart.isProductInCart(product1));
    }

    @Test
    public void testGetBill1() throws ExceptionalMassage {
        cart.addProductToCart(product1, supplier1);
        cart.increaseProductCount(product1, supplier1);
        cart.increaseProductCount(product1, supplier1);
        cart.addProductToCart(product2, supplier2);
        cart.increaseProductCount(product2, supplier2);
        Assert.assertEquals(700, cart.getBill());
        cart.removeProductFromCart(product1, supplier1);
        cart.removeProductFromCart(product2, supplier2);
        Assert.assertTrue(cart.isCartClear());
    }

    @Test
    public void testGetAllSuppliers() throws ExceptionalMassage {
        Assert.assertEquals(0, cart.getAllSupplier().size());
        cart.addProductToCart(product1, supplier1);
        Assert.assertEquals(1, cart.getAllSupplier().size());
        Assert.assertTrue(cart.getAllSupplier().contains(supplier1));
        cart.addProductToCart(product2, supplier2);
        Assert.assertEquals(2, cart.getAllSupplier().size());
        Assert.assertTrue(cart.getAllSupplier().contains(supplier2));
        Assert.assertTrue(cart.getAllSupplier().contains(supplier2));
        cart.removeProductFromCart(product1, supplier1);
        cart.removeProductFromCart(product2, supplier2);
        Assert.assertEquals(0, cart.getAllSupplier().size());
    }

    @Test
    public void testGetSupplierPurchase1() throws ExceptionalMassage {
        cart.addProductToCart(product1, supplier1);
        cart.increaseProductCount(product1, supplier1);
        cart.increaseProductCount(product1, supplier1);
        cart.addProductToCart(product2, supplier2);
        cart.increaseProductCount(product2, supplier2);
        Assert.assertEquals(300, cart.getSupplierPurchase(supplier1));
        Assert.assertEquals(400, cart.getSupplierPurchase(supplier2));
        cart.removeProductFromCart(product1, supplier1);
        cart.removeProductFromCart(product2, supplier2);
        Assert.assertTrue(cart.isCartClear());
    }

    @Test
    public void testCodedDiscount() throws ExceptionalMassage {
        String massage = "";
        try {
            cart.removeCodedDiscount();
        } catch (ExceptionalMassage e) {
            massage = e.getMessage();
        }
        cart.addProductToCart(product1, supplier1);
        cart.increaseProductCount(product1, supplier1);
        cart.increaseProductCount(product1, supplier1);
        cart.addProductToCart(product2, supplier2);
        cart.increaseProductCount(product2, supplier2);
        Assert.assertEquals(700, cart.getBill());
        Assert.assertEquals(massage, "Code hasn't applied yet.");
        cart.applyCodedDiscount(codedDiscount1.getDiscountCode());
        Assert.assertEquals( codedDiscount1, cart.getCodedDiscount());
        Assert.assertEquals(350, cart.getBill());
        cart.removeCodedDiscount();
        try {
            cart.applyCodedDiscount("jojo");
        } catch (ExceptionalMassage e) {
            massage = e.getMessage();
        }
        Assert.assertEquals("This Code is invalid.", massage);
        cart.applyCodedDiscount(codedDiscount2.getDiscountCode());
        Assert.assertEquals(600, cart.getBill());
        cart.removeCodedDiscount();
        cart.removeProductFromCart(product1, supplier1);
        cart.removeProductFromCart(product2, supplier2);
        Assert.assertTrue(cart.isCartClear());
    }
}
