package gui.profile;

import account.Customer;
import account.Supplier;
import controller.Controller;
import gui.GMenu;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import product.Product;

/**
 * @since 0.0.2
 * @author Aryan Ahadinia
 */

public class ViewProductCustomers extends GMenu {
    private final Product product;
    private final Supplier supplier;

    public ViewProductCustomers(String menuName, GMenu parentMenu, Stage stage, Controller controller, Product product, Supplier supplier) {
        super(menuName, parentMenu, stage, controller);
        this.product = product;
        this.supplier = supplier;
    }

    @Override
    protected Scene createScene() {
        VBox mainLayout = new VBox();
        ScrollPane scrollLayout = new ScrollPane(customerTable());
        mainLayout.getChildren().addAll(createHeader(), scrollLayout);
        GridPane backgroundLayout = new GridPane();
        backgroundLayout.getChildren().add(mainLayout);
        backgroundLayout.setAlignment(Pos.CENTER);
        scrollLayout.setMinWidth(1000);
        return new Scene(scrollLayout);
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
        customerTableView.setItems(controller.getProductController().getCustomersBoughtProductObservable(product, supplier));
        customerTableView.getColumns().addAll(username, firstName, lastName, email, phoneNumber, credit);
        customerTableView.setPrefWidth(800);
        return customerTableView;
    }
}
