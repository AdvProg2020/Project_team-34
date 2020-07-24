package gui.profile;

import account.*;
import controller.Controller;
import exceptionalMassage.ExceptionalMassage;
import gui.GMenu;
import gui.alerts.AlertBox;
import gui.alerts.ChoiceBox;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * @author Aryan Ahadinia
 * @since 0.0.2
 */
public class ManageUsersGMenu extends GMenu {
    private ArrayList<Supervisor> onlineSupervisors;
    private ArrayList<Supervisor> allSupervisors;
    private ArrayList<Supporter> onlineSupporters;
    private ArrayList<Supporter> allSupporters;
    private ArrayList<Customer> onlineCustomers;
    private ArrayList<Customer> allCustomers;
    private ArrayList<Supplier> onlineSuppliers;
    private ArrayList<Supplier> allSuppliers;

    private final ScrollPane accountTables;

    private boolean updatePermission;

    public ManageUsersGMenu(GMenu parentMenu, Stage stage, Controller controller) {
        super("Manage User", parentMenu, stage, controller);
        this.onlineSupervisors = new ArrayList<>();
        this.allSupervisors = new ArrayList<>();
        this.onlineSupporters = new ArrayList<>();
        this.allSupporters = new ArrayList<>();
        this.onlineCustomers = new ArrayList<>();
        this.allCustomers = new ArrayList<>();
        this.onlineSuppliers = new ArrayList<>();
        this.allSuppliers = new ArrayList<>();
        this.accountTables = createAccountsTables();
        this.updatePermission = true;
        Task updater = new Task() {
            @Override
            protected Object call() throws Exception {
                while (updatePermission) {
                    Platform.runLater(() -> update());
                    Thread.sleep(60000);
                }
                return null;
            }
        };
        new Thread(updater).start();
    }

    @Override
    protected Scene createScene() {
        VBox mainLayout = new VBox();
        mainLayout.setSpacing(20);

        HBox deleteUserBox = new HBox();
        TextField deletingUsername = new TextField();
        Button deleteButton = new Button("Delete");
        deletingUsername.setPromptText("Delete username");
        deletingUsername.setAlignment(Pos.CENTER);
        deleteUserBox.setSpacing(10);
        deleteUserBox.setAlignment(Pos.CENTER);
        deleteUserBox.getChildren().addAll(deletingUsername, deleteButton);
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

        GridPane backgroundLayout = new GridPane();

        mainLayout.getChildren().addAll(createSupervisorBox(this, true), deleteUserBox);
        mainLayout.getChildren().addAll(accountTables);

        backgroundLayout.getChildren().add(mainLayout);
        backgroundLayout.setAlignment(Pos.CENTER);
        backgroundLayout.setPadding(new Insets(10, 0, 0, 0));

        backgroundLayout.setMaxHeight(800);

        return new Scene(backgroundLayout);
    }

    @Override
    public void showAndWait() {
        stage.initModality(Modality.APPLICATION_MODAL);
        Image logoImage = null;
        try {
            logoImage = new Image(new FileInputStream("./src/main/resources/header/Logo.png"));
        } catch (FileNotFoundException e) {
            System.err.println("Logo not found");
        }
        stage.setOnCloseRequest(e -> {
            e.consume();
            updatePermission = false;
            stage.close();
        });
        stage.getIcons().add(logoImage);
        stage.setScene(createScene());
        stage.showAndWait();
    }

    private void update() {
        try {
            onlineSupervisors = controller.getAccountController().getOnlineSupervisors();
        } catch (ExceptionalMassage exceptionalMassage) {
            onlineSupervisors = new ArrayList<>();
        }
        try {
            allSupervisors = controller.getAccountController().getSupervisorObservableList();
        } catch (ExceptionalMassage exceptionalMassage) {
            allSupervisors = new ArrayList<>();
        }
        try {
            onlineSupporters = controller.getAccountController().getOnlineSupporters();
        } catch (ExceptionalMassage exceptionalMassage) {
            onlineSupporters = new ArrayList<>();
        }
        try {
            allSupporters = controller.getAccountController().getSupporterObservableList();
        } catch (ExceptionalMassage exceptionalMassage) {
            allSupporters = new ArrayList<>();
        }
        try {
            onlineCustomers = controller.getAccountController().getOnlineCustomers();
        } catch (ExceptionalMassage exceptionalMassage) {
            onlineCustomers = new ArrayList<>();
        }
        try {
            allCustomers = controller.getAccountController().getCustomerObservableList();
        } catch (ExceptionalMassage exceptionalMassage) {
            allCustomers = new ArrayList<>();
        }
        try {
            onlineSuppliers = controller.getAccountController().getOnlineSuppliers();
        } catch (ExceptionalMassage exceptionalMassage) {
            onlineSuppliers = new ArrayList<>();
        }
        try {
            allSuppliers = controller.getAccountController().getSupplierObservableList();
        } catch (ExceptionalMassage exceptionalMassage) {
            allSuppliers = new ArrayList<>();
        }
        accountTables.setContent(createAccountsTables().getContent());
    }

