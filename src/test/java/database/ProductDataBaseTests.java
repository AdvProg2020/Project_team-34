package database;

import account.Supplier;
import org.junit.Assert;
import org.junit.Test;
import product.Category;
import product.Product;
import state.State;

import java.util.ArrayList;
import java.util.HashMap;

public class ProductDataBaseTests {
    /*
    @Test
    public void testAddAndSelectAll (){

        ProductDataBase.createNewTable();

        Product product1 = new Product(new Supplier("supplier1", "Aryan", "Ahadinia","@","1"
        ,"aryan1",0,"team_34"),"product1", "company1", 90, 5,
                "be described",null);
        Product product2 = new Product(new Supplier("supplier2", "Soheil", "Mahdizadeh","@","2"
                ,"soheil1",0,"team_34"),"product2", "company2", 91, 6,
                "description",null);

        Supplier supplier =  new Supplier("supplier3", "Rouzbeh", "Pirayadi","@","3"
                ,"rouzbeh1",100000000,"team_34");
        HashMap<Supplier, Integer> price = new HashMap<>();
        price.put(supplier,93);
        HashMap<Supplier, Integer> remainedNumber = new HashMap<>();
        price.put(supplier,7);
        ArrayList<Supplier> listOfSupplier = new ArrayList<>();
        listOfSupplier.add(supplier);

        Product repeatedProduct = new Product("repeated product", "team_34",product1.getPriceForEachSupplier(),product1.getListOfSuppliers(),product1.getRemainedNumberForEachSupplier(),"description",
                6,product1.getProductId(),State.valueOf("PREPARING_TO_BUILD"),null);

        ProductDataBase.add(product1);
        ProductDataBase.add(product2);
        ProductDataBase.add(repeatedProduct);

        ArrayList<Product> actualProducts= ProductDataBase.getAllProducts();
        String [] actualName= new String[2];
        for (int i=0 ; i< 2; i++) {
            actualName[i] = actualProducts.get(i).getName();
        }
        Assert.assertArrayEquals(new String [] {"product1","product2"} , actualName);
    }

    @Test
    public void testDelete(){

        ProductDataBase.delete("T34P000000000000002");

        ArrayList<Product> actualProducts= ProductDataBase.getAllProducts();
        String [] actualName= new String[1];
        for (int i=0 ; i< 1; i++) {
            actualName[i] = actualProducts.get(i).getName();
        }
        Assert.assertArrayEquals(new String [] {"product1"} , actualName);

    }

    @Test
    public void testUpdate(){

        Product productToBeUpdated = new Product(new Supplier("supplier1", "Rouzbeh", "Pirayadi","@","10"
                ,"rouzbeh1",100,"team_34"),"productToBeUpdated", "company1", 100, 5,
                 "be described",null);

        productToBeUpdated.setName("productUpdatedSuccessfully");
        ProductDataBase.update(productToBeUpdated);

        ArrayList<Product> actualProducts= ProductDataBase.getAllProducts();
        String [] actualName= new String[2];
        for (int i=0 ; i< 2; i++) {
            actualName[i] = actualProducts.get(i).getName();
        }
        Assert.assertArrayEquals(new String [] {"product1","productUpdatedSuccessfully"} , actualName);
    }

     */
}
