package gui.profile;

import controller.Controller;
import discount.CodedDiscount;
import exceptionalMassage.ExceptionalMassage;
import gui.GMenu;
import gui.alerts.AlertBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class ViewDiscountCodesForCustomerGMenu extends GMenu{

    public ViewDiscountCodesForCustomerGMenu(GMenu parentMenu, Stage stage, Controller controller) {
        super("Profile", parentMenu, stage, controller);
    }

    @Override
    protected Scene createScene() {

        GridPane backgroundLayout = new GridPane();
        VBox mainPane = new VBox();
        mainPane.setMinWidth(800);
        Text  resultLabel = new Text();
        resultLabel.setWrappingWidth(600);
        try {
            ArrayList<CodedDiscount> codedDiscounts = controller.getOffController().controlGetCodedDiscountByCustomer();
        } catch (ExceptionalMassage ex){
            new AlertBox(this, ex, controller).showAndWait();
        }

        for (CodedDiscount codedDiscount : codedDiscounts) {
            mainPane.getChildren().add(createCodedDiscountLabel(codedDiscount, resultLabel));
        }

        mainPane.getChildren().add(resultLabel);

        mainPane.setSpacing(10);
        mainPane.setPadding(new Insets(10, 10 , 10 , 10));
        mainPane.setAlignment(Pos.CENTER);

        backgroundLayout.add(createHeader(), 0,0);
        backgroundLayout.add(mainPane, 0, 1);
        backgroundLayout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(backgroundLayout);
        return scene;
    }
        private Label createCodedDiscountLabel (CodedDiscount codedDiscount, Text resultLabel){
        Label codedDiscountLabel = new Label("Discount Code: \t\t\t\t\t\t" + codedDiscount.getDiscountCode());
        codedDiscountLabel.setPrefWidth(500);
        codedDiscountLabel.setOnMouseClicked(e->{
        });
        codedDiscountLabel.setStyle("-fx-font-size: 16; -fx-border-color: orange");
        codedDiscountLabel.setOnMouseClicked(e->{
            try {
                resultLabel.setText("Start Date :  \t" + codedDiscount.getStart() + " ,  \tEnd Date : \t" + codedDiscount.getEnd() + "\t Remained Number Of Usage for you \t" + controller.getOffController().controlGetRemainedNumberInCodedDiscountForCustomer(codedDiscount));
                resultLabel.setStyle("-fx-font-size: 13; -fx-stroke: orange");
            } catch (ExceptionalMassage exceptionalMassage) {
                exceptionalMassage.printStackTrace();
            }
        });
        return codedDiscountLabel;
    }
}
