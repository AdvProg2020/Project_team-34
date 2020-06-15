package gui.profile;

import account.Supervisor;
import account.Supplier;
import controller.Controller;
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

public class EditPersonalInfoGMenu extends GMenu {

    public EditPersonalInfoGMenu(GMenu parentMenu, Stage stage, Controller controller) {
        super("Edit Personal Info", parentMenu, stage, controller);
    }

    @Override
    protected Scene createScene() {
        TextField usernameField = new TextField();
        TextField nameField = new TextField();
        TextField familyNameField = new TextField();
        TextField emailField = new TextField();
        TextField phoneNumberField = new TextField();
        PasswordField passwordField = new PasswordField();
        TextField nameOfCompanyField = new TextField();
        TextField creditField = new TextField();
        Button cancelButton = new Button("Cancel");
        Button applyButton = new Button("Apply");

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
        nameField.setOnKeyTyped(e -> buttonCheck(nameField, applyButton));
        layoutPane.add(nameLabel, 0, row);
        layoutPane.add(nameField, 1, row);
        row++;

        familyNameField.setText(controller.getAccountController().getAccountFamilyName());
        familyNameField.setOnKeyTyped(e -> buttonCheck(familyNameField, applyButton));
        layoutPane.add(familyNameLabel, 0, row);
        layoutPane.add(familyNameField, 1, row);
        row++;

        emailField.setText(controller.getAccountController().getAccountEmail());
        emailField.setOnKeyTyped(e -> buttonCheck(emailField, applyButton));
        layoutPane.add(emailLabel, 0, row);
        layoutPane.add(emailField, 1, row);
        row++;

        phoneNumberField.setText(controller.getAccountController().getAccountPhoneNumber());
        phoneNumberField.setOnKeyTyped(e -> buttonCheckInt(phoneNumberField, applyButton));
        layoutPane.add(phoneNumberLabel, 0, row);
        layoutPane.add(phoneNumberField, 1, row);
        row++;

        if (!(controller.getAccount() instanceof Supervisor)) {
            creditField.setText(String.valueOf(controller.getAccountController().getAccountCredit()));
            creditLabel.setOnKeyTyped(e -> buttonCheckInt(creditField, applyButton));
            layoutPane.add(creditLabel, 0, row);
            layoutPane.add(creditField, 1, row);
            row++;
            if (controller.getAccount() instanceof Supplier) {
                creditField.setDisable(true);
            }
        }

        if (controller.getAccount() instanceof Supplier) {
            nameOfCompanyField.setText(controller.getAccountController().getAccountNameOfCompany());
            nameOfCompanyField.setOnKeyTyped(e -> buttonCheck(nameOfCompanyField, applyButton));
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

    private void buttonCheck(TextField textField, Button applyButton) {
        applyButton.setDisable(textField.getText() == null || textField.getText().trim().length() == 0);
    }

    private void buttonCheckInt(TextField textField, Button applyButton) {
        if (textField.getText().matches("\\d+")) {
            buttonCheck(textField, applyButton);
        } else {
            applyButton.setDisable(true);
        }
    }
}
