package gui.profile;

import account.Customer;
import account.Supervisor;
import account.Supplier;
import controller.Controller;
import exceptionalMassage.ExceptionalMassage;
import gui.GMenu;
import gui.alerts.AlertBox;
import gui.alerts.ChoiceBox;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ManageUsersGMenu extends GMenu {
    public ManageUsersGMenu(GMenu parentMenu, Stage stage, Controller controller) {
        super("Manage Users", parentMenu, stage, controller);
    }

    @Override
    protected Scene createScene() {
        VBox mainLayout = new VBox();
        GridPane backgroundLayout = new GridPane();
        TableView<Supervisor> supervisorTableView = new TableView<>();
        TableView<Supplier> supplierTableView = new TableView<>();
        TableView<Customer> customerTableView = new TableView<>();
        HBox deleteUserBox = new HBox();
        TextField deletingUsername = new TextField();
        Button deleteButton = new Button("Delete");

        deletingUsername.setPromptText("Delete username");
        deletingUsername.setAlignment(Pos.CENTER);
        deleteButton.setOnAction(e -> {
            ChoiceBox choiceBox = new ChoiceBox(this, "Are you sure?", "Yes", "No", controller);
            choiceBox.showAndWait();
            if (choiceBox.getAnswer()) {
                try {
                    controller.getAccountController().controlDeleteUser(deletingUsername.getText());
                } catch (ExceptionalMassage exceptionalMassage) {
                    new AlertBox(this, exceptionalMassage, controller);
                }
            }
        });
        deleteUserBox.setSpacing(10);
        deleteUserBox.getChildren().addAll(deletingUsername, deleteButton);

        mainLayout.setSpacing(10);
        mainLayout.getChildren().addAll(createHeader(), createSupervisorBox(), deleteUserBox, new Label("Supervisors:"),
                supervisorTableView, new Label("Suppliers:"), supplierTableView, new Label("Customers"),
                customerTableView);

        return null;
    }

    private VBox createSupervisorBox() {
        VBox mainLayout = new VBox();
        TextField usernameField = new TextField();
        PasswordField passwordField = new PasswordField();
        TextField firstName = new TextField();
        TextField lastName = new TextField();
        TextField email = new TextField();
        TextField phoneNumber = new TextField();
        Button done = new Button("Create");

        done.setDisable(true);

        done.setOnAction(e -> {
            try {
                controller.getAccountController().controlCreateAccount(usernameField.getText(), "supervisor",
                        firstName.getText(), lastName.getText(), email.getText(), phoneNumber.getText(),
                        passwordField.getText(), 0, null);
            } catch (ExceptionalMassage exceptionalMassage) {
                new AlertBox(this, exceptionalMassage, controller);
            }
        });

        mainLayout.setOnKeyTyped(e -> {
            if (usernameField.getText().trim().length() != 0 && firstName.getText().trim().length() != 0 &&
                    lastName.getText().trim().length() != 0 && passwordField.getText().trim().length() != 0 &&
                    email.getText().trim().length() != 0 && phoneNumber.getText().trim().length() != 0) {
                done.setDisable(false);
            } else {
                done.setDisable(true);
            }
        });

        mainLayout.getChildren().addAll(usernameField, passwordField, firstName, lastName, email, phoneNumber, done);
        return mainLayout;
    }
}
