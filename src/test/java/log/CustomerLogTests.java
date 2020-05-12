package log;

import account.Customer;
import account.Supplier;
import cart.Cart;
import cart.ShippingInfo;
import exceptionalMassage.ExceptionalMassage;
import org.junit.Assert;
import org.junit.Test;
import product.Product;

import java.util.ArrayList;
import java.util.Date;

public class CustomerLogTests {
    private static final long createdCustomerLogCountBefore = CustomerLog.getAllCustomerLogCreatedCount();
    private static final long createdSupplierLogCountBefore = SupplierLog.getAllSupplierLogCreatedCount();
    private static final ArrayList<SupplierLog> allSupplierLogCreatedBefore = new ArrayList<>(SupplierLog.getAllSupplierLogs());
    private static final Customer customer1 = new Customer("aryanahadinia241CLT", "Aryan1",
            "Ahadinia1", "aryanahadinia24@gmail.com1", "11111111111", "1111",
            1000);
    private static final Customer customer2 = new Customer("aryanahadinia242CLT", "Aryan2",
            "Ahadinia2", "aryanahadinia24@gmail.com2", "22222222222", "2222",
            2000);
    private static final Customer customer3 = new Customer("aryanahadinia243CLT", "Aryan3",
            "Ahadinia3", "aryanahadinia24@gmail.com3", "33333333333", "3333",
            3000);
    private static final Customer customer4 = new Customer("aryanahadinia244CLT", "Aryan4",
            "Ahadinia4", "aryanahadinia24@gmail.com4", "44444444444", "4444",
            4000);
    private static final Supplier supplier1 = new Supplier("sup1CLT", "fs1", "ls1",
            "1@sup.com", "09111111111", "1111", 111, "c1");
    private static final Supplier supplier2 = new Supplier("sup2CLT", "fs2", "ls2",
            "2@sup.com", "09222222222", "2222", 222, "c2");
    private static final Product product1 = new Product(supplier1, "p1", "b1", 100,
            10, "A good product1", null);
    private static final Product product2 = new Product(supplier2, "p2", "b2", 200,
            20, "A good Product2", null);
    private static final ShippingInfo shippingInfo = new ShippingInfo("aryan", "ahadinia",
            "tehran", "d5", "1477996171", "09306926009");
    private static final Cart cart1 = createCart(customer1, 2, 1);
    private static final Cart cart2 = createCart(customer2, 3, 0);
    private static final Cart cart3 = createCart(customer4, 3, 6);
    private static final CustomerLog customerLog1 = new CustomerLog(cart1);
    private static final CustomerLog customerLog2 = new CustomerLog(cart2);
    private static final CustomerLog dataBaseConstructorConstructed = new CustomerLog("identifier C CLT",
            new Date(System.currentTimeMillis()), LogStatus.DELIVERED, cart3);
    private static final ArrayList<SupplierLog> allSupplierLogCreatedAfter = new ArrayList<>(SupplierLog.getAllSupplierLogs());
    private static final ArrayList<SupplierLog> allSupplierLogCreatedIn = getAllSupplierLogCreatedIn();

    private static Cart createCart(Customer owner, int product1Count, int product2Count) {
        Cart cart = owner.getCart();
        try {
            for (int i = 0; i < product1Count; i++)
                cart.addProductToCart(product1, supplier1);
            for (int i = 0; i < product2Count; i++)
                cart.addProductToCart(product2, supplier2);
            cart.submitShippingInfo(shippingInfo);
        } catch (ExceptionalMassage exceptionalMassage) {
            exceptionalMassage.printStackTrace();
        }
        return cart;
    }

    private static ArrayList<SupplierLog> getAllSupplierLogCreatedIn() {
        ArrayList<SupplierLog> allSupplierLogCreatedIn = new ArrayList<>(allSupplierLogCreatedAfter);
        allSupplierLogCreatedIn.removeAll(allSupplierLogCreatedBefore);
        return allSupplierLogCreatedIn;
    }

    @Test
    public void dataBaseConstructor() {
        Assert.assertEquals("identifier C CLT" ,dataBaseConstructorConstructed.getIdentifier());
        Assert.assertEquals(cart3, dataBaseConstructorConstructed.getCart());
        Assert.assertEquals(LogStatus.DELIVERED, dataBaseConstructorConstructed.getDeliveryStatus());
    }

    @Test
    public void testConstruction() {
        Assert.assertTrue(customerLog1.getDate().getTime() <= System.currentTimeMillis() + 1000);
        Assert.assertTrue(System.currentTimeMillis() - 1000 <= customerLog1.getDate().getTime());
        Assert.assertEquals(cart1, customerLog1.getCart());
    }

