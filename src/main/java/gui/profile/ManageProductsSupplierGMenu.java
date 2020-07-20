package gui.profile;

import account.Supplier;
import controller.Controller;
import exceptionalMassage.ExceptionalMassage;
import gui.GMenu;
import gui.alerts.AlertBox;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import product.Product;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;

import static javafx.scene.shape.StrokeType.OUTSIDE;

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
        ListView products = new ListView();
        products.setPrefHeight(398.0);
        products.setPrefWidth(350.0);
        products.setLayoutX(50.0);
        products.setLayoutY(154.0);

        // Adding child to parent
        anchorPane0.getChildren().add(products);
        Label label4 = new Label();
        label4.setLayoutX(50.0);
        label4.setLayoutY(120.0);
        label4.setText("Products:");

        // Adding child to parent
        anchorPane0.getChildren().add(label4);
        Button detailButton = new Button();
        detailButton.setPrefHeight(33.0);
        detailButton.setPrefWidth(233.0);
        detailButton.setLayoutX(505.0);
        detailButton.setStyle("-fx-background-color: #4678c8;"+"-fx-background-radius: 100PX;"+"-fx-text-fill: #f5f5f2;");
        detailButton.setLayoutY(238.0);
        detailButton.setText("View details");
        detailButton.setMnemonicParsing(false);

        // Adding child to parent
        anchorPane0.getChildren().add(detailButton);
        Button createButton = new Button();
        createButton.setPrefHeight(33.0);
        createButton.setPrefWidth(233.0);
        createButton.setLayoutX(505.0);
        createButton.setStyle("-fx-background-color: #4678c8;"+"-fx-background-radius: 100PX;"+"-fx-text-fill: #f5f5f2;");
        createButton.setLayoutY(395.0);
        createButton.setText("Create");
        createButton.setMnemonicParsing(false);

        // Adding child to parent
        anchorPane0.getChildren().add(createButton);
        Button editButton = new Button();
        editButton.setPrefHeight(33.0);
        editButton.setPrefWidth(233.0);
        editButton.setLayoutX(505.0);
        editButton.setStyle("-fx-background-color: #4678c8;"+"-fx-background-radius: 100PX;"+"-fx-text-fill: #f5f5f2;");
        editButton.setLayoutY(317.0);
        editButton.setText("Edit");
        editButton.setMnemonicParsing(false);

        // Adding child to parent
        anchorPane0.getChildren().add(editButton);
        Button removeButton = new Button();
        removeButton.setPrefHeight(33.0);
        removeButton.setPrefWidth(233.0);
        removeButton.setLayoutX(505.0);
        removeButton.setStyle("-fx-background-color: #4678c8;"+"-fx-background-radius: 100PX;"+"-fx-text-fill: #f5f5f2;");
        removeButton.setLayoutY(473.0);
        removeButton.setText("Remove");
        removeButton.setMnemonicParsing(false);

        // Adding child to parent
        anchorPane0.getChildren().add(removeButton);

        Button showCustomers = new Button();
        showCustomers.setPrefHeight(33.0);
        showCustomers.setPrefWidth(233.0);
        showCustomers.setLayoutX(505.0);
        showCustomers.setStyle("-fx-background-color: #4678c8;"+"-fx-background-radius: 100PX;"+"-fx-text-fill: #f5f5f2;");
        showCustomers.setLayoutY(165.0);
        showCustomers.setText("Show customers");
        showCustomers.setMnemonicParsing(false);

        anchorPane0.getChildren().add(showCustomers);
        // Adding controller

        try {
            for (Product product : controller.getProductController().getProductForSupplier((Supplier) controller.getAccount())) {
                products.getItems().add(product.getProductId());
            }
        } catch (ExceptionalMassage exceptionalMassage) {
            new AlertBox(this, exceptionalMassage, controller).showAndWait();
        }


        createButton.setOnAction( e -> {
            Stage newStage = createNewStage(true,null);
            newStage.setTitle("Create product");
            newStage.showAndWait();
        });

        editButton.setOnAction( e -> {
            ObservableList<String> selectedProduct = products.getSelectionModel().getSelectedItems();
            for (String productId : selectedProduct) {
                Stage newStage = null;
                try {
                    newStage = createNewStage(false, controller.getProductController().getProductById(productId));
                } catch (ExceptionalMassage exceptionalMassage) {
                    new AlertBox(this, exceptionalMassage, controller).showAndWait();
                }
                newStage.setTitle("Edit product");
                newStage.showAndWait();
            }

        });

        removeButton.setOnAction( e -> {
            ObservableList<String> selectedProduct = products.getSelectionModel().getSelectedItems();
            for (String productId : selectedProduct) {
                try{
                    controller.getProductController().controlRemoveProductById(productId);
                } catch (ExceptionalMassage ex){
                    new AlertBox(this, ex, controller);
                }
            }
            products.getItems().clear();
            try {
                for (Product product : controller.getProductController().getProductForSupplier((Supplier) controller.getAccount())) {
                    products.getItems().add(product.getProductId());
                }
            } catch (ExceptionalMassage exceptionalMassage) {
                new AlertBox(this, exceptionalMassage, controller).showAndWait();
            }

        });

        detailButton.setOnAction( e -> {
            ObservableList<String> ids = products.getSelectionModel().getSelectedItems();
            for (String id : ids) {
                Image logoImage = null;
                try {
                    logoImage = new Image(new FileInputStream("./src/main/resources/header/Logo.png"));
                } catch (FileNotFoundException exc) {
                }
                Stage newStage = new Stage();
                newStage.getIcons().add(logoImage);
                newStage.setTitle("Product details");
                newStage.setScene(createDetails(id));
                newStage.showAndWait();
            }
        });

        showCustomers.setOnAction(e -> {
            ObservableList<String> selectedProduct = products.getSelectionModel().getSelectedItems();
            for (String s : selectedProduct) {
                try {
                    stage.setScene(new ViewProductCustomers("View products customer",this,stage,controller,controller.getProductController().getProductById(s)).getScene());
                } catch (ExceptionalMassage exceptionalMassage) {
                    new AlertBox(this, exceptionalMassage, controller).showAndWait();
                }
            }
        });


        return new Scene(anchorPane0);
    }

    private Stage createNewStage(boolean isCreate, Product editing) {
        Image logoImage = null;
        try {
            logoImage = new Image(new FileInputStream("./src/main/resources/header/Logo.png"));
        } catch (FileNotFoundException exc) {
        }
        Stage newStage = new Stage();
        newStage.initModality(Modality.APPLICATION_MODAL);
        newStage.setScene(isCreate ? createProduct() : editProduct(editing));
        newStage.getIcons().add(logoImage);
        return newStage;
    }

    private Scene editProduct(Product editing){
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
        ComboBox<String> availableField = new ComboBox<>();
        availableField.setPrefHeight(31.0);
        availableField.setPrefWidth(268.0);
        availableField.setLayoutX(40.0);
        availableField.setLayoutY(189.0);
        availableField.setPromptText("Choose field!");

        // Adding child to parent
        anchorPane0.getChildren().add(availableField);
        HBox newValueHbox = new HBox();
        newValueHbox.setPrefHeight(51.0);
        newValueHbox.setPrefWidth(316.0);
        newValueHbox.setLayoutX(340.0);
        newValueHbox.setStyle("-fx-background-color: white;"+"-fx-border-color: #a2a2a2;"+"-fx-border-width: 0px 0px 2px 0px;");
        newValueHbox.setLayoutY(179.0);
        TextField valueField = new TextField();
        valueField.setPrefHeight(51.0);
        valueField.setPrefWidth(295.0);
        valueField.setStyle("-fx-background-color: transparent;");
        valueField.setOpacity(0.83);
        valueField.setPromptText("Enter new Value");

        // Adding child to parent
        newValueHbox.getChildren().add(valueField);

        // Adding child to parent
        anchorPane0.getChildren().add(newValueHbox);

        // Adding controller
        availableField.getItems().addAll("name","nameOfCompany","description");
        availableField.getItems().addAll(editing.getSpecification().keySet());

        Button addToListButton = new Button();
        addToListButton.setPrefHeight(37.0);
        addToListButton.setPrefWidth(154);
        addToListButton.setLayoutX(600.0);
        addToListButton.setStyle("-fx-background-color: #4678c8;"+"-fx-background-radius: 100PX;"+"-fx-text-fill: #f5f5f2;");
        addToListButton.setLayoutY(238);
        addToListButton.setLayoutX(80);
        addToListButton.setText("Add to list");
        addToListButton.setMnemonicParsing(false);

        Button editButton = new Button();
        editButton.setPrefHeight(37.0);
        editButton.setPrefWidth(154);
        editButton.setLayoutX(600.0);
        editButton.setStyle("-fx-background-color: #4678c8;"+"-fx-background-radius: 100PX;"+"-fx-text-fill: #f5f5f2;");
        editButton.setLayoutY(238);
        editButton.setLayoutX(415);
        editButton.setText("Edit");
        editButton.setMnemonicParsing(false);

        anchorPane0.getChildren().addAll(editButton, addToListButton);

        // Adding child to parent


        HashMap<String , String> editRequest = new HashMap<>();

        Button fileChooserButton = new Button("...");
        fileChooserButton.setPrefWidth(62);
        fileChooserButton.setPrefHeight(59);



        addToListButton.setOnAction( e -> {
            String key = availableField.getValue();
            String value = valueField.getText();
            editRequest.put(key, value);
            availableField.setPromptText("Choose field!");
            valueField.clear();
        });

        editButton.setOnAction( e -> {
            try{
                controller.getProductController().controlEditProductById(editing.getProductId(),editRequest);
                ((Stage)anchorPane0.getScene().getWindow()).close();
            } catch (ExceptionalMassage ex){
                new AlertBox(this, ex, controller).showAndWait();
            }

        });

        return new Scene(anchorPane0);
    }

    private Scene createProduct(){
        AnchorPane anchorPane0 = new AnchorPane();
        anchorPane0.setPrefHeight(740.0);
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
        hBox2.setLayoutY(638.0);

        // Adding child to parent
        anchorPane0.getChildren().add(hBox2);
        Label label3 = new Label();
        label3.setLayoutX(65.0);
        label3.setLayoutY(119.0);
        label3.setText("Create product menu:");

        // Adding child to parent
        anchorPane0.getChildren().add(label3);
        GridPane gridPane4 = new GridPane();
        gridPane4.setPrefHeight(432.0);
        gridPane4.setPrefWidth(356.0);
        gridPane4.setLayoutX(65.0);
        gridPane4.setLayoutY(159.0);
        HBox hBox5 = new HBox();
        hBox5.setPrefHeight(60.0);
        hBox5.setPrefWidth(316.0);
        hBox5.setStyle("-fx-background-color: white;"+"-fx-border-color: #a2a2a2;"+"-fx-border-width: 0px 0px 2px 0px;");
        TextField nameField = new TextField();
        nameField.setPrefHeight(51.0);
        nameField.setPrefWidth(295.0);
        nameField.setStyle("-fx-background-color: transparent;");
        nameField.setOpacity(0.83);
        nameField.setPromptText("Product name");

        // Adding child to parent
        hBox5.getChildren().add(nameField);

        // Adding child to parent
        gridPane4.add(hBox5,0,0);
        HBox hBox7 = new HBox();
        hBox7.setPrefHeight(51.0);
        hBox7.setPrefWidth(345.0);
        hBox7.setStyle("-fx-background-color: white;"+"-fx-border-color: #a2a2a2;"+"-fx-border-width: 0px 0px 2px 0px;");
        TextField nameOfCompanyField = new TextField();
        nameOfCompanyField.setPrefHeight(51.0);
        nameOfCompanyField.setPrefWidth(295.0);
        nameOfCompanyField.setStyle("-fx-background-color: transparent;");
        nameOfCompanyField.setOpacity(0.83);
        nameOfCompanyField.setPromptText("Name of company");

        // Adding child to parent
        hBox7.getChildren().add(nameOfCompanyField);

        // Adding child to parent
        gridPane4.add(hBox7,0,1);
        HBox hBox9 = new HBox();
        hBox9.setPrefHeight(51.0);
        hBox9.setPrefWidth(345.0);
        hBox9.setStyle("-fx-background-color: white;"+"-fx-border-color: #a2a2a2;"+"-fx-border-width: 0px 0px 2px 0px;");
        TextField priceField = new TextField();
        priceField.setPrefHeight(51.0);
        priceField.setPrefWidth(295.0);
        priceField.setStyle("-fx-background-color: transparent;");
        priceField.setOpacity(0.83);
        priceField.setPromptText("Price");

        // Adding child to parent
        hBox9.getChildren().add(priceField);

        // Adding child to parent
        gridPane4.add(hBox9,0,2);
        HBox hBox11 = new HBox();
        hBox11.setPrefHeight(51.0);
        hBox11.setPrefWidth(345.0);
        hBox11.setStyle("-fx-background-color: white;"+"-fx-border-color: #a2a2a2;"+"-fx-border-width: 0px 0px 2px 0px;");
        TextField remainedNumberField = new TextField();
        remainedNumberField.setPrefHeight(51.0);
        remainedNumberField.setPrefWidth(295.0);
        remainedNumberField.setStyle("-fx-background-color: transparent;");
        remainedNumberField.setOpacity(0.83);
        remainedNumberField.setPromptText("Remained number");

        // Adding child to parent
        hBox11.getChildren().add(remainedNumberField);

        // Adding child to parent

        gridPane4.add(hBox11,0,3);
        HBox hBox13 = new HBox();
        hBox13.setPrefHeight(51.0);
        hBox13.setPrefWidth(356.0);
        hBox13.setStyle("-fx-background-color: white;"+"-fx-border-color: #a2a2a2;"+"-fx-border-width: 0px 0px 2px 0px;");
        ComboBox<String> categoryField = allProductClassifierCategoriesChoiceBox();
        categoryField.setPrefHeight(51.0);
        categoryField.setPrefWidth(356.0);
        //categoryField.setStyle("-fx-background-color: transparent;");
        categoryField.setOpacity(0.83);

        // Adding child to parent
        hBox13.getChildren().add(categoryField);

        // Adding child to parent
        gridPane4.add(hBox13,0,4);
        HBox hBox15 = new HBox();
        hBox15.setPrefHeight(51.0);
        hBox15.setPrefWidth(345.0);
        hBox15.setStyle("-fx-background-color: white;"+"-fx-border-color: #a2a2a2;"+"-fx-border-width: 0px 0px 2px 0px;");
        TextField descriptionField = new TextField();
        descriptionField.setPrefHeight(51.0);
        descriptionField.setPrefWidth(295.0);
        descriptionField.setStyle("-fx-background-color: transparent;");
        descriptionField.setOpacity(0.83);
        descriptionField.setPromptText("Enter description");

        // Adding child to parent
        hBox15.getChildren().add(descriptionField);

        // Adding child to parent

        gridPane4.add(hBox15,0,5);
        HBox hBox16 = new HBox();
        hBox16.setPrefHeight(51.0);
        hBox16.setPrefWidth(345.0);
        hBox16.setStyle("-fx-background-color: white;"+"-fx-border-color: #a2a2a2;"+"-fx-border-width: 0px 0px 2px 0px;");
        TextField imageURL = new TextField();
        imageURL.setPrefHeight(51.0);
        imageURL.setPrefWidth(295.0);
        imageURL.setStyle("-fx-background-color: transparent;");
        imageURL.setOpacity(0.83);
        imageURL.setPromptText("Choose image");
        Button fileChooserButton = new Button("...");
        fileChooserButton.setPrefWidth(62);
        fileChooserButton.setPrefHeight(59);

        // Adding child to parent
        hBox16.getChildren().add(imageURL);
        hBox16.getChildren().add(fileChooserButton);

        // Adding child to parent

        gridPane4.add(hBox16,0,6);

        // Adding child to parent
        anchorPane0.getChildren().add(gridPane4);
        Button createButton = new Button();
        createButton.setPrefHeight(37.0);
        createButton.setPrefWidth(233.0);
        createButton.setLayoutX(600.0);
        createButton.setStyle("-fx-background-color: #4678c8;"+"-fx-background-radius: 100PX;"+"-fx-text-fill: #f5f5f2;");
        createButton.setLayoutY(507.0);
        createButton.setText("Create");
        createButton.setMnemonicParsing(false);

// Adding child to parent
        anchorPane0.getChildren().add(createButton);
        HBox hBox18 = new HBox();
        hBox18.setPrefHeight(21.0);
        hBox18.setPrefWidth(145.0);
        hBox18.setLayoutX(551.0);
        hBox18.setStyle("-fx-background-color: white;"+"-fx-border-color: #a2a2a2;"+"-fx-border-width: 0px 0px 2px 0px;");
        hBox18.setLayoutY(209.0);
        TextField keyField = new TextField();
        keyField.setPrefHeight(51.0);
        keyField.setPrefWidth(237.0);
        keyField.setStyle("-fx-background-color: transparent;");
        keyField.setOpacity(0.83);
        keyField.setPromptText("Enter Key");

// Adding child to parent
        hBox18.getChildren().add(keyField);

// Adding child to parent
        anchorPane0.getChildren().add(hBox18);
        HBox hBox20 = new HBox();
        hBox20.setPrefHeight(31.0);
        hBox20.setPrefWidth(145.0);
        hBox20.setLayoutX(740.0);
        hBox20.setStyle("-fx-background-color: white;"+"-fx-border-color: #a2a2a2;"+"-fx-border-width: 0px 0px 2px 0px;");
        hBox20.setLayoutY(209.0);
        TextField valueField = new TextField();
        valueField.setPrefHeight(51.0);
        valueField.setPrefWidth(295.0);
        valueField.setStyle("-fx-background-color: transparent;");
        valueField.setOpacity(0.83);
        valueField.setPromptText("Enter Value");

// Adding child to parent
        hBox20.getChildren().add(valueField);

// Adding child to parent
        anchorPane0.getChildren().add(hBox20);
        Label label22 = new Label();
        label22.setLayoutX(714.0);
        label22.setLayoutY(215.0);
        label22.setText(":");

// Adding child to parent
        anchorPane0.getChildren().add(label22);
        Button addToList = new Button();
        addToList.setPrefHeight(37.0);
        addToList.setPrefWidth(233.0);
        addToList.setLayoutX(602.0);
        addToList.setStyle("-fx-background-color: #4678c8;"+"-fx-background-radius: 100PX;"+"-fx-text-fill: #f5f5f2;");
        addToList.setLayoutY(269.0);
        addToList.setText("Add to list");
        addToList.setMnemonicParsing(false);

        // Adding child to parent
        Text hashMapText = new Text();
        hashMapText.setLayoutY(350);
        hashMapText.setLayoutX(612);
        hashMapText.setWrappingWidth(210);
        hashMapText.setText("Specification :\n");

        anchorPane0.getChildren().add(hashMapText);

// Adding child to parent
        anchorPane0.getChildren().add(addToList);
        gridPane4.setVgap(15);



        // Adding controller

        addToList.setDisable(true);

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png")
        );

        createButton.setDisable(true);

        File[] selectedImage = new File[1];

        fileChooserButton.setOnAction( e -> {
            Stage newStage = new Stage();
            newStage.initModality(Modality.APPLICATION_MODAL);
            newStage.setTitle("Create product");
            selectedImage[0] = fileChooser.showOpenDialog(newStage);
            try {
                imageURL.setText(selectedImage[0].getAbsolutePath());
            } catch (NullPointerException ex){
                imageURL.clear();
            }
            boolean isDisable2 = (nameField.getText().trim().equals("") || priceField.getText().trim().equals("") || remainedNumberField.getText().trim().equals("") ||
                    nameOfCompanyField.getText().trim().equals("") || imageURL.getText().trim().equals("") || categoryField.getValue() == null );

            createButton.setDisable(isDisable2);
        });

        anchorPane0.onKeyReleasedProperty().set( e -> {
            boolean isDisable = (keyField.getText().trim().equals("") || valueField.getText().trim().equals("") );
            addToList.setDisable(isDisable);
            boolean isDisable2 = (nameField.getText().trim().equals("") || priceField.getText().trim().equals("") || remainedNumberField.getText().trim().equals("") ||
                    nameOfCompanyField.getText().trim().equals("") || imageURL.getText().trim().equals("") || categoryField.getValue() == null );

            createButton.setDisable(isDisable2);
        });

        categoryField.setOnAction(e -> {
            boolean isDisable2 = (nameField.getText().trim().equals("") || priceField.getText().trim().equals("") || remainedNumberField.getText().trim().equals("") ||
                    nameOfCompanyField.getText().trim().equals("") || imageURL.getText().trim().equals("") || categoryField.getValue() == null );
            createButton.setDisable(isDisable2);

        });

        HashMap<String, String> specification = new HashMap<>();

        imageURL.setDisable(true);

        addToList.setOnAction( e -> {
            specification.put(keyField.getText(), valueField.getText());
            keyField.setText("");
            valueField.setText("");
            // Updating text
            String text = "";
            for (String s : specification.keySet()) {
                text += s + "  :  " + specification.get(s) + "\n";
            }
            hashMapText.setText(text);
            addToList.setDisable(true);
        });

        createButton.setOnAction( e -> {
            String name = nameField.getText();
            String companyName = nameOfCompanyField.getText();
            String categoryName = categoryField.getValue();
            String description = descriptionField.getText();
            String imgURL = selectedImage[0].getAbsolutePath();
            try{
                int price = Integer.parseInt(priceField.getText());
                int remainedNumber = Integer.parseInt(remainedNumberField.getText());
                try{
                    controller.getProductController().controlAddProduct(name,companyName,price,remainedNumber,categoryName,description,specification,imgURL);
                    ((Stage)anchorPane0.getScene().getWindow()).close();
                }  catch (ExceptionalMassage ex){
                    new AlertBox(this, ex, controller).showAndWait();
                }
            } catch (NumberFormatException ex){
                new AlertBox(this, "Enter number for price or remained number, please","OK",controller).showAndWait();

            }
        });

        return new Scene(anchorPane0);

    }

    private ComboBox<String> allProductClassifierCategoriesChoiceBox() {
        ComboBox<String> choiceBox = new ComboBox<>();
        try {
            choiceBox.getItems().addAll(controller.getProductController().controlGetAllProductCategoriesName());
        } catch (ExceptionalMassage exceptionalMassage) {
            new AlertBox(this, exceptionalMassage, controller).showAndWait();
        }
        choiceBox.setPromptText("Choose Category");
        return choiceBox;
    }

    private Scene createDetails(String productId){
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
            text3.setText(controller.getProductController().getProductById(productId).toString());
        } catch (ExceptionalMassage exceptionalMassage) {
            new AlertBox(this, exceptionalMassage, controller).showAndWait();
        }


        return new Scene(anchorPane0);
    }
}
