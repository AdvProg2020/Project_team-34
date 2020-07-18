package gui.profile;

import controller.Controller;
import exceptionalMassage.ExceptionalMassage;
import gui.GMenu;
import gui.alerts.AlertBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.w3c.dom.Text;

public class ManageWageGMenu extends GMenu {
    public ManageWageGMenu(GMenu parentMenu, Stage stage, Controller controller) {
        super("Manage Wage & Minimum", parentMenu, stage, controller);
    }

    @Override
    protected Scene createScene() {
        GridPane backgroundLayout = new GridPane();
        VBox mainPane = new VBox();
        GridPane wagePane = new GridPane();

        Label wageLabel = new Label("Wage");
        Label minimumLabel = new Label("Minimum Amount");
        TextField wageTextField = new TextField();
        TextField minimumTextField = new TextField();
        Button applyButton = new Button("Apply");
        GMenu.addStyleToButton(applyButton);

        try {
            wageTextField.setText(String.valueOf(controller.getAccountController().controlGetWage()));
            minimumTextField.setText(String.valueOf(controller.getAccountController().controlGetMinimum()));
        } catch (ExceptionalMassage exceptionalMassage) {
            exceptionalMassage.printStackTrace();
        }


        wagePane.add(wageLabel, 0, 0);
        wagePane.add(minimumLabel, 0, 1);
        wagePane.add(wageTextField, 1, 0);
        wagePane.add(minimumTextField, 1, 1);
        wagePane.setHgap(30);
        wagePane.setVgap(30);
        wagePane.setAlignment(Pos.CENTER);

        mainPane.setAlignment(Pos.CENTER);
        mainPane.setSpacing(20);
        mainPane.setPadding(new Insets(10,20,20,20));
        mainPane.getChildren().addAll(wagePane, applyButton);

        applyButton.setOnMouseClicked(e->{
            try {
                int wageAmount = Integer.parseInt(wageTextField.getText());
                int minimumAmount = Integer.parseInt(minimumTextField.getText());
                try {
                    if(wageAmount < 0 || wageAmount > 100){
                        new AlertBox(this, "Enter Valid Percent", "OK", controller).showAndWait();
                    }else {
                        controller.getAccountController().controlSetWageAndMinimum(wageAmount, minimumAmount);
                        stage.setScene(new SupervisorProfileGMenu(this, stage, controller).createScene());
                    }
                } catch (ExceptionalMassage exceptionalMassage) {
                    new AlertBox(this, exceptionalMassage, controller).showAndWait();
                }
            }catch (NumberFormatException exception){
                new AlertBox(this, "Please Enter Valid Numbers", "OK", controller).showAndWait();
            }
        });

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
