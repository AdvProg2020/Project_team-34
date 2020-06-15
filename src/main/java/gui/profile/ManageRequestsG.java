package gui.profile;

import gui.GMenu;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class ManageRequestsG extends GMenu {

    public ManageRequestsG(String menuName, GMenu parentMenu, Stage stage) {
        super(menuName, parentMenu, stage);
    }

    @Override
    protected Scene createScene() {
        AnchorPane anchorPane0 = new AnchorPane();

        anchorPane0.setPrefHeight(700.0);
        anchorPane0.setStyle("-fx-background-color: #f5f5f2");
        anchorPane0.setPrefWidth(850.0);


        HBox hBox1 = new HBox();
        hBox1.setPrefHeight(102.0);
        hBox1.setPrefWidth(850.0);
        hBox1.setStyle("-fx-background-color: #4477c8;");

        // Adding child to parent
        anchorPane0.getChildren().add(hBox1);
        HBox hBox2 = new HBox();
        hBox2.setPrefHeight(102.0);
        hBox2.setPrefWidth(850.0);
        hBox2.setStyle("-fx-background-color: #4477c8;");
        hBox2.setLayoutY(598.0);

        // Adding child to parent
        anchorPane0.getChildren().add(hBox2);
        ListView listView3 = new ListView();
        listView3.setPrefHeight(398.0);
        listView3.setPrefWidth(350.0);
        listView3.setLayoutX(50.0);
        listView3.setLayoutY(154.0);

        // Adding child to parent
        anchorPane0.getChildren().add(listView3);
        Label label4 = new Label();
        label4.setLayoutX(50.0);
        label4.setLayoutY(120.0);
        label4.setText("Requests:");

        // Adding child to parent
        anchorPane0.getChildren().add(label4);
        Button button5 = new Button();
        button5.setPrefHeight(33.0);
        button5.setPrefWidth(233.0);
        button5.setLayoutX(505.0);
        button5.setStyle("-fx-background-color: #4678c8;"+"-fx-background-radius: 100PX;"+"-fx-text-fill: #f5f5f2;");
        button5.setLayoutY(334.0);
        button5.setText("View details");
        button5.setMnemonicParsing(false);

        // Adding child to parent
        anchorPane0.getChildren().add(button5);
        Button button7 = new Button();
        button7.setPrefHeight(33.0);
        button7.setPrefWidth(233.0);
        button7.setLayoutX(505.0);
        button7.setStyle("-fx-background-color: #4678c8;"+"-fx-background-radius: 100PX;"+"-fx-text-fill: #f5f5f2;");
        button7.setLayoutY(519.0);
        button7.setText("Accept");
        button7.setMnemonicParsing(false);

        // Adding child to parent
        anchorPane0.getChildren().add(button7);
        Button button8 = new Button();
        button8.setPrefHeight(33.0);
        button8.setPrefWidth(233.0);
        button8.setLayoutX(505.0);
        button8.setStyle("-fx-background-color: #4678c8;"+"-fx-background-radius: 100PX;"+"-fx-text-fill: #f5f5f2;");
        button8.setLayoutY(457.0);
        button8.setText("Reject");
        button8.setMnemonicParsing(false);

        // Adding child to parent
        anchorPane0.getChildren().add(button8);

        return new Scene(anchorPane0);

    }
}
