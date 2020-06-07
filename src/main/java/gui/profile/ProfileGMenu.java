package gui.profile;

import account.Account;
import account.Supervisor;
import account.Supplier;
import gui.GMenu;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import menu.menuAbstract.Menu;

public class ProfileGMenu extends GMenu {
    private final Account account;

    private final TextField usernameField;
    private final TextField nameField;
    private final TextField familyNameField;
    private final TextField emailField;
    private final TextField phoneNumberField;
    private final PasswordField passwordField;
    private final TextField nameOfCompanyField;
    private final TextField creditField;

    public ProfileGMenu(String menuName, Menu parentMenu, Account account) {
        super(menuName, parentMenu);
        this.account = account;
        this.usernameField = new TextField();
        this.nameField = new TextField();
        this.familyNameField = new TextField();
        this.emailField = new TextField();
        this.phoneNumberField = new TextField();
        this.passwordField = new PasswordField();
        this.nameOfCompanyField = new TextField();
        this.creditField = new TextField();
    }

    @Override
    protected Scene createScene() {
        int row = 0;

        Label usernameLabel = new Label("Username");
        Label nameLabel = new Label("First Name");
        Label familyNameLabel = new Label("Last Name");
        Label emailLabel = new Label("Email");
        Label phoneNumberLabel = new Label("Phone Number");
        Label passwordLabel = new Label("new Password");

        Label nameOfCompanyLabel = new Label("Name of Company");
        Label creditLabel = new Label("Credit");


        GridPane gridPane = new GridPane();
        Scene scene = new Scene(gridPane);

        usernameField.setText(account.getUserName());
        usernameField.setDisable(true);
        gridPane.add(usernameLabel, 0, row);
        gridPane.add(usernameField, 1, row);
        row++;

        nameField.setText(account.getName());
        gridPane.add(nameLabel, 0, row);
        gridPane.add(nameField, 1, row);
        row++;

        familyNameField.setText(account.getFamilyName());
        gridPane.add(familyNameLabel, 0, row);
        gridPane.add(familyNameField, 1, row);
        row++;

        emailField.setText(account.getEmail());
        gridPane.add(emailLabel, 0, row);
        gridPane.add(emailField, 1, row);
        row++;

        phoneNumberField.setText(account.getPhoneNumber());
        gridPane.add(phoneNumberLabel, 0, row);
        gridPane.add(phoneNumberField, 1, row);
        row++;

        if (!(account instanceof Supervisor)) {
            creditField.setText(account.getPhoneNumber());
            gridPane.add(creditLabel, 0, row);
            gridPane.add(creditField, 1, row);
            row++;
            if (account instanceof Supplier) {
                creditField.setDisable(true);
            }
        }

        if (account instanceof Supplier) {
            nameOfCompanyField.setText(((Supplier) account).getNameOfCompany());
            gridPane.add(nameOfCompanyLabel, 0, row);
            gridPane.add(nameOfCompanyField, 1, row);
            row++;
        }

        gridPane.add(passwordLabel, 0, row);
        gridPane.add(passwordField, 1, row);
        row++;

        return scene;
    }
}
