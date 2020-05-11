package cart;

import database.ShippingInfoDataBase;
import org.junit.*;

import java.util.ArrayList;

public class ShippingInfoTest {
    private static final int shippingInfoCreatedCountBefore = ShippingInfo.getTotalShippingInfoCreated();
    private static final ArrayList<ShippingInfo> testInstances = createTestInstances();
    private static final ShippingInfo dataBaseConstructorInstance = new ShippingInfo("identifierC",
            "firstNameC", "lastNameC", "cityC", "addressC", "postalCodeC",
            "phoneNumberC");
    private static final int shippingInfoCreatedCountAfter = ShippingInfo.getTotalShippingInfoCreated();

    private static ArrayList<ShippingInfo> createTestInstances() {
        ShippingInfoDataBase.createNewTable(); //check for error
        ArrayList<ShippingInfo> testInstances = new ArrayList<>();
        for (int i = 1; i <= 100; i++)
            testInstances.add(new ShippingInfo("firstName " + i, "lastName " + i, "city " + i,
                    "address " + i, "postalCode " + i, "phoneNumber " + i));
        return testInstances;
    }

    @Test
    public void testTotalShippingInfoCreated() {
        Assert.assertEquals(101, shippingInfoCreatedCountAfter-shippingInfoCreatedCountBefore);
    }

    @Test
    public void testGenerateIdentifier() {
        Assert.assertEquals("T34SI" + String.format("%015d", shippingInfoCreatedCountAfter + 1),
                ShippingInfo.generateIdentifier());
    }

    @Test
    public void testIdentifier() {
        ShippingInfo shippingInfo = ShippingInfo.getShippingInfoByIdentifier(
                "T34SI" + String.format("%015d", shippingInfoCreatedCountAfter + 1));
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
}
