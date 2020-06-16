package gui.profile;

import controller.Controller;
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


    public CustomerProfileGMenu(GMenu parentMenu, Stage stage, Controller controller) {
        super("Profile", parentMenu, stage, controller);

    }

    @Override
    protected Scene createScene() {

        GridPane backgroundLayout = new GridPane();
        HBox mainPane = new HBox();
        VBox buttonPane = new VBox();
        VBox viewPane = new VBox();

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


        Button editPersonalInfoButton = new Button("Edit Personal Info");
        editPersonalInfoButton.getStylesheets().add(new File("src/main/resources/css/Style.css").toURI().toString());
        editPersonalInfoButton.getStyleClass().add("button");

        editPersonalInfoButton.setOnMouseClicked(e->{
            stage.setScene(new EditPersonalInfoGMenu(this, stage, controller).createScene());
        });


        buttonPane.setAlignment(Pos.CENTER);
        buttonPane.setSpacing(10);
        buttonPane.setPadding(new Insets(10, 10 , 10 , 10));
        buttonPane.getChildren().addAll(viewCartButton, viewOrdersButton, viewDiscountCodesButton,editPersonalInfoButton );

        buttonPane.setStyle("-fx-background-color : #f8e8e2");

        //int balance = controller.getAccountController().getAccountCredit();
        Label balanceLabel = new Label(String.valueOf(controller.getAccountController().getAccount().getCredit()));

        viewPane.getChildren().addAll(balanceLabel);

        mainPane.getChildren().addAll( GMenu.createViewPersonalInfo(controller.getAccountController().getAccount()), buttonPane);
        mainPane.setSpacing(10);
        mainPane.setPadding(new Insets(10, 10 , 10 , 10));
        mainPane.setAlignment(Pos.CENTER);

        backgroundLayout.add(createHeader(), 0,0);
        backgroundLayout.add(mainPane, 0, 1);
        backgroundLayout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(backgroundLayout);
        return scene;
    }
}
