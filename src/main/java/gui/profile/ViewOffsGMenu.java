package gui.profile;

import controller.Controller;
import discount.Sale;
import gui.GMenu;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class ViewOffsGMenu extends GMenu {

    public ViewOffsGMenu(GMenu parentMenu, Stage stage, Controller controller) {
        super("Offs", parentMenu, stage, controller);
    }

    @Override
    protected Scene createScene() {
        AnchorPane anchorPane0 = new AnchorPane();
        anchorPane0.setPrefHeight(700.0);
        anchorPane0.setPrefWidth(850.0);
        anchorPane0.setStyle("-fx-background-color: #f5f5f2;");
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
        label4.setText("Sales :");

        // Adding child to parent
        anchorPane0.getChildren().add(label4);
        Button button5 = new Button();
        button5.setPrefHeight(33.0);
        button5.setPrefWidth(233.0);
        button5.setLayoutX(505.0);
        button5.setStyle("-fx-background-color: #4678c8;"+"-fx-background-radius: 100PX;"+"-fx-text-fill: #f5f5f2;");
        button5.setLayoutY(394.0);
        button5.setText("View details");
        button5.setMnemonicParsing(false);

        // Adding child to parent
        anchorPane0.getChildren().add(button5);
        Button button6 = new Button();
        button6.setPrefHeight(33.0);
        button6.setPrefWidth(233.0);
        button6.setLayoutX(505.0);
        button6.setStyle("-fx-background-color: #4678c8;"+"-fx-background-radius: 100PX;"+"-fx-text-fill: #f5f5f2;");
        button6.setLayoutY(519.0);
        button6.setText("Create");
        button6.setMnemonicParsing(false);

        // Adding child to parent
        anchorPane0.getChildren().add(button6);
        Button button7 = new Button();
        button7.setPrefHeight(33.0);
        button7.setPrefWidth(233.0);
        button7.setLayoutX(505.0);
        button7.setStyle("-fx-background-color: #4678c8;"+"-fx-background-radius: 100PX;"+"-fx-text-fill: #f5f5f2;");
        button7.setLayoutY(457.0);
        button7.setText("Edit");
        button7.setMnemonicParsing(false);

        // Adding child to parent
        anchorPane0.getChildren().add(button7);

        return new Scene(anchorPane0);
    }

    private Scene createEdit(Sale sale){
        AnchorPane anchorPane0 = new AnchorPane();
        anchorPane0.setPrefHeight(573.0);
        anchorPane0.setPrefWidth(1096.0);
        anchorPane0.setStyle("-fx-background-color: #f5f5f2;");
        HBox hBox1 = new HBox();
        hBox1.setPrefHeight(102.0);
        hBox1.setPrefWidth(1096.0);
        hBox1.setStyle("-fx-background-color: #4677c8;");
        hBox1.setLayoutY(471.0);

// Adding child to parent
        anchorPane0.getChildren().add(hBox1);
        HBox hBox2 = new HBox();
        hBox2.setPrefHeight(102.0);
        hBox2.setPrefWidth(1096.0);
        hBox2.setStyle("-fx-background-color: #4677c8;");

// Adding child to parent
        anchorPane0.getChildren().add(hBox2);
        HBox hBox3 = new HBox();
        hBox3.setPrefHeight(51.0);
        hBox3.setPrefWidth(345.0);
        hBox3.setLayoutX(52.0);
        hBox3.setStyle("-fx-background-color: white;"+"-fx-border-color: #a2a2a2;"+"-fx-border-width: 0px 0px 2px 0px;");
        hBox3.setLayoutY(156.0);
        DatePicker datePicker4 = new DatePicker();
        datePicker4.setPrefHeight(49.0);
        datePicker4.setPrefWidth(352.0);
        datePicker4.setStyle("-fx-background-color: transparent;");
        datePicker4.setPromptText("Start Date");

// Adding child to parent
        hBox3.getChildren().add(datePicker4);

// Adding child to parent
        anchorPane0.getChildren().add(hBox3);
        HBox hBox5 = new HBox();
        hBox5.setPrefHeight(51.0);
        hBox5.setPrefWidth(345.0);
        hBox5.setLayoutX(52.0);
        hBox5.setStyle("-fx-background-color: white;"+"-fx-border-color: #a2a2a2;"+"-fx-border-width: 0px 0px 2px 0px;");
        hBox5.setLayoutY(229.0);
        DatePicker datePicker6 = new DatePicker();
        datePicker6.setPrefHeight(49.0);
        datePicker6.setPrefWidth(352.0);
        datePicker6.setStyle("-fx-background-color: transparent;");
        datePicker6.setPromptText("End Date");

// Adding child to parent
        hBox5.getChildren().add(datePicker6);

// Adding child to parent
        anchorPane0.getChildren().add(hBox5);
        ListView listView7 = new ListView();
        listView7.setPrefHeight(138.0);
        listView7.setPrefWidth(283.0);
        listView7.setLayoutX(438.0);
        listView7.setLayoutY(156.0);

// Adding child to parent
        anchorPane0.getChildren().add(listView7);
        Label label8 = new Label();
        label8.setLayoutX(438.0);
        label8.setLayoutY(121.0);
        label8.setText("Adding products:");

// Adding child to parent
        anchorPane0.getChildren().add(label8);
        Label label9 = new Label();
        label9.setLayoutX(52.0);
        label9.setLayoutY(121.0);
        label9.setText("Create Discount code");

// Adding child to parent
        anchorPane0.getChildren().add(label9);
        HBox hBox10 = new HBox();
        hBox10.setPrefHeight(51.0);
        hBox10.setPrefWidth(345.0);
        hBox10.setLayoutX(52.0);
        hBox10.setStyle("-fx-background-color: white;"+"-fx-border-color: #a2a2a2;"+"-fx-border-width: 0px 0px 2px 0px;");
        hBox10.setLayoutY(306.0);
        TextField textField11 = new TextField();
        textField11.setPrefHeight(51.0);
        textField11.setPrefWidth(295.0);
        textField11.setStyle("-fx-background-color: transparent;");
        textField11.setOpacity(0.83);
        textField11.setPromptText("Enter discount percent");

// Adding child to parent
        hBox10.getChildren().add(textField11);

// Adding child to parent
        anchorPane0.getChildren().add(hBox10);
        Button button12 = new Button();
        button12.setPrefHeight(49.0);
        button12.setPrefWidth(365.0);
        button12.setLayoutX(553.0);
        button12.setStyle("-fx-background-color: #4678c8;"+"-fx-background-radius: 100PX;"+"-fx-text-fill: #f5f5f2;");
        button12.setLayoutY(345.0);
        button12.setText("Edit sale");
        button12.setMnemonicParsing(false);

// Adding child to parent
        anchorPane0.getChildren().add(button12);
        ListView listView13 = new ListView();
        listView13.setPrefHeight(138.0);
        listView13.setPrefWidth(283.0);
        listView13.setLayoutX(767.0);
        listView13.setLayoutY(156.0);

// Adding child to parent
        anchorPane0.getChildren().add(listView13);
        Label label14 = new Label();
        label14.setLayoutX(767.0);
        label14.setLayoutY(121.0);
        label14.setText("Removing products:");

// Adding child to parent
        anchorPane0.getChildren().add(label14);

        return new Scene(anchorPane0);
    }

    private Scene createCreate(){
        AnchorPane anchorPane0 = new AnchorPane();
        anchorPane0.setPrefHeight(573.0);
        anchorPane0.setPrefWidth(850.0);
        anchorPane0.setStyle("-fx-background-color: #f5f5f2;");
        HBox hBox1 = new HBox();
        hBox1.setPrefHeight(102.0);
        hBox1.setPrefWidth(850.0);
        hBox1.setStyle("-fx-background-color: #4677c8;");
        hBox1.setLayoutY(471.0);

// Adding child to parent
        anchorPane0.getChildren().add(hBox1);
        HBox hBox2 = new HBox();
        hBox2.setPrefHeight(102.0);
        hBox2.setPrefWidth(850.0);
        hBox2.setStyle("-fx-background-color: #4677c8;");

// Adding child to parent
        anchorPane0.getChildren().add(hBox2);
        HBox hBox3 = new HBox();
        hBox3.setPrefHeight(51.0);
        hBox3.setPrefWidth(345.0);
        hBox3.setLayoutX(52.0);
        hBox3.setStyle("-fx-background-color: white;"+"-fx-border-color: #a2a2a2;"+"-fx-border-width: 0px 0px 2px 0px;");
        hBox3.setLayoutY(156.0);
        DatePicker datePicker4 = new DatePicker();
        datePicker4.setPrefHeight(49.0);
        datePicker4.setPrefWidth(352.0);
        datePicker4.setStyle("-fx-background-color: transparent;");
        datePicker4.setPromptText("Start Date");

// Adding child to parent
        hBox3.getChildren().add(datePicker4);

// Adding child to parent
        anchorPane0.getChildren().add(hBox3);
        HBox hBox5 = new HBox();
        hBox5.setPrefHeight(51.0);
        hBox5.setPrefWidth(345.0);
        hBox5.setLayoutX(52.0);
        hBox5.setStyle("-fx-background-color: white;"+"-fx-border-color: #a2a2a2;"+"-fx-border-width: 0px 0px 2px 0px;");
        hBox5.setLayoutY(229.0);
        DatePicker datePicker6 = new DatePicker();
        datePicker6.setPrefHeight(49.0);
        datePicker6.setPrefWidth(352.0);
        datePicker6.setStyle("-fx-background-color: transparent;");
        datePicker6.setPromptText("End Date");

// Adding child to parent
        hBox5.getChildren().add(datePicker6);

// Adding child to parent
        anchorPane0.getChildren().add(hBox5);
        ListView listView7 = new ListView();
        listView7.setPrefHeight(138.0);
        listView7.setPrefWidth(370.0);
        listView7.setLayoutX(438.0);
        listView7.setLayoutY(156.0);

// Adding child to parent
        anchorPane0.getChildren().add(listView7);
        Label label8 = new Label();
        label8.setLayoutX(438.0);
        label8.setLayoutY(121.0);
        label8.setText("Products:");

// Adding child to parent
        anchorPane0.getChildren().add(label8);
        Label label9 = new Label();
        label9.setLayoutX(52.0);
        label9.setLayoutY(121.0);
        label9.setText("Create Sale");

// Adding child to parent
        anchorPane0.getChildren().add(label9);
        HBox hBox10 = new HBox();
        hBox10.setPrefHeight(51.0);
        hBox10.setPrefWidth(345.0);
        hBox10.setLayoutX(52.0);
        hBox10.setStyle("-fx-background-color: white;"+"-fx-border-color: #a2a2a2;"+"-fx-border-width: 0px 0px 2px 0px;");
        hBox10.setLayoutY(306.0);
        TextField textField11 = new TextField();
        textField11.setPrefHeight(51.0);
        textField11.setPrefWidth(295.0);
        textField11.setStyle("-fx-background-color: transparent;");
        textField11.setOpacity(0.83);
        textField11.setPromptText("Enter discount percent");

// Adding child to parent
        hBox10.getChildren().add(textField11);

// Adding child to parent
        anchorPane0.getChildren().add(hBox10);
        Button button12 = new Button();
        button12.setPrefHeight(70.0);
        button12.setPrefWidth(201.0);
        button12.setLayoutX(523.0);
        button12.setStyle("-fx-background-color: #4678c8;"+"-fx-background-radius:"+"100PX; -fx-text-fill: #f5f5f2;");
        button12.setLayoutY(372.0);
        button12.setText("Create Discount Code");
        button12.setMnemonicParsing(false);

// Adding child to parent
        anchorPane0.getChildren().add(button12);

        return new Scene(anchorPane0);
    }
}
