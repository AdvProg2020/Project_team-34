package controller;

import account.Customer;
import account.Supplier;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import communications.ControllerSource;
import communications.Utils;
import discount.CodedDiscount;
import discount.Sale;
import exceptionalMassage.ExceptionalMassage;
import product.Product;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class OffController {
    private final Controller mainController;

    public OffController(Controller mainController) {
        this.mainController = mainController;
    }

    public JsonElement communication(String function, JsonArray inputs) throws ExceptionalMassage {
        return new JsonParser().parse(mainController.
                communication(function, inputs, ControllerSource.OFF_CONTROLLER).getContent());
    }

    public ArrayList<CodedDiscount> controlGetAllCodedDiscounts() throws ExceptionalMassage {
        JsonElement arrayList = communication("controlGetAllCodedDiscounts", new JsonArray());
        return Utils.convertJsonElementToCodedDiscountArrayList(arrayList);
    }

    public CodedDiscount controlGetDiscountByCode(String code) throws ExceptionalMassage {
        JsonArray inputs = new JsonArray();
        inputs.add(code);
        return CodedDiscount.convertJsonStringToCodedDiscount(communication("controlGetDiscountByCode",
                inputs).toString());
    }

    public void controlEditDiscountByCode(String code, Date newStartDate, Date newEndDate, int newPercent,
                                          int newMaxDiscount) throws ExceptionalMassage {
        JsonArray inputs = jsonArrayFor(code, newStartDate, newEndDate, newPercent, newMaxDiscount);
        communication("controlEditDiscountByCode", inputs);
    }

    public void controlCreateCodedDiscount(String code, Date startDate, Date endDate, int percent, int maxDiscountAmount,
                                           HashMap<Customer, Integer> maxNumberOfUsage) throws ExceptionalMassage {
        JsonArray inputs = new JsonArray();
        inputs.add(code);
        inputs.add(String.valueOf(startDate.getTime()));
        inputs.add(String.valueOf(endDate.getTime()));
        inputs.add(String.valueOf(percent));
        inputs.add(String.valueOf(maxDiscountAmount));
        HashMap<String, String> idToMaxNumberOfUsage = new HashMap<>();
        for (Customer customer : maxNumberOfUsage.keySet()) {
            idToMaxNumberOfUsage.put(customer.getUserName(), String.valueOf(maxNumberOfUsage.get(customer)));
        }
        inputs.add(Utils.convertStringToStringHashMapToJsonElement(idToMaxNumberOfUsage).toString());
        communication("controlCreateCodedDiscount", inputs);
    }

    public void controlRemoveDiscountCode(String code) throws ExceptionalMassage {
        JsonArray inputs = new JsonArray();
        inputs.add(code);
        communication("controlRemoveDiscountCode", inputs);
    }

    public void controlCreateSale(Date startDate, Date endDate, int percent, ArrayList<Product> products)
            throws ExceptionalMassage {
        JsonArray inputs = new JsonArray();
        inputs.add(String.valueOf(startDate.getTime()));
        inputs.add(String.valueOf(endDate.getTime()));
        inputs.add(String.valueOf(percent));
        ArrayList<String> productIds = new ArrayList<>();
        for (Product product : products) {
            productIds.add(product.getProductId());
        }
        inputs.add(Utils.convertStringArrayListToJsonElement(productIds).toString());
        communication("controlCreateSale", inputs);
    }

    public ArrayList<Sale> controlGetAllSales() throws ExceptionalMassage {
        //check name
        return Utils.convertJsonElementToSaleArrayList(communication("controlGetAllSales", new JsonArray()));
    }

    public Sale controlGetSaleById(String id) throws ExceptionalMassage {
        JsonArray inputs = new JsonArray();
        inputs.add(id);
        return Sale.convertJsonStringToSale(communication("controlGetSaleById", inputs).getAsString());
    }

    public void controlEditSaleById(String id, Date newEndDate, Date newStartDate, int newPercent, ArrayList<Product> addingProduct,
                                    ArrayList<Product> removingProduct) throws ExceptionalMassage {
        //check inputs
        JsonArray inputs = new JsonArray();
        inputs.add(id);
        inputs.add(String.valueOf(newEndDate.getTime()));
        inputs.add(String.valueOf(newStartDate.getTime()));
        inputs.add(String.valueOf(newPercent));
        ArrayList<String> addingProductIds = new ArrayList<>();
        ArrayList<String> removingProductIds = new ArrayList<>();
        for (Product product : addingProduct) {
            addingProductIds.add(product.getProductId());
        }
        for (Product product : removingProduct) {
            removingProductIds.add(product.getProductId());
        }
        inputs.add(Utils.convertStringArrayListToJsonElement(addingProductIds).toString());
        inputs.add(Utils.convertStringArrayListToJsonElement(removingProductIds).toString());
        communication("controlEditSaleById", inputs);
    }

    public void controlRemoveSaleById(String id) throws ExceptionalMassage {
        JsonArray inputs = new JsonArray();
        inputs.add(id);
        communication("controlRemoveSaleById", inputs);
    }

    public ArrayList<CodedDiscount> controlGetCodedDiscountByCustomer() throws ExceptionalMassage {
        return Utils.convertJsonElementToCodedDiscountArrayList(
                communication("controlGetCodedDiscountByCustomer", new JsonArray()));
    }

    public int controlGetRemainedNumberInCodedDiscountForCustomer(CodedDiscount codedDiscount) throws ExceptionalMassage {
        JsonArray inputs = new JsonArray();
        inputs.add(codedDiscount.getDiscountCode());
        return communication("controlGetRemainedNumberInCodedDiscountForCustomer", inputs).getAsInt();
    }

    public ArrayList<Sale> getAllSaleRequestsIdForThisSupplier() throws ExceptionalMassage {
        return Utils.convertJsonElementToSaleArrayList(communication("getAllSaleRequestsIdForThisSupplier",
                new JsonArray()));
    }

    public int controlGetPriceForEachProductAfterSale(Product product, Supplier supplier) throws ExceptionalMassage {
        JsonArray inputs = new JsonArray();
        inputs.add(product.getProductId());
        inputs.add(supplier.getUserName());
        return communication("controlGetPriceForEachProductAfterSale", inputs).getAsInt();
    }

    public void removeCodedDiscount(CodedDiscount codedDiscount) throws ExceptionalMassage {
        JsonArray inputs = new JsonArray();
        inputs.add(codedDiscount.getDiscountCode());
        communication("removeCodedDiscount", inputs);
    }

    private JsonArray jsonArrayFor(String code, Date startDate, Date endDate, int percent, int maxDiscountAmount) {
        JsonArray inputs = new JsonArray();
        inputs.add(code);
        inputs.add(String.valueOf(startDate.getTime()));
        inputs.add(String.valueOf(endDate.getTime()));
        inputs.add(String.valueOf(percent));
        inputs.add(String.valueOf(maxDiscountAmount));
        return inputs;
    }

    public boolean isProductHasAnySale(Product product) throws ExceptionalMassage{
        JsonArray inputs = new JsonArray();
        inputs.add(product.getProductId());
        return communication("isProductHasAnySale", inputs).getAsBoolean();
    }

    public boolean isProductInThisSuppliersSale(Product product, Supplier supplier) throws ExceptionalMassage{
        JsonArray inputs = new JsonArray();
        inputs.add(product.getProductId());
        inputs.add(supplier.getUserName());
        return communication("isProductInThisSuppliersSale", inputs).getAsBoolean();
    }

    public Sale getProductSale(Product product, Supplier supplier) throws ExceptionalMassage {
        JsonArray inputs = new JsonArray();
        inputs.add(product.getProductId());
        inputs.add(supplier.getUserName());
        return Sale.convertJsonStringToSale(communication("getProductSale",inputs).toString());
    }

    public Sale controlGetMaxSaleForThisProduct(Product product) throws ExceptionalMassage {
        JsonArray inputs = new JsonArray();
        inputs.add(product.getProductId());
        return  Sale.convertJsonStringToSale(communication("controlGetMaxSaleForThisProduct", inputs).toString());
    }

    public ArrayList<Product> controlGetNotSaleProductsForSupplier(String saleId) throws ExceptionalMassage{
        JsonArray inputs = new JsonArray();
        inputs.add(saleId);
        return Utils.convertJsonElementToProductArrayList(communication("controlGetNotSaleProductsForSupplier",inputs));
    }
}
