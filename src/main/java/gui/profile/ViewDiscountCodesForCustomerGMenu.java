package gui.profile;

import controller.Controller;
import discount.CodedDiscount;
import exceptionalMassage.ExceptionalMassage;
import gui.GMenu;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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

        Label resultLabel = new Label();
        ArrayList<CodedDiscount> codedDiscounts = controller.getOffController().controlGetCodedDiscountByCustomer();
//        Date start = new Date(System.currentTimeMillis()-1000000);
//        Date end = new Date(System.currentTimeMillis() + 1000000);
//        CodedDiscount newCodedDiscount = new CodedDiscount("salam",start, end,30,100, new HashMap<>());
//        CodedDiscount newCodedDiscount2 = new CodedDiscount("bye", end, start, 50, 300, new HashMap<>());
//        codedDiscounts.add(newCodedDiscount);
//        codedDiscounts.add(newCodedDiscount2);

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
        private Label createCodedDiscountLabel (CodedDiscount codedDiscount, Label resultLabel){
        Label codedDiscountLabel = new Label("Discount Code: \t\t\t\t\t\t" + codedDiscount.getDiscountCode());
        codedDiscountLabel.setPrefWidth(500);
        codedDiscountLabel.setOnMouseClicked(e->{
        });
        codedDiscountLabel.setStyle("-fx-border-color: orange");
        codedDiscountLabel.setOnMouseClicked(e->{
            try {
                resultLabel.setText("Start Date : " + codedDiscount.getStart() + " ,  End Date :" + codedDiscount.getEnd() + controller.getOffController().controlGetRemainedNumberInCodedDiscountForCustomer(codedDiscount));
            } catch (ExceptionalMassage exceptionalMassage) {
                exceptionalMassage.printStackTrace();
            }
            resultLabel.setStyle("-fx-border-color: orange");
        });
        return codedDiscountLabel;
    }
}
