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
        if (doesCartAlreadyExists(cart)) {
            return;
        }
        String sql = "INSERT into Carts (identifier, ownerUsername, listOfProductInCartIds, countForEachProductIn,saleForEachProductIn , discountCode, shippingInfoId) " +
                "VALUES (?,?, ? , ? , ? , ?,?)";
        try (Connection connection = connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1,cart.getIdentifier());
            statement.setString(2,cart.getOwner().getUserName());
            statement.setString(3, convertObjectToJsonString(convertProductInCartArrayListToStringArrayList(cart.getProductsIn())));
            statement.setString(4, convertObjectToJsonString(convertProductInCartIntegerHashMapToStringIntegerHashMap(cart.getProductInCount())));
            statement.setString(5, convertObjectToJsonString(convertProductInCartSaleHashMapToStringHashMap(cart.getProductInSale())) );
            statement.setString(6, cart.getCodedDiscount().getDiscountCode());
            statement.setString(6, cart.getShippingInfo().getIdentifier());

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
            productInCartArrayList.add(ProductInCart.getProductInCartById(eachId));
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
            productInCartIntegerHashMap.put(ProductInCart.getProductInCartById(eachIdentifier),stringIntegerHashMap.get(eachIdentifier));
        }
        return productInCartIntegerHashMap;
    }

    private static HashMap<String,String> convertProductInCartSaleHashMapToStringHashMap (HashMap<ProductInCart, Sale> productInCartSaleHashMap){
        HashMap<String , String> stringHashMap = new HashMap<>();
        for (ProductInCart eachProductInCart : productInCartSaleHashMap.keySet()) {
            stringHashMap.put(eachProductInCart.getIdentifier(),productInCartSaleHashMap.get(eachProductInCart).getOffId());
        }
        return stringHashMap;
    }

    private static HashMap<ProductInCart, Sale> convertStringHashMapToProductInCartSaleHashMap (HashMap<String, String> stringHashMap){
        HashMap<ProductInCart, Sale> productInCartSaleHashMap = new HashMap<>();
        for (String eachIdentifier : stringHashMap.keySet()) {
            productInCartSaleHashMap.put(ProductInCart.getProductInCartById(eachIdentifier),Sale.getSaleById(stringHashMap.get(eachIdentifier)));
        }
        return productInCartSaleHashMap;
    }

    private static boolean doesCartAlreadyExists(Cart cart) {
        ArrayList<Cart> list = getAllCarts();
        if (list == null)
            return false;
        for (Cart eachCart : list) {
            if (eachCart.getIdentifier().equals(cart.getIdentifier()))
                return true;
        }
        return false;
    }

    public static void update(Cart cart) {
        delete(cart.getIdentifier());
        add(cart);
    }

    public static void delete(String cartId) {
        DataBase.delete("Carts", "identifier", cartId);
    }

    public static ArrayList<Cart> getAllCarts() {
        String sql = "SELECT *  FROM Carts";

        try (Connection connection = connect();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            ArrayList<Cart> carts = new ArrayList<>();
            while (resultSet.next()) {
                String cartId = resultSet.getString("identifier");
                Customer owner = (Customer) (Account.getAccountByUsername(resultSet.getString("ownerUsername")));
                ArrayList<ProductInCart> productInCarts = convertStringArrayListToProductInCartArrayList(convertJsonToArrayList(resultSet.getString("listOfProductInCartsId")));
                HashMap<ProductInCart, Integer> productInCount = convertStringIntegerHashMapToProductInCartIntegerHashMap(convertJsonToHashMap(resultSet.getString("countForEachProductIn")));
                HashMap<ProductInCart, Sale> productInSale = convertStringHashMapToProductInCartSaleHashMap(convertJsonToStringStringHashMap(resultSet.getString("saleForEachProductIn")));
                CodedDiscount codedDiscount = CodedDiscount.getCodedDiscountByCode(resultSet.getString("discountCode"));
                ShippingInfo shippingInfo = ShippingInfo.getShippingInfoById(resultSet.getString("shippingInfoId"));

                carts.add(new Cart(cartId,owner,productInCarts,productInCount,productInSale,codedDiscount,shippingInfo));
            }
            return carts;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }


}
