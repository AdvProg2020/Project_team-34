package gui.profile;

import account.Supplier;
import controller.Controller;
import discount.CodedDiscount;
import discount.Sale;
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
import product.Product;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

import static javafx.scene.shape.StrokeType.OUTSIDE;

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
        TableView salesList = new TableView();
        salesList.setPrefHeight(398.0);
        salesList.setPrefWidth(350.0);
        salesList.setLayoutX(50.0);
        salesList.setLayoutY(154.0);

        // Adding child to parent
        anchorPane0.getChildren().add(salesList);
        Label label4 = new Label();
        label4.setLayoutX(50.0);
        label4.setLayoutY(120.0);
        label4.setText("Sales :");

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

        Text saleInfo = new Text();
        saleInfo.setWrappingWidth(233);
        saleInfo.setLayoutX(505);
        saleInfo.setLayoutY(154);

        anchorPane0.getChildren().add( saleInfo);

        // Adding child to parent
        anchorPane0.getChildren().add(editButton);

        //salesList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);


        // Adding controller

        TableColumn saleId = new TableColumn("Sale ID");
        saleId.setCellValueFactory(new PropertyValueFactory<>("offId"));
        TableColumn percent = new TableColumn("Percent");
        percent.setCellValueFactory(new PropertyValueFactory<>("percent"));
        TableColumn start = new TableColumn("Start");
        start.setCellValueFactory(new PropertyValueFactory<>("start"));
        TableColumn end = new TableColumn("End");
        end.setCellValueFactory(new PropertyValueFactory<>("end"));
        salesList.getColumns().addAll(saleId,percent, start, end);


        try {
            for (Sale sale : controller.getOffController().controlGetAllSales()) {
                salesList.getItems().add(sale);
            }
        } catch (ExceptionalMassage exceptionalMassage) {
            exceptionalMassage.printStackTrace();
        }


        detailsButton.setOnAction( e -> {
            ObservableList<Sale> saleIds = salesList.getSelectionModel().getSelectedItems();
            for (Sale id : saleIds) {
                saleInfo.setText(id.toString());
            }
        });

        editButton.setOnAction( e -> {
            ObservableList<Sale> saleIds = salesList.getSelectionModel().getSelectedItems();
            for (Sale id : saleIds) {
                Image logoImage = null;
                try {
                    logoImage = new Image(new FileInputStream("./src/main/resources/header/Logo.png"));
                } catch (FileNotFoundException exc) {
                }

                Stage newStage = new Stage();
                newStage.initModality(Modality.APPLICATION_MODAL);
                newStage.setScene(createEdit(id));
                newStage.getIcons().add(logoImage);
                newStage.setTitle("Edit Sale Menu");
                newStage.showAndWait();
            }

        });

        createButton.setOnAction( e -> {
            Image logoImage = null;
            try {
                logoImage = new Image(new FileInputStream("./src/main/resources/header/Logo.png"));
            } catch (FileNotFoundException exc) {
            }
            Stage newStage = new Stage();
            newStage.initModality(Modality.APPLICATION_MODAL);
            newStage.setScene(createCreate());
            newStage.getIcons().add(logoImage);
            newStage.setTitle("Create sale menu");
            newStage.showAndWait();

        });

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
        ListView addingProducts = new ListView();
        addingProducts.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        addingProducts.setPrefHeight(138.0);
        addingProducts.setPrefWidth(283.0);
        addingProducts.setLayoutX(438.0);
        addingProducts.setLayoutY(156.0);

// Adding child to parent
        anchorPane0.getChildren().add(addingProducts);
        Label label8 = new Label();
        label8.setLayoutX(438.0);
        label8.setLayoutY(121.0);
        label8.setText("Adding products:");

// Adding child to parent
        anchorPane0.getChildren().add(label8);
        Label label9 = new Label();
        label9.setLayoutX(52.0);
        label9.setLayoutY(121.0);
        label9.setText("Edit Sale:");

// Adding child to parent
        anchorPane0.getChildren().add(label9);
        HBox hBox10 = new HBox();
        hBox10.setPrefHeight(51.0);
        hBox10.setPrefWidth(345.0);
        hBox10.setLayoutX(52.0);
        hBox10.setStyle("-fx-background-color: white;"+"-fx-border-color: #a2a2a2;"+"-fx-border-width: 0px 0px 2px 0px;");
        hBox10.setLayoutY(306.0);
        TextField newPercentField = new TextField();
        newPercentField.setPrefHeight(51.0);
        newPercentField.setPrefWidth(295.0);
        newPercentField.setStyle("-fx-background-color: transparent;");
        newPercentField.setOpacity(0.83);
        newPercentField.setPromptText("Enter discount percent");

// Adding child to parent
        hBox10.getChildren().add(newPercentField);

// Adding child to parent
        anchorPane0.getChildren().add(hBox10);
        Button editButton = new Button();
        editButton.setPrefHeight(49.0);
        editButton.setPrefWidth(365.0);
        editButton.setLayoutX(553.0);
        editButton.setStyle("-fx-background-color: #4678c8;"+"-fx-background-radius: 100PX;"+"-fx-text-fill: #f5f5f2;");
        editButton.setLayoutY(345.0);
        editButton.setText("Edit sale");
        editButton.setMnemonicParsing(false);

