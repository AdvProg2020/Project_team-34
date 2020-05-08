package request;

import discount.Sale;
import product.Product;
import state.State;

import java.util.ArrayList;

/**
 * @author soheil
 * @since 0.01
 */

public class SaleRequest extends Request{
    private Sale oldSale;
    private Sale newSale;

    public SaleRequest(Sale oldSale, Sale newSale) {
        super(null);
        this.oldSale = oldSale;
        this.newSale = newSale;
    }
    //Getters:

    public Sale getOldSale() {
        return oldSale;
    }

    public Sale getNewSale() {
        return newSale;
    }



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
                newSale.setState(State.CONFIRMED);
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
                '}';
    }
}
