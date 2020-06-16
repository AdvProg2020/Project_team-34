package gui.profile;

import controller.Controller;
import gui.GMenu;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SupplierProfileGMenu extends GMenu {
    public SupplierProfileGMenu(GMenu parentMenu, Stage stage, Controller controller) {
        super("Profile, Supplier", parentMenu, stage, controller);
    }

    @Override
    protected Scene createScene() {
        GridPane backgroundLayout = new GridPane();
        HBox mainPane = new HBox();
        VBox buttonPane = new VBox();
        VBox viewPane = new VBox();


        buttonPane.setAlignment(Pos.CENTER);
        buttonPane.setSpacing(10);
        buttonPane.setPadding(new Insets(10, 10 , 10 , 10));
        buttonPane.getChildren().addAll();
        buttonPane.setStyle("-fx-background-color : #f8e8e2");

        mainPane.getChildren().addAll( GMenu.createViewPersonalInfo(controller.getAccountController().getAccount()), buttonPane);

        backgroundLayout.add(createHeader(), 0,0);
        backgroundLayout.add(mainPane, 0, 1);
        backgroundLayout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(backgroundLayout);
        return scene;
    }
}
