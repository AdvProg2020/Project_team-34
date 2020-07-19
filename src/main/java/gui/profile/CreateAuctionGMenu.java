package gui.profile;

import controller.Controller;
import gui.GMenu;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import product.Product;

import java.util.ArrayList;

public class CreateAuctionGMenu extends GMenu {


    public CreateAuctionGMenu (GMenu parentMenu, Stage stage, Controller controller) {
        super("Create Auction", parentMenu, stage, controller);
    }

    @Override
    protected Scene createScene() {
        GridPane backgroundLayout = new GridPane();
        HBox mainPane = new HBox();
        VBox sidePane = new VBox();

        DatePicker datePicker = new DatePicker();

        ListView<String> productsListView = new ListView<>();
        ArrayList<Product> allProductsNotInAuction = new ArrayList<>(); //controller must be;
        for (Product product : allProductsNotInAuction) {
            productsListView.getItems().add(product.getProductId());
        }

        Button createAuctionButton = new Button("Create Auction");
        GMenu.addStyleToButton(createAuctionButton);
        createAuctionButton.setOnMouseClicked(e->{
            stage.setScene(new CreateAuctionGMenu(this, stage, controller).createScene());
        });
        sidePane.getChildren().addAll(datePicker, createAuctionButton);
        sidePane.setAlignment(Pos.CENTER);
        sidePane.setSpacing(30);

        mainPane.setSpacing(30);
        mainPane.setPadding(new Insets(15, 15, 15, 15));
        mainPane.getChildren().addAll(productsListView, sidePane);


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
