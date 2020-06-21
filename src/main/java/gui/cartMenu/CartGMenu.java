package gui.cartMenu;

import account.Supplier;
import cart.ProductInCart;
import controller.Controller;
import gui.GMenu;
import gui.loginMenu.LoginGMenu;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import product.Product;

import java.util.ArrayList;
import java.util.HashMap;

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
//        mainPane.setPrefHeight(513.0);
//        mainPane.setPrefWidth(657.0);
//        TableView tableView = new TableView();
//        tableView.setPrefHeight(272.0);
//        tableView.setPrefWidth(450.0);
//        tableView.setLayoutX(97.0);
//        tableView.setLayoutY(121.0);



        buttonPane.setHgap(100);



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


        Button updateCart = new Button();
//        updateCart.setPrefHeight(26.0);
//        updateCart.setPrefWidth(110.0);
//        updateCart.setLayoutX(273.0);
//        updateCart.setLayoutY(432.0);
        updateCart.setText("Update Cart");
//        updateCart.setMnemonicParsing(false);
        GMenu.addStyleToButton(updateCart);



        buttonPane.add(updateCart, 2, 3);



        backgroundLayout.add( createHeader(), 0,0);
        backgroundLayout.add( productsInCartPane, 0, 1);
        backgroundLayout.add(buttonPane, 0, 2);
        Scene scene = new Scene(backgroundLayout);
        return scene;
    }

    private static VBox createProductsInCartPane ( Controller controller){
        VBox productsInCartPane = new VBox();
        Label label = new Label();
        label.setStyle("-fx-background-color: #9cbfe3;");
        label.setContentDisplay(CENTER);
        label.setPrefWidth(400);
        label.setText("Products On Your Shopping Cart");
        label.setAlignment(Pos.CENTER);

        productsInCartPane.getChildren().add(label);
        productsInCartPane.setSpacing(20);
        productsInCartPane.setAlignment(Pos.CENTER);

        productsInCartPane.getChildren().add(createTableHeader());

        ArrayList<ProductInCart> productInCarts = controller.getAccountController().controlViewCart().getProductsIn();
        //Supplier supplier = new Supplier("i", "i", "i", "i", "ij", "kj", 6, "hdi");
        //Product product = new Product(supplier, "laptop", "Asus", 499, 5, "good", null, null, new HashMap<>());
        //ProductInCart newProductInCart = new ProductInCart(product, supplier);
        //productInCarts.add(newProductInCart);
        for (ProductInCart productInCart : productInCarts) {
            productsInCartPane.getChildren().add(createProductGridPane(productInCart,controller.getAccountController().numberOfProductInCart(productInCart), controller, productsInCartPane));
        }
        return productsInCartPane;
    }

    private static GridPane createProductGridPane(ProductInCart productInCart, int count, Controller controller, Pane productInCartPane) {
        GridPane gridPane = new GridPane();
        Product product = productInCart.getProduct();
        Label IdLabel = new Label(product.getProductId());
        Label nameLabel = new Label(product.getName());
        Label priceLabel = new Label(String.valueOf(product.getPrice(productInCart.getSupplier())));
        Label countLabel = new Label(String.valueOf(count));

        Button increment = new Button("+");
        Button decrement = new Button("-");

        int column = 0;
        gridPane.add(IdLabel, column, 1);
        column++;
        gridPane.add(nameLabel, column, 1);
        column++;
        gridPane.add(priceLabel, column, 1);
        column++;
        gridPane.add(countLabel, column, 1);
        column++;
        gridPane.add(increment, column, 0);
        gridPane.add(decrement, column, 2);

        increment.setOnMouseClicked(e->{
//            try {
//                controller.getAccountController().increaseProductQuantity(productInCart.getProduct().getProductId(), productInCart.getSupplier().getNameOfCompany());
                countLabel.setText(String.valueOf(Integer.parseInt(countLabel.getText()) + 1));
                if(countLabel.getText().equals("0"))
                    productInCartPane.getChildren().remove(gridPane);

//            } catch (ExceptionalMassage exceptionalMassage) {
//                exceptionalMassage.printStackTrace();
//            }
        });

        decrement.setOnMouseClicked(e->{
//            try {
//                controller.getAccountController().decreaseProductQuantity(productInCart.getProduct().getProductId(), productInCart.getSupplier().getNameOfCompany());
                    countLabel.setText(String.valueOf(Integer.parseInt(countLabel.getText()) - 1));
                    if(countLabel.getText().equals("0"))
                        productInCartPane.getChildren().remove(gridPane);
//            } catch (ExceptionalMassage exceptionalMassage) {
//                exceptionalMassage.printStackTrace();
//            }
        });

        gridPane.setHgap(70);
        gridPane.setVgap(0);
        gridPane.setStyle("-fx-border-color: orange");
        return gridPane;
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
