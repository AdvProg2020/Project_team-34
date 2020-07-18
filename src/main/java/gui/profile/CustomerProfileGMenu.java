package gui.profile;

import controller.Controller;
import discount.CodedDiscount;
import gui.GMenu;
import gui.cartMenu.CartGMenu;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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
        GMenu.addStyleToButton(viewCartButton);
        viewCartButton.setOnMouseClicked(e->{
            stage.setScene(new CartGMenu(this, stage, controller).getScene());
        });

        Button viewOrdersButton = new Button("View Orders");
        GMenu.addStyleToButton(viewOrdersButton);
        viewOrdersButton.setOnMouseClicked(e->{
            stage.setScene(new ViewLogsForCustomerGMenu(this, stage, controller).getScene());
        });

        Button viewDiscountCodesButton = new Button("View Discount Codes");
        GMenu.addStyleToButton(viewDiscountCodesButton);
        viewDiscountCodesButton.setOnMouseClicked(e->{
            stage.setScene(new ViewDiscountCodesForCustomerGMenu(this, stage, controller).getScene());
        });


        Button editPersonalInfoButton = new Button("Edit Personal Info");
        GMenu.addStyleToButton(editPersonalInfoButton);
        editPersonalInfoButton.setOnMouseClicked(e->{
            stage.setScene(new EditPersonalInfoGMenu(this, stage, controller).createScene());
        });

        Button chooseSupporterButton = new Button("Choose Supporter");
        GMenu.addStyleToButton(chooseSupporterButton);
        chooseSupporterButton.setOnAction(e->{
            stage.setScene(new ChooseSupporterGMenu(this, stage,controller).createScene());
        });


        buttonPane.setAlignment(Pos.CENTER);
        buttonPane.setSpacing(10);
        buttonPane.setPadding(new Insets(10, 10 , 10 , 10));
        buttonPane.getChildren().addAll(viewCartButton, viewOrdersButton, viewDiscountCodesButton,editPersonalInfoButton,chooseSupporterButton);

        buttonPane.setStyle("-fx-background-color : #f8e8e2");


        System.out.println(controller.getAccount().getUserName());
        mainPane.getChildren().addAll( GMenu.createViewPersonalInfo(controller.getAccount()), buttonPane, viewPane);
        mainPane.setSpacing(10);
        mainPane.setPadding(new Insets(10, 10 , 10 , 10));
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

    private Label createCodedDiscountLabel (CodedDiscount codedDiscount, Label resultLabel){
        Label codedDiscountLabel = new Label("Discount Code: \t\t\t\t\t\t" + codedDiscount.getDiscountCode());
        codedDiscountLabel.setPrefWidth(500);
        codedDiscountLabel.setOnMouseClicked(e->{
        });
        codedDiscountLabel.setStyle("-fx-border-color: orange");
        codedDiscountLabel.setOnMouseClicked(e->{
            resultLabel.setText("Start Date : " + codedDiscount.getStart() + " ,  End Date :" + codedDiscount.getEnd());
        });
        return codedDiscountLabel;
    }
}