    @Test
    public void testStatus() throws ExceptionalMassage {
        Assert.assertEquals(LogStatus.PENDING, customerLog1.getDeliveryStatus());
        customerLog1.proceedToNextStep();
        Assert.assertEquals(LogStatus.PREPARING, customerLog1.getDeliveryStatus());
        customerLog1.proceedToNextStep();
        Assert.assertEquals(LogStatus.SENDING, customerLog1.getDeliveryStatus());
        customerLog1.proceedToNextStep();
        Assert.assertEquals(LogStatus.DELIVERED, customerLog1.getDeliveryStatus());
        String massage = "";
        try {
            customerLog1.proceedToNextStep();
        } catch (ExceptionalMassage e) {
            massage = e.getMessage();
        }
        Assert.assertEquals("This order has already delivered." ,massage);
    }

    @Test
    public void testIdentifier() {
        Assert.assertNull(CustomerLog.getCustomerLogById(
                "T34CL" + String.format("%015d", createdCustomerLogCountBefore + 100)));
        CustomerLog customerLogGotById = CustomerLog.getCustomerLogById(
                "T34CL" + String.format("%015d", createdCustomerLogCountBefore + 1));
        Assert.assertEquals(customerLog1, customerLogGotById);
    }

    @Test
    public void testPayment() {
        Assert.assertEquals(400, customerLog1.getPaidAmount());
        Assert.assertEquals(0, customerLog1.getCodedDiscountAmount());
        Assert.assertEquals(300, customerLog2.getPaidAmount());
        Assert.assertEquals(0, customerLog2.getCodedDiscountAmount());
    }

    @Test
    public void testGetCustomerCustomerLogs() {
        ArrayList<CustomerLog> customerLogs1 = CustomerLog.getCustomerCustomerLogs(customer1);
        ArrayList<CustomerLog> customerLogs2 = CustomerLog.getCustomerCustomerLogs(customer2);
        ArrayList<CustomerLog> customerLogs3 = CustomerLog.getCustomerCustomerLogs(customer3);
        Assert.assertEquals(1, customerLogs1.size());
        Assert.assertTrue(customerLogs1.contains(customerLog1));
        Assert.assertEquals(1, customerLogs2.size());
        Assert.assertTrue(customerLogs2.contains(customerLog2));
        Assert.assertEquals(0, customerLogs3.size());
    }

    @Test
    public void testGetCustomerBoughtProductFromSupplier() {
        ArrayList<Customer> product1supplier1Customers = CustomerLog.
                getAllCustomersBoughtProductFromSupplier(product1, supplier1);
        ArrayList<Customer> product1supplier2Customers = CustomerLog.
                getAllCustomersBoughtProductFromSupplier(product1, supplier2);
        ArrayList<Customer> product2supplier2Customers = CustomerLog.
                getAllCustomersBoughtProductFromSupplier(product2, supplier2);
        Assert.assertEquals(3, product1supplier1Customers.size());
        Assert.assertEquals(0, product1supplier2Customers.size());
        Assert.assertEquals(2, product2supplier2Customers.size());
        Assert.assertTrue(product1supplier1Customers.contains(customer1));
        Assert.assertTrue(product1supplier1Customers.contains(customer2));
        Assert.assertTrue(product1supplier1Customers.contains(customer4));
        Assert.assertTrue(product2supplier2Customers.contains(customer1));
        Assert.assertTrue(product2supplier2Customers.contains(customer4));
    }

    @Test
    public void testGetCustomerBoughtProduct() {
        ArrayList<Customer> product1Customers = CustomerLog.getAllCustomersBoughtProduct(product1);
        ArrayList<Customer> product2Customers = CustomerLog.getAllCustomersBoughtProduct(product2);
        Assert.assertEquals(3, product1Customers.size());
        Assert.assertTrue(product1Customers.contains(customer1));
        Assert.assertTrue(product1Customers.contains(customer2));
        Assert.assertTrue(product1Customers.contains(customer4));
        Assert.assertEquals(2, product2Customers.size());
        Assert.assertTrue(product2Customers.contains(customer1));
        Assert.assertTrue(product2Customers.contains(customer4));
    }

