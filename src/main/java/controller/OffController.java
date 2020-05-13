package controller;

import account.Account;
import account.Customer;
import discount.CodedDiscount;
import discount.Sale;
import exceptionalMassage.ExceptionalMassage;
import product.Product;
import request.SaleRequest;

import java.util.ArrayList;
import java.util.Date;

public class OffController {

    private Controller mainController;

    public OffController(Controller mainController) {
        this.mainController = mainController;
    }

    public void controlSubmitDiscountCode(String discountCode) throws ExceptionalMassage {
        Account account = mainController.getAccount();
        if (account == null)
            throw new ExceptionalMassage("Login First.");
        if (!(account instanceof Customer))
            throw new ExceptionalMassage("Login as a customer.");
        mainController.getCart().applyCodedDiscount(discountCode);
    }

    public void controlRemoveDiscountCode() throws ExceptionalMassage {
        Account account = mainController.getAccount();
        if (account == null)
            throw new ExceptionalMassage("Login First.");
        if (!(account instanceof Customer))
            throw new ExceptionalMassage("Login as a customer.");
        mainController.getCart().removeCodedDiscount();
    }
    public ArrayList<CodedDiscount> controlGetAllCodedDiscounts(){
        return CodedDiscount.getCodedDiscounts();
    }

    public CodedDiscount controlGetDiscountByCode(String code) {
        for (CodedDiscount codedDiscount : CodedDiscount.getCodedDiscounts()) {
            if(codedDiscount.getDiscountCode().equals(code)){
                return codedDiscount;
            }
        }
        return null;
    }

    public void controlEditDiscountByCode (String code, Date newStartDate, Date newEndDate, int newPercent, int newMaxDiscount) throws ExceptionalMassage{
        if(controlGetDiscountByCode(code) == null){
            throw new ExceptionalMassage("No CodedDiscount with this Code");
        }
        controlGetDiscountByCode(code).setStart(newStartDate);
        controlGetDiscountByCode(code).setEnd(newEndDate);
        controlGetDiscountByCode(code).setPercent(newPercent);
        controlGetDiscountByCode(code).setMaxDiscountAmount(newMaxDiscount);
    }

    public void controlCreateCodedDiscount(String code,Date startDate, Date endDate, int percent, int maxDiscountAmount) throws ExceptionalMassage {
        for (CodedDiscount codedDiscount : controlGetAllCodedDiscounts()) {
            if(codedDiscount.getDiscountCode().equals(code)){
                throw new ExceptionalMassage("Code already exists!");
            }
        }
        new CodedDiscount(code,startDate, endDate, percent, maxDiscountAmount);
    }

    public void controlRemoveDiscountCode(String code) throws ExceptionalMassage{
        if(controlGetDiscountByCode(code) == null){
            throw new ExceptionalMassage("No such code!");
        }
        CodedDiscount.removeCodeFromList(controlGetDiscountByCode(code));
    }

    public void controlCreateSale(Date startDate, Date endDate, int percent, ArrayList<Product> products){
        Sale newSale = new Sale(startDate,endDate,percent);
        for (Product product : products) {
            newSale.addProductToSale(product);
        }
        new SaleRequest(null, newSale);
    }

    public ArrayList<Sale> controlGetAllSales(){
        return Sale.getSales();
    }

    public Sale controlGetSaleById(String id){
        for (Sale sale : Sale.getSales()) {
            if(sale.getOffId().equals(id)){
                return sale;
            }
        }
        return null;
    }

    public void controlEditSaleById(String id, Date newEndDate, Date newStartDate, int newPercent, ArrayList<Product> addingProduct, ArrayList<Product> removingProduct) throws ExceptionalMassage{
        if(controlGetSaleById(id) == null){
            throw new ExceptionalMassage("No such sale with this code!");
        }
        Sale newSale = new Sale(newStartDate,newEndDate,newPercent,id);
        for (Product product : controlGetSaleById(id).getProducts()) {
            newSale.addProductToSale(product);
        }
        for (Product product : addingProduct) {
            newSale.addProductToSale(product);
        }
        for (Product product : removingProduct) {
            newSale.removeProductFromSale(product);
        }
        new SaleRequest(controlGetSaleById(id),newSale);
    }

    public void controlRemoveSaleById(String id) throws ExceptionalMassage{
        if(controlGetSaleById(id) == null){
            throw new ExceptionalMassage("No such sale with id!");
        }
        Sale.getSales().remove(controlGetSaleById(id));
    }

    public ArrayList<CodedDiscount> controlGetCodedDiscountByCustomer(){
        ArrayList<CodedDiscount> codedDiscounts = new ArrayList<>();
        for (CodedDiscount codedDiscount : CodedDiscount.getCodedDiscounts()) {
            if(codedDiscount.getCustomers().contains((Customer)mainController.getAccount())){
                codedDiscounts.add(codedDiscount);
            }
        }
        return codedDiscounts;
    }
}
