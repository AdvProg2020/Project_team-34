package exceptionalMassage;

import org.junit.Assert;
import org.junit.Test;

public class ExceptionalMassageTest {
    @Test
    public void TestExceptionalMassage() {
        ExceptionalMassage exceptionalMassage = new ExceptionalMassage("Exceptional Massage Test 1.");
        Assert.assertEquals("Exceptional Massage Test 1.", exceptionalMassage.getMessage());
    }
}
