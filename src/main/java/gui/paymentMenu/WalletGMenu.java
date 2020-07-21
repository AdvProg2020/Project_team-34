package gui.paymentMenu;

import account.Account;
import account.Supplier;
import controller.Controller;
import exceptionalMassage.ExceptionalMassage;
import gui.GMenu;
import gui.alerts.AlertBox;
import gui.profile.SupplierProfileGMenu;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class WalletGMenu extends GMenu {
    public WalletGMenu(GMenu parentMenu, Stage stage, Controller controller) {
        super("Your wallet", parentMenu, stage, controller);
    }

    @Override
    protected Scene createScene() {
        Label label = new Label("Your credit is $" +
                (controller.getAccount() == null ? 0 : controller.getAccount().getCredit()) + " now.");
        label.setStyle("-fx-font-size: 18px; -fx-font-weight: bolder;");
        VBox vBox = new VBox();
        vBox.getChildren().addAll(createHeader(), label, depositBox(), withdrawBox());
        GridPane background = new GridPane();
        background.getChildren().addAll(vBox);
        vBox.setAlignment(Pos.CENTER);
        background.setAlignment(Pos.CENTER);
        return new Scene(background);
    }

    private GridPane depositBox() {
        Label deposit = new Label("   Charge your wallet");
        deposit.setStyle("-fx-font-size: 12px; -fx-font-weight: bolder;");
        GridPane box = new GridPane();
        TextField username = new TextField();
        TextField accountNumber = new TextField();
        PasswordField passwordField = new PasswordField();
        CheckBox useMyDefault = new CheckBox("Use my default instead");
        TextField amount = new TextField();
        Button button = new Button("Charge wallet");

        username.setStyle("-fx-alignment: center; -fx-max-width: 200; -fx-min-width: 200; -fx-pref-width: 200; " +
                "-fx-border-radius: 15; -fx-background-radius: 15; -fx-border-color: #4678c8; -fx-border-width: 2px;");
        accountNumber.setStyle("-fx-alignment: center; -fx-max-width: 200; -fx-min-width: 200; -fx-pref-width: 200; " +
                "-fx-border-radius: 15; -fx-background-radius: 15; -fx-border-color: #4678c8; -fx-border-width: 2px;");
        passwordField.setStyle("-fx-alignment: center; -fx-max-width: 200; -fx-min-width: 200; -fx-pref-width: 200; " +
                "-fx-border-radius: 15; -fx-background-radius: 15; -fx-border-color: #4678c8; -fx-border-width: 2px;");
        amount.setStyle("-fx-alignment: center; -fx-max-width: 200; -fx-min-width: 200; -fx-pref-width: 200; " +
                "-fx-border-radius: 15; -fx-background-radius: 15; -fx-border-color: #4678c8; -fx-border-width: 2px;");
        button.setStyle("-fx-alignment: center; -fx-max-width: 200; -fx-min-width: 200; -fx-pref-width: 200; " +
                "-fx-border-radius: 15; -fx-background-radius: 15; -fx-border-width: 2px;");

        username.setPromptText("Username");
        accountNumber.setPromptText("Account number");
        passwordField.setPromptText("Password");
        amount.setPromptText("Amount of payment");

        box.add(deposit, 0, 0);
        box.add(username, 0, 1);
        box.add(passwordField, 1, 1);
        box.add(accountNumber, 0, 2);
        box.add(useMyDefault, 1, 2);
        box.add(amount, 0, 3);
        box.add(button, 1, 3);

        box.setHgap(40);
        box.setVgap(8);
        box.setPadding(new Insets(20, 20, 20, 20));
        box.setAlignment(Pos.CENTER);

        useMyDefault.setOnAction(e -> {
            accountNumber.setDisable(useMyDefault.isSelected());
            username.setDisable(useMyDefault.isSelected());
            passwordField.setDisable(useMyDefault.isSelected());
            username.clear();
            passwordField.clear();
            accountNumber.clear();
            if (!useMyDefault.isSelected()) {
                button.setDisable(username.getText().equals("") || passwordField.getText().equals("") || !isInt(accountNumber) || !isInt(amount));
            } else {
                button.setDisable(!isInt(amount));
            }
        });

        button.setDisable(true);

        username.setOnKeyTyped(e -> {
            button.setDisable(username.getText().equals("") || passwordField.getText().equals("") || !isInt(accountNumber) || !isInt(amount));
        });

        passwordField.setOnKeyTyped(e -> {
            button.setDisable(username.getText().equals("") || passwordField.getText().equals("") || !isInt(accountNumber) || !isInt(amount));
        });

        accountNumber.setOnKeyTyped(e -> {
            button.setDisable(username.getText().equals("") || passwordField.getText().equals("") || !isInt(accountNumber) || !isInt(amount));
        });

        amount.setOnKeyTyped(e -> {
            if (!useMyDefault.isSelected()) {
                button.setDisable(username.getText().equals("") || passwordField.getText().equals("") || !isInt(accountNumber) || !isInt(amount));
            } else {
                button.setDisable(!isInt(amount));
            }
        });

        button.setOnAction(e -> {
            if (useMyDefault.isSelected()) {
                try {
                    controller.getAccountController().controlPay(Integer.parseInt(amount.getText()));
                    stage.setScene(getScene());
                } catch (ExceptionalMassage exceptionalMassage) {
                    new AlertBox(this, exceptionalMassage, controller).showAndWait();
                }
            } else {
                try {
                    controller.getAccountController().controlPay(username.getText(), Integer.parseInt(accountNumber.getText()),
                            passwordField.getText() ,Integer.parseInt(amount.getText()));
                    stage.setScene(getScene());
                } catch (ExceptionalMassage exceptionalMassage) {
                    new AlertBox(this, exceptionalMassage, controller).showAndWait();
                }
            }
        });

        return box;
    }

    private GridPane withdrawBox() {
        GridPane box = new GridPane();
        Label deposit = new Label("   Uncharge your wallet");
        deposit.setStyle("-fx-font-size: 12px; -fx-font-weight: bolder;");
        TextField accountNumber = new TextField();
        CheckBox useMyDefault = new CheckBox("Use my default instead");
        TextField amount = new TextField();
        Button button = new Button("Uncharge wallet");

        accountNumber.setStyle("-fx-alignment: center; -fx-max-width: 200; -fx-min-width: 200; -fx-pref-width: 200; " +
                "-fx-border-radius: 15; -fx-background-radius: 15; -fx-border-color: firebrick; -fx-border-width: 2px;");
        amount.setStyle("-fx-alignment: center; -fx-max-width: 200; -fx-min-width: 200; -fx-pref-width: 200; " +
                "-fx-border-radius: 15; -fx-background-radius: 15; -fx-border-color: firebrick; -fx-border-width: 2px;");
        button.setStyle("-fx-alignment: center; -fx-max-width: 200; -fx-min-width: 200; -fx-pref-width: 200; " +
                "-fx-border-radius: 15; -fx-background-radius: 15; -fx-border-width: 2px;");

        accountNumber.setPromptText("Account number");
        amount.setPromptText("Amount of payment");

        box.add(deposit, 0, 0);
        box.add(accountNumber, 0, 1);
        box.add(useMyDefault, 1, 1);
        box.add(amount, 0, 2);
        box.add(button, 1, 2);

        box.setHgap(40);
        box.setVgap(8);
        box.setPadding(new Insets(20, 20, 20, 20));
        box.setAlignment(Pos.CENTER);

        button.setDisable(true);

        useMyDefault.setOnAction(e -> {
            accountNumber.setDisable(useMyDefault.isSelected());
            accountNumber.clear();
            if (!useMyDefault.isSelected()) {
                button.setDisable(!isInt(accountNumber) || !isInt(amount));
            } else {
                button.setDisable(!isInt(amount));
            }
        });

        accountNumber.setOnKeyTyped(e -> {
            button.setDisable(!isInt(accountNumber) || !isInt(amount));
        });

        amount.setOnKeyTyped(e -> {
            if (!useMyDefault.isSelected()) {
                button.setDisable(!isInt(accountNumber) || !isInt(amount));
            } else {
                button.setDisable(!isInt(amount));
            }
        });

        button.setOnAction(e -> {
            if (useMyDefault.isSelected()) {
                try {
                    controller.getAccountController().controlPayBack(Integer.parseInt(amount.getText()));
                    stage.setScene(getScene());
                } catch (ExceptionalMassage exceptionalMassage) {
                    new AlertBox(this, exceptionalMassage, controller).showAndWait();
                }
            } else {
                try {
                    controller.getAccountController().controlPayBack(Integer.parseInt(accountNumber.getText()),
                            Integer.parseInt(amount.getText()));
                    stage.setScene(getScene());
                } catch (ExceptionalMassage exceptionalMassage) {
                    new AlertBox(this, exceptionalMassage, controller).showAndWait();
                }
            }
        });

        return box;
    }

    public boolean isInt(TextField textField) {
        if (textField.getText().equals(""))
            return false;
        try {
            int i = Integer.parseInt(textField.getText());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
