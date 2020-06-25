package gui.cartMenu;

import cart.Cart;
import cart.ProductInCart;
import controller.Controller;
import discount.Sale;
import exceptionalMassage.ExceptionalMassage;
import gui.GMenu;
import gui.alerts.AlertBox;
import gui.loginMenu.LoginGMenu;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import product.Product;

import java.io.File;
import java.util.ArrayList;

import static javafx.scene.control.ContentDisplay.CENTER;

public class CartGMenu extends GMenu {

    public CartGMenu(GMenu parentMenu, Stage stage, Controller controller) {
        super("Cart Menu", parentMenu, stage, controller);
    }

    @Override
    protected Scene createScene() {
        GridPane backgroundLayout = new GridPane();
        VBox productsInCartPane ;
        GridPane buttonPane = new GridPane();

        buttonPane.setHgap(100);
        buttonPane.setAlignment(Pos.CENTER);

        productsInCartPane = createProductsInCartPane(controller);

        Button clearCart = new Button();
        clearCart.setText("Clear Cart");
        GMenu.addStyleToButton(clearCart);
        buttonPane.add(clearCart, 0, 3);
        clearCart.setOnMouseClicked(e->{
            productsInCartPane.getChildren().clear();
            controller.getAccountController().controlClearCart();
        });

        Button placeOrder = new Button();
        placeOrder.setText("Place Order");
        GMenu.addStyleToButton(placeOrder);
        buttonPane.add(placeOrder, 1, 3);

        placeOrder.setOnMouseClicked(e -> {
            controller.getAccountController().controlViewCart().update();
            if (controller.getAccountController().hasSomeOneLoggedIn()) {
                stage.setScene(new PurchaseMenuG(this, stage, controller).getScene());
            } else {
                stage.setScene(new LoginGMenu(this, stage, controller).getScene());
            }
        });


//        Button updateCart = new Button();
//
//        updateCart.setText("Update Cart");
////        updateCart.setMnemonicParsing(false);
//        GMenu.addStyleToButton(updateCart);
//
//        buttonPane.add(updateCart, 2, 3);
//        buttonPane.setAlignment(Pos.CENTER);
//        buttonPane.setPadding(new Insets(10, 10, 10, 10));

        backgroundLayout.setVgap(20);
        backgroundLayout.add(createHeader(), 0,0);
        backgroundLayout.add(productsInCartPane, 0, 1);
        backgroundLayout.add(buttonPane, 0, 2);
        backgroundLayout.setAlignment(Pos.CENTER);
        backgroundLayout.setPadding(new Insets(10, 10, 10, 10));
        Scene scene = new Scene(backgroundLayout);
        return scene;
    }

    private  VBox createProductsInCartPane ( Controller controller){
        VBox productsInCartPane = new VBox();
        Label label = new Label();
        label.setStyle("-fx-background-color: transparent");
        label.setContentDisplay(CENTER);
        label.setPrefWidth(400);
        label.setText("Products On Your Shopping Cart");
        label.setAlignment(Pos.CENTER);

        productsInCartPane.setMinWidth(820);
        productsInCartPane.getChildren().add(label);
        productsInCartPane.setSpacing(20);
        productsInCartPane.setAlignment(Pos.CENTER);

//        productsInCartPane.getChildren().add(createTableHeader());

        Label billLabel = new Label("Bill : " + controller.getAccountController().controlViewCart().getBill());
        billLabel.setStyle("-fx-font-size: 20");

        ArrayList<ProductInCart> productInCarts = controller.getAccountController().controlViewCart().getProductsIn();
        productsInCartPane.setAlignment(Pos.CENTER);
        for (ProductInCart productInCart : productInCarts) {
            productsInCartPane.getChildren().add(createProductGridPane(productInCart,controller.getAccountController().numberOfProductInCart(productInCart), controller, productsInCartPane,billLabel ));
        }

        productsInCartPane.getChildren().add(billLabel);

        return productsInCartPane;
    }

