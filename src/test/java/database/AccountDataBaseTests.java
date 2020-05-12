package database;

import account.Account;
import account.Customer;
import account.Supervisor;
import account.Supplier;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class AccountDataBaseTests {
    /*
    @Test
    public void testAccountDataBase(){

        AccountDataBase.createNewTable();


        AccountDataBase.add(new Supervisor("rpirayadi", "rouzbeh", "pirayadi", "r@yahoo",
                "34","r1234",10000000));
        AccountDataBase.add(new Customer("AA", "Aryan", "ahadinia", "aryan@gmail",
                "0912", "a1235", 2));
        AccountDataBase.add(new Supplier("Soheil.MH","soheil", "mahdizadeh","s@",
                "1234", "s1236", 23, "TEAM_34"));

        ArrayList <Account> actualAccounts = AccountDataBase.getAllAccounts();
        String [] actualNames = new String[3];
        for (int i = 0 ; i< 3; i++) {
            actualNames[i] = actualAccounts.get(i).getName();
        }
        Assert.assertArrayEquals(new String[] {"rouzbeh", "Aryan" , "soheil"}, actualNames);

    }
    @Test
    public  void  testUpdate(){
        Supplier supplier = new Supplier("goingToBeUpdated","corona", "cobid","s@",
                "1234", "s1236", 23, "TEAM_34");
        supplier.setName("finished");
        AccountDataBase.add(supplier);

        ArrayList <Account> actualAccounts = AccountDataBase.getAllAccounts();
        String [] actualNames = new String[4];
        for (int i = 0 ; i< 4; i++) {
            actualNames[i] = actualAccounts.get(i).getName();
        }
        Assert.assertArrayEquals(new String[] {"rouzbeh", "Aryan" , "soheil","finished"}, actualNames);



    }

     */
}
