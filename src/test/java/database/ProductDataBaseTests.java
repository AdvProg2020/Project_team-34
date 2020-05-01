package database;

import account.Supplier;
import org.junit.Assert;
import org.junit.Test;
import product.Category;
import product.Product;

import java.util.ArrayList;
import java.util.HashMap;

public class ProductDataBaseTests {
    @Test
    public void testAddAndSelectAll (){

        ProductDataBase.createNewTable();

        Category category = null;
        try {
             category = new Category("category1", true, null);
        }
        catch (Exception exception){
            System.out.println(exception.getMessage());
        }
        Product product1 = new Product(new Supplier("supplier1", "Aryan", "Ahadinia","@","1"
        ,"aryan1",0,"team_34"),"product1", "company1", 90, 5,
               category, "be described");
        Product product2 = new Product(new Supplier("supplier2", "Soheil", "Mahdizadeh","@","2"
                ,"soheil1",0,"team_34"),"product2", "company2", 91, 6,
                category, "description");

        Supplier supplier =  new Supplier("supplier3", "Rouzbeh", "Pirayadi","@","3"
                ,"rouzbeh1",100000000,"team_34");
        HashMap<Supplier, Integer> price = new HashMap<>();
        price.put(supplier,93);
        HashMap<Supplier, Integer> remainedNumber = new HashMap<>();
        price.put(supplier,7);
        ArrayList<Supplier> listOfSupplier = new ArrayList<>();
        listOfSupplier.add(supplier);

        Product repeatedProduct = new Product("repeated product", "team_34",price,listOfSupplier,
                remainedNumber,category,"really good", null,100,product1.getProductId(), "PREPARING_TO_BUILD");

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

        Category category = null;
        try {
            category = new Category("category1", true, null);
        }
        catch (Exception exception){
            System.out.println(exception.getMessage());
        }
        Product productToBeUpdated = new Product(new Supplier("supplier1", "Rouzbeh", "Pirayadi","@","10"
                ,"rouzbeh1",100,"team_34"),"productToBeUpdated", "company1", 100, 5,
                category, "be described");

        productToBeUpdated.setName("productUpdatedSuccessfully");
        ProductDataBase.update(productToBeUpdated);

        ArrayList<Product> actualProducts= ProductDataBase.getAllProducts();
        String [] actualName= new String[2];
        for (int i=0 ; i< 2; i++) {
            actualName[i] = actualProducts.get(i).getName();
        }
        Assert.assertArrayEquals(new String [] {"product1","productUpdatedSuccessfully"} , actualName);
    }
}
