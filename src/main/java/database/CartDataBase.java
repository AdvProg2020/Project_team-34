package database;

import account.Account;
import account.Customer;
import cart.Cart;
import cart.ProductInCart;
import cart.ShippingInfo;
import discount.CodedDiscount;
import discount.Sale;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

import static database.DataBase.*;

/**
 * @author rpirayadi
 * @since 0.0.1
 */
public class CartDataBase {

    public static void createNewTable() {
        HashMap<String, String> content = new HashMap<>();

        content.put("identifier" , "String");
        content.put("ownerUsername" , "String");
        content.put("listOfProductInCartIds" , "String");
        content.put("countForEachProductIn" , "String");
        content.put("saleForEachProductIn" , "String");
        content.put("discountCode" , "String");
        content.put("shippingInfoId" , "String");

        DataBase.createNewTable("Carts", content);
    }

    public static void add(Cart cart) {
        if (DataBase.doesIdAlreadyExist("Carts", "identifier", cart.getIdentifier())) {
            return;
        }
        String sql = "INSERT into Carts (identifier, ownerUsername, listOfProductInCartIds, countForEachProductIn,saleForEachProductIn , discountCode, shippingInfoId) " +
                "VALUES (?,?, ? , ? , ? , ?,?)";
        try (PreparedStatement statement = DataBase.getConnection().prepareStatement(sql)) {

            statement.setString(1,cart.getIdentifier());
            if(cart.getOwner() == null)
                statement.setString(2,null);
            else {
                statement.setString(2,cart.getOwner().getUserName());
            }
            statement.setString(3, convertObjectToJsonString(convertProductInCartArrayListToStringArrayList(cart.getProductsIn())));
            statement.setString(4, convertObjectToJsonString(convertProductInCartIntegerHashMapToStringIntegerHashMap(cart.getProductInCount())));
            statement.setString(5, convertObjectToJsonString(convertProductInCartSaleHashMapToStringHashMap(cart.getProductInSale())) );
            if(cart.getCodedDiscount() == null){
                statement.setString(6,null);
            }
            else {
                statement.setString(6, cart.getCodedDiscount().getDiscountCode());
            }
            if(cart.getShippingInfo() == null){
                statement.setString(7, null);
            }
            else {
                statement.setString(7, cart.getShippingInfo().getIdentifier());
            }

            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public static ArrayList<String> convertProductInCartArrayListToStringArrayList(ArrayList<ProductInCart> productInCarts){
        ArrayList<String> stringArrayList = new ArrayList<>();
        for (ProductInCart eachProductInCart : productInCarts) {
            stringArrayList.add(eachProductInCart.getIdentifier());
        }
        return stringArrayList;
    }

    private static ArrayList<ProductInCart> convertStringArrayListToProductInCartArrayList(ArrayList<String> stringArrayList){
        ArrayList<ProductInCart> productInCartArrayList = new ArrayList<>();
        for (String eachId : stringArrayList) {
            productInCartArrayList.add(ProductInCart.getProductInCartByIdentifier(eachId));
        }
        return productInCartArrayList;
    }

    private static HashMap<String,Integer> convertProductInCartIntegerHashMapToStringIntegerHashMap (HashMap<ProductInCart, Integer> productInCartIntegerHashMap){
        HashMap<String,Integer> stringIntegerHashMap = new HashMap<>();
        for (ProductInCart eachProductInCart : productInCartIntegerHashMap.keySet()) {
            stringIntegerHashMap.put(eachProductInCart.getIdentifier(),productInCartIntegerHashMap.get(eachProductInCart));
        }
        return stringIntegerHashMap;
    }

    private static HashMap<ProductInCart, Integer> convertStringIntegerHashMapToProductInCartIntegerHashMap (HashMap<String,Integer> stringIntegerHashMap){
        HashMap<ProductInCart, Integer> productInCartIntegerHashMap = new HashMap<>();
        for (String eachIdentifier : stringIntegerHashMap.keySet()) {
            productInCartIntegerHashMap.put(ProductInCart.getProductInCartByIdentifier(eachIdentifier),stringIntegerHashMap.get(eachIdentifier));
        }
        return productInCartIntegerHashMap;
    }

    private static HashMap<String,String> convertProductInCartSaleHashMapToStringHashMap (HashMap<ProductInCart, Sale> productInCartSaleHashMap){
        HashMap<String , String> stringHashMap = new HashMap<>();
        for (ProductInCart eachProductInCart : productInCartSaleHashMap.keySet()) {
            Sale sale = productInCartSaleHashMap.get(eachProductInCart);
            String saleId;
            if(sale == null){
               saleId = null;
            }
            else {
                saleId = sale.getOffId();
            }
            stringHashMap.put(eachProductInCart.getIdentifier(),saleId);
        }
        return stringHashMap;
    }

    private static HashMap<ProductInCart, Sale> convertStringHashMapToProductInCartSaleHashMap (HashMap<String, String> stringHashMap){
        HashMap<ProductInCart, Sale> productInCartSaleHashMap = new HashMap<>();
        for (String eachIdentifier : stringHashMap.keySet()) {
            productInCartSaleHashMap.put(ProductInCart.getProductInCartByIdentifier(eachIdentifier),Sale.getSaleById(stringHashMap.get(eachIdentifier)));
        }
        return productInCartSaleHashMap;
    }


    public static void update(Cart cart) {
        delete(cart.getIdentifier());
        add(cart);
    }

    public static void delete(String cartId) {
        DataBase.delete("Carts", "identifier", cartId);
    }

    public static void importAllCarts() {
        String sql = "SELECT *  FROM Carts";

        try (Statement statement = DataBase.getConnection().createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                String cartId = resultSet.getString("identifier");
                Customer owner = (Customer) (Account.getAccountByUsername(resultSet.getString("ownerUsername")));
                ArrayList<ProductInCart> productInCarts = convertStringArrayListToProductInCartArrayList(convertJsonToArrayList(resultSet.getString("listOfProductInCartIds")));
                HashMap<ProductInCart, Integer> productInCount = convertStringIntegerHashMapToProductInCartIntegerHashMap(convertJsonToHashMap(resultSet.getString("countForEachProductIn")));
                HashMap<ProductInCart, Sale> productInSale = convertStringHashMapToProductInCartSaleHashMap(convertJsonToStringStringHashMap(resultSet.getString("saleForEachProductIn")));
                CodedDiscount codedDiscount = CodedDiscount.getCodedDiscountByCode(resultSet.getString("discountCode"));
                ShippingInfo shippingInfo = ShippingInfo.getShippingInfoByIdentifier(resultSet.getString("shippingInfoId"));

               new Cart(cartId,owner,productInCarts,productInCount,productInSale,codedDiscount,shippingInfo);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


}
