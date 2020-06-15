package gui.profile;

import gui.GMenu;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;

public class CustomerProfileGMenu extends GMenu {
    private final GridPane backgroundLayout;
    private final HBox mainPane;
    private final VBox buttonPane;
    private final VBox viewPane;


    public CustomerProfileGMenu(String menuName, GMenu parentMenu, Stage stage) {
        super(menuName, parentMenu, stage);
        this.backgroundLayout = new GridPane();
        this.mainPane = new HBox();
        this.buttonPane = new VBox();
        this.viewPane = new VBox();
    }

    @Override
    protected Scene createScene() {

        Button viewCartButton = new Button("View cart");
        viewCartButton.getStylesheets().add(new File("src/main/resources/css/Style.css").toURI().toString());
        viewCartButton.getStyleClass().add("button");

        Button viewOrdersButton = new Button("View Orders");
        viewOrdersButton.getStylesheets().add(new File("src/main/resources/css/Style.css").toURI().toString());
        viewOrdersButton.getStyleClass().add("button");

//        Button viewBalanceButton = new Button("View Balance");
//        viewBalanceButton.getStylesheets().add(new File("src/main/resources/css/Style.css").toURI().toString());
//        viewBalanceButton.getStyleClass().add("button");

        Button viewDiscountCodesButton = new Button("View Discount Codes");
        viewDiscountCodesButton.getStylesheets().add(new File("src/main/resources/css/Style.css").toURI().toString());
        viewDiscountCodesButton.getStyleClass().add("button");


        buttonPane.setAlignment(Pos.CENTER);
        buttonPane.setSpacing(10);
        buttonPane.setPadding(new Insets(10, 10 , 10 , 10));
        buttonPane.getChildren().addAll(viewCartButton, viewOrdersButton, viewDiscountCodesButton);

        buttonPane.setStyle("-fx-background-color : #f8e8e2");

        //int balance = controller.getAccountController().getAccountCredit();
        Label balanceLabel = new Label(String.valueOf(450));

        viewPane.getChildren().addAll(balanceLabel);

        mainPane.getChildren().addAll( buttonPane, viewPane, GMenu.createViewPersonalInfo(controller.getAccount()));
        mainPane.setSpacing(10);
        mainPane.setPadding(new Insets(10, 10 , 10 , 10));

        backgroundLayout.add(createHeader(), 0,0);
        backgroundLayout.add(mainPane, 0, 1);
        backgroundLayout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(backgroundLayout);
        return scene;
    }
}
