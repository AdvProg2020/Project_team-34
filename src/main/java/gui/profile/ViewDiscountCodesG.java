package gui.profile;

import discount.CodedDiscount;
import gui.GMenu;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class ViewDiscountCodesG extends GMenu {

    public ViewDiscountCodesG(String menuName, GMenu parentMenu, Stage stage) {
        super(menuName, parentMenu, stage);
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
        label4.setText("Discount Codes:");

        // Adding child to parent
        anchorPane0.getChildren().add(label4);
        Button detailsButton = new Button();
        detailsButton.setPrefHeight(33.0);
        detailsButton.setPrefWidth(233.0);
        detailsButton.setLayoutX(505.0);
        detailsButton.setStyle("-fx-background-color: #4678c8;"+"-fx-background-radius: 100PX;"+"-fx-text-fill: #f5f5f2;");
        detailsButton.setLayoutY(394.0);
        detailsButton.setText("View details");
        detailsButton.setMnemonicParsing(false);

        // Adding child to parent
        anchorPane0.getChildren().add(detailsButton);
        Button createButton = new Button();
        createButton.setPrefHeight(33.0);
        createButton.setPrefWidth(233.0);
        createButton.setLayoutX(505.0);
        createButton.setStyle("-fx-background-color: #4678c8;"+"-fx-background-radius: 100PX;"+"-fx-text-fill: #f5f5f2;");
        createButton.setLayoutY(519.0);
        createButton.setText("Create");
        createButton.setMnemonicParsing(false);

        // Adding child to parent
        anchorPane0.getChildren().add(createButton);
        Button editButton = new Button();
        editButton.setPrefHeight(33.0);
        editButton.setPrefWidth(233.0);
        editButton.setLayoutX(505.0);
        editButton.setStyle("-fx-background-color: #4678c8;"+"-fx-background-radius: 100PX;"+"-fx-text-fill: #f5f5f2;");
        editButton.setLayoutY(457.0);
        editButton.setText("Edit");
        editButton.setMnemonicParsing(false);

        // Adding child to parent
        anchorPane0.getChildren().add(editButton);

        // Adding controller


        return new Scene(anchorPane0);
    }

    private Scene createEditMenu(CodedDiscount codedDiscount){
        AnchorPane anchorPane0 = new AnchorPane();
        anchorPane0.setPrefHeight(588.0);
        anchorPane0.setPrefWidth(850.0);
        anchorPane0.setStyle("-fx-background-color: #f5f5f2;");
        HBox hBox1 = new HBox();
        hBox1.setPrefHeight(102.0);
        hBox1.setPrefWidth(850.0);
        hBox1.setStyle("-fx-background-color: #4677c8;");
        hBox1.setLayoutY(486.0);

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
        Label label7 = new Label();
        label7.setLayoutX(52.0);
        label7.setLayoutY(121.0);
        label7.setText("Create Discount code");

// Adding child to parent
        anchorPane0.getChildren().add(label7);
        HBox hBox8 = new HBox();
        hBox8.setPrefHeight(51.0);
        hBox8.setPrefWidth(345.0);
        hBox8.setLayoutX(52.0);
        hBox8.setStyle("-fx-background-color: white;"+"-fx-border-color: #a2a2a2;"+"-fx-border-width: 0px 0px 2px 0px;");
        hBox8.setLayoutY(306.0);
        TextField textField9 = new TextField();
        textField9.setPrefHeight(51.0);
        textField9.setPrefWidth(295.0);
        textField9.setStyle("-fx-background-color: transparent;");
        textField9.setOpacity(0.83);
        textField9.setPromptText("Enter Discount Code");

// Adding child to parent
        hBox8.getChildren().add(textField9);

// Adding child to parent
        anchorPane0.getChildren().add(hBox8);
        HBox hBox10 = new HBox();
        hBox10.setPrefHeight(51.0);
        hBox10.setPrefWidth(345.0);
        hBox10.setLayoutX(52.0);
        hBox10.setStyle("-fx-background-color: white;"+"-fx-border-color: #a2a2a2;"+"-fx-border-width: 0px 0px 2px 0px;");
        hBox10.setLayoutY(381.0);
        TextField textField11 = new TextField();
        textField11.setPrefHeight(51.0);
        textField11.setPrefWidth(295.0);
        textField11.setStyle("-fx-background-color: transparent;");
        textField11.setOpacity(0.83);
        textField11.setPromptText("Enter Maximum discount amount");

// Adding child to parent
        hBox10.getChildren().add(textField11);

// Adding child to parent
        anchorPane0.getChildren().add(hBox10);
        Button button12 = new Button();
        button12.setPrefHeight(70.0);
        button12.setPrefWidth(201.0);
        button12.setLayoutX(501.0);
        button12.setStyle("-fx-background-color: #4678c8;"+"-fx-background-radius: 100PX;"+"-fx-text-fill: #f5f5f2;");
        button12.setLayoutY(259.0);
        button12.setText("Edit Discount Code");
        button12.setMnemonicParsing(false);

// Adding child to parent
        anchorPane0.getChildren().add(button12);

        return new Scene(anchorPane0);
    }

    private Scene createCreateMenu(){
        AnchorPane anchorPane0 = new AnchorPane();
        anchorPane0.setPrefHeight(650.0);
        anchorPane0.setPrefWidth(850.0);
        anchorPane0.setStyle("-fx-background-color: #f5f5f2;");
        HBox hBox1 = new HBox();
        hBox1.setPrefHeight(102.0);
        hBox1.setPrefWidth(850.0);
        hBox1.setStyle("-fx-background-color: #4677c8;");
        hBox1.setLayoutY(548.0);

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
        label8.setText("Customers:");

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
        textField11.setPromptText("Enter DIscount Code");

// Adding child to parent
        hBox10.getChildren().add(textField11);

// Adding child to parent
        anchorPane0.getChildren().add(hBox10);
        HBox hBox12 = new HBox();
        hBox12.setPrefHeight(51.0);
        hBox12.setPrefWidth(345.0);
        hBox12.setLayoutX(52.0);
        hBox12.setStyle("-fx-background-color: white;"+"-fx-border-color: #a2a2a2;"+"-fx-border-width: 0px 0px 2px 0px;");
        hBox12.setLayoutY(381.0);
        TextField textField13 = new TextField();
        textField13.setPrefHeight(51.0);
        textField13.setPrefWidth(295.0);
        textField13.setStyle("-fx-background-color: transparent;");
        textField13.setOpacity(0.83);
        textField13.setPromptText("Enter Maximum discount amount");

// Adding child to parent
        hBox12.getChildren().add(textField13);

// Adding child to parent
        anchorPane0.getChildren().add(hBox12);
        HBox hBox14 = new HBox();
        hBox14.setPrefHeight(51.0);
        hBox14.setPrefWidth(345.0);
        hBox14.setLayoutX(52.0);
        hBox14.setStyle("-fx-background-color: white;"+"-fx-border-color: #a2a2a2;"+"-fx-border-width: 0px 0px 2px 0px;");
        hBox14.setLayoutY(455.0);
        TextField textField15 = new TextField();
        textField15.setPrefHeight(51.0);
        textField15.setPrefWidth(295.0);
        textField15.setStyle("-fx-background-color: transparent;");
        textField15.setOpacity(0.83);
        textField15.setPromptText("Enter Maximum number of usage");

// Adding child to parent
        hBox14.getChildren().add(textField15);

// Adding child to parent
        anchorPane0.getChildren().add(hBox14);
        Button button16 = new Button();
        button16.setPrefHeight(70.0);
        button16.setPrefWidth(201.0);
        button16.setLayoutX(523.0);
        button16.setStyle("-fx-background-color: #4678c8;"+"-fx-background-radius: 100PX;"+"-fx-text-fill: #f5f5f2;");
        button16.setLayoutY(372.0);
        button16.setText("Create Discount Code");
        button16.setMnemonicParsing(false);

// Adding child to parent
        anchorPane0.getChildren().add(button16);

        return new Scene(anchorPane0);
    }
}
