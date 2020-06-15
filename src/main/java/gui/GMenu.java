package gui;

import account.Customer;
import account.Supervisor;
import account.Supplier;
import controller.Controller;
import gui.loginMenu.LoginGMenu;
import gui.mainMenu.MainMenuG;
import javafx.geometry.Pos;

import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public abstract class GMenu {
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

        ImageView logoView = GMenu.getImageView("./src/main/resources/header/Logo.png", 150, 150);
        ImageView backView = GMenu.getImageView("./src/main/resources/header/Back.png", 60, 60);
        ImageView cartView = GMenu.getImageView("./src/main/resources/header/CartIcon.png", 60, 60);
        ImageView userView = GMenu.getImageView("./src/main/resources/header/User.png", 60, 60);
        ImageView allProducts = GMenu.getImageView("./src/main/resources/header/Menu.png", 60, 60);

        MenuBar userMenuBar = new MenuBar();
        Menu user = new Menu();
        userMenuBar.setStyle("-fx-background-color: transparent");
        user.setGraphic(userView);
        MenuItem signIn = new MenuItem("Sign In / Sign Up");
        MenuItem signOut = new MenuItem("Sign out");
        MenuItem EditPersonalInfo = new MenuItem("Edit Personal Info");

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
            user.getItems().addAll(EditPersonalInfo, signOut);
        } else if (controller.getAccountController().getAccount() instanceof Supervisor) {
            user.getItems().addAll(EditPersonalInfo, signOut);
        } else if (controller.getAccountController().getAccount() instanceof Supplier) {
            user.getItems().addAll(EditPersonalInfo, signOut);
        }

        hBox.setMinWidth(600);
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
}
