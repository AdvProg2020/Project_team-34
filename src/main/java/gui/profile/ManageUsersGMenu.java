package gui.profile;

import account.Account;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * @author Aryan Ahadinia
 * @since 0.0.2
 */

public class ManageUsersGMenu extends GMenu {
    public ManageUsersGMenu(GMenu parentMenu, Stage stage, Controller controller) {
        super("Manage Users", parentMenu, stage, controller);
    }

    @Override
    protected Scene createScene() {
        VBox mainLayout = new VBox();
        GridPane backgroundLayout = new GridPane();
        TableView<Supplier> supplierTableView = supplierTable();
        TableView<Customer> customerTableView = customerTable();
        TableView<Supervisor> supervisorTableView = supervisorTable();
        HBox deleteUserBox = new HBox();
        TextField deletingUsername = new TextField();
        Button deleteButton = new Button("Delete");
        Scene scene = new Scene(backgroundLayout);

        deletingUsername.setPromptText("Delete username");
        deletingUsername.setAlignment(Pos.CENTER);
        deleteButton.setOnAction(e -> {
            ChoiceBox choiceBox = new ChoiceBox(this, "Are you sure?", "Yes", "No", controller);
            choiceBox.showAndWait();
            if (choiceBox.getAnswer()) {
                try {
                    controller.getAccountController().controlDeleteUser(deletingUsername.getText());
                } catch (ExceptionalMassage exceptionalMassage) {
                    new AlertBox(this, exceptionalMassage, controller).showAndWait();
                }
            }
            stage.setScene(createScene());
        });
        deleteUserBox.setSpacing(10);
        deleteUserBox.setAlignment(Pos.CENTER);
        deleteUserBox.getChildren().addAll(deletingUsername, deleteButton);

        mainLayout.setSpacing(10);
        mainLayout.getChildren().addAll(createHeader(), createSupervisorBox(this), deleteUserBox, new Label("Supervisors:"),
                supervisorTableView, new Label("Suppliers:"), supplierTableView, new Label("Customers"),
                customerTableView);

        ScrollPane scrollPane = new ScrollPane(mainLayout);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        backgroundLayout.getChildren().add(scrollPane);
        backgroundLayout.setAlignment(Pos.CENTER);

        return scene;
    }

    private TableView<Supervisor> supervisorTable() {
        TableColumn<Supervisor, String> username = new TableColumn<>("Username");
        username.setCellValueFactory(new PropertyValueFactory<>("userName"));
        TableColumn<Supervisor, String> firstName = new TableColumn<>("First Name");
        firstName.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<Supervisor, String> lastName = new TableColumn<>("Last Name");
        lastName.setCellValueFactory(new PropertyValueFactory<>("familyName"));
        TableColumn<Supervisor, String> email = new TableColumn<>("Email");
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        TableColumn<Supervisor, String> phoneNumber = new TableColumn<>("Phone Number");
        phoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        TableView<Supervisor> supervisorTableView = new TableView<>();
        supervisorTableView.setItems(Account.getSupervisorsObservableList());
        supervisorTableView.getColumns().addAll(username, firstName, lastName, email, phoneNumber);
        supervisorTableView.setPrefWidth(800);
        return supervisorTableView;
    }

    private TableView<Supplier> supplierTable() {
        TableColumn<Supplier, String> username = new TableColumn<>("Username");
        username.setCellValueFactory(new PropertyValueFactory<>("userName"));
        TableColumn<Supplier, String> firstName = new TableColumn<>("First Name");
        firstName.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<Supplier, String> lastName = new TableColumn<>("Last Name");
        lastName.setCellValueFactory(new PropertyValueFactory<>("familyName"));
        TableColumn<Supplier, String> email = new TableColumn<>("Email");
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        TableColumn<Supplier, String> phoneNumber = new TableColumn<>("Phone Number");
        phoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        TableColumn<Supplier, String> nameOfCompany = new TableColumn<>("Name Of Company");
        nameOfCompany.setCellValueFactory(new PropertyValueFactory<>("nameOfCompany"));
        TableColumn<Supplier, Integer> credit = new TableColumn<>("Credit");
        credit.setCellValueFactory(new PropertyValueFactory<>("credit"));
        TableView<Supplier> supplierTableView = new TableView<>();
        supplierTableView.setItems(Account.getSuppliersObservableList());
        supplierTableView.setPrefWidth(800);
        supplierTableView.getColumns().addAll(username, firstName, lastName, email, phoneNumber, nameOfCompany, credit);
        return supplierTableView;
    }

    private TableView<Customer> customerTable() {
        TableColumn<Customer, String> username = new TableColumn<>("Username");
        username.setCellValueFactory(new PropertyValueFactory<>("userName"));
        TableColumn<Customer, String> firstName = new TableColumn<>("First Name");
        firstName.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<Customer, String> lastName = new TableColumn<>("Last Name");
        lastName.setCellValueFactory(new PropertyValueFactory<>("familyName"));
        TableColumn<Customer, String> email = new TableColumn<>("Email");
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        TableColumn<Customer, String> phoneNumber = new TableColumn<>("Phone number");
        phoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        TableColumn<Customer, Integer> credit = new TableColumn<>("Credit");
        credit.setCellValueFactory(new PropertyValueFactory<>("credit"));
        TableView<Customer> customerTableView = new TableView<>();
        customerTableView.setItems(Account.getCustomersObservableList());
        customerTableView.getColumns().addAll(username, firstName, lastName, email, phoneNumber, credit);
        customerTableView.setPrefWidth(800);
        return customerTableView;
    }
}
