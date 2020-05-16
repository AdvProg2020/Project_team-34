package account;

import database.AccountDataBase;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class AccountTest {

    private static Supervisor supervisor = new Supervisor("rpirayadi", "rouzbeh", "pirayadi", "r@yahoo",
            "34", "r1234", 10000000);
    private static Customer customer = new Customer("AA", "Aryan", "ahadinia", "aryan@gmail",
            "0912", "a1235", 2);
    private static Supplier supplier = new Supplier("Soheil.MH", "soheil", "mahdizadeh", "s@",
            "1234", "s1236", 23, "TEAM_34");

    @Test
    public void testCreateTable(){
        AccountDataBase.createNewTable();
    }

    @Test
    public void testSetName(){
        supervisor.setName("roozbeh");
        Assert.assertEquals("roozbeh", supervisor.getName());
    }

    @Test
    public void testSetEmail(){
        supervisor.setEmail("r@gmail");
        Assert.assertEquals("r@gmail", supervisor.getEmail());
    }

    @Test
    public void testSetPhoneNumber(){
        supervisor.setPhoneNumber("4426");
        Assert.assertEquals("4426", supervisor.getPhoneNumber());
    }

    @Test
    public void testSetFamilyName(){
        supervisor.setFamilyName("not pirayadi");
        Assert.assertEquals("not pirayadi", supervisor.getFamilyName());
    }

    @Test
    public void testSetPassword(){
        supervisor.setPassword("r4321");
        Assert.assertEquals("r4321", supervisor.getPassword());
    }

    @Test
    public void testSetCredit(){
        supervisor.setCredit(10000001);
        Assert.assertEquals(10000001, supervisor.getCredit());
    }
    @Test
    public void testGetByUsername() {
        testCreateTable();

        Account gotByUsername = Account.getAccountByUsername("rpirayadi");
        Assert.assertEquals(supervisor, gotByUsername);

        Account gotByWrongUsername = Account.getAccountByUsername("not a valid username:)");
        Assert.assertNull(gotByWrongUsername);

        Account gotByNull = Account.getAccountByUsername(null);
        Assert.assertNull(gotByNull);

    }

    @Test
    public void testGetAllUsername(){
        ArrayList<String> allUsername = Account.getAllUsername();
        ArrayList<String > expectedUsername = new ArrayList<>();
        expectedUsername.add("rpirayadi");
        expectedUsername.add("AA");
        expectedUsername.add("Soheil.MH");
        Assert.assertEquals(expectedUsername, allUsername);
    }

}
