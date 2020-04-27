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
    @Test
    public void testAddAndSelectAll (){
        ProductDateBase.createNewTable();
        ProductDateBase productDateBase = new ProductDateBase();

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

        productDateBase.add(product1);
        productDateBase.add(product2);
        productDateBase.add(repeatedProduct);

        ArrayList<Product> actualProducts= productDateBase.getAllProducts();
        String [] actualName= new String[2];
        for (int i=0 ; i< 2; i++) {
            actualName[i] = actualProducts.get(i).getName();
        }
        Assert.assertArrayEquals(new String [] {"product1","product2"} , actualName);
    }
}
