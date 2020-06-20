package database;

import account.Account;
import account.Supplier;
import cart.ProductInCart;
import product.Product;

import java.sql.*;
import java.util.HashMap;


/**
 * @author rpirayadi
 * @since 0.0.1
 */
public class ProductInCartDataBase {

    public static void createNewTable() {
        HashMap<String, String> content = new HashMap<>();
        content.put("productInCartId", "String");
        content.put("productId", "String");
        content.put("supplierUserName", "String");

        DataBase.createNewTable("ProductInCarts", content);
    }

    public static void add(ProductInCart productInCart) {
        if (DataBase.doesIdAlreadyExist("ProductInCarts", "productInCartId", productInCart.getIdentifier())) {
            return;
        }
        String sql = "INSERT into ProductInCarts (productInCartId, productId, supplierUsername) " +
                "VALUES (?,?, ?)";
        try (PreparedStatement statement = DataBase.getConnection().prepareStatement(sql)) {

            statement.setString(1, productInCart.getIdentifier());
            statement.setString(2, productInCart.getProduct().getProductId());
            statement.setString(3, productInCart.getSupplier().getUserName());

            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void update(ProductInCart productInCart) {
        delete(productInCart.getIdentifier());
        add(productInCart);
    }

    public static void delete(String productInCartId) {
        DataBase.delete("ProductInCarts", "productInCartId", productInCartId);
    }


    public static void importAllProductInCarts() {
        String sql = "SELECT *  FROM ProductInCarts";

        try (Statement statement = DataBase.getConnection().createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                String productInCartId = resultSet.getString("productInCartId");
                Product product = Product.getProductById(resultSet.getString("productId"));
                Supplier supplier = (Supplier) Account.getAccountByUsername(resultSet.getString("supplierUsername"));

                new ProductInCart(productInCartId,product,supplier);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}















