package gui.profile;

import controller.Controller;
import gui.GMenu;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class ManageProductsSupplierGMenu extends GMenu {

    public ManageProductsSupplierGMenu(GMenu parentMenu, Stage stage, Controller controller) {
        super("Manage Products, Supplier", parentMenu, stage, controller);
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
        label4.setText("Products:");

        // Adding child to parent
        anchorPane0.getChildren().add(label4);
        Button button5 = new Button();
        button5.setPrefHeight(33.0);
        button5.setPrefWidth(233.0);
        button5.setLayoutX(505.0);
        button5.setStyle("-fx-background-color: #4678c8;"+"-fx-background-radius: 100PX;"+"-fx-text-fill: #f5f5f2;");
        button5.setLayoutY(238.0);
        button5.setText("View details");
        button5.setMnemonicParsing(false);

        // Adding child to parent
        anchorPane0.getChildren().add(button5);
        Button button6 = new Button();
        button6.setPrefHeight(33.0);
        button6.setPrefWidth(233.0);
        button6.setLayoutX(505.0);
        button6.setStyle("-fx-background-color: #4678c8;"+"-fx-background-radius: 100PX;"+"-fx-text-fill: #f5f5f2;");
        button6.setLayoutY(395.0);
        button6.setText("Create");
        button6.setMnemonicParsing(false);

        // Adding child to parent
        anchorPane0.getChildren().add(button6);
        Button button7 = new Button();
        button7.setPrefHeight(33.0);
        button7.setPrefWidth(233.0);
        button7.setLayoutX(505.0);
        button7.setStyle("-fx-background-color: #4678c8;"+"-fx-background-radius: 100PX;"+"-fx-text-fill: #f5f5f2;");
        button7.setLayoutY(317.0);
        button7.setText("Edit");
        button7.setMnemonicParsing(false);

        // Adding child to parent
        anchorPane0.getChildren().add(button7);
        Button button8 = new Button();
        button8.setPrefHeight(33.0);
        button8.setPrefWidth(233.0);
        button8.setLayoutX(505.0);
        button8.setStyle("-fx-background-color: #4678c8;"+"-fx-background-radius: 100PX;"+"-fx-text-fill: #f5f5f2;");
        button8.setLayoutY(473.0);
        button8.setText("Remove");
        button8.setMnemonicParsing(false);

        // Adding child to parent
        anchorPane0.getChildren().add(button8);

        return new Scene(anchorPane0);
    }

    private Scene editProduct(){
        AnchorPane anchorPane0 = new AnchorPane();
        anchorPane0.setPrefHeight(392.0);
        anchorPane0.setPrefWidth(728.0);
        anchorPane0.setStyle("-fx-background-color: #f5f5f2;");
        HBox hBox1 = new HBox();
        hBox1.setPrefHeight(102.0);
        hBox1.setPrefWidth(728.0);
        hBox1.setStyle("-fx-background-color: #4477c8;");

// Adding child to parent
        anchorPane0.getChildren().add(hBox1);
        HBox hBox2 = new HBox();
        hBox2.setPrefHeight(102.0);
        hBox2.setPrefWidth(728.0);
        hBox2.setStyle("-fx-background-color: #4477c8;");
        hBox2.setLayoutY(290.0);

// Adding child to parent
        anchorPane0.getChildren().add(hBox2);
        ChoiceBox choiceBox3 = new ChoiceBox();
        choiceBox3.setPrefHeight(31.0);
        choiceBox3.setPrefWidth(268.0);
        choiceBox3.setLayoutX(40.0);
        choiceBox3.setLayoutY(189.0);

// Adding child to parent
        anchorPane0.getChildren().add(choiceBox3);
        HBox hBox4 = new HBox();
        hBox4.setPrefHeight(51.0);
        hBox4.setPrefWidth(316.0);
        hBox4.setLayoutX(340.0);
        hBox4.setStyle("-fx-background-color: white;"+"-fx-border-color: #a2a2a2;"+"-fx-border-width: 0px 0px 2px 0px;");
        hBox4.setLayoutY(179.0);
        TextField textField5 = new TextField();
        textField5.setPrefHeight(51.0);
        textField5.setPrefWidth(295.0);
        textField5.setStyle("-fx-background-color: transparent;");
        textField5.setOpacity(0.83);
        textField5.setPromptText("Enter new Value");

// Adding child to parent
        hBox4.getChildren().add(textField5);

// Adding child to parent
        anchorPane0.getChildren().add(hBox4);
        Label label6 = new Label();
        label6.setLayoutX(40.0);
        label6.setLayoutY(158.0);
        label6.setText("Choose your field:");

// Adding child to parent
        anchorPane0.getChildren().add(label6);

        return new Scene(anchorPane0);
    }

    private Scene createProduct(){
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
        TextField firstNameField = new TextField();
        firstNameField.setPrefHeight(51.0);
        firstNameField.setPrefWidth(295.0);
        firstNameField.setStyle("-fx-background-color: transparent;");
        firstNameField.setOpacity(0.83);
        firstNameField.setPromptText("First name");

        // Adding child to parent
        hBox5.getChildren().add(firstNameField);

        // Adding child to parent
        gridPane4.add(hBox5,0,0);
        HBox hBox7 = new HBox();
        hBox7.setPrefHeight(51.0);
        hBox7.setPrefWidth(345.0);
        hBox7.setStyle("-fx-background-color: white;"+"-fx-border-color: #a2a2a2;"+"-fx-border-width: 0px 0px 2px 0px;");
        TextField lastNameField = new TextField();
        lastNameField.setPrefHeight(51.0);
        lastNameField.setPrefWidth(295.0);
        lastNameField.setStyle("-fx-background-color: transparent;");
        lastNameField.setOpacity(0.83);
        lastNameField.setPromptText("Last name");

        // Adding child to parent
        hBox7.getChildren().add(lastNameField);

        // Adding child to parent
        gridPane4.add(hBox7,0,1);
        HBox hBox9 = new HBox();
        hBox9.setPrefHeight(51.0);
        hBox9.setPrefWidth(345.0);
        hBox9.setStyle("-fx-background-color: white;"+"-fx-border-color: #a2a2a2;"+"-fx-border-width: 0px 0px 2px 0px;");
        TextField cityNameField = new TextField();
        cityNameField.setPrefHeight(51.0);
        cityNameField.setPrefWidth(295.0);
        cityNameField.setStyle("-fx-background-color: transparent;");
        cityNameField.setOpacity(0.83);
        cityNameField.setPromptText("City name");

        // Adding child to parent
        hBox9.getChildren().add(cityNameField);

        // Adding child to parent
        gridPane4.add(hBox9,0,2);
        HBox hBox11 = new HBox();
        hBox11.setPrefHeight(51.0);
        hBox11.setPrefWidth(345.0);
        hBox11.setStyle("-fx-background-color: white;"+"-fx-border-color: #a2a2a2;"+"-fx-border-width: 0px 0px 2px 0px;");
        TextField addressField = new TextField();
        addressField.setPrefHeight(51.0);
        addressField.setPrefWidth(295.0);
        addressField.setStyle("-fx-background-color: transparent;");
        addressField.setOpacity(0.83);
        addressField.setPromptText("Address");

        // Adding child to parent
        hBox11.getChildren().add(addressField);

        // Adding child to parent

        gridPane4.add(hBox11,0,3);
        HBox hBox13 = new HBox();
        hBox13.setPrefHeight(51.0);
        hBox13.setPrefWidth(356.0);
        hBox13.setStyle("-fx-background-color: white;"+"-fx-border-color: #a2a2a2;"+"-fx-border-width: 0px 0px 2px 0px;");
        TextField postalCodeField = new TextField();
        postalCodeField.setPrefHeight(51.0);
        postalCodeField.setPrefWidth(295.0);
        postalCodeField.setStyle("-fx-background-color: transparent;");
        postalCodeField.setOpacity(0.83);
        postalCodeField.setPromptText("Postal code");

        // Adding child to parent
        hBox13.getChildren().add(postalCodeField);

        // Adding child to parent
        gridPane4.add(hBox13,0,4);
        HBox hBox15 = new HBox();
        hBox15.setPrefHeight(51.0);
        hBox15.setPrefWidth(345.0);
        hBox15.setStyle("-fx-background-color: white;"+"-fx-border-color: #a2a2a2;"+"-fx-border-width: 0px 0px 2px 0px;");
        TextField phoneNumberField = new TextField();
        phoneNumberField.setPrefHeight(51.0);
        phoneNumberField.setPrefWidth(295.0);
        phoneNumberField.setStyle("-fx-background-color: transparent;");
        phoneNumberField.setOpacity(0.83);
        phoneNumberField.setPromptText("Phone number");

        // Adding child to parent
        hBox15.getChildren().add(phoneNumberField);

        // Adding child to parent

        gridPane4.add(hBox15,0,5);

        // Adding child to parent
        anchorPane0.getChildren().add(gridPane4);
        Button button17 = new Button();
        button17.setPrefHeight(37.0);
        button17.setPrefWidth(233.0);
        button17.setLayoutX(600.0);
        button17.setStyle("-fx-background-color: #4678c8;"+"-fx-background-radius: 100PX;"+"-fx-text-fill: #f5f5f2;");
        button17.setLayoutY(507.0);
        button17.setText("Create");
        button17.setMnemonicParsing(false);

// Adding child to parent
        anchorPane0.getChildren().add(button17);
        HBox hBox18 = new HBox();
        hBox18.setPrefHeight(21.0);
        hBox18.setPrefWidth(145.0);
        hBox18.setLayoutX(551.0);
        hBox18.setStyle("-fx-background-color: white;"+"-fx-border-color: #a2a2a2;"+"-fx-border-width: 0px 0px 2px 0px;");
        hBox18.setLayoutY(209.0);
        TextField textField19 = new TextField();
        textField19.setPrefHeight(51.0);
        textField19.setPrefWidth(237.0);
        textField19.setStyle("-fx-background-color: transparent;");
        textField19.setOpacity(0.83);
        textField19.setPromptText("Enter Key");

// Adding child to parent
        hBox18.getChildren().add(textField19);

// Adding child to parent
        anchorPane0.getChildren().add(hBox18);
        HBox hBox20 = new HBox();
        hBox20.setPrefHeight(31.0);
        hBox20.setPrefWidth(145.0);
        hBox20.setLayoutX(740.0);
        hBox20.setStyle("-fx-background-color: white;"+"-fx-border-color: #a2a2a2;"+"-fx-border-width: 0px 0px 2px 0px;");
        hBox20.setLayoutY(209.0);
        TextField textField21 = new TextField();
        textField21.setPrefHeight(51.0);
        textField21.setPrefWidth(295.0);
        textField21.setStyle("-fx-background-color: transparent;");
        textField21.setOpacity(0.83);
        textField21.setPromptText("Enter Value");

// Adding child to parent
        hBox20.getChildren().add(textField21);

// Adding child to parent
        anchorPane0.getChildren().add(hBox20);
        Label label22 = new Label();
        label22.setLayoutX(714.0);
        label22.setLayoutY(215.0);
        label22.setText(":");

// Adding child to parent
        anchorPane0.getChildren().add(label22);
        Button button23 = new Button();
        button23.setPrefHeight(37.0);
        button23.setPrefWidth(233.0);
        button23.setLayoutX(602.0);
        button23.setStyle("-fx-background-color: #4678c8;"+"-fx-background-radius: 100PX;"+"-fx-text-fill: #f5f5f2;");
        button23.setLayoutY(269.0);
        button23.setText("Add to list");
        button23.setMnemonicParsing(false);

// Adding child to parent
        anchorPane0.getChildren().add(button23);
        gridPane4.setVgap(15);

        return new Scene(anchorPane0);
    }
}
