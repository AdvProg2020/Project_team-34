package controller;

import account.Account;
import account.Customer;
import account.Supplier;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import discount.CodedDiscount;
import discount.Sale;
import exceptionalMassage.ExceptionalMassage;
import jdk.jshell.execution.Util;
import product.Product;
import server.communications.RequestStatus;
import server.communications.Response;
import server.communications.Utils;
import state.State;

import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class OffController {
    private final Controller mainController;

    public OffController(Controller mainController) {
        this.mainController = mainController;
    }

    public Response controlGetAllCodedDiscounts() {
        return new Response(RequestStatus.SUCCESSFUL, Utils.convertCodedDiscountArrayListToJsonElement(CodedDiscount.getCodedDiscounts()).toString());
    }

    private CodedDiscount controlInternalGetDiscountByCode(String code) {
        for (CodedDiscount codedDiscount : CodedDiscount.getCodedDiscounts()) {
            if (codedDiscount.getDiscountCode().equals(code)) {
                return codedDiscount;
            }
        }
        return null;
    }

    public Response controlGetDiscountByCode(String code) {
        CodedDiscount codedDiscount = controlInternalGetDiscountByCode(code);
        if (codedDiscount == null) {
            return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, "no such code!");
        }
        return new Response(RequestStatus.SUCCESSFUL, Utils.convertObjectToJsonString(codedDiscount));
    }

    public Response controlEditDiscountByCode(String code, String newStartDateString, String newEndDateString,
                                              String newPercentString, String newMaxDiscountString) {
        Date newStartDate = new Date(Long.parseLong(newStartDateString));
        Date newEndDate = new Date(Long.parseLong(newEndDateString));
        int newPercent = Integer.parseInt(newPercentString);
        int newMaxDiscount = Integer.parseInt(newMaxDiscountString);
        CodedDiscount codedDiscount = controlInternalGetDiscountByCode(code);
        if (codedDiscount == null) {
            return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, "No CodedDiscount with this Code");
        }
        codedDiscount.setStart(newStartDate);
        codedDiscount.setEnd(newEndDate);
        codedDiscount.setPercent(newPercent);
        codedDiscount.setMaxDiscountAmount(newMaxDiscount);
        return new Response(RequestStatus.SUCCESSFUL, "");
    }

    public Response controlCreateCodedDiscount(String code, String startDateStr, String endDateStr, String percentStr,
                                           String maxDiscountAmountStr, String maxNumberOfUsageStr) {
        Date startDate = new Date(Long.parseLong(startDateStr));
        Date endDate = new Date(Long.parseLong(endDateStr));
        int percent = Integer.parseInt(percentStr);
        int maxDiscountAmount = Integer.parseInt(maxDiscountAmountStr);
        HashMap<Customer, Integer> maxNumberOfUsage = new HashMap<>();
        HashMap<String, String> idToMax = Utils.convertJsonElementStringToStringToHashMap(new JsonParser().parse(maxNumberOfUsageStr));
        for (String s : idToMax.keySet()) {
            maxNumberOfUsage.put((Customer) Account.getAccountByUsernameWithinAvailable(s),Integer.parseInt(idToMax.get(s)));
        }
        if (maxNumberOfUsage.size() == 0) {
            return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, "Select at Least one customer, please!");
        }
        new CodedDiscount(code, startDate, endDate, percent, maxDiscountAmount, maxNumberOfUsage);
        return new Response(RequestStatus.SUCCESSFUL, "");
    }

    public Response controlRemoveDiscountCode(String code) {
        CodedDiscount codedDiscount = controlInternalGetDiscountByCode(code);
        if (codedDiscount == null) {
            return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, "No such code!");
        }
        CodedDiscount.removeCodeFromList(codedDiscount);
        return new Response(RequestStatus.SUCCESSFUL, "");
    }

    public Response controlCreateSale(String startDateString, String endDateString, String percentString,
                                      String productIds) {
        Date startDate = new Date(Long.parseLong(startDateString));
        Date endDate = new Date(Long.parseLong(endDateString));
        int percent = Integer.parseInt(percentString);
        ArrayList<Product> products = Utils.convertProductIdArrayListToProductArrayList(Utils.convertJsonElementToStringArrayList((new JsonParser()).parse(productIds)));
        if (products.size() == 0) {
            return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, "Select at least one product!");
        }
        Sale newSale = new Sale((Supplier) mainController.getAccount(), startDate, endDate, percent, null);
        for (Product product : products) {
            newSale.addProductToSale(product);
        }
        newSale.setState(State.PREPARING_TO_BUILD);
        return new Response(RequestStatus.SUCCESSFUL, "");
    }

    public Response controlGetAllSales() {
        ArrayList<Sale> allSalesForThisSupplier = new ArrayList<>();
        for (Sale sale : Sale.getSales()) {
            if (sale.getSupplier().equals(mainController.getAccount()) && sale.getState() == State.CONFIRMED) {
                allSalesForThisSupplier.add(sale);
            }
        }
        return new Response(RequestStatus.SUCCESSFUL, Utils.convertSaleArrayListToJsonElement(allSalesForThisSupplier).toString());
    }

    private Sale controlInternalGetSaleById(String id) {
        for (Sale sale : Sale.getSales()) {
            if (sale.getOffId().equals(id)) {
                return sale;
            }
        }
        return null;
    }

    public Response controlGetSaleById(String id) {
        return new Response(RequestStatus.SUCCESSFUL, Utils.convertObjectToJsonString(controlInternalGetSaleById(id)));
    }

    public Response controlEditSaleById(String id, String newEndDateStr, String newStartDateStr, String newPercentStr,
                                        String addingProductStr, String removingProductStr) {
        Date newEndDate = new Date(Long.parseLong(newEndDateStr));
        Date newStartDate = new Date(Long.parseLong(newStartDateStr));
        int newPercent = Integer.parseInt(newPercentStr);
        ArrayList<Product> addingProduct = Utils.convertProductIdArrayListToProductArrayList(Utils.convertJsonElementToStringArrayList(new JsonParser().
                parse(addingProductStr)));
        ArrayList<Product> removingProduct = Utils.convertProductIdArrayListToProductArrayList(Utils.convertJsonElementToStringArrayList(new JsonParser().
                parse(removingProductStr)));
        Sale sale = controlInternalGetSaleById(id);
        if (sale == null) {
            return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, "No such sale with this code!");
        }
        Sale newSale = new Sale((Supplier) mainController.getAccount(), newStartDate, newEndDate, newPercent, id);
        for (Product product : sale.getProducts()) {
            newSale.addProductToSale(product);
        }
        for (Product product : addingProduct) {
            newSale.addProductToSale(product);
        }
        for (Product product : removingProduct) {
            newSale.removeProductFromSale(product);
        }
        return new Response(RequestStatus.SUCCESSFUL, "");
    }

    public Response controlRemoveSaleById(String id) {
        Sale sale = controlInternalGetSaleById(id);
        if (sale == null) {
            return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, "No such sale with id!");
        }
        Sale.getSales().remove(sale);
        return new Response(RequestStatus.SUCCESSFUL, "");
    }

    public Response controlGetCodedDiscountByCustomer() {
        ArrayList<CodedDiscount> codedDiscounts = new ArrayList<>();
        for (CodedDiscount codedDiscount : CodedDiscount.getCodedDiscounts()) {
            if (codedDiscount.getCustomers().contains((Customer) mainController.getAccount())) {
                codedDiscounts.add(codedDiscount);
            }
        }
        return new Response(RequestStatus.SUCCESSFUL, Utils.convertCodedDiscountArrayListToJsonElement(codedDiscounts).
                toString());
    }

    public Response controlGetRemainedNumberInCodedDiscountForCustomer(String codedDiscountCode) {
        CodedDiscount codedDiscount = CodedDiscount.getCodedDiscountByCode(codedDiscountCode);
        if (!(mainController.getAccount() instanceof Customer)) {
            return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, "Sign in as a Customer");
        }
        return new Response(RequestStatus.SUCCESSFUL, Utils.convertObjectToJsonString(codedDiscount.
                getRemainedNumberByCustomer((Customer) mainController.getAccount())));
    }

    public Response getAllSaleRequestsIdForThisSupplier() {
        return new Response(RequestStatus.SUCCESSFUL, Utils.convertSaleArrayListToJsonElement(Sale.
                getAllSaleRequestsBySupplier((Supplier) mainController.getAccount())).toString());
    }

    public Response controlGetPriceForEachProductAfterSale(String productId, String supplierUsername) {
        Product product = Product.getProductById(productId);
        Supplier supplier = (Supplier) Account.getAccountByUsernameWithinAvailable(supplierUsername);
        Sale sale = Sale.getProductSale(product, supplier);
        int price = product.getPrice(supplier);
        if (sale != null) {
            int percent = sale.getPercent();
            price = product.getPrice(supplier) * (100 - percent) / 100;
        }
        return new Response(RequestStatus.SUCCESSFUL, Utils.convertObjectToJsonString(price));
    }

    public Response removeCodedDiscount(String codedDiscountId) {
        CodedDiscount codedDiscount = CodedDiscount.getCodedDiscountByCode(codedDiscountId);
        CodedDiscount.removeCodeFromList(codedDiscount);
        return new Response(RequestStatus.SUCCESSFUL, "");
    }

    public Response isProductHasAnySale(String productId){
        Product product = Product.getProductById(productId);
        return new Response(RequestStatus.SUCCESSFUL,Utils.convertObjectToJsonString(String.valueOf(Sale.
                isProductHasAnySale(product))));
    }

    public Response getProductSale(String productId,String supplierUsername){
        Product product = Product.getProductById(productId);
        Supplier supplier = (Supplier) Account.getAccountByUsernameWithinAvailable(supplierUsername);
        return new Response(RequestStatus.SUCCESSFUL,Utils.convertObjectToJsonString(Sale.getProductSale(product,supplier)));
    }

    public Response controlGetMaxSaleForThisProduct(String productId){
        Product product = Product.getProductById(productId);
        Sale sale = Sale.getMaxSaleForThisProduct(product);
        return new Response(RequestStatus.SUCCESSFUL,Utils.convertObjectToJsonString(sale));
    }

    public Response isProductInThisSuppliersSale(String productId,String supplierUsername){
        Product product = Product.getProductById(productId);
        Supplier supplier = (Supplier) Account.getAccountByUsernameWithinAvailable(supplierUsername);
        return new Response(RequestStatus.SUCCESSFUL, String.valueOf(Sale.isProductInThisSuppliersSale(product,supplier)));
    }

    public Response controlGetNotSaleProductsForSupplier(String saleId){
        Sale sale = Sale.getSaleById(saleId);
        if(sale == null){
            return Response.createResponseFromExceptionalMassage(new ExceptionalMassage("Sale doesn't exist!"));
        }
        ArrayList<Product> suppliersProduct = Product.getProductForSupplier((Supplier) mainController.getAccount());
        ArrayList<Product> notSaleProducts = new ArrayList<>();
        for (Product product : suppliersProduct) {
            if(!sale.getProducts().contains(product)){
                notSaleProducts.add(product);
            }
        }
        return new Response(RequestStatus.SUCCESSFUL, Utils.convertProductArrayListToJsonElement(notSaleProducts).toString());
    }
}
