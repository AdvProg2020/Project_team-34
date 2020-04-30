package database;

import account.Account;
import account.Customer;
import account.Supervisor;
import account.Supplier;
import log.CustomerLog;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class AccountDataBaseTests {
    @Test
    public void testAccountDataBase(){
        AccountDataBase accountDataBase = new AccountDataBase();
        accountDataBase.createNewTable();


        accountDataBase.add(new Supervisor("rpirayadi", "rouzbeh", "pirayadi", "r@yahoo",
                "34","r1234",10000000));
        /*accountDataBase.add(new Customer("AA", "Aryan", "ahadinia", "aryan@gmail",
                "0912", "a1235", 2));
        accountDataBase.add(new Supplier("Soheil.MH","soheil", "mahdizadeh","s@",
                "1234", "s1236", 23, "TEAM_34"));

         */

        ArrayList <Account> actualAccounts = accountDataBase.getAllAccounts();
        String [] actualNames = new String[1];
        for (int i = 0 ; i< 1; i++) {
            actualNames[i] = actualAccounts.get(i).getName();
        }
        Assert.assertArrayEquals(new String[] {"rouzbeh"}, actualNames);

    }
}