    private ScrollPane createAccountsTables() {
        VBox mainLayout = new VBox();
        mainLayout.setSpacing(10);

        TableView<Supervisor> onlineSupervisors = onlineSupervisorTable(this.onlineSupervisors);
        TableView<Supervisor> offlineSupervisors = offlineSupervisorTable(this.allSupervisors, this.onlineSupervisors);
        TableView<Supporter> onlineSupporters = onlineSupporterTable(this.onlineSupporters);
        TableView<Supporter> offlineSupporters = offlineSupporterTable(this.allSupporters, this.onlineSupporters);
        TableView<Customer> onlineCustomers = onlineCustomerTable(this.onlineCustomers);
        TableView<Customer> offlineCustomers = offlineCustomersTable(this.allCustomers, this.onlineCustomers);
        TableView<Supplier> onlineSuppliers = onlineSupplierTable(this.onlineSuppliers);
        TableView<Supplier> offlineSuppliers = offlineSupplierTable(this.allSuppliers, this.onlineSuppliers);

        mainLayout.getChildren().addAll(new Label("Online Supervisors:"), onlineSupervisors);
        mainLayout.getChildren().addAll(new Label("Offline Supervisors"), offlineSupervisors);
        mainLayout.getChildren().addAll(new Label("Online Supporters"), onlineSupporters);
        mainLayout.getChildren().addAll(new Label("Offline Supporters"), offlineSupporters);
        mainLayout.getChildren().addAll(new Label("Online Suppliers"), onlineSuppliers);
        mainLayout.getChildren().addAll(new Label("Offline Suppliers"), offlineSuppliers);
        mainLayout.getChildren().addAll(new Label("Online Customers"), onlineCustomers);
        mainLayout.getChildren().addAll(new Label("Offline Customers"), offlineCustomers);

        ScrollPane scrollPane = new ScrollPane(mainLayout);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setMinWidth(815);
        return scrollPane;
    }

    private TableView<Supervisor> supervisorTable() {
        TableView<Supervisor> supervisorTableView = new TableView<>();
        TableColumn<Supervisor, String> username = new TableColumn<>("Username");
        username.setCellValueFactory(new PropertyValueFactory<>("userName"));
        supervisorTableView.getColumns().add(username);
        TableColumn<Supervisor, String> firstName = new TableColumn<>("First Name");
        firstName.setCellValueFactory(new PropertyValueFactory<>("name"));
        supervisorTableView.getColumns().add(firstName);
        TableColumn<Supervisor, String> lastName = new TableColumn<>("Last Name");
        lastName.setCellValueFactory(new PropertyValueFactory<>("familyName"));
        supervisorTableView.getColumns().add(lastName);
        TableColumn<Supervisor, String> email = new TableColumn<>("Email");
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        supervisorTableView.getColumns().add(email);
        TableColumn<Supervisor, String> phoneNumber = new TableColumn<>("Phone Number");
        phoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        supervisorTableView.getColumns().add(phoneNumber);
        supervisorTableView.setPrefWidth(800);
        return supervisorTableView;
    }

    private TableView<Supervisor> onlineSupervisorTable(ArrayList<Supervisor> onlineArray) {
        TableView<Supervisor> supervisorTable = supervisorTable();
        ObservableList<Supervisor> online = FXCollections.observableArrayList();
        online.addAll(onlineArray);
        supervisorTable.setItems(online);
        return supervisorTable;
    }

    private TableView<Supervisor> offlineSupervisorTable(ArrayList<Supervisor> all, ArrayList<Supervisor> onlineArray) {
        TableView<Supervisor> supervisorTable = supervisorTable();
        ArrayList<Supervisor> offlineArray = new ArrayList<>(all);
        offlineArray.removeAll(onlineArray);
        ObservableList<Supervisor> offline = FXCollections.observableArrayList();
        offline.addAll(offlineArray);
        supervisorTable.setItems(offline);
        return supervisorTable;
    }

    private TableView<Supporter> supporterTable() {
        TableView<Supporter> supporterTableView = new TableView<>();
        TableColumn<Supporter, String> username = new TableColumn<>("Username");
        username.setCellValueFactory(new PropertyValueFactory<>("userName"));
        supporterTableView.getColumns().add(username);
        TableColumn<Supporter, String> firstName = new TableColumn<>("First Name");
        firstName.setCellValueFactory(new PropertyValueFactory<>("name"));
        supporterTableView.getColumns().add(firstName);
        TableColumn<Supporter, String> lastName = new TableColumn<>("Last Name");
        lastName.setCellValueFactory(new PropertyValueFactory<>("familyName"));
        supporterTableView.getColumns().add(lastName);
        TableColumn<Supporter, String> email = new TableColumn<>("Email");
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        supporterTableView.getColumns().add(email);
        TableColumn<Supporter, String> phoneNumber = new TableColumn<>("Phone Number");
        phoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        supporterTableView.getColumns().add(phoneNumber);
        supporterTableView.setPrefWidth(800);
        return supporterTableView;
    }

