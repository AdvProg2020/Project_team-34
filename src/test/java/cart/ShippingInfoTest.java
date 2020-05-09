package cart;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;

public class ShippingInfoTest {
    private static ArrayList<ShippingInfo> testInstances = new ArrayList<>();

    @BeforeClass
    public static void beforeClass() throws Exception {
        for (int i = 1; i < 101; i++) {
            testInstances.add(new ShippingInfo("firstName " + i, "lastName " + i, "city " + i,
                    "address " + i, "postalCode " + i, "phoneNumber " + i));
        }
    }

    @Test
    public void testIdentifier1() {
        ShippingInfo shippingInfo = ShippingInfo.getShippingInfoByIdentifier("T34SI000000000000010");
        Assert.assertEquals("firstName 10", shippingInfo.getFirstName());
    }
}
