package gui.profile;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTimePicker;
import controller.Controller;
import exceptionalMassage.ExceptionalMassage;
import gui.GMenu;
import gui.alerts.AlertBox;
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
import java.util.Date;

public class CreateAuctionGMenu extends GMenu {


    public CreateAuctionGMenu(GMenu parentMenu, Stage stage, Controller controller) {
        super("Create Auction", parentMenu, stage, controller);
    }

    @Override
    protected Scene createScene() {
        GridPane backgroundLayout = new GridPane();
        HBox mainPane = new HBox();
        VBox sidePane = new VBox();

        JFXTimePicker jfxTimePicker = new JFXTimePicker();
        JFXDatePicker jfxDatePicker = new JFXDatePicker();
//        HBox hBox = new HBox();
//        hBox.getChildren().addAll(jfxTimePicker, jfxDatePicker);
//        hBox.setSpacing(20);
//        hBox.setPadding(new Insets(100, 100, 100, 100));
//        stage.setScene(new Scene(hBox));
        jfxTimePicker.setOnAction(e -> System.out.println(
                jfxTimePicker.getValue().getHour()));

        ListView<String> productsListView = new ListView<>();
        ArrayList<Product> allProductsNotInAuction = new ArrayList<>();
        try {
            allProductsNotInAuction = controller.getProductController().controlGetNotAuctionedProductsOfSupplier();
        } catch (ExceptionalMassage exceptionalMassage) {
            new AlertBox(this, exceptionalMassage, controller);
        }
        for (Product product : allProductsNotInAuction) {
            productsListView.getItems().add(product.getProductId());
        }

        Button createAuctionButton = new Button("Create Auction");
        GMenu.addStyleToButton(createAuctionButton);
        createAuctionButton.setOnMouseClicked(e -> {
            Product product;
            try {
                if (productsListView.getSelectionModel().getSelectedItems().size() != 0) {
                    product = controller.getProductController().getProductById(productsListView.getSelectionModel().getSelectedItems().get(0));
                    try {
                        controller.getProductController().controlAddAuction(product, new Date(GMenu.getTime(jfxDatePicker, jfxTimePicker)));
                    } catch (NullPointerException ex){
                        new AlertBox(this, "Please enter Date and Time.","OK", controller).showAndWait();
                    }
                    stage.setScene(new SupplierProfileGMenu(this, stage, controller).createScene());
                }else {
                    new AlertBox(this, "Please Select Product", "OK", controller).showAndWait();
                }
            } catch (ExceptionalMassage exceptionalMassage) {
                new AlertBox(this, exceptionalMassage, controller).showAndWait();
            }
        });
        sidePane.getChildren().addAll(jfxDatePicker, jfxTimePicker, createAuctionButton);
        sidePane.setAlignment(Pos.CENTER);
        sidePane.setSpacing(40);

        mainPane.setSpacing(30);
        mainPane.setPadding(new Insets(15, 15, 15, 15));
        mainPane.getChildren().addAll(productsListView, sidePane);


        VBox headerBackground = new VBox();
        headerBackground.setStyle("-fx-background-color: #4677c8");
        headerBackground.getChildren().add(createHeader());
        backgroundLayout.add(headerBackground, 0, 0);
        backgroundLayout.add(mainPane, 0, 1);
        backgroundLayout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(backgroundLayout);
        return scene;
    }
}
