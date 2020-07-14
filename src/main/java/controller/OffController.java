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
        JsonArray jsonArray = communication("controlGetAllCodedDiscounts", new JsonArray()).getAsJsonArray();
        ArrayList<CodedDiscount> codedDiscounts = new ArrayList<>();
        for (JsonElement jsonElement : jsonArray) {
            codedDiscounts.add(CodedDiscount.convertJsonStringToCodedDiscount(jsonElement.getAsString()));
        }
        return codedDiscounts;
    }

    public CodedDiscount controlGetDiscountByCode(String code) throws ExceptionalMassage {
        JsonArray inputs = new JsonArray();
        inputs.add(code);
        return CodedDiscount.convertJsonStringToCodedDiscount(communication("controlGetDiscountByCode",
                inputs).getAsString());
    }

    public void controlEditDiscountByCode(String code, Date newStartDate, Date newEndDate, int newPercent,
                                          int newMaxDiscount) throws ExceptionalMassage {
        JsonArray inputs = jsonArrayFor(code, newStartDate, newEndDate, newPercent, newMaxDiscount);
        communication("controlEditDiscountByCode", inputs);
    }

    public void controlCreateCodedDiscount(String code, Date startDate, Date endDate, int percent, int maxDiscountAmount,
                                           HashMap<Customer, Integer> maxNumberOfUsage) throws ExceptionalMassage {
        JsonArray inputs = jsonArrayFor(code, startDate, endDate, percent, maxDiscountAmount);
        JsonObject jsonHashmap = new JsonObject();
        for (Customer customer : maxNumberOfUsage.keySet()) {
            jsonHashmap.add(Utils.convertObjectToJsonString(customer),
                    new JsonParser().parse(Utils.convertObjectToJsonString(maxNumberOfUsage.get(customer))));
        }
        inputs.add(jsonHashmap);
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
        inputs.add(Utils.convertProductArrayListToJsonElement(products));
        communication("controlCreateSale", inputs);
    }

    public ArrayList<Sale> controlGetAllSales() throws ExceptionalMassage {
        //check name
        return Utils.convertJasonElementToSaleArrayList(communication("controlGetAllSales", new JsonArray()));
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
        inputs.add(Utils.convertProductArrayListToJsonElement(addingProduct));
        inputs.add(Utils.convertProductArrayListToJsonElement(removingProduct));
        communication("controlEditSaleById", inputs);
    }

    public void controlRemoveSaleById(String id) throws ExceptionalMassage {
        JsonArray inputs = new JsonArray();
        inputs.add(id);
        communication("controlRemoveSaleById", inputs);
    }

    public ArrayList<CodedDiscount> controlGetCodedDiscountByCustomer() throws ExceptionalMassage {
        return Utils.convertJasonElementToCodedDiscountArrayList(
                communication("controlGetCodedDiscountByCustomer", new JsonArray()));
    }

    public int controlGetRemainedNumberInCodedDiscountForCustomer(CodedDiscount codedDiscount) throws ExceptionalMassage {
        JsonArray inputs = new JsonArray();
        inputs.add(Utils.convertObjectToJsonString(codedDiscount));
        return communication("controlGetRemainedNumberInCodedDiscountForCustomer", inputs).getAsInt();
    }

    public ArrayList<Sale> getAllSaleRequestsIdForThisSupplier() throws ExceptionalMassage {
        return Utils.convertJasonElementToSaleArrayList(communication("getAllSaleRequestsIdForThisSupplier",
                new JsonArray()));
    }

    public int controlGetPriceForEachProductAfterSale(Product product, Supplier supplier) throws ExceptionalMassage {
        JsonArray inputs = new JsonArray();
        inputs.add(Utils.convertObjectToJsonString(product));
        inputs.add(Utils.convertObjectToJsonString(supplier));
        return communication("controlGetPriceForEachProductAfterSale", inputs).getAsInt();
    }

    public void removeCodedDiscount(CodedDiscount codedDiscount) throws ExceptionalMassage {
        JsonArray inputs = new JsonArray();
        inputs.add(Utils.convertObjectToJsonString(codedDiscount));
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
}
