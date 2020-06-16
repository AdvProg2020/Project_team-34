package gui.profile;

import controller.Controller;
import gui.GMenu;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SupervisorProfileGMenu extends GMenu {
    public SupervisorProfileGMenu(GMenu parentMenu, Stage stage, Controller controller) {
        super("Profile, Supervisor", parentMenu, stage, controller);
    }

    @Override
    protected Scene createScene() {
        GridPane backgroundLayout = new GridPane();
        HBox mainPane = new HBox();
        VBox buttonPane = new VBox();
        VBox viewPane = new VBox();


        Button manageCodedDiscountButton = new Button("Manage Coded Discount");
        GMenu.addStyleToButton(manageCodedDiscountButton);
        manageCodedDiscountButton.setOnMouseClicked(e->{
            stage.setScene(new ViewDiscountCodesG(this, stage, controller).createScene());
        });

        Button manageAccountButton = new Button("Manage Accounts");
        GMenu.addStyleToButton(manageAccountButton);

        Button manageCategoryButton = new Button("Manage Category");
        GMenu.addStyleToButton(manageCategoryButton);
        manageCategoryButton.setOnMouseClicked(e->{
            stage.setScene(new ManageCategoriesGMenu(this, stage, controller).createScene());
        });

        buttonPane.setAlignment(Pos.CENTER);
        buttonPane.setSpacing(10);
        buttonPane.setPadding(new Insets(10, 10 , 10 , 10));
        buttonPane.getChildren().addAll(manageCodedDiscountButton, manageAccountButton, manageCategoryButton);
        buttonPane.setStyle("-fx-background-color : #f8e8e2");

        mainPane.getChildren().addAll( GMenu.createViewPersonalInfo(controller.getAccountController().getAccount()), buttonPane);

        backgroundLayout.add(createHeader(), 0,0);
        backgroundLayout.add(mainPane, 0, 1);
        backgroundLayout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(backgroundLayout);
        return scene;
    }
}
