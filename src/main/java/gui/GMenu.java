package gui;


import account.Account;
import account.Customer;
import account.Supervisor;
import account.Supplier;
import controller.Controller;
import exceptionalMassage.ExceptionalMassage;
import gui.alerts.AlertBox;
import gui.allProductMenu.AllProductGMenu;
import gui.cartMenu.CartGMenu;
import gui.loginMenu.LoginGMenu;
import gui.mainMenu.MainMenuG;
import gui.profile.*;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.w3c.dom.events.MouseEvent;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;

public abstract class GMenu {
    public final static SimpleDateFormat FORMAT = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss");
    protected final GMenu parentMenu;
    protected final String menuName;
    protected final Stage stage;
    public final Controller controller;

    public GMenu(String menuName, GMenu parentMenu, Stage stage, Controller controller) {
        this.menuName = menuName;
        this.parentMenu = parentMenu;
        this.controller = controller;
        this.stage = stage;
    }

    public Scene getScene() {
        stage.setTitle(menuName);
        return createScene();
    }

    protected abstract Scene createScene();

    public HBox createHeader() {
        HBox hBox = new HBox();

        ImageView logoView = GMenu.getImageView("./src/main/resources/header/Logo.png", 100, 100);
        ImageView backView = GMenu.getImageView("./src/main/resources/header/Back.png", 25, 25);
        ImageView cartView = GMenu.getImageView("./src/main/resources/header/CartIcon.png", 45, 45);
        ImageView userView = GMenu.getImageView("./src/main/resources/header/User.png", 45, 45);
        ImageView allProducts = GMenu.getImageView("./src/main/resources/header/Menu.png", 45, 45);
        ImageView saleView = GMenu.getImageView("./src/main/resources/header/Sales.png", 45, 45);

        MenuBar userMenuBar = new MenuBar();
        Menu user = new Menu();
        userMenuBar.setStyle("-fx-background-color: transparent");
        user.setGraphic(userView);
        MenuItem signIn = new MenuItem("Sign In / Sign Up");
        MenuItem signOut = new MenuItem("Sign out");
        MenuItem viewPersonalInfo = new MenuItem("View Personal Info");
        MenuItem manageCategories = new MenuItem("Manage Categories");
        MenuItem logs = new MenuItem("View Logs");
        MenuItem manageUsers = new MenuItem("Manage Users");

        logoView.setOnMouseClicked(e ->
                stage.setScene(new MainMenuG( this, stage,controller).getScene()));
        backView.setOnMouseClicked(e -> {
            if (parentMenu == null) {
                stage.close();
                System.exit(0);
            } else {
                stage.setScene(parentMenu.getScene());
            }
        });
        signIn.setOnAction(e -> {
            new LoginGMenu( this, stage, controller).showAndWait();
        });
        allProducts.setOnMouseClicked(e -> stage.setScene(new AllProductGMenu( this,
                stage,controller).getScene()));
        cartView.setOnMouseClicked(e -> stage.setScene(new CartGMenu( this, stage, controller).getScene()));
        manageUsers.setOnAction(e -> stage.setScene(new ManageUsersGMenu(this, stage, controller).getScene()));

        userMenuBar.getMenus().addAll(user);

        hBox.setSpacing(20);
        hBox.getChildren().addAll(backView, logoView, allProducts, saleView, userMenuBar);
        hBox.setAlignment(Pos.CENTER);
        if (!controller.getAccountController().hasSomeOneLoggedIn() ||
                (controller.getAccountController().getAccount() instanceof Customer)) {
            hBox.getChildren().addAll(cartView);
        }

        signOut.setOnAction(e -> {
            controller.getAccountController().controlLogout();
            stage.setScene(new MainMenuG(this, stage, controller).getScene());
        });

        if (!controller.getAccountController().hasSomeOneLoggedIn()) {
            user.getItems().add(signIn);
        } else if (controller.getAccountController().getAccount() instanceof Customer) {
            user.getItems().addAll(viewPersonalInfo, logs, signOut);
            viewPersonalInfo.setOnAction(e -> stage.setScene(new CustomerProfileGMenu(this, stage, controller).getScene()));
            logs.setOnAction(e -> stage.setScene(new ViewLogsForCustomerGMenu(this, stage, controller).getScene()));
        } else if (controller.getAccountController().getAccount() instanceof Supervisor) {
            user.getItems().addAll(viewPersonalInfo, logs, manageCategories, manageUsers, signOut);
            viewPersonalInfo.setOnAction(e -> stage.setScene(new SupervisorProfileGMenu(this, stage, controller).getScene()));
            manageCategories.setOnAction(e -> stage.setScene(new ManageCategoriesGMenu(this, stage, controller).getScene()));
            logs.setOnAction(e -> stage.setScene(new ViewLogsForSupervisorGMenu(this, stage, controller).getScene()));
        } else if (controller.getAccountController().getAccount() instanceof Supplier) {
            user.getItems().addAll(viewPersonalInfo, logs, signOut);
            viewPersonalInfo.setOnAction(e -> stage.setScene(new SupplierProfileGMenu(this, stage, controller).getScene()));
            logs.setOnAction(e -> stage.setScene(new ViewLogsForSupplierGMenu(this, stage, controller).getScene()));
        }

        hBox.setMinWidth(500);
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

    public static GridPane createViewPersonalInfo(Account account){
        GridPane showingInfoPane = new GridPane();
        int row = 0;

        Label accountTypeLabel  = new Label("Account Type");
        Label accountTypeValue = new Label(account.getAccountType());
        showingInfoPane.add(accountTypeLabel, 0, row);
        showingInfoPane.add(accountTypeValue,1, row);
        row++;

        Label usernameLabel = new Label("Username");
        Label usernameValue = new Label(account.getUserName());
        showingInfoPane.add(usernameLabel, 0, row);
        showingInfoPane.add(usernameValue,1, row);
        row++;
        Label nameLabel = new Label("First Name");
        Label nameValue = new Label(account.getName());
        showingInfoPane.add(nameLabel,0, row);
        showingInfoPane.add(nameValue, 1,row);
        row++;
        Label familyNameLabel = new Label("Last Name");
        Label familyNameValue = new Label(account.getFamilyName());
        showingInfoPane.add(familyNameLabel, 0, row);
        showingInfoPane.add(familyNameValue, 1, row);
        row++;
        Label emailLabel = new Label("Email");
        Label emailLabelValue = new Label(account.getEmail());
        showingInfoPane.add(emailLabel,0 , row);
        showingInfoPane.add(emailLabelValue,1, row);
        row++;
        Label phoneNumberLabel = new Label("Phone Number");
        Label phoneNumberValue = new Label(account.getPhoneNumber());
        showingInfoPane.add(phoneNumberLabel, 0, row);
        showingInfoPane.add(phoneNumberValue,1, row);
        row++;

        if(account instanceof Supplier) {
            Label nameOfCompanyLabel = new Label("Name of Company");
            Label nameOfCompanyValue = new Label(((Supplier)account).getNameOfCompany());
            showingInfoPane.add(nameOfCompanyLabel,0, row);
            showingInfoPane.add(nameOfCompanyValue,1, row);
            row++;
        }

        if(!(account instanceof Supervisor)){
            Label creditLabel = new Label("Credit");
            Label creditValue = new Label(String.valueOf(account.getCredit()));
            showingInfoPane.add(creditLabel,0, row);
            showingInfoPane.add(creditValue,1, row);
        }

        showingInfoPane.setAlignment(Pos.CENTER);
        showingInfoPane.setVgap(30);
        showingInfoPane.setHgap(30);
        showingInfoPane.setPadding(new Insets(10, 10 , 10 , 10));
        return showingInfoPane;
    }

    public Scene createLogScene(VBox logsBox) {
        VBox mainLayout = new VBox();
        GridPane background = new GridPane();
        Scene scene = new Scene(background);

        logsBox.setPadding(new Insets(10, 10, 10, 10));
        logsBox.setSpacing(10);

        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.getChildren().addAll(createHeader(), logsBox);

        background.getChildren().add(mainLayout);
        background.setAlignment(Pos.CENTER);

        return scene;
    }


    public static void addStyleToButton(Button button){
        button.getStylesheets().add(new File("src/main/resources/css/Style.css").toURI().toString());
        button.getStyleClass().add("button");
    }


    public void showAndWait() {
        stage.initModality(Modality.APPLICATION_MODAL);
        Image logoImage = null;
        try {
            logoImage = new Image(new FileInputStream("./src/main/resources/header/Logo.png"));
        } catch (FileNotFoundException e) {
        }
        stage.getIcons().add(logoImage);
        stage.setScene(createScene());
        stage.setResizable(false);
        stage.showAndWait();
    }

    protected GridPane createSupervisorBox(GMenu gMenu) {
        GridPane mainLayout = new GridPane();
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        TextField firstName = new TextField();
        firstName.setPromptText("First Name");
        TextField lastName = new TextField();
        lastName.setPromptText("Last Name");
        TextField email = new TextField();
        email.setPromptText("Email");
        TextField phoneNumber = new TextField();
        phoneNumber.setPromptText("Phone number");
        Button done = new Button("Create");

        done.setOnAction(e -> {
            try {
                controller.getAccountController().controlCreateAccount(usernameField.getText(), "supervisor",
                        firstName.getText(), lastName.getText(), email.getText(), phoneNumber.getText(),
                        passwordField.getText(), 0, null);
                stage.setScene(gMenu.createScene());
            } catch (ExceptionalMassage exceptionalMassage) {
                new AlertBox(this, exceptionalMassage, controller).showAndWait();
            }
        });

        mainLayout.setHgap(10);
        mainLayout.setVgap(10);
        mainLayout.add(usernameField, 0, 0);
        mainLayout.add(passwordField, 1, 0);
        mainLayout.add(firstName, 2, 0);
        mainLayout.add(lastName, 3, 0);
        mainLayout.add(email, 0, 1);
        mainLayout.add(phoneNumber, 1, 1);
        mainLayout.add(done, 3, 1);
        mainLayout.setAlignment(Pos.CENTER);
        return mainLayout;
    }
}
