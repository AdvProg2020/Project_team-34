package discount;

import account.Supplier;
import database.ProductDataBase;
import database.SaleDataBase;
import exceptionalMassage.ExceptionalMassage;
import product.Product;
import state.State;

import java.util.ArrayList;
import java.util.Date;

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
        SaleDataBase.update(this);
    }

    public void setState(State state) {
        this.state = state;
        SaleDataBase.update(this);
    }

    public void removeProductFromSale(Product product){
        products.remove(product);
        SaleDataBase.update(this);
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
        for (Sale sale : sales) {
            if (sale.isProductInSale(product) && sale.getSupplier() == supplier && sale.isSaleActive()) {
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
        String result = "";
        if(Sale.getSaleById(convertRequestIdToSaleId(requestId)) == null){
            throw new ExceptionalMassage("No such id!");
        }
        Sale sale = Sale.getSaleById(convertRequestIdToSaleId(requestId));
        if(sale.getState() == State.PREPARING_TO_BUILD){
            result += "This Sale wants to be created!\n";
            result += "sale info :\n";
            result += "start date :" + sale.getStart().toString() + "\n";
            result += "end date :" + sale.getEnd().toString() + "\n";
            result = result + "off percent :" + sale.getPercent() + "\n";
            result += "products:\n";
            for (Product product : sale.getProducts()) {
                result += product.getName() + "\n";
            }
        } else {
            Sale rootSale = Sale.getSaleById(sale.getRootSaleId());
            result = result + "start date :" + rootSale.getStart().toString() + "==>" + sale.getStart().toString() + "\n";
            result = result + "end date :" + rootSale.getEnd().toString() + "==>" + sale.getEnd().toString() + "\n";
            result = result + "off percent :" + rootSale.getPercent() + "==>" + sale.getPercent() + "\n";
            result += "current products in the sale :\n";
            for (Product product : rootSale.getProducts()) {
                result += product.getName() + "\n";
            }
            result += "products after being edited :\n";
            for (Product product : sale.getProducts()) {
                result += product.getName() + "\n";
            }
        }
        return result;
    }

    private static String convertSaleIdToRequestId(String requestId){
        return  "T34SR"+requestId.substring(4);
    }

    private static String convertRequestIdToSaleId(String productId){
        return  "T34S"+productId.substring(5);
    }

    public static void acceptOrDeclineRequest(String requestId, boolean isAccepted) throws  ExceptionalMassage{
        Sale saleRequest = Sale.getSaleById(convertRequestIdToSaleId(requestId));
        if(saleRequest == null)
            throw new ExceptionalMassage("Invalid request identifier");
        if(!isAccepted) {
            sales.remove(saleRequest);
            ProductDataBase.delete(saleRequest.getOffId());
            return;
        }
        if(saleRequest.getState() == State.PREPARING_TO_BUILD) {
            saleRequest.setState(State.CONFIRMED);
            SaleDataBase.update(saleRequest);
        }
        else {
            setRequestValuesInRealSale(saleRequest);
            sales.remove(saleRequest);
            ProductDataBase.delete(saleRequest.getOffId());
            SaleDataBase.update(Sale.getSaleById(saleRequest.getRootSaleId()));
        }

    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    private static void setRequestValuesInRealSale(Sale saleRequest){
        Sale realSale = Sale.getSaleById(saleRequest.getRootSaleId());
        realSale.setStart(saleRequest.getStart());
        realSale.setEnd(saleRequest.getEnd());
        realSale.setPercent(saleRequest.getPercent());
        realSale.setProducts(saleRequest.getProducts());

    }





    @Override
    public String toString() {
        return "offId=" + offId + "\n" +
                ", products=" + products +
                ", state=" + state +
                ", percent=" + percent +
                '}';
    }
}
