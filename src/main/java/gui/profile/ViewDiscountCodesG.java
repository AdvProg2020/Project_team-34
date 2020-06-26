package gui.profile;

import account.Account;
import account.Customer;
import controller.Controller;
import discount.CodedDiscount;
import exceptionalMassage.ExceptionalMassage;
import gui.GMenu;
import gui.alerts.AlertBox;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;

import static javafx.scene.shape.StrokeType.OUTSIDE;

public class ViewDiscountCodesG extends GMenu {

    public ViewDiscountCodesG(GMenu parentMenu, Stage stage, Controller controller) {
        super("Discounts", parentMenu, stage, controller);
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

        HBox header = createHeader();
        hBox1.getChildren().add(header);

        // Adding child to parent
        anchorPane0.getChildren().add(hBox1);
        HBox hBox2 = new HBox();
        hBox2.setPrefHeight(102.0);
        hBox2.setPrefWidth(850.0);
        hBox2.setStyle("-fx-background-color: #4477c8;");
        hBox2.setLayoutY(598.0);

        // Adding child to parent
        anchorPane0.getChildren().add(hBox2);
        TableView listView3 = new TableView();
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

        Button removeButton = new Button();
        removeButton.setPrefHeight(33.0);
        removeButton.setPrefWidth(233.0);
        removeButton.setLayoutX(505.0);
        removeButton.setStyle("-fx-background-color: #4678c8;"+"-fx-background-radius: 100PX;"+"-fx-text-fill: #f5f5f2;");
        removeButton.setLayoutY(337.0);
        removeButton.setText("Removing");
        removeButton.setMnemonicParsing(false);

        anchorPane0.getChildren().add(removeButton);
        // Adding controller
        // Adding controller

        TableColumn discountCode = new TableColumn("Code");
        discountCode.setCellValueFactory(new PropertyValueFactory<>("discountCode"));
        TableColumn percent = new TableColumn("Percent");
        percent.setCellValueFactory(new PropertyValueFactory<>("percent"));
        TableColumn start = new TableColumn("Start");
        start.setCellValueFactory(new PropertyValueFactory<>("start"));
        TableColumn end = new TableColumn("End");
        end.setCellValueFactory(new PropertyValueFactory<>("end"));
        listView3.getColumns().addAll(discountCode,percent, start, end);

        for (CodedDiscount codedDiscount : controller.getOffController().controlGetAllCodedDiscounts()) {
            listView3.getItems().add(codedDiscount);
        }

        detailsButton.setOnAction( e -> {
            ObservableList<CodedDiscount> codes = listView3.getSelectionModel().getSelectedItems();
            for (CodedDiscount code : codes) {
                Stage newStage = new Stage();
                newStage.setScene(createDetails(code.getDiscountCode()));
                newStage.showAndWait();
            }
            listView3.getItems().clear();
            for (CodedDiscount codedDiscount : controller.getOffController().controlGetAllCodedDiscounts()) {
                listView3.getItems().add(codedDiscount);
            }
        });

        editButton.setOnAction( e -> {
            ObservableList<CodedDiscount> codes = listView3.getSelectionModel().getSelectedItems();
            for (CodedDiscount code : codes) {
                try {

                    Image logoImage = null;
                    try {
                        logoImage = new Image(new FileInputStream("./src/main/resources/header/Logo.png"));
                    } catch (FileNotFoundException exc) {
                    }

                    Stage newStage = new Stage();
                    newStage.initModality(Modality.APPLICATION_MODAL);
                    newStage.setScene(createEditMenu(controller.getOffController().controlGetDiscountByCode(code.getDiscountCode())));
                    newStage.getIcons().add(logoImage);
                    newStage.setTitle("Edit discount code");
                    newStage.showAndWait();

                }catch (ExceptionalMassage ex){
                    new AlertBox(this, ex, controller).showAndWait();
                }
            }
            listView3.getItems().clear();
            for (CodedDiscount codedDiscount : controller.getOffController().controlGetAllCodedDiscounts()) {
                listView3.getItems().add(codedDiscount);
            }
        });

        createButton.setOnAction(e -> {
            Image logoImage = null;
            try {
                logoImage = new Image(new FileInputStream("./src/main/resources/header/Logo.png"));
            } catch (FileNotFoundException exc) {
            }

            Stage newStage = new Stage();
            newStage.initModality(Modality.APPLICATION_MODAL);
            newStage.setScene(createCreateMenu());
            newStage.getIcons().add(logoImage);
            newStage.setTitle("Create coded discount");
            newStage.showAndWait();

            listView3.getItems().clear();
            for (CodedDiscount codedDiscount : controller.getOffController().controlGetAllCodedDiscounts()) {
                listView3.getItems().add(codedDiscount);
            }
        });

        removeButton.setOnAction( e -> {
            ObservableList<CodedDiscount> codes = listView3.getSelectionModel().getSelectedItems();
            for (CodedDiscount code : codes) {
                controller.getOffController().removeCodedDiscount(code);
            }
            listView3.getItems().clear();
            for (CodedDiscount codedDiscount : controller.getOffController().controlGetAllCodedDiscounts()) {
                listView3.getItems().add(codedDiscount);
            }

        });




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
        DatePicker startDatePicker = new DatePicker();
        startDatePicker.setPrefHeight(49.0);
        startDatePicker.setPrefWidth(352.0);
        startDatePicker.setStyle("-fx-background-color: transparent;");
        startDatePicker.setPromptText("Start Date");

// Adding child to parent
        hBox3.getChildren().add(startDatePicker);

// Adding child to parent
        anchorPane0.getChildren().add(hBox3);
        HBox hBox5 = new HBox();
        hBox5.setPrefHeight(51.0);
        hBox5.setPrefWidth(345.0);
        hBox5.setLayoutX(52.0);
        hBox5.setStyle("-fx-background-color: white;"+"-fx-border-color: #a2a2a2;"+"-fx-border-width: 0px 0px 2px 0px;");
        hBox5.setLayoutY(229.0);
        DatePicker endDatePicker = new DatePicker();
        endDatePicker.setPrefHeight(49.0);
        endDatePicker.setPrefWidth(352.0);
        endDatePicker.setStyle("-fx-background-color: transparent;");
        endDatePicker.setPromptText("End Date");

// Adding child to parent
        hBox5.getChildren().add(endDatePicker);

// Adding child to parent
        anchorPane0.getChildren().add(hBox5);
        Label label7 = new Label();
        label7.setLayoutX(52.0);
        label7.setLayoutY(121.0);
        label7.setText("Edit discount code");

// Adding child to parent
        anchorPane0.getChildren().add(label7);
        HBox hBox8 = new HBox();
        hBox8.setPrefHeight(51.0);
        hBox8.setPrefWidth(345.0);
        hBox8.setLayoutX(52.0);
        hBox8.setStyle("-fx-background-color: white;"+"-fx-border-color: #a2a2a2;"+"-fx-border-width: 0px 0px 2px 0px;");
        hBox8.setLayoutY(306.0);
        TextField discountPercentField = new TextField();
        discountPercentField.setPrefHeight(51.0);
        discountPercentField.setPrefWidth(295.0);
        discountPercentField.setStyle("-fx-background-color: transparent;");
        discountPercentField.setOpacity(0.83);
        discountPercentField.setPromptText("Enter Discount Percent");

// Adding child to parent
        hBox8.getChildren().add(discountPercentField);

// Adding child to parent
        anchorPane0.getChildren().add(hBox8);
        HBox hBox10 = new HBox();
        hBox10.setPrefHeight(51.0);
        hBox10.setPrefWidth(345.0);
        hBox10.setLayoutX(52.0);
        hBox10.setStyle("-fx-background-color: white;"+"-fx-border-color: #a2a2a2;"+"-fx-border-width: 0px 0px 2px 0px;");
        hBox10.setLayoutY(381.0);
        TextField maxAmountField = new TextField();
        maxAmountField.setPrefHeight(51.0);
        maxAmountField.setPrefWidth(295.0);
        maxAmountField.setStyle("-fx-background-color: transparent;");
        maxAmountField.setOpacity(0.83);
        maxAmountField.setPromptText("Enter Maximum discount amount");

// Adding child to parent
        hBox10.getChildren().add(maxAmountField);

// Adding child to parent
        anchorPane0.getChildren().add(hBox10);
        Button editButton = new Button();
        editButton.setPrefHeight(70.0);
        editButton.setPrefWidth(201.0);
        editButton.setLayoutX(501.0);
        editButton.setStyle("-fx-background-color: #4678c8;"+"-fx-background-radius: 100PX;"+"-fx-text-fill: #f5f5f2;");
        editButton.setLayoutY(259.0);
        editButton.setText("Edit Discount Code");
        editButton.setMnemonicParsing(false);

// Adding child to parent
        anchorPane0.getChildren().add(editButton);

        //Adding controller

        startDatePicker.setValue(codedDiscount.getStart().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());

        endDatePicker.setValue(codedDiscount.getEnd().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());

        discountPercentField.setText(String.valueOf(codedDiscount.getPercent()));

        maxAmountField.setText(String.valueOf(codedDiscount.getMaxDiscountAmount()));

        editButton.setOnAction( e -> {
            Date newStart = Date.from(startDatePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            Date newEnd = Date.from(endDatePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            try {
                int newPercent = Integer.parseInt(discountPercentField.getText());
                int newMaxAmount = Integer.parseInt(maxAmountField.getText());
                try {
                    controller.getOffController().controlEditDiscountByCode(codedDiscount.getDiscountCode(), newStart, newEnd, newPercent, newMaxAmount);
                    ((Stage)anchorPane0.getScene().getWindow()).close();
                } catch (ExceptionalMassage ex) {
                    new AlertBox(this, ex, controller).showAndWait();
                }

            }
            catch(NumberFormatException ex){
                new AlertBox(this, "Enter number for percent or maximum amount, please","OK",controller).showAndWait();
            }

        });

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
        DatePicker startDatePicker = new DatePicker();
        startDatePicker.setPrefHeight(49.0);
        startDatePicker.setPrefWidth(352.0);
        startDatePicker.setStyle("-fx-background-color: transparent;");
        startDatePicker.setPromptText("Start Date");

// Adding child to parent
        hBox3.getChildren().add(startDatePicker);

// Adding child to parent
        anchorPane0.getChildren().add(hBox3);
        HBox hBox5 = new HBox();
        hBox5.setPrefHeight(51.0);
        hBox5.setPrefWidth(345.0);
        hBox5.setLayoutX(52.0);
        hBox5.setStyle("-fx-background-color: white;"+"-fx-border-color: #a2a2a2;"+"-fx-border-width: 0px 0px 2px 0px;");
        hBox5.setLayoutY(229.0);
        DatePicker endDatePicker = new DatePicker();
        endDatePicker.setPrefHeight(49.0);
        endDatePicker.setPrefWidth(352.0);
        endDatePicker.setStyle("-fx-background-color: transparent;");
        endDatePicker.setPromptText("End Date");

// Adding child to parent
        hBox5.getChildren().add(endDatePicker);

// Adding child to parent
        anchorPane0.getChildren().add(hBox5);
        ListView customers = new ListView();
        customers.setPrefHeight(138.0);
        customers.setPrefWidth(370.0);
        customers.setLayoutX(438.0);
        customers.setLayoutY(156.0);

// Adding child to parent
        anchorPane0.getChildren().add(customers);
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
        TextField discountPercentField = new TextField();
        discountPercentField.setPrefHeight(51.0);
        discountPercentField.setPrefWidth(295.0);
        discountPercentField.setStyle("-fx-background-color: transparent;");
        discountPercentField.setOpacity(0.83);
        discountPercentField.setPromptText("Enter Discount Code Percent");

// Adding child to parent
        hBox10.getChildren().add(discountPercentField);

// Adding child to parent
        anchorPane0.getChildren().add(hBox10);
        HBox hBox12 = new HBox();
        hBox12.setPrefHeight(51.0);
        hBox12.setPrefWidth(345.0);
        hBox12.setLayoutX(52.0);
        hBox12.setStyle("-fx-background-color: white;"+"-fx-border-color: #a2a2a2;"+"-fx-border-width: 0px 0px 2px 0px;");
        hBox12.setLayoutY(381.0);
        TextField maximumAmountField = new TextField();
        maximumAmountField.setPrefHeight(51.0);
        maximumAmountField.setPrefWidth(295.0);
        maximumAmountField.setStyle("-fx-background-color: transparent;");
        maximumAmountField.setOpacity(0.83);
        maximumAmountField.setPromptText("Enter Maximum discount amount");

// Adding child to parent
        hBox12.getChildren().add(maximumAmountField);

// Adding child to parent
        anchorPane0.getChildren().add(hBox12);
        HBox hBox14 = new HBox();
        hBox14.setPrefHeight(51.0);
        hBox14.setPrefWidth(345.0);
        hBox14.setLayoutX(52.0);
        hBox14.setStyle("-fx-background-color: white;"+"-fx-border-color: #a2a2a2;"+"-fx-border-width: 0px 0px 2px 0px;");
        hBox14.setLayoutY(455.0);
        TextField maxNumberOfUsageField = new TextField();
        maxNumberOfUsageField.setPrefHeight(51.0);
        maxNumberOfUsageField.setPrefWidth(295.0);
        maxNumberOfUsageField.setStyle("-fx-background-color: transparent;");
        maxNumberOfUsageField.setOpacity(0.83);
        maxNumberOfUsageField.setPromptText("Enter Maximum number of usage");

// Adding child to parent
        hBox14.getChildren().add(maxNumberOfUsageField);

        anchorPane0.getChildren().add(hBox14);

        //
        HBox hbox155 = new HBox();
        hbox155.setPrefHeight(51.0);
        hbox155.setPrefWidth(345.0);
        hbox155.setLayoutX(425.0);
        hbox155.setStyle("-fx-background-color: white;"+"-fx-border-color: #a2a2a2;"+"-fx-border-width: 0px 0px 2px 0px;");
        hbox155.setLayoutY(381.0);
        TextField codeField = new TextField();
        codeField.setPrefHeight(51.0);
        codeField.setPrefWidth(295.0);
        codeField.setStyle("-fx-background-color: transparent;");
        codeField.setOpacity(0.83);
        codeField.setPromptText("Enter code here");

        hbox155.getChildren().add(codeField);

// Adding child to parent
        anchorPane0.getChildren().add(hbox155);
        Button createButton = new Button();
        createButton.setPrefHeight(70.0);
        createButton.setPrefWidth(201.0);
        createButton.setLayoutX(523.0);
        createButton.setStyle("-fx-background-color: #4678c8;"+"-fx-background-radius: 100PX;"+"-fx-text-fill: #f5f5f2;");
        createButton.setLayoutY(446.0);
        createButton.setText("Create Discount Code");
        createButton.setMnemonicParsing(false);

// Adding child to parent
        anchorPane0.getChildren().add(createButton);
        customers.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        //Adding controller
        for (String username : controller.getAccountController().controlGetListOfAccountUserNames()) {
            if(Account.getAccountByUsernameWithinAvailable(username) instanceof Customer) {
                customers.getItems().add(username);
            }
        }

        createButton.setDisable(true);

        createButton.setOnAction( e -> {
            Date startDate = Date.from(startDatePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            Date  endDate = Date.from(endDatePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            String code = codeField.getText();
            try{
                int percent = Integer.parseInt(discountPercentField.getText());
                int maxAmount = Integer.parseInt(maximumAmountField.getText());
                int maxNumber = Integer.parseInt(maxNumberOfUsageField.getText());
                HashMap<Customer, Integer> maxNumberOfUsage = new HashMap<>();
                try {
                    ObservableList<String> userNames = customers.getSelectionModel().getSelectedItems();
                    for (String userName : userNames) {
                        maxNumberOfUsage.put((Customer)Customer.getAccountByUsernameWithinAvailable(userName),maxNumber);
                    }
                    controller.getOffController().controlCreateCodedDiscount(code, startDate, endDate, percent, maxAmount,maxNumberOfUsage);
                    ((Stage)anchorPane0.getScene().getWindow()).close();
                } catch(ExceptionalMassage ex ){
                    new AlertBox(this, ex, controller).showAndWait();
                }
            }catch (NumberFormatException ex){
                new AlertBox(this, "Enter number for percent or maximum amount or maximum number, please","OK",controller).showAndWait();
            }
        });

        anchorPane0.onKeyReleasedProperty().set( e -> {
            boolean isDisable = (discountPercentField.getText().trim().equals("") || maximumAmountField.getText().trim().equals("") || maxNumberOfUsageField.getText().trim().equals("") ||
                    codeField.getText().trim().equals("") || startDatePicker.getValue().toString().trim().equals("") || endDatePicker.getValue().toString().trim().equals(""));
            createButton.setDisable(isDisable);
        });


        return new Scene(anchorPane0);
    }

    private Scene createDetails(String discountCode){
        AnchorPane anchorPane0 = new AnchorPane();

        anchorPane0.setPrefHeight(550.0);


        anchorPane0.setPrefWidth(700.0);
        anchorPane0.setStyle("-fx-background-color: #f5f5f2;");
        HBox hBox1 = new HBox();
        hBox1.setPrefHeight(102.0);
        hBox1.setPrefWidth(700.0);
        hBox1.setStyle("-fx-background-color: #4477c8;");

        // Adding child to parent
        anchorPane0.getChildren().add(hBox1);
        HBox hBox2 = new HBox();
        hBox2.setPrefHeight(102.0);
        hBox2.setPrefWidth(700.0);
        hBox2.setStyle("-fx-background-color: #4477c8;");
        hBox2.setLayoutY(448.0);

        // Adding child to parent
        anchorPane0.getChildren().add(hBox2);
        Text text3 = new Text();
        text3.setStrokeWidth(0.0);
        text3.setStrokeType(OUTSIDE);
        text3.setLayoutX(111.0);
        text3.setLayoutY(160.0);
        text3.setText("Tabrik!");

        text3.setWrappingWidth(477.6708984375);

        // Adding child to parent
        anchorPane0.getChildren().add(text3);
        try {
            text3.setText(controller.getOffController().controlGetDiscountByCode(discountCode).toString());
        } catch (ExceptionalMassage ex){
            new AlertBox(this, ex, controller).showAndWait();
        }

        return new Scene(anchorPane0);
    }
}
