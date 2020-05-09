package cart;

import database.ShippingInfoDataBase;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;

public class ShippingInfoTest {
    private static final ArrayList<ShippingInfo> testInstances = new ArrayList<>();
    private static ShippingInfo dataBaseConstructorInstance;

    @BeforeClass
    public static void beforeClass() throws Exception {
        //table created but error raise
        ShippingInfoDataBase.createNewTable();
        for (int i = 1; i < 101; i++) {
            testInstances.add(new ShippingInfo("firstName " + i, "lastName " + i, "city " + i,
                    "address " + i, "postalCode " + i, "phoneNumber " + i));
        }
        dataBaseConstructorInstance = new ShippingInfo("identifierC", "firstNameC",
                "lastNameC", "cityC", "addressC", "postalCodeC", "phoneNumberC");
    }

    @Test
    public void testIdentifier1() {
        ShippingInfo shippingInfo = ShippingInfo.getShippingInfoByIdentifier("T34SI000000000000010");
        assert shippingInfo != null;
        Assert.assertEquals("firstName 10", shippingInfo.getFirstName());
    }

    @Test
    public void testIdentifier2() {
        ShippingInfo shippingInfo = ShippingInfo.getShippingInfoByIdentifier("T34SI000000000000101");
        Assert.assertNull(shippingInfo);
    }

    @Test
    public void testLastName() {
        ShippingInfo shippingInfo = testInstances.get(10);
        Assert.assertEquals("lastName 11", shippingInfo.getLastName());
    }

    @Test
    public void testPostalCode() {
        ShippingInfo shippingInfo = testInstances.get(20);
        Assert.assertEquals("postalCode 21", shippingInfo.getPostalCode());
    }

    @Test
    public void testPhoneNumber() {
        ShippingInfo shippingInfo = testInstances.get(25);
        Assert.assertEquals("phoneNumber 26", shippingInfo.getPhoneNumber());
    }

    @Test
    public void testCity() {
        ShippingInfo shippingInfo = testInstances.get(50);
        Assert.assertEquals("city 51", shippingInfo.getCity());
    }

    @Test
    public void testAddress() {
        ShippingInfo shippingInfo = testInstances.get(42);
        Assert.assertEquals("address 43", shippingInfo.getAddress());
    }

    @Test
    public void testDataBaseConstructorIdentifier() {
        Assert.assertEquals("identifierC", dataBaseConstructorInstance.getIdentifier());
    }

    @Test
    public void testDataBaseConstructorFirstName() {
        Assert.assertEquals("firstNameC", dataBaseConstructorInstance.getFirstName());
    }

    @Test
    public void testDataBaseConstructorLastName() {
        Assert.assertEquals("lastNameC", dataBaseConstructorInstance.getLastName());
    }

    @Test
    public void testDataBaseConstructorCity() {
        Assert.assertEquals("cityC", dataBaseConstructorInstance.getCity());
    }

    @Test
    public void testDataBaseConstructorAddress() {
        Assert.assertEquals("addressC", dataBaseConstructorInstance.getAddress());
    }

    @Test
    public void testDataBaseConstructorPhoneNumber() {
        Assert.assertEquals("phoneNumberC", dataBaseConstructorInstance.getPhoneNumber());
    }

    @Test
    public void testDataBaseConstructorPostalCode() {
        Assert.assertEquals("postalCodeC", dataBaseConstructorInstance.getPostalCode());
    }

    @AfterClass
    public static void afterClass() throws Exception {
        ShippingInfo.clear();
        //database clear
    }
}
