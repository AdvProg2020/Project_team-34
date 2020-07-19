package database;

import account.*;
import auction.Auction;
import product.Product;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.HashMap;

/**
 * @author rpirayadi
 * @since 0.0.3
 */
public class AuctionDataBase {
    public static void createNewTable() {
        HashMap<String, String> content = new HashMap<>();
        content.put("identifier", "String");
        content.put("chatRoomIdentifier", "String");
        content.put("productId", "String");
        content.put("supplierUserName", "String");
        content.put("highestPromoterUsername", "String");
        content.put("highestPromotion", "int");
        content.put("endDate", "long");
        content.put("wage", "int");

        DataBase.createNewTable("Auctions", content);
    }


    public static void add(Auction auction) {
        if (DataBase.doesIdAlreadyExist("Auctions", "identifier", auction.getIdentifier() )) {
            return;
        }
        String sql = "INSERT into Auctions (identifier, chatRoomIdentifier, productId, supplierUsername, " +
                "highestPromoterUsername, highestPromotion, endDate, wage)" +
                "VALUES (?, ? , ? , ? , ?, ? ,?, ?)";
        try (PreparedStatement statement = DataBase.getConnection().prepareStatement(sql)) {
            statement.setString(1, auction.getIdentifier());
            statement.setString(2, auction.getChatRoomIdentifier());
            statement.setString(3, auction.getProduct().getProductId());
            statement.setString(4, auction.getSupplier().getUserName());
            statement.setString(5, auction.getHighestPromoter().getUserName());
            statement.setInt(6, auction.getHighestPromotion());
            statement.setLong(7, auction.getEnd().getTime());
            statement.setInt(8, auction.getWage());

            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public static void update(Auction auction) {
        delete(auction.getIdentifier());
        add(auction);
    }

    public static void delete(String identifier) {
        DataBase.delete("Auctions", "identifier", identifier);
    }


    public static void importAllAuctions() {
        String sql = "SELECT *  FROM Auctions";

        try (Statement statement = DataBase.getConnection().createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                String identifier = resultSet.getString("identifier");
                String chatRoomIdentifier = resultSet.getString("chatRoomIdentifier");
                Product product = Product.getProductById(resultSet.getString("productId"));
                Supplier supplier = (Supplier)(Account.getAccountByUsernameWithinAll(resultSet.getString("supplierUsername")));
                Customer highestPromoter = (Customer) Account.getAccountByUsernameWithinAll(resultSet.getString("highestPromoterUsername"));
                int highestPromotion = resultSet.getInt("highestPromotion");
                Date endDate = new Date(resultSet.getLong("credit"));
                int wage = resultSet.getInt("wage");

                new Auction(identifier, chatRoomIdentifier,product, supplier,highestPromoter,highestPromotion, endDate, wage);

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
