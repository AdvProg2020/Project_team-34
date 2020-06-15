package gui.cartMenu;

import gui.GMenu;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import static javafx.scene.shape.StrokeType.OUTSIDE;

public class PurchaseMenuG extends GMenu {

    public PurchaseMenuG(String menuName, GMenu parentMenu, Stage stage) {
        super(menuName, parentMenu, stage);
    }

    @Override
    protected Scene createScene() {
        AnchorPane anchorPane0 = new AnchorPane();
        anchorPane0.setPrefHeight(700.0);
        anchorPane0.setPrefWidth(1000.0);
        anchorPane0.setStyle("-fx-background-color: #f5f5f2;");
        HBox hBox1 = new HBox();
        hBox1.setPrefHeight(102.0);
        hBox1.setPrefWidth(1000.0);
        hBox1.setStyle("-fx-background-color: #4477c8;");

        // Adding child to parent
        anchorPane0.getChildren().add(hBox1);
        HBox hBox2 = new HBox();
        hBox2.setPrefHeight(102.0);
        hBox2.setPrefWidth(1000.0);
        hBox2.setStyle("-fx-background-color: #4477c8;");
        hBox2.setLayoutY(598.0);

        // Adding child to parent
        anchorPane0.getChildren().add(hBox2);
        Label label3 = new Label();
        label3.setLayoutX(65.0);
        label3.setLayoutY(119.0);
        label3.setText("Purchase Menu:");

        // Adding child to parent
        anchorPane0.getChildren().add(label3);
        GridPane gridPane4 = new GridPane();
        gridPane4.setPrefHeight(385.0);
        gridPane4.setPrefWidth(356.0);
        gridPane4.setLayoutX(65.0);
        gridPane4.setLayoutY(159.0);
        HBox hBox5 = new HBox();
        hBox5.setPrefHeight(60.0);
        hBox5.setPrefWidth(316.0);
        hBox5.setStyle("-fx-background-color: white;"+"-fx-border-color: #a2a2a2;"+"-fx-border-width: 0px 0px 2px 0px;");
        TextField textField6 = new TextField();
        textField6.setPrefHeight(51.0);
        textField6.setPrefWidth(295.0);
        textField6.setStyle("-fx-background-color: transparent;");
        textField6.setOpacity(0.83);
        textField6.setPromptText("First name");

        // Adding child to parent
        hBox5.getChildren().add(textField6);

        // Adding child to parent
        gridPane4.add(hBox5,0,0);
        HBox hBox7 = new HBox();
        hBox7.setPrefHeight(51.0);
        hBox7.setPrefWidth(345.0);
        hBox7.setStyle("-fx-background-color: white;"+"-fx-border-color: #a2a2a2;"+"-fx-border-width: 0px 0px 2px 0px;");
        TextField textField8 = new TextField();
        textField8.setPrefHeight(51.0);
        textField8.setPrefWidth(295.0);
        textField8.setStyle("-fx-background-color: transparent;");
        textField8.setOpacity(0.83);
        textField8.setPromptText("Last name");

        // Adding child to parent
        hBox7.getChildren().add(textField8);

        // Adding child to parent
        gridPane4.add(hBox7,0,1);
        HBox hBox9 = new HBox();
        hBox9.setPrefHeight(51.0);
        hBox9.setPrefWidth(345.0);
        hBox9.setStyle("-fx-background-color: white;"+"-fx-border-color: #a2a2a2;"+"-fx-border-width: 0px 0px 2px 0px;");
        TextField textField10 = new TextField();
        textField10.setPrefHeight(51.0);
        textField10.setPrefWidth(295.0);
        textField10.setStyle("-fx-background-color: transparent;");
        textField10.setOpacity(0.83);
        textField10.setPromptText("City name");

        // Adding child to parent
        hBox9.getChildren().add(textField10);

        // Adding child to parent
        gridPane4.add(hBox9,0,2);
        HBox hBox11 = new HBox();
        hBox11.setPrefHeight(51.0);
        hBox11.setPrefWidth(345.0);
        hBox11.setStyle("-fx-background-color: white;"+"-fx-border-color: #a2a2a2;"+"-fx-border-width: 0px 0px 2px 0px;");
        TextField textField12 = new TextField();
        textField12.setPrefHeight(51.0);
        textField12.setPrefWidth(295.0);
        textField12.setStyle("-fx-background-color: transparent;");
        textField12.setOpacity(0.83);
        textField12.setPromptText("Address");

        // Adding child to parent
        hBox11.getChildren().add(textField12);

        // Adding child to parent

        gridPane4.add(hBox11,0,3);
        HBox hBox13 = new HBox();
        hBox13.setPrefHeight(51.0);
        hBox13.setPrefWidth(356.0);
        hBox13.setStyle("-fx-background-color: white;"+"-fx-border-color: #a2a2a2;"+"-fx-border-width: 0px 0px 2px 0px;");
        TextField textField14 = new TextField();
        textField14.setPrefHeight(51.0);
        textField14.setPrefWidth(295.0);
        textField14.setStyle("-fx-background-color: transparent;");
        textField14.setOpacity(0.83);
        textField14.setPromptText("Postal code");

        // Adding child to parent
        hBox13.getChildren().add(textField14);

        // Adding child to parent
        gridPane4.add(hBox13,0,4);
        HBox hBox15 = new HBox();
        hBox15.setPrefHeight(51.0);
        hBox15.setPrefWidth(345.0);
        hBox15.setStyle("-fx-background-color: white;"+"-fx-border-color: #a2a2a2;"+"-fx-border-width: 0px 0px 2px 0px;");
        TextField textField16 = new TextField();
        textField16.setPrefHeight(51.0);
        textField16.setPrefWidth(295.0);
        textField16.setStyle("-fx-background-color: transparent;");
        textField16.setOpacity(0.83);
        textField16.setPromptText("Phone number");

        // Adding child to parent
        hBox15.getChildren().add(textField16);

        // Adding child to parent

        gridPane4.add(hBox15,0,5);

        // Adding child to parent
        anchorPane0.getChildren().add(gridPane4);
        CheckBox checkBox17 = new CheckBox();
        checkBox17.setLayoutX(527.0);
        checkBox17.setLayoutY(130.0);
        checkBox17.setText("Apply discount code!");
        checkBox17.setMnemonicParsing(false);

        // Adding child to parent
        anchorPane0.getChildren().add(checkBox17);
        HBox hBox18 = new HBox();
        hBox18.setPrefHeight(51.0);
        hBox18.setPrefWidth(356.0);
        hBox18.setLayoutX(517.0);
        hBox18.setStyle("-fx-background-color: white;"+"-fx-border-color: #a2a2a2;"+"-fx-border-width: 0px 0px 2px 0px;");
        hBox18.setLayoutY(186.0);
        TextField textField19 = new TextField();
        textField19.setPrefHeight(51.0);
        textField19.setPrefWidth(295.0);
        textField19.setStyle("-fx-background-color: transparent;");
        textField19.setOpacity(0.83);
        textField19.setPromptText("Discount code");

        // Adding child to parent
        hBox18.getChildren().add(textField19);

        // Adding child to parent
        anchorPane0.getChildren().add(hBox18);
        Text text20 = new Text();
        text20.setStrokeWidth(0.0);
        text20.setStrokeType(OUTSIDE);
        text20.setLayoutX(517.0);
        text20.setLayoutY(260.0);
        text20.setText("Cart Info sits here!");
        text20.setWrappingWidth(356.0);

        // Adding child to parent
        anchorPane0.getChildren().add(text20);
        Button button21 = new Button();
        button21.setPrefHeight(33.0);
        button21.setPrefWidth(233.0);
        button21.setLayoutX(579.0);
        button21.setStyle("-fx-background-color: #4678c8;"+"-fx-background-radius: 100PX;"+"-fx-text-fill: #f5f5f2;");
        button21.setLayoutY(511.0);
        button21.setText("Purchase");
        button21.setMnemonicParsing(false);

        // Adding child to parent
        anchorPane0.getChildren().add(button21);
        gridPane4.setVgap(15);

        //Adding controller!




        return new Scene(anchorPane0);
    }
}
