package request;

import discount.Sale;
import product.Product;

import java.util.ArrayList;

/**
 * @author soheil
 * @since 0.01
 */

public class SaleRequest extends Request{
    private Sale oldSale;
    private Sale newSale;
    private ArrayList<Product> addingProducts;
    private ArrayList<Product> removingProducts;

    public SaleRequest(Sale oldSale, Sale newSale,
                       ArrayList<Product> addingProducts, ArrayList<Product> removingProducts) {
        super(null);
        this.oldSale = oldSale;
        this.newSale = newSale;
        this.addingProducts = addingProducts;
        this.removingProducts = removingProducts;
    }
    //Getters:

    public Sale getOldSale() {
        return oldSale;
    }

    public Sale getNewSale() {
        return newSale;
    }

    public ArrayList<Product> getAddingProducts() {
        return addingProducts;
    }

    public ArrayList<Product> getRemovingProducts() {
        return removingProducts;
    }
    //methods

    public boolean isThisEditRequest(){
        if(oldSale == null){
            return false;
        }
        return true;
    }

    public static String generateRequestId() {
        return null;
    }

    @Override
    public void doRequest(boolean acceptedOrNot) {
        if(acceptedOrNot){
            if(isThisEditRequest()){
                Sale.removeSale(oldSale);
            }
            for (Product addingProduct : addingProducts) {
                newSale.addProductToSale(addingProduct);
            }
            for (Product removingProduct : removingProducts) {
                newSale.removeProductFromSale(removingProduct);
            }
        } else {
            Sale.removeSale(newSale);
        }
    }



    /**
     *
     * @return String form of a JSON object for storing in the data base.
     */
    @Override
    public String toString() {
        return "SaleRequest{" +
                ", oldSale=" + oldSale +
                ", newSale=" + newSale +
                ", addingProducts=" + addingProducts +
                ", removingProducts=" + removingProducts +
                '}';
    }
}
