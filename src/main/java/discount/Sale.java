package discount;

import account.Supplier;
import exceptionalMassage.ExceptionalMassage;
import product.Product;
import state.State;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * @author soheil
 * @since 0.01
 */

public class Sale extends Discount{
    private static ArrayList<Sale> sales = new ArrayList<>();
    private static int allCreatedSalesNum = 0;
    private String offId;
    private String rootSaleId;
    private ArrayList<Product> products;
    private State state;
    private Supplier supplier;

    public Sale(Supplier supplier,Date start, Date end, int percent,String rootSaleId) {
        super(start, end, percent);

        this.offId = generateOffId();
        allCreatedSalesNum++;
        products = new ArrayList<>();
        if(rootSaleId == null){
            state = State.PREPARING_TO_BUILD;
        } else {
            state = State.PREPARING_TO_EDIT;
        }
        this.supplier = supplier;
        addSale(this);
    }

    public Sale( Date start, Date end, int percent, String offId, ArrayList<Product> products, State state) {
        super(start, end, percent);
        this.offId = offId;
        this.products = products;
        this.state = state;
    }

    private String generateOffId(){
        return "T34S" + String.format("%015d",allCreatedSalesNum  + 1);
    }

    public static void addSale(Sale sale){
        sales.add(sale);
    }

    public void addProductToSale(Product product){
        products.add(product);
    }

    public String getOffId() {
        return offId;
    }

    public State getState() {
        return state;
    }

    public void setOffId(String offId) {
        this.offId = offId;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void removeProductFromSale(Product product){
        products.remove(product);
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public static ArrayList<Sale> getSales(){
        return sales;
    }

    public static Sale getSaleById(String id){
        for (Sale sale : sales) {
            if(sale.getOffId().equals(id)){
                return sale;
            }
        }
        return null;
    }

    public static void removeSale(Sale sale){
        sales.remove(sale);
    }

    public static void importAllData(){

    }

    public Supplier getSupplier() {
        return supplier;
    }

    public boolean isProductInSale(Product product){
        for (Product product1 : products) {
            if(product1 == product){
                return true;
            }
        }
        return false;
    }

    public static ArrayList<Sale> getActiveSales(){
        ArrayList<Sale> activeSales = new ArrayList<>();
        for (Sale sale : sales) {
            if(sale.isSaleActive()){
                activeSales.add(sale);
            }
        }
        return activeSales;
    }

    public boolean isSaleActive(){
        Date date = new Date(System.currentTimeMillis());
        if(this.getState() == State.CONFIRMED && (this.start.before(date) && this.end.after(date))){
            return true;
        } else {
            return false;
        }
    }

    public static Sale getProductSale(Product product, Supplier supplier) {
        //HAZARD!!!
        //this method has written by Aryan Ahadinia for use in Cart.java
        //usable!
        for (Sale sale : sales) {
            if (sale.isProductInSale(product) && sale.getSupplier() == supplier) {
                return sale;
            }
        }
        return null;
    }

    public static ArrayList<String> getAllSaleRequestId(){
        ArrayList<String > result = new ArrayList<>();
        String id;
        for (Sale sale : sales)
        {
            if(sale.getState() == State.PREPARING_TO_EDIT || sale.getState() == State.PREPARING_TO_BUILD){
                result.add(convertSaleIdToRequestId(sale.getOffId()));
            }
        }
        return result;
    }

    public String getRootSaleId() {
        return rootSaleId;
    }

    public static String getDetailsForSaleRequest(String requestId) throws ExceptionalMassage{
        if(Sale.getSaleById(convertRequestIdToSaleId(requestId)) == null){
            throw new ExceptionalMassage("No such id!");
        }
        Sale sale = Sale.getSaleById(convertRequestIdToSaleId(requestId));
        Sale rootSale = Sale.getSaleById(sale.getRootSaleId());
        String result = "";
        result = result + "start date :" +rootSale.getStart().toString() + "==>" + sale.getStart().toString() + "\n";
        result = result + "end date :" + rootSale.getEnd().toString() + "==>" + sale.getEnd().toString() + "\n";
        result = result + "off percent :" + rootSale.getPercent() +"==>" + sale.getPercent() + "\n";
        result += "current products in the sale :\n";
        for (Product product : rootSale.getProducts()) {
            result += product.getName() + "\n";
        }
        result += "products after being edited :\n";
        for (Product product : sale.getProducts()) {
            result += product.getName() + "\n";
        }
        return result;
    }

    private static String convertSaleIdToRequestId(String requestId){
        return  "T34SR"+requestId.substring(4);
    }

    private static String convertRequestIdToSaleId(String productId){
        return  "T34S"+productId.substring(5);
    }




    @Override
    public String toString() {
        return "Sale{" +
                "offId='" + offId + '\'' +
                ", products=" + products +
                ", state=" + state +
                ", percent=" + percent +
                '}';
    }
}