    private TableView<Supporter> onlineSupporterTable(ArrayList<Supporter> onlineArray) {
        TableView<Supporter> supporterTable = supporterTable();
        ObservableList<Supporter> online = FXCollections.observableArrayList();
        online.addAll(onlineArray);
        supporterTable.setItems(online);
        return supporterTable;
    }

    private TableView<Supporter> offlineSupporterTable(ArrayList<Supporter> all, ArrayList<Supporter> onlineArray) {
        TableView<Supporter> supporterTable = supporterTable();
        ArrayList<Supporter> offlineArray = new ArrayList<>(all);
        offlineArray.removeAll(onlineArray);
        ObservableList<Supporter> offline = FXCollections.observableArrayList();
        offline.addAll(offlineArray);
        supporterTable.setItems(offline);
        return supporterTable;
    }

    private TableView<Supplier> supplierTable() {
        TableView<Supplier> supplierTableView = new TableView<>();
        TableColumn<Supplier, String> username = new TableColumn<>("Username");
        username.setCellValueFactory(new PropertyValueFactory<>("userName"));
        supplierTableView.getColumns().add(username);
        TableColumn<Supplier, String> firstName = new TableColumn<>("First Name");
        firstName.setCellValueFactory(new PropertyValueFactory<>("name"));
        supplierTableView.getColumns().add(firstName);
        TableColumn<Supplier, String> lastName = new TableColumn<>("Last Name");
        lastName.setCellValueFactory(new PropertyValueFactory<>("familyName"));
        supplierTableView.getColumns().add(lastName);
        TableColumn<Supplier, String> email = new TableColumn<>("Email");
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        supplierTableView.getColumns().add(email);
        TableColumn<Supplier, String> phoneNumber = new TableColumn<>("Phone Number");
        phoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        supplierTableView.getColumns().add(phoneNumber);
        TableColumn<Supplier, String> nameOfCompany = new TableColumn<>("Name Of Company");
        nameOfCompany.setCellValueFactory(new PropertyValueFactory<>("nameOfCompany"));
        supplierTableView.getColumns().add(nameOfCompany);
        TableColumn<Supplier, Integer> credit = new TableColumn<>("Credit");
        credit.setCellValueFactory(new PropertyValueFactory<>("credit"));
        supplierTableView.getColumns().add(credit);
        supplierTableView.setPrefWidth(800);
        return supplierTableView;
    }

    private TableView<Supplier> onlineSupplierTable(ArrayList<Supplier> onlineArray) {
        TableView<Supplier> supplierTable = supplierTable();
        ObservableList<Supplier> online = FXCollections.observableArrayList();
        online.addAll(onlineArray);
        supplierTable.setItems(online);
        return supplierTable;
    }

    private TableView<Supplier> offlineSupplierTable(ArrayList<Supplier> all, ArrayList<Supplier> onlineArray) {
        TableView<Supplier> supplierTable = supplierTable();
        ArrayList<Supplier> offlineArray = new ArrayList<>(all);
        offlineArray.removeAll(onlineArray);
        ObservableList<Supplier> offline = FXCollections.observableArrayList();
        offline.addAll(offlineArray);
        supplierTable.setItems(offline);
        return supplierTable;
    }

    private TableView<Customer> customerTable() {
        TableView<Customer> customerTableView = new TableView<>();
        TableColumn<Customer, String> username = new TableColumn<>("Username");
        username.setCellValueFactory(new PropertyValueFactory<>("userName"));
        customerTableView.getColumns().add(username);
        TableColumn<Customer, String> firstName = new TableColumn<>("First Name");
        firstName.setCellValueFactory(new PropertyValueFactory<>("name"));
        customerTableView.getColumns().add(firstName);
        TableColumn<Customer, String> lastName = new TableColumn<>("Last Name");
        lastName.setCellValueFactory(new PropertyValueFactory<>("familyName"));
        customerTableView.getColumns().add(lastName);
        TableColumn<Customer, String> email = new TableColumn<>("Email");
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        customerTableView.getColumns().add(email);
        TableColumn<Customer, String> phoneNumber = new TableColumn<>("Phone number");
        phoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        customerTableView.getColumns().add(phoneNumber);
        TableColumn<Customer, Integer> credit = new TableColumn<>("Credit");
        credit.setCellValueFactory(new PropertyValueFactory<>("credit"));
        customerTableView.getColumns().add(credit);
        customerTableView.setPrefWidth(800);
        return customerTableView;
    }

    private TableView<Customer> onlineCustomerTable(ArrayList<Customer> onlineArray) {
        TableView<Customer> customerTable = customerTable();
        ObservableList<Customer> online = FXCollections.observableArrayList();
        online.addAll(onlineArray);
        customerTable.setItems(online);
        return customerTable;
    }

    private TableView<Customer> offlineCustomersTable(ArrayList<Customer> all, ArrayList<Customer> onlineArray) {
        TableView<Customer> customerTable = customerTable();
        ArrayList<Customer> offlineArray = new ArrayList<>(all);
        offlineArray.removeAll(onlineArray);
        ObservableList<Customer> offline = FXCollections.observableArrayList();
        offline.addAll(offlineArray);
        customerTable.setItems(offline);
        return customerTable;
    }
}
