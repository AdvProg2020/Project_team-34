package gui.profile;

import controller.Controller;
import exceptionalMassage.ExceptionalMassage;
import gui.GMenu;
import gui.alerts.AlertBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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

        Button viewProductsForThisSupplier = new Button("View My Products");
        GMenu.addStyleToButton(viewProductsForThisSupplier);
        viewProductsForThisSupplier.setOnMouseClicked(e->{
            stage.setScene(new ManageProductsSupplierGMenu(this, stage, controller).createScene());
        });

        Button viewRequestsForThisSupplier = new Button("View My Requests");
        GMenu.addStyleToButton(viewRequestsForThisSupplier);
        viewRequestsForThisSupplier.setOnMouseClicked(e->{
            stage.setScene(new ManageRequestForSupplierGMenu(this, stage, controller).createScene());
        });

        Button viewLogButton = new Button("View Log");
        GMenu.addStyleToButton(viewLogButton);
        viewLogButton.setOnMouseClicked(e->{
            stage.setScene(new ViewLogsForSupplierGMenu(this, stage, controller).createScene());
        });

        Button editPersonalInfoButton = new Button("Edit Personal Info");
        GMenu.addStyleToButton(editPersonalInfoButton);
        editPersonalInfoButton.setOnMouseClicked(e->{
            stage.setScene(new EditPersonalInfoGMenu(this, stage, controller).createScene());
        });

        Button manageOffsButton = new Button("Manage Offs");
        GMenu.addStyleToButton(manageOffsButton);
        manageOffsButton.setOnMouseClicked(e->{
            stage.setScene(new ViewOffsGMenu(this, stage, controller).getScene());
        });

        Button manageAuctionsButton = new Button("Manage Auctions");
        GMenu.addStyleToButton(manageAuctionsButton);
        manageAuctionsButton.setOnMouseClicked(e->{
            stage.setScene(new ManageAuctionsForSupplierGMenu(this, stage, controller).getScene());
        });

        buttonPane.setAlignment(Pos.CENTER);
        buttonPane.setSpacing(10);
        buttonPane.setPadding(new Insets(10, 10 , 10 , 10));
        buttonPane.getChildren().addAll(viewProductsForThisSupplier,viewRequestsForThisSupplier, viewLogButton, editPersonalInfoButton, manageOffsButton, manageAuctionsButton);
        buttonPane.setStyle("-fx-background-color : #f8e8e2");

        try {
            mainPane.getChildren().addAll( GMenu.createViewPersonalInfo(controller.getAccountController().getAccount()), buttonPane);
        } catch (ExceptionalMassage exceptionalMassage) {
            new AlertBox(this, exceptionalMassage, controller).showAndWait();
        }
        mainPane.setAlignment(Pos.CENTER);

        VBox headerBackground = new VBox();
        headerBackground.setStyle("-fx-background-color: #4677c8");
        headerBackground.getChildren().add(createHeader());
        backgroundLayout.add(headerBackground, 0,0);
        backgroundLayout.add(mainPane, 0, 1);
        backgroundLayout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(backgroundLayout);
        return scene;
    }
}
