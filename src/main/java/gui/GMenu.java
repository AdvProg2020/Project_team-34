package gui;

import account.Account;
import account.Customer;
import account.Supervisor;
import account.Supplier;
import controller.Controller;
import gui.allProductMenu.AllProductGMenu;
import gui.loginMenu.LoginGMenu;
import gui.mainMenu.MainMenuG;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
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

        ImageView logoView = GMenu.getImageView("./src/main/resources/header/Logo.png", 120, 120);
        ImageView backView = GMenu.getImageView("./src/main/resources/header/Back.png", 50, 50);
        ImageView cartView = GMenu.getImageView("./src/main/resources/header/CartIcon.png", 50, 50);
        ImageView userView = GMenu.getImageView("./src/main/resources/header/User.png", 50, 50);
        ImageView allProducts = GMenu.getImageView("./src/main/resources/header/Menu.png", 50, 50);

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
        allProducts.setOnMouseClicked(e -> stage.setScene(new AllProductGMenu("All Products", this, stage).getScene()));

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

        hBox.setMinWidth(500);
        hBox.setPadding(new Insets(10, 10, 10, 10));
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

    public static GridPane createViewPersonalInfo(Account account){
        GridPane showingInfoPane = new GridPane();
        int row = 0;

        Label usernameLabel = new Label("Username");
        Label usernameValue = new Label(account.getUserName());
        showingInfoPane.add(usernameLabel, row, 0);
        showingInfoPane.add(usernameValue, row , 1);

        Label nameLabel = new Label("First Name");
        Label nameValue = new Label(account.getName());
        showingInfoPane.add(nameLabel, row, 0);
        showingInfoPane.add(nameValue, row , 1);

        Label familyNameLabel = new Label("Last Name");
        Label familyNameValue = new Label(account.getFamilyName());
        showingInfoPane.add(familyNameLabel, row, 0);
        showingInfoPane.add(familyNameValue, row , 1);

        Label emailLabel = new Label("Email");
        Label emailLabelValue = new Label(account.getEmail());
        showingInfoPane.add(emailLabel, row, 0);
        showingInfoPane.add(emailLabelValue, row , 1);

        Label phoneNumberLabel = new Label("Phone Number");
        Label phoneNumberValue = new Label(account.getPhoneNumber());
        showingInfoPane.add(phoneNumberLabel, row, 0);
        showingInfoPane.add(phoneNumberValue, row , 1);

        if(account instanceof Supplier) {
            Label nameOfCompanyLabel = new Label("Name of Company");
            Label nameOfCompanyValue = new Label();
            showingInfoPane.add(nameOfCompanyLabel, row, 0);
            showingInfoPane.add(nameOfCompanyValue, row , 1);
        }

        return showingInfoPane;
    }
}
