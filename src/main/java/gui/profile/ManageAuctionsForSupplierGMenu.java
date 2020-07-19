package gui.profile;

import account.Supplier;
import auction.Auction;
import controller.Controller;
import exceptionalMassage.ExceptionalMassage;
import gui.GMenu;
import gui.alerts.AlertBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import product.Product;

import java.util.ArrayList;

public class ManageAuctionsForSupplierGMenu extends GMenu {
    public ManageAuctionsForSupplierGMenu(GMenu parentMenu, Stage stage, Controller controller) {
        super("Manage Auctions", parentMenu, stage, controller);
    }

    @Override
    protected Scene createScene() {
        GridPane backgroundLayout = new GridPane();
        HBox mainPane = new HBox();
        VBox buttonPane = new VBox();

//        TableView<String> auctionsTableView = new TableView<>();
//
//        TableColumn<String, Auction> identifier = new TableColumn<>("identifier");
//        identifier.setCellValueFactory(new PropertyValueFactory<>("identifier"));
//        TableColumn<String, Auction> product_id = new TableColumn<>("product id");
////        product_id.setCellValueFactory(new PropertyValueFactory<>("percent"));
//        TableColumn<String, Auction> highestPromotion = new TableColumn<>("highest Promotion");
//        highestPromotion.setCellValueFactory(new PropertyValueFactory<>("highestPromotion"));
//        auctionsTableView.getColumns().addAll(identifier,product_id, highestPromotion);

        ListView<String> auctionsListView = new ListView<>();
        ArrayList<String> allAuctions = new ArrayList<>();
        try {
            allAuctions = controller.getProductController().controlGetAuctionsForASupplier();
        } catch (ExceptionalMassage exceptionalMassage) {
            new AlertBox(this, exceptionalMassage,controller).showAndWait();
        }
        for (String auction : allAuctions) {
            auctionsListView.getItems().add(auction);
        }

        Button viewDetailsButton = new Button("View details");
        GMenu.addStyleToButton(viewDetailsButton);
        viewDetailsButton.setOnMouseClicked(e->{
//            controller.getProductController()
//        controller.getProductController().controlGetAu
        auctionsListView.getSelectionModel().getSelectedItems().get(0);

        });

        Button createAuctionButton = new Button("Create Auction");
        GMenu.addStyleToButton(createAuctionButton);
        createAuctionButton.setOnMouseClicked(e->{
            stage.setScene(new CreateAuctionGMenu(this, stage, controller).createScene());
        });

        buttonPane.setSpacing(30);
        buttonPane.setAlignment(Pos.CENTER);
        buttonPane.getChildren().addAll( viewDetailsButton, createAuctionButton);
        mainPane.setSpacing(30);
        mainPane.setAlignment(Pos.CENTER);
        mainPane.setPadding(new Insets(15, 15, 15, 15));
        mainPane.getChildren().addAll(auctionsListView,buttonPane);

        VBox headerBackground = new VBox();
        headerBackground.setStyle("-fx-background-color: #4677c8");
        headerBackground.getChildren().add(createHeader());
        backgroundLayout.add(headerBackground, 0,0);
        backgroundLayout.add(mainPane, 0, 1);
        backgroundLayout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(backgroundLayout);
        return scene;
    }
}
