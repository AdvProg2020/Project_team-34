package gui.loginMenu;

import controller.Controller;
import exceptionalMassage.ExceptionalMassage;
import gui.GMenu;
import gui.alerts.AlertBox;
import gui.mainMenu.MainMenuG;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class NewRegisterGMenu extends GMenu {
    private final Stage popupCaller;

    public NewRegisterGMenu(GMenu parentMenu, Stage stage, Stage popupCaller, Controller controller) {
        super("Sign Up", parentMenu, stage, controller);
        this.popupCaller = popupCaller;
    }

    @Override
    protected Scene createScene() {
        GridPane registerBox = new GridPane();

        ToggleGroup accountTypeGroup = new ToggleGroup();
        RadioButton customerAccount = new RadioButton("Customer");
        customerAccount.setToggleGroup(accountTypeGroup);
        RadioButton supplierAccount = new RadioButton("Supplier");
        supplierAccount.setToggleGroup(accountTypeGroup);
        accountTypeGroup.selectToggle(customerAccount);
        HBox accountTypeBox = new HBox();
        customerAccount.setStyle("-fx-alignment: center; -fx-max-width: 140; -fx-min-width: 140; -fx-pref-width: 140; " +
                "-fx-border-radius: 15; -fx-background-radius: 15; -fx-border-color: #4678c8; -fx-border-width: 2px;" +
                "-fx-font-size: 14px");
        customerAccount.setPadding(new Insets(5, 5, 5, 5));
        supplierAccount.setStyle("-fx-alignment: center; -fx-max-width: 140; -fx-min-width: 140; -fx-pref-width: 140; " +
                "-fx-border-radius: 15; -fx-background-radius: 15; -fx-border-color: #4678c8; -fx-border-width: 2px;" +
                "-fx-font-size: 14px");
        supplierAccount.setPadding(new Insets(5, 5, 5, 5));
        accountTypeBox.setAlignment(Pos.CENTER);
        accountTypeBox.setSpacing(20);
        accountTypeBox.getChildren().addAll(customerAccount, supplierAccount);

        TextField nameOfCompany = new TextField();
        nameOfCompany.setPromptText("Name of your company");
        nameOfCompany.setStyle("-fx-alignment: center; -fx-max-width: 300; -fx-min-width: 300; -fx-pref-width: 300; " +
                "-fx-border-radius: 15; -fx-background-radius: 15; -fx-border-color: #4678c8; -fx-border-width: 2px;" +
                "-fx-font-size: 14px");
        nameOfCompany.setVisible(false);

        TextField username = new TextField();
        username.setPromptText("Username");
        username.setStyle("-fx-alignment: center; -fx-max-width: 300; -fx-min-width: 300; -fx-pref-width: 300; " +
                "-fx-border-radius: 15; -fx-background-radius: 15; -fx-border-color: #4678c8; -fx-border-width: 2px;" +
                "-fx-font-size: 14px");

        TextField firstName = new TextField();
        firstName.setPromptText("First Name");
        firstName.setStyle("-fx-alignment: center; -fx-max-width: 300; -fx-min-width: 300; -fx-pref-width: 300; " +
                "-fx-border-radius: 15; -fx-background-radius: 15; -fx-border-color: #4678c8; -fx-border-width: 2px;" +
                "-fx-font-size: 14px");

        TextField lastName = new TextField();
        lastName.setPromptText("Last Name");
        lastName.setStyle("-fx-alignment: center; -fx-max-width: 300; -fx-min-width: 300; -fx-pref-width: 300; " +
                "-fx-border-radius: 15; -fx-background-radius: 15; -fx-border-color: #4678c8; -fx-border-width: 2px;" +
                "-fx-font-size: 14px");

        TextField email = new TextField();
        email.setPromptText("Email");
        email.setStyle("-fx-alignment: center; -fx-max-width: 300; -fx-min-width: 300; -fx-pref-width: 300; " +
                "-fx-border-radius: 15; -fx-background-radius: 15; -fx-border-color: #4678c8; -fx-border-width: 2px;" +
                "-fx-font-size: 14px");

        TextField phoneNumber = new TextField();
        phoneNumber.setPromptText("Phone number");
        phoneNumber.setStyle("-fx-alignment: center; -fx-max-width: 300; -fx-min-width: 300; -fx-pref-width: 300; " +
                "-fx-border-radius: 15; -fx-background-radius: 15; -fx-border-color: #4678c8; -fx-border-width: 2px;" +
                "-fx-font-size: 14px");

        CheckBox bankAccount = new CheckBox("Also create a bank account");
        bankAccount.setStyle("-fx-alignment: center; -fx-max-width: 300; -fx-min-width: 300; -fx-pref-width: 300; " +
                "-fx-border-radius: 15; -fx-background-radius: 15; -fx-border-color: #4678c8; -fx-border-width: 2px;" +
                "-fx-font-size: 14px");
        bankAccount.setPadding(new Insets(5, 5, 5, 5));
        bankAccount.setSelected(true);

        TextField bankUsername = new TextField();
        bankUsername.setPromptText("Bank Username");
        bankUsername.setStyle("-fx-alignment: center; -fx-max-width: 300; -fx-min-width: 300; -fx-pref-width: 300; " +
                "-fx-border-radius: 15; -fx-background-radius: 15; -fx-border-color: #4678c8; -fx-border-width: 2px;" +
                "-fx-font-size: 14px");

        PasswordField bankPassword = new PasswordField();
        bankPassword.setPromptText("Bank Password");
        bankPassword.setStyle("-fx-alignment: center; -fx-max-width: 300; -fx-min-width: 300; -fx-pref-width: 300; " +
                "-fx-border-radius: 15; -fx-background-radius: 15; -fx-border-color: #4678c8; -fx-border-width: 2px;" +
                "-fx-font-size: 14px");

        Button exit = new Button("Close");
        exit.setStyle("-fx-alignment: center; -fx-max-width: 300; -fx-min-width: 300; -fx-pref-width: 300; " +
                "-fx-border-radius: 15; -fx-background-radius: 15; -fx-border-width: 2px; -fx-font-size: 14px");
        exit.setCancelButton(true);

        Button signUp = new Button("Sign Up");
        signUp.setStyle("-fx-alignment: center; -fx-max-width: 300; -fx-min-width: 300; -fx-pref-width: 300; " +
                "-fx-border-radius: 15; -fx-background-radius: 15; -fx-border-width: 2px; -fx-font-size: 14px");
        signUp.setDefaultButton(true);

        registerBox.add(accountTypeBox, 0, 1);
        registerBox.add(username, 0, 2);
        registerBox.add(nameOfCompany, 1, 2);
        registerBox.add(firstName, 0, 3);
        registerBox.add(lastName, 1, 3);
        registerBox.add(email, 0, 4);
        registerBox.add(phoneNumber, 1, 4);
        registerBox.add(bankAccount, 0, 5);
        registerBox.add(bankUsername, 0, 6);
        registerBox.add(bankPassword, 1, 6);
        registerBox.add(signUp, 1, 7);
        registerBox.add(exit, 0, 7);

        customerAccount.setOnAction(e -> {
            nameOfCompany.setVisible(!customerAccount.isArmed());
            nameOfCompany.setDisable(customerAccount.isArmed());
            nameOfCompany.clear();
        });

        supplierAccount.setOnAction(e -> {
            nameOfCompany.setVisible(!customerAccount.isArmed());
            nameOfCompany.setDisable(customerAccount.isArmed());
            nameOfCompany.clear();
        });

        bankAccount.setOnAction(e -> {
            bankUsername.clear();
            bankUsername.setDisable(!bankAccount.isSelected());
            bankPassword.clear();
            bankPassword.setDisable(!bankAccount.isSelected());
        });

        exit.setOnAction(e -> {
            stage.close();
        });

        signUp.setOnAction(e -> {
            String type = accountTypeGroup.getSelectedToggle().equals(customerAccount) ? "customer" : "supplier";
            try {
                controller.getAccountController().controlCreateAccount(type, username.getText(), firstName.getText(),
                        lastName.getText(), email.getText(), phoneNumber.getText(), nameOfCompany.getText(),
                        bankUsername.getText(), bankPassword.getText(), bankAccount.isSelected());
                stage.close();
                popupCaller.setScene(new MainMenuG(null, popupCaller, controller).getScene());
            } catch (ExceptionalMassage exceptionalMassage) {
                new AlertBox(this, exceptionalMassage, controller).showAndWait();
            }
        });

        registerBox.setAlignment(Pos.CENTER);
        registerBox.setHgap(40);
        registerBox.setVgap(15);
        registerBox.setPadding(new Insets(10, 10, 10, 10));

        VBox backgroundLayout = new VBox();
        Pane upperColor = new Pane();
        Pane lowerColor = new Pane();
        upperColor.setStyle("-fx-max-height: 100px; -fx-min-height: 100px; -fx-pref-height: 100px;" +
                " -fx-background-color: #4678c8");
        lowerColor.setStyle("-fx-max-height: 100px; -fx-min-height: 100px; -fx-pref-height: 100px;" +
                " -fx-background-color: #4678c8");
        backgroundLayout.getChildren().addAll(upperColor, registerBox, lowerColor);
        backgroundLayout.setAlignment(Pos.CENTER);
        return new Scene(backgroundLayout);
    }
}