    @Test
    public void testSupplierLogCreation() {
        Assert.assertEquals(5, allSupplierLogCreatedIn.size());
        SupplierLog supplierLog1 = allSupplierLogCreatedIn.get(0);
        Assert.assertEquals(customerLog1, supplierLog1.getCustomerLog());
        Assert.assertEquals(supplier1, supplierLog1.getSupplier());
        Assert.assertEquals(200, supplierLog1.getEarnedMoney());
        Assert.assertEquals(200, supplierLog1.getTotalPurchase());
        Assert.assertEquals(0, supplierLog1.getDiscountAmount());
        Assert.assertEquals("T34SL" + String.format("%015d", createdSupplierLogCountBefore + 1),
                supplierLog1.getIdentifier());
        SupplierLog supplierLog2 = allSupplierLogCreatedIn.get(1);
        Assert.assertEquals(customerLog1, supplierLog2.getCustomerLog());
        Assert.assertEquals(supplier2, supplierLog2.getSupplier());
        Assert.assertEquals(200, supplierLog2.getEarnedMoney());
        Assert.assertEquals(200, supplierLog2.getTotalPurchase());
        Assert.assertEquals(0, supplierLog1.getDiscountAmount());
        Assert.assertEquals("T34SL" + String.format("%015d", createdSupplierLogCountBefore + 2),
                supplierLog2.getIdentifier());
        SupplierLog supplierLog3 = allSupplierLogCreatedIn.get(2);
        Assert.assertEquals(customerLog2, supplierLog3.getCustomerLog());
        Assert.assertEquals(supplier1, supplierLog3.getSupplier());
        Assert.assertEquals(300, supplierLog3.getEarnedMoney());
        Assert.assertEquals(300, supplierLog3.getTotalPurchase());
        Assert.assertEquals(0, supplierLog3.getDiscountAmount());
        Assert.assertEquals("T34SL" + String.format("%015d", createdSupplierLogCountBefore + 3),
                supplierLog3.getIdentifier());
        SupplierLog supplierLog4 = allSupplierLogCreatedIn.get(3);
        Assert.assertEquals(dataBaseConstructorConstructed, supplierLog4.getCustomerLog());
        Assert.assertEquals(supplier1, supplierLog4.getSupplier());
        Assert.assertEquals(300, supplierLog4.getEarnedMoney());
        Assert.assertEquals(300, supplierLog4.getTotalPurchase());
        Assert.assertEquals(0, supplierLog4.getDiscountAmount());
        Assert.assertEquals("T34SL" + String.format("%015d", createdSupplierLogCountBefore + 4),
                supplierLog4.getIdentifier());
        SupplierLog supplierLog5 = allSupplierLogCreatedIn.get(4);
        Assert.assertEquals(dataBaseConstructorConstructed, supplierLog5.getCustomerLog());
        Assert.assertEquals(supplier2, supplierLog5.getSupplier());
        Assert.assertEquals(1200, supplierLog5.getEarnedMoney());
        Assert.assertEquals(1200, supplierLog5.getTotalPurchase());
        Assert.assertEquals(0, supplierLog5.getDiscountAmount());
        Assert.assertEquals("T34SL" + String.format("%015d", createdSupplierLogCountBefore + 5),
                supplierLog5.getIdentifier());
    }

    @Test
    public void testGetSupplierSupplierLog() {
        ArrayList<SupplierLog> supplier1Logs = SupplierLog.getSupplierSupplierLog(supplier1);
        ArrayList<SupplierLog> supplier2Logs = SupplierLog.getSupplierSupplierLog(supplier2);
        Assert.assertEquals(3, supplier1Logs.size());
        Assert.assertEquals(2, supplier2Logs.size());
    }

    @Test
    public void testGetSupplierLogById() {
        Assert.assertNull(SupplierLog.getSupplierLogById("T34SL" +
                String.format("%015d", SupplierLog.getAllSupplierLogCreatedCount() + 1)));
        SupplierLog supplierLog5 = SupplierLog.getSupplierLogById("T34SL" +
                String.format("%015d", createdSupplierLogCountBefore + 5));
        Assert.assertNotNull(supplierLog5);
        Assert.assertEquals(dataBaseConstructorConstructed, supplierLog5.getCustomerLog());
        Assert.assertEquals(supplier2, supplierLog5.getSupplier());
        Assert.assertEquals(1200, supplierLog5.getEarnedMoney());
        Assert.assertEquals(1200, supplierLog5.getTotalPurchase());
        Assert.assertEquals(0, supplierLog5.getDiscountAmount());
    }

    @Test
    public void testCustomerLogToString() {
        String expected =
                "Order on .+, Order Identifier: identifier C CLT\n" +
                "delivery status: DELIVERED\n" +
                "paidAmount: 1500\n" +
                "codedDiscountAmount: 0\n" +
                "Cart, Identifier: \\w+ for <username: aryanahadinia244CLT>\n" +
                "ShippingInfo\\{firstName: 'aryan', lastName: 'ahadinia', city: 'tehran', address: 'd5', postalCode: '1477996171', phoneNumber: '09306926009'}\\n" +
                "Product1. " + product1.getProductId() + " from c1 X 3\n" +
                "Product2. " + product2.getProductId() + " from c2 X 6\n";
        System.out.println(expected);
        System.out.println(dataBaseConstructorConstructed.toString());
        Assert.assertTrue(dataBaseConstructorConstructed.toString().matches(expected));
    }

    @Test
    public void testLogStatusToString() {
        Assert.assertEquals("PENDING", LogStatus.PENDING.toString());
        Assert.assertEquals("PREPARING", LogStatus.PREPARING.toString());
        Assert.assertEquals("SENDING", LogStatus.SENDING.toString());
        Assert.assertEquals("DELIVERED", LogStatus.DELIVERED.toString());
    }

    @Test
    public void testSupplierLogToString() {
        SupplierLog supplierLog = allSupplierLogCreatedIn.get(0);
        String expected =
                "SupplierLog on .+, Log identifier: " + supplierLog.getIdentifier() + "\n" +
                        "earnedMoney: 200, discountAmount: 0, totalPurchase: 200\n" +
                        "Product1\\. .+ X 2\n";
        System.out.println(supplierLog.toString());
        System.out.println(expected);
        Assert.assertTrue(supplierLog.toString().matches(expected));
    }
}
