package database;

import account.Account;
import account.Customer;
import account.Supplier;
import feedback.Comment;
import org.junit.Assert;
import org.junit.Test;
import product.Product;

import java.util.ArrayList;

public class CommentDataBaseTests {

    @Test
    public void Test(){

        CommentDataBase.createNewTable();
        Customer customer = new Customer("rpirayadi", "rouzbeh", "pirayadi" , "@",
                "0912", "easyPassword", 998);
        Supplier supplier = new Supplier("Soheil.MH","soheil", "mahdizadeh","s@",
                "1234", "s1236", 23, "TEAM_34");
        Product product = new Product(supplier,"laptop","ASUS", 2,400,
                null,"this laptop is perfect");
        Comment comment1 = new Comment(customer,product,"My Opinion","this laptop is not so good",false);
        CommentDataBase.add(comment1);

        comment1.setTitle("Your Opinion");
        CommentDataBase.update(comment1);

        ArrayList<Comment> allComments =CommentDataBase.getAllComments();
        String [] actualTitles = new String[1];
        for (int i = 0 ; i< 1; i++) {
            actualTitles[i] = allComments.get(i).getTitle();
        }

        Assert.assertArrayEquals(new String[] {"Your Opinion"}, actualTitles);
    }
}