    private HBox createProductGridPane(ProductInCart productInCart, int count, Controller controller, VBox productInCartPane, Label billLabel) {
        GridPane gridPane = new GridPane();
        Product product = productInCart.getProduct();
        int priceBeforeSale = product.getPrice(productInCart.getSupplier());
        int priceAfterSale = controller.getOffController().controlGetPriceForEachProductAfterSale(product, productInCart.getSupplier());
        Label IdLabel = new Label(product.getProductId());
        Label nameLabel = new Label(product.getName());

        String textPriceLabel = String.valueOf(priceBeforeSale);
        if(priceAfterSale != priceBeforeSale)
            textPriceLabel = textPriceLabel + "=>" + String.valueOf(priceAfterSale);
        Label priceLabel = new Label(textPriceLabel);
        Label countLabel = new Label(String.valueOf(count));
        String textLastPriceLabel =  String.valueOf(priceBeforeSale * count);
        if(priceAfterSale != priceBeforeSale)
            textLastPriceLabel = textLastPriceLabel + "=>" + String.valueOf(priceAfterSale * count);


        Label lastPrice = new Label(textLastPriceLabel);

//        lastPrice.setMinHeight(90);
//        lastPrice.setMinHeight(90);
        gridPane.setHgap(70);
        gridPane.setVgap(0);
        gridPane.setAlignment(Pos.CENTER);
        HBox hBox = new HBox();
        hBox.setStyle("-fx-border-color: orange");
        hBox.setSpacing(20);
        hBox.setPadding(new Insets(10, 30, 10, 30));
        hBox.getChildren().addAll(getImageView(product.getImageUrl(), 70, 70), gridPane);

        Button increment = new Button("+");
        Button decrement = new Button("-");

        HBox countBox = new HBox();
        countBox.getChildren().addAll(decrement, countLabel, increment);
        countBox.setMaxHeight(30);
        countBox.setSpacing(5);
        countBox.setAlignment(Pos.CENTER);
        countBox.getStylesheets().add(new File("src/main/java/gui/cartMenu/style.css").toURI().toString());

        int column = 0;
        gridPane.add(IdLabel, column, 1);
        column++;
        gridPane.add(nameLabel, column, 1);
        column++;
        gridPane.add(priceLabel, column, 1);
        column++;
        gridPane.add(countBox, column, 1);
        column++;
        gridPane.add(lastPrice, column , 1);

        increment.setOnMouseClicked(e->{
            try {
                controller.getAccountController().increaseProductQuantity(productInCart.getProduct().getProductId(), productInCart.getSupplier().getNameOfCompany());
                countLabel.setText(String.valueOf(Integer.parseInt(countLabel.getText()) + 1));
                updateLastPriceLabel(lastPrice,Integer.parseInt(countLabel.getText()), priceBeforeSale, priceAfterSale);
                updateBillLabel(billLabel, controller);
                controller.getAccountController().controlViewCart().update();
                if(countLabel.getText().equals("0")) {
                    productInCartPane.getChildren().remove(hBox);
                }

            } catch (ExceptionalMassage exceptionalMassage) {
                new AlertBox(this, exceptionalMassage,controller).showAndWait();
            }
        });

        decrement.setOnMouseClicked(e->{
            try {
                controller.getAccountController().decreaseProductQuantity(productInCart.getProduct().getProductId(), productInCart.getSupplier().getNameOfCompany());
                countLabel.setText(String.valueOf(Integer.parseInt(countLabel.getText()) - 1));
                updateLastPriceLabel(lastPrice, Integer.parseInt(countLabel.getText()),priceBeforeSale,priceAfterSale);
                updateBillLabel(billLabel , controller);
                controller.getAccountController().controlViewCart().update();
                    if(countLabel.getText().equals("0")) {
                        productInCartPane.getChildren().remove(hBox);
                    }
            } catch (ExceptionalMassage exceptionalMassage) {
                new AlertBox(this, exceptionalMassage,controller).showAndWait();
            }
        });


        return hBox;
    }

    private static void updateBillLabel(Label billLabel, Controller controller){
        billLabel.setText("Bill : " + controller.getAccountController().controlViewCart().getBill());
    }

    private static void updateLastPriceLabel (Label lastPriceLabel , int count ,int priceBeforeSale, int priceAfterSale){
        String textLastPriceLabel =  String.valueOf(priceBeforeSale * count);
        if(priceAfterSale != priceBeforeSale)
            textLastPriceLabel = textLastPriceLabel + "=>" + String.valueOf(priceAfterSale * count);
        lastPriceLabel.setText(textLastPriceLabel);

    }

    private static HBox createTableHeader(){
        HBox hBox = new HBox();
        Label idLabel = new Label("      Product Id");
        Label nameLabel = new Label("Product Name");
        Label priceLabel = new Label("Price");
        Label countLabel = new Label("Count");

        hBox.getChildren().addAll(idLabel, nameLabel, priceLabel, countLabel);
        hBox.setSpacing(70);

        return hBox;
    }

}