// Adding child to parent
        anchorPane0.getChildren().add(editButton);
        ListView removingProducts = new ListView();
        removingProducts.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        removingProducts.setPrefHeight(138.0);
        removingProducts.setPrefWidth(283.0);
        removingProducts.setLayoutX(767.0);
        removingProducts.setLayoutY(156.0);

// Adding child to parent
        anchorPane0.getChildren().add(removingProducts);
        Label label14 = new Label();
        label14.setLayoutX(767.0);
        label14.setLayoutY(121.0);
        label14.setText("Removing products:");

// Adding child to parent
        anchorPane0.getChildren().add(label14);

        startDatePicker.setValue(sale.getStart().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());

        endDatePicker.setValue(sale.getEnd().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());

        newPercentField.setText(String.valueOf(sale.getPercent()));

        try {
            for (Product product : controller.getOffController().controlGetNotSaleProductsForSupplier(sale.getOffId())) {
                addingProducts.getItems().add(product.getProductId());
            }
        } catch (ExceptionalMassage exceptionalMassage) {
            exceptionalMassage.printStackTrace();
        }

        for (Product product : sale.getProducts()) {
            removingProducts.getItems().add(product.getProductId());
        }

        editButton.setOnAction( e -> {
            Date newStart = Date.from(startDatePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            Date newEnd = Date.from(endDatePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            try{
                int newPercent = Integer.parseInt(newPercentField.getText());
                try {
                    ObservableList<String> adding = addingProducts.getSelectionModel().getSelectedItems();
                    ArrayList<Product> addingProductsArray = new ArrayList<>();
                    for (String s : adding) {
                        addingProductsArray.add(controller.getProductController().getProductById(s));
                    }
                    ObservableList<String> removing = removingProducts.getSelectionModel().getSelectedItems();
                    ArrayList<Product> removingProductsArray = new ArrayList<>();
                    for (String s : removing) {
                        removingProductsArray.add(controller.getProductController().getProductById(s));
                    }
                    controller.getOffController().controlEditSaleById(sale.getOffId(), newEnd, newStart, newPercent, addingProductsArray, removingProductsArray);
                    ((Stage) anchorPane0.getScene().getWindow()).close();
                } catch (ExceptionalMassage ex){
                    new AlertBox(this, ex, controller);
                }
            } catch (NumberFormatException ex){
                new AlertBox(this, "Enter number for percent please","OK",controller).showAndWait();
            }
        });

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
        ListView productsList = new ListView();
        productsList.setPrefHeight(138.0);
        productsList.setPrefWidth(370.0);
        productsList.setLayoutX(438.0);
        productsList.setLayoutY(156.0);

// Adding child to parent
        anchorPane0.getChildren().add(productsList);
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
        TextField percentField = new TextField();
        percentField.setPrefHeight(51.0);
        percentField.setPrefWidth(295.0);
        percentField.setStyle("-fx-background-color: transparent;");
        percentField.setOpacity(0.83);
        percentField.setPromptText("Enter discount percent");

// Adding child to parent
        hBox10.getChildren().add(percentField);

// Adding child to parent
        anchorPane0.getChildren().add(hBox10);
        Button createSaleButton = new Button();
        createSaleButton.setPrefHeight(70.0);
        createSaleButton.setPrefWidth(201.0);
        createSaleButton.setLayoutX(523.0);
        createSaleButton.setStyle("-fx-background-color: #4678c8;"+"-fx-background-radius:"+"100PX; -fx-text-fill: #f5f5f2;");
        createSaleButton.setLayoutY(372.0);
        createSaleButton.setText("Create Sale");
        createSaleButton.setMnemonicParsing(false);

// Adding child to parent
        anchorPane0.getChildren().add(createSaleButton);

        try {
            for (Product product : controller.getProductController().getProductForSupplier((Supplier) controller.getAccount())) {
                if(!controller.getOffController().isProductHasAnySale(product)) {
                    productsList.getItems().add(product.getProductId());
                }
            }
        } catch (ExceptionalMassage exceptionalMassage) {
            exceptionalMassage.printStackTrace();
        }

        productsList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        createSaleButton.setOnAction( e -> {
            Date newStart = Date.from(startDatePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            Date newEnd = Date.from(endDatePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            try{
                int percent = Integer.parseInt(percentField.getText());
                ArrayList<Product> arrayList = new ArrayList<>();
                ObservableList<String> products = productsList.getSelectionModel().getSelectedItems();
                for (String product : products) {
                    try {
                        arrayList.add(controller.getProductController().getProductById(product));
                    } catch (ExceptionalMassage exceptionalMassage) {
                        exceptionalMassage.printStackTrace();
                    }
                }
                try{
                    controller.getOffController().controlCreateSale(newStart,newEnd,percent,arrayList);
                    ((Stage)anchorPane0.getScene().getWindow()).close();
                } catch (ExceptionalMassage ex){
                    new AlertBox(this, ex, controller).showAndWait();
                }

            } catch (NumberFormatException ex){
                new AlertBox(this, "Enter number for percent, please","OK",controller).showAndWait();
            }
        });





        return new Scene(anchorPane0);
    }
}
