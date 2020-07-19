package gui.profile;

import auction.Auction;
import controller.Controller;
import gui.GMenu;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ManageAuctionsForSupplierGMenu extends GMenu {
    public ManageAuctionsForSupplierGMenu(GMenu parentMenu, Stage stage, Controller controller) {
        super("Manage Auctions", parentMenu, stage, controller);
    }

    @Override
    protected Scene createScene() {
        GridPane backgroundLayout = new GridPane();
        HBox mainPane = new HBox();
        VBox buttonPane = new VBox();
        VBox viewPane = new VBox();

        TableView<String> auctionsTableView = new TableView<>();

        TableColumn<String, Auction> identifier = new TableColumn<>("identifier");
        identifier.setCellValueFactory(new PropertyValueFactory<>("identifier"));
        TableColumn<String, Auction> product_id = new TableColumn<>("product id");
//        product_id.setCellValueFactory(new PropertyValueFactory<>("percent"));
        TableColumn<String, Auction> highestPromotion = new TableColumn<>("highest Promotion");
        highestPromotion.setCellValueFactory(new PropertyValueFactory<>("highestPromotion"));
        auctionsTableView.getColumns().addAll(identifier,product_id, highestPromotion);


        Button viewDetailsButton = new Button("View details");
        GMenu.addStyleToButton(viewDetailsButton);
        viewDetailsButton.setOnMouseClicked(e->{
            stage.setScene(new ManageProductsSupplierGMenu(this, stage, controller).createScene());
        });

        Button createAuctionButton = new Button("Create Auction");
        GMenu.addStyleToButton(createAuctionButton);
        createAuctionButton.setOnMouseClicked(e->{
            stage.setScene(new CreateAuctionGMenu(this, stage, controller).createScene());
        });


        mainPane.getChildren().add(auctionsTableView);

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
