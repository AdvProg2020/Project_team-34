package gui.paymentMenu;

import controller.Controller;
import gui.GMenu;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PaymentMenuForCustomer extends GMenu {

    private int afterPay;

    private int amount;

    public PaymentMenuForCustomer(GMenu parentMenu, Stage stage, Controller controller, int amount, int afterPay) {
        super("Payment Menu", parentMenu, stage, controller);
        this.amount = amount;
        this.afterPay = afterPay;
    }

    @Override
    protected Scene createScene() {
        Label label = new Label("Pay $" + amount);
        label.setStyle("-fx-font-weight: bolder");
        TextField username = new TextField();
        TextField bankAccountNumber = new TextField();
        PasswordField password = new PasswordField();
        CheckBox useDefaultAccount = new CheckBox("Use my default account");
        Button pay = new Button("Pay");
        HBox buttonBox = new HBox();
        VBox paymentInfoBox = new VBox();
        GridPane backgroundLayout = new GridPane();
        Scene scene = new Scene(backgroundLayout);

        username.setPromptText("Username");
        username.setAlignment(Pos.CENTER);
        username.setMinWidth(300);
        username.setMaxWidth(300);
        username.setStyle("-fx-border-color: #4678c8; -fx-border-width: 2px; -fx-border-radius: 15px; " +
                "-fx-background-radius: 15px");
        bankAccountNumber.setPromptText("Account number");
        bankAccountNumber.setAlignment(Pos.CENTER);
        bankAccountNumber.setMinWidth(300);
        bankAccountNumber.setMaxWidth(300);
        bankAccountNumber.setStyle("-fx-border-color: #4678c8; -fx-border-width: 2px; -fx-border-radius: 15px; " +
                "-fx-background-radius: 15px");
        password.setPromptText("Password");
        password.setAlignment(Pos.CENTER);
        password.setMinWidth(300);
        password.setMaxWidth(300);
        password.setStyle("-fx-border-color: #4678c8; -fx-border-width: 2px; -fx-border-radius: 15px; " +
                "-fx-background-radius: 15px");
        pay.setDefaultButton(true);
        pay.setPrefWidth(100);
        buttonBox.getChildren().add(pay);
        paymentInfoBox.getChildren().addAll(createHeader(), label, username, bankAccountNumber, password,
                useDefaultAccount, buttonBox);
        backgroundLayout.getChildren().addAll(paymentInfoBox);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);

        backgroundLayout.setAlignment(Pos.CENTER);
        paymentInfoBox.setSpacing(20);
        paymentInfoBox.setPadding(new Insets(10, 10, 10, 10));
        paymentInfoBox.setAlignment(Pos.CENTER);

        pay.setDisable(true);

        useDefaultAccount.setOnAction(e -> {
            username.clear();
            bankAccountNumber.clear();
            password.clear();
            boolean isSelected = useDefaultAccount.isSelected();
            pay.setDisable(!isSelected);
            username.setDisable(isSelected);
            bankAccountNumber.setDisable(isSelected);
            password.setDisable(isSelected);
        });

        username.setOnKeyTyped(e -> {
            pay.setDisable(username.getText().trim().equals("") || password.getText().trim().equals("") ||
                    bankAccountNumber.getText().trim().equals(""));
        });

        password.setOnKeyTyped(e -> {
            pay.setDisable(username.getText().trim().equals("") || password.getText().trim().equals("") ||
                    bankAccountNumber.getText().trim().equals(""));
        });

        return scene;
    }
}