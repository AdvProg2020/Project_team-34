package gui.cartMenu;

import com.google.gson.internal.$Gson$Preconditions;
import controller.Controller;
import exceptionalMassage.ExceptionalMassage;
import gui.GMenu;
import gui.alerts.AlertBox;
import gui.mainMenu.MainMenuG;
import gui.profile.ViewLogsForCustomerGMenu;
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

    public PurchaseMenuG(GMenu parentMenu, Stage stage, Controller controller) {
        super("Purchase Menu", parentMenu, stage, controller);
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
        HBox header = createHeader();
        hBox1.getChildren().add(header);

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
        CheckBox discountCodeCheckBox = new CheckBox();
        discountCodeCheckBox.setLayoutX(527.0);
        discountCodeCheckBox.setLayoutY(130.0);
        discountCodeCheckBox.setText("Apply discount code!");
        discountCodeCheckBox.setMnemonicParsing(false);

        // Adding child to parent
        anchorPane0.getChildren().add(discountCodeCheckBox);
        HBox hBox18 = new HBox();
        hBox18.setPrefHeight(51.0);
        hBox18.setPrefWidth(300.0);
        hBox18.setLayoutX(517.0);
        hBox18.setStyle("-fx-background-color: white;"+"-fx-border-color: #a2a2a2;"+"-fx-border-width: 0px 0px 2px 0px;");
        hBox18.setLayoutY(186.0);

        Button submitDiscountCode = new Button();
        submitDiscountCode.setPrefHeight(33.0);
        submitDiscountCode.setPrefWidth(134.0);
        submitDiscountCode.setLayoutX(840.0);
        submitDiscountCode.setStyle("-fx-background-color: #4678c8;"+"-fx-background-radius: 100PX;"+"-fx-text-fill: #f5f5f2;");
        submitDiscountCode.setLayoutY(188.0);
        submitDiscountCode.setText("Submit");
        submitDiscountCode.setMnemonicParsing(false);

        TextField discountCodeField = new TextField();
        discountCodeField.setPrefHeight(51.0);
        discountCodeField.setPrefWidth(295.0);
        discountCodeField.setStyle("-fx-background-color: transparent;");
        discountCodeField.setOpacity(0.83);
        discountCodeField.setPromptText("Discount code");

        // Adding child to parent
        hBox18.getChildren().add(discountCodeField);
        anchorPane0.getChildren().add(submitDiscountCode);

        // Adding child to parent
        anchorPane0.getChildren().add(hBox18);
        Text cartInfo = new Text();
        cartInfo.setStrokeWidth(0.0);
        cartInfo.setStrokeType(OUTSIDE);
        cartInfo.setLayoutX(517.0);
        cartInfo.setLayoutY(260.0);
        cartInfo.setText("Cart Info sits here!");
        cartInfo.setWrappingWidth(356.0);

        // Adding child to parent
        anchorPane0.getChildren().add(cartInfo);
        Button purchaseButton = new Button();
        purchaseButton.setPrefHeight(33.0);
        purchaseButton.setPrefWidth(233.0);
        purchaseButton.setLayoutX(579.0);
        purchaseButton.setStyle("-fx-background-color: #4678c8;"+"-fx-background-radius: 100PX;"+"-fx-text-fill: #f5f5f2;");
        purchaseButton.setLayoutY(511.0);
        purchaseButton.setText("Purchase");
        purchaseButton.setMnemonicParsing(false);

        // Adding child to parent
        anchorPane0.getChildren().add(purchaseButton);
        gridPane4.setVgap(15);

        Button submitShippingInfo = new Button();
        submitShippingInfo.setPrefHeight(33.0);
        submitShippingInfo.setPrefWidth(233.0);
        submitShippingInfo.setLayoutX(579.0);
        submitShippingInfo.setStyle("-fx-background-color: #4678c8;"+"-fx-background-radius: 100PX;"+"-fx-text-fill: #f5f5f2;");
        submitShippingInfo.setLayoutY(464.0);
        submitShippingInfo.setText("Submit shipping info");
        submitShippingInfo.setMnemonicParsing(false);

        anchorPane0.getChildren().add(submitShippingInfo);
        //Adding controller!
        if(!discountCodeCheckBox.isSelected()){
            discountCodeField.setDisable(true);
        }

        submitDiscountCode.setDisable(true);

        discountCodeCheckBox.setOnAction( e -> {
            if(discountCodeCheckBox.isSelected()){
                discountCodeField.setDisable(false);
                submitDiscountCode.setDisable(false);
            } else {
                discountCodeField.setDisable(true);
                submitDiscountCode.setDisable(true);
            }
        });

        cartInfo.setText(controller.getAccountController().controlViewCart().toString());

        submitDiscountCode.setOnAction( e -> {
            try{
                controller.getAccountController().controlSubmitDiscountCode(discountCodeField.getText());
                cartInfo.setText(controller.getAccountController().controlViewCart().toString());
            } catch (ExceptionalMassage ex){
                new AlertBox(this, ex, controller).showAndWait();
            }
        });

        purchaseButton.setDisable(true);


        submitShippingInfo.setOnAction( e -> {
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String cityName = cityNameField.getText();
            String address = addressField.getText();
            String postalCode = postalCodeField.getText();
            String phoneNumber = phoneNumberField.getText();

            try{
                controller.getAccountController().controlSubmitShippingInfo(firstName,lastName,cityName,address,postalCode,phoneNumber);
                purchaseButton.setDisable(false);
                cartInfo.setText(controller.getAccountController().controlViewCart().toString());
            } catch (ExceptionalMassage ex){
                new AlertBox(this, ex, controller).showAndWait();
            }
        });

        purchaseButton.setOnAction( e -> {
            try{
                boolean hasCustomerWonCode =  controller.getAccountController().finalizeOrder();
                new AlertBox(this, "You have successfully purchased your cart!","OK",controller).showAndWait();
                if(hasCustomerWonCode){
                    new AlertBox(this, "Congratulations, You have won a coded discount!", "OK",controller).showAndWait();
                }
                stage.setScene(new ViewLogsForCustomerGMenu(new MainMenuG(null, stage, controller), stage, controller).getScene());
            } catch (ExceptionalMassage ex){
                new AlertBox(this, ex, controller).showAndWait();
            }
        });

        anchorPane0.onKeyReleasedProperty().set( e -> {
            boolean isDisable = (firstNameField.getText().trim().equals("") || lastNameField.getText().trim().equals("") || phoneNumberField.getText().trim().equals("") ||
                    cityNameField.getText().trim().equals("") || addressField.getText().trim().equals("") || postalCodeField.getText().trim().equals(""));
            submitShippingInfo.setDisable(isDisable);
        });

        return new Scene(anchorPane0);
    }
}
