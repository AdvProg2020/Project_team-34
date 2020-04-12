package discount;

import product.Product;

import java.util.ArrayList;
import java.util.Date;

public class Sale extends Discount{
    private String offId;
    private ArrayList<Product> products;
    private static ArrayList<Sale> sales;
    //enumeration


    public Sale(Date start, Date end, int percent, String offId) {
        super(start, end, percent);
        this.offId = offId;
    }

    public void addSale(Sale sale){
        sales.add(sale);
    }

    public void addProductToSale(Product product){

    }

    public void removeProductFromSale(Product product){

    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public ArrayList<Sale> getSales(){
        return null;
    }

    //setter

}
