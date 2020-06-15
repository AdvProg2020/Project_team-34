package gui;

import account.Customer;
import account.Supervisor;
import account.Supplier;
import controller.Controller;
import gui.allProductMenu.AllProductGMenu;
import gui.cartMenu.CartGMenu;
import gui.loginMenu.LoginGMenu;
import gui.mainMenu.MainMenuG;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;

public abstract class GMenu {
    public final static SimpleDateFormat FORMAT = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss");
    protected final GMenu parentMenu;
    protected final String menuName;
    protected final Stage stage;
    public final Controller controller;

    public GMenu(String menuName, GMenu parentMenu, Stage stage) {
        this.menuName = menuName;
        this.parentMenu = parentMenu;
        this.controller = new Controller();
        this.stage = stage;
    }

    public Scene getScene() {
        return createScene();
    }

    protected abstract Scene createScene();

    public HBox createHeader() {
        HBox hBox = new HBox();

        ImageView logoView = GMenu.getImageView("./src/main/resources/header/Logo.png", 100, 100);
        ImageView backView = GMenu.getImageView("./src/main/resources/header/Back.png", 45, 45);
        ImageView cartView = GMenu.getImageView("./src/main/resources/header/CartIcon.png", 45, 45);
        ImageView userView = GMenu.getImageView("./src/main/resources/header/User.png", 45, 45);
        ImageView allProducts = GMenu.getImageView("./src/main/resources/header/Menu.png", 45, 45);

        MenuBar userMenuBar = new MenuBar();
        Menu user = new Menu();
        userMenuBar.setStyle("-fx-background-color: transparent");
        user.setGraphic(userView);
        MenuItem signIn = new MenuItem("Sign In / Sign Up");
        MenuItem signOut = new MenuItem("Sign out");
        MenuItem ViewPersonalInfo = new MenuItem("View Personal Info");

        logoView.setOnMouseClicked(e ->
                stage.setScene(new MainMenuG("Main Menu", this, stage).getScene()));
        backView.setOnMouseClicked(e -> {
            if (parentMenu == null) {
                stage.close();
                System.exit(0);
            } else {
                stage.setScene(parentMenu.getScene());
            }
        });
        signIn.setOnAction(e -> {
            stage.setScene(new LoginGMenu("Sign In / Sign Up", this, stage).getScene());
        });
        allProducts.setOnMouseClicked(e -> stage.setScene(new AllProductGMenu("All Products", this,
                stage).getScene()));
        cartView.setOnMouseClicked(e -> stage.setScene(new CartGMenu("Cart", this, stage).getScene()));

        userMenuBar.getMenus().addAll(user);

        hBox.setSpacing(20);
        hBox.getChildren().addAll(logoView, allProducts, backView, userMenuBar);
        hBox.setAlignment(Pos.CENTER);
        if (!controller.getAccountController().hasSomeOneLoggedIn() ||
                (controller.getAccountController().getAccount() instanceof Customer)) {
            hBox.getChildren().addAll(cartView);
        }

        if (!controller.getAccountController().hasSomeOneLoggedIn()) {
            user.getItems().add(signIn);
        } else if (controller.getAccountController().getAccount() instanceof Customer) {
            user.getItems().addAll(ViewPersonalInfo, signOut);
            ViewPersonalInfo.setOnAction(e -> {

            });
        } else if (controller.getAccountController().getAccount() instanceof Supervisor) {
            user.getItems().addAll(ViewPersonalInfo, signOut);
            ViewPersonalInfo.setOnAction(e -> {

            });
        } else if (controller.getAccountController().getAccount() instanceof Supplier) {
            user.getItems().addAll(ViewPersonalInfo, signOut);
            ViewPersonalInfo.setOnAction(e -> {

            });
        }

        hBox.setMinWidth(450);
        hBox.setPadding(new Insets(5, 5, 5, 5));
        hBox.setStyle("-fx-background-color: transparent");
        return hBox;
    }

    public static ImageView getImageView(String source, int height, int width) {
        Image logoImage = null;
        try {
            logoImage = new Image(new FileInputStream(source));
        } catch (FileNotFoundException e) {
            return null;
        }
        ImageView imageView = new ImageView(logoImage);
        imageView.setFitHeight(height);
        imageView.setFitWidth(width);
        return imageView;
    }

    public static VBox getLogBox() {
        VBox vBox = new VBox();
        vBox.setStyle("-fx-border-width: 2");
        vBox.setStyle("-fx-border-radius: 15");
        vBox.setStyle("-fx-border-color: #4678c8");
        vBox.setPadding(new Insets(10, 10, 10, 10));
        return vBox;
    }
}