package gui.paymentMenu;

import controller.Controller;
import gui.GMenu;
import gui.profile.ViewLogsForCustomerGMenu;
import gui.profile.ViewProductCustomers;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PaymentMenuForCustomer extends GMenu {
    private final TextField username;
    private final PasswordField password;
    private final CheckBox useDefaultAccount;

    public final EventHandler<ActionEvent> inPurchase = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            stage.setScene(new ViewLogsForCustomerGMenu(null, stage, controller).getScene());
        }
    };

    public final EventHandler<ActionEvent> inCreditCharge = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {

        }
    };

    private final EventHandler<ActionEvent> onPay;

    public PaymentMenuForCustomer(GMenu parentMenu, Stage stage, Controller controller, int afterPay) {
        super("Payment Menu", parentMenu, stage, controller);
        username = new TextField();
        password = new PasswordField();
        useDefaultAccount = new CheckBox("Use my default account");
        if (afterPay == 1) {
            onPay = inPurchase;
        } else {
            onPay = inCreditCharge;
        }
    }

    @Override
    protected Scene createScene() {
        TextField username = new TextField();
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
        password.setPromptText("Password");
        password.setAlignment(Pos.CENTER);
        password.setMinWidth(300);
        password.setMaxWidth(300);
        password.setStyle("-fx-border-color: #4678c8; -fx-border-width: 2px; -fx-border-radius: 15px; " +
                "-fx-background-radius: 15px");
        pay.setDefaultButton(true);
        pay.setPrefWidth(100);
        buttonBox.getChildren().add(pay);
        paymentInfoBox.getChildren().addAll(createHeader(), username, password, useDefaultAccount, buttonBox);
        backgroundLayout.getChildren().addAll(paymentInfoBox);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);

        backgroundLayout.setAlignment(Pos.CENTER);
        paymentInfoBox.setSpacing(20);
        paymentInfoBox.setPadding(new Insets(10, 10, 10, 10));
        paymentInfoBox.setAlignment(Pos.CENTER);

        pay.setDisable(true);

        useDefaultAccount.setOnAction(e -> {
            username.clear();
            password.clear();
            boolean isSelected = useDefaultAccount.isSelected();
            pay.setDisable(!isSelected);
            username.setDisable(isSelected);
            password.setDisable(isSelected);
        });

        username.setOnKeyTyped(e -> {
            pay.setDisable(username.getText().trim().equals("") || password.getText().trim().equals(""));
        });

        password.setOnKeyTyped(e -> {
            pay.setDisable(username.getText().trim().equals("") || password.getText().trim().equals(""));
        });

        pay.setOnAction(onPay);

        return scene;
    }
}
