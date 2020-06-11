package gui.profile;

import account.Account;
import account.Supervisor;
import account.Supplier;
import gui.GMenu;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class ProfileGMenu extends GMenu {
    private final TextField usernameField;
    private final TextField nameField;
    private final TextField familyNameField;
    private final TextField emailField;
    private final TextField phoneNumberField;
    private final PasswordField passwordField;
    private final TextField nameOfCompanyField;
    private final TextField creditField;
    private final Button cancelButton;
    private final Button applyButton;

    public ProfileGMenu(String menuName, GMenu parentMenu, Stage stage) {
        super(menuName, parentMenu, stage);
        this.usernameField = new TextField();
        this.nameField = new TextField();
        this.familyNameField = new TextField();
        this.emailField = new TextField();
        this.phoneNumberField = new TextField();
        this.passwordField = new PasswordField();
        this.nameOfCompanyField = new TextField();
        this.creditField = new TextField();
        this.applyButton = new Button("Apply");
        this.cancelButton = new Button("Cancel");
    }

    @Override
    protected Scene createScene() {
        int row = 0;

        Label usernameLabel = new Label("Username");
        Label nameLabel = new Label("First Name");
        Label familyNameLabel = new Label("Last Name");
        Label emailLabel = new Label("Email");
        Label phoneNumberLabel = new Label("Phone Number");
        Label passwordLabel = new Label("New Password");

        Label nameOfCompanyLabel = new Label("Name of Company");
        Label creditLabel = new Label("Credit");

        GridPane layoutPane = new GridPane();
        layoutPane.setAlignment(Pos.CENTER);
        layoutPane.setPadding(new Insets(10, 10, 10, 10));
        layoutPane.setVgap(10);
        layoutPane.setHgap(10);
        GridPane backgroundPane = new GridPane();
        Scene scene = new Scene(layoutPane);

        usernameField.setText(controller.getAccountController().getAccountUsername());
        usernameField.setDisable(true);
        layoutPane.add(usernameLabel, 0, row);
        layoutPane.add(usernameField, 1, row);
        row++;

        nameField.setText(controller.getAccountController().getAccountName());
        nameField.setOnKeyTyped(e -> buttonCheck(nameField));
        layoutPane.add(nameLabel, 0, row);
        layoutPane.add(nameField, 1, row);
        row++;

        familyNameField.setText(controller.getAccountController().getAccountFamilyName());
        familyNameField.setOnKeyTyped(e -> buttonCheck(familyNameField));
        layoutPane.add(familyNameLabel, 0, row);
        layoutPane.add(familyNameField, 1, row);
        row++;

        emailField.setText(controller.getAccountController().getAccountEmail());
        emailField.setOnKeyTyped(e -> buttonCheck(emailField));
        layoutPane.add(emailLabel, 0, row);
        layoutPane.add(emailField, 1, row);
        row++;

        phoneNumberField.setText(controller.getAccountController().getAccountPhoneNumber());
        phoneNumberField.setOnKeyTyped(e -> buttonCheckInt(phoneNumberField));
        layoutPane.add(phoneNumberLabel, 0, row);
        layoutPane.add(phoneNumberField, 1, row);
        row++;

        if (!(controller.getAccount() instanceof Supervisor)) {
            creditField.setText(String.valueOf(controller.getAccountController().getAccountCredit()));
            creditLabel.setOnKeyTyped(e -> buttonCheckInt(creditField));
            layoutPane.add(creditLabel, 0, row);
            layoutPane.add(creditField, 1, row);
            row++;
            if (controller.getAccount() instanceof Supplier) {
                creditField.setDisable(true);
            }
        }

        if (controller.getAccount() instanceof Supplier) {
            nameOfCompanyField.setText(controller.getAccountController().getAccountNameOfCompany());
            nameOfCompanyField.setOnKeyTyped(e -> buttonCheck(nameOfCompanyField));
            layoutPane.add(nameOfCompanyLabel, 0, row);
            layoutPane.add(nameOfCompanyField, 1, row);
            row++;
        }

        layoutPane.add(passwordLabel, 0, row);
        layoutPane.add(passwordField, 1, row);
        row++;

        applyButton.setOnAction(e -> {
            String name = nameField.getText();
            String familyName = familyNameField.getText();
            String email = emailField.getText();
            String phoneNumber = phoneNumberField.getText();
            String password = passwordField.getText();
            String nameOfCompany = nameOfCompanyField.getText();
            int credit = Integer.parseInt(creditField.getText());
            controller.getAccountController().editAllFieldsSupplier(name, familyName, email, phoneNumber, password,
                    credit, nameOfCompany);
        });

        cancelButton.setOnAction(e -> stage.setScene(parentMenu.getScene()));

        layoutPane.add(cancelButton, 0, row);
        layoutPane.add(applyButton, 1, row);

        return scene;
    }

    private void buttonCheck(TextField textField) {
        applyButton.setDisable(textField.getText() == null || textField.getText().trim().length() == 0);
    }

    private void buttonCheckInt(TextField textField) {
        if (textField.getText().matches("\\d+")) {
            buttonCheck(textField);
        } else {
            applyButton.setDisable(true);
        }
    }
}
