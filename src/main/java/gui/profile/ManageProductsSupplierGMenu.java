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

        // Adding controller

        for (Product product : Product.getProductForSupplier((Supplier) controller.getAccount())) {
            products.getItems().add(product.getProductId());
        }


        createButton.setOnAction( e -> {
            Stage newStage = createNewStage(true,null);
            newStage.setTitle("Create product");
            newStage.showAndWait();
        });

        editButton.setOnAction( e -> {
            ObservableList<String> selectedProduct = products.getSelectionModel().getSelectedItems();
            for (String productId : selectedProduct) {
                Stage newStage = createNewStage(false, Product.getProductById(productId));
                newStage.setTitle("Edit product");
                newStage.showAndWait();
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
        TextField categoryField = new TextField();
        categoryField.setPrefHeight(51.0);
        categoryField.setPrefWidth(295.0);
        categoryField.setStyle("-fx-background-color: transparent;");
        categoryField.setOpacity(0.83);
        categoryField.setPromptText("Category name");

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

        File[] selectedImage = new File[1];

        fileChooserButton.setOnAction( e -> {
            Stage newStage = new Stage();
            newStage.initModality(Modality.APPLICATION_MODAL);
            newStage.setTitle("Create product");
            selectedImage[0] = fileChooser.showOpenDialog(newStage);
            imageURL.setText(selectedImage[0].getAbsolutePath());
        });

        anchorPane0.onKeyReleasedProperty().set( e -> {
            boolean isDisable = (keyField.getText().trim().equals("") || valueField.getText().trim().equals("") );
            addToList.setDisable(isDisable);
        });

        HashMap<String, String> specification = new HashMap<>();

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
            String categoryName = categoryField.getText();
            String description = descriptionField.getText();
            try{
                int price = Integer.parseInt(priceField.getText());
                int remainedNumber = Integer.parseInt(remainedNumberField.getText());
                //try{

                //}  catch (ExceptionalMassage ex){

                //}
            } catch (NumberFormatException ex){
                new AlertBox(this, "Enter number for price or remained number, please","OK",controller).showAndWait();

            }
        });

        return new Scene(anchorPane0);

    }
}
