package gui.loginMenu;

import controller.Controller;
import exceptionalMassage.ExceptionalMassage;
import gui.GMenu;
import gui.alerts.AlertBox;
import gui.mainMenu.MainMenuG;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import static javafx.scene.shape.StrokeType.OUTSIDE;

public class RegisterGMenu extends GMenu {
    private Stage popUpCallerStage;

    public RegisterGMenu(GMenu parentMenu, Stage stage, Stage popUpCallerStage, Controller controller) {
        super("Register Menu", parentMenu, stage, controller);
        this.popUpCallerStage = popUpCallerStage;
        stage.setTitle("Register Menu");
    }

    @Override
    protected Scene createScene() {
        AnchorPane anchorPane0 = new AnchorPane();
        anchorPane0.setPrefHeight(650.0);
        anchorPane0.setPrefWidth(880.0);
        anchorPane0.setStyle("-fx-background-color: #f5f5f2;");
        HBox hBox1 = new HBox();
        hBox1.setPrefHeight(102.0);
        hBox1.setPrefWidth(883.0);
        hBox1.setStyle("-fx-background-color: #4477c8;");

        // Adding child to parent
        anchorPane0.getChildren().add(hBox1);
        HBox hBox4 = new HBox();
        hBox4.setPrefHeight(102.0);
        hBox4.setPrefWidth(883.0);
        hBox4.setStyle("-fx-background-color: #4477c8;");
        hBox4.setLayoutY(548.0);

        // Adding child to parent
        anchorPane0.getChildren().add(hBox4);
        Label label5 = new Label();
        label5.setPrefHeight(24.0);
        label5.setPrefWidth(121.0);
        label5.setLayoutX(58.0);
        label5.setLayoutY(127.0);
        label5.setText("Sign Up");

        // Adding child to parent
        anchorPane0.getChildren().add(label5);
        GridPane gridPane6 = new GridPane();
        gridPane6.setPrefHeight(277.0);
        gridPane6.setPrefWidth(356.0);
        gridPane6.setLayoutX(58.0);
        gridPane6.setLayoutY(187.0);
        HBox hBox7 = new HBox();
        gridPane6.add(hBox7,0,1);
        hBox7.setPrefHeight(60.0);
        hBox7.setPrefWidth(316.0);
        hBox7.setStyle("-fx-background-color: white;"+"-fx-border-color: #a2a2a2;"+"-fx-border-width: 0px 0px 2px 0px;");
        TextField firstNameField = new TextField();
        firstNameField.setPrefHeight(51.0);
        firstNameField.setPrefWidth(295.0);
        firstNameField.setStyle("-fx-background-color: transparent;");
        firstNameField.setOpacity(0.83);
        firstNameField.setPromptText("First name");

        // Adding child to parent
        hBox7.getChildren().add(firstNameField);

        // Adding child to parent
        HBox hBox9 = new HBox();
        hBox9.setPrefHeight(51.0);
        hBox9.setPrefWidth(345.0);
        gridPane6.add(hBox9,0,2);
        hBox9.setStyle("-fx-background-color: white;"+"-fx-border-color: #a2a2a2;"+"-fx-border-width: 0px 0px 2px 0px;");
        TextField lastNameField = new TextField();
        lastNameField.setPrefHeight(51.0);
        lastNameField.setPrefWidth(295.0);
        lastNameField.setStyle("-fx-background-color: transparent;");
        lastNameField.setOpacity(0.83);
        lastNameField.setPromptText("Last name");

        // Adding child to parent
        hBox9.getChildren().add(lastNameField);

        // Adding child to parent
        HBox hBox11 = new HBox();
        hBox11.setPrefHeight(51.0);
        hBox11.setPrefWidth(345.0);
        gridPane6.add(hBox11,0,3);
        hBox11.setStyle("-fx-background-color: white;"+"-fx-border-color: #a2a2a2;"+"-fx-border-width: 0px 0px 2px 0px;");
        TextField emailField = new TextField();
        emailField.setPrefHeight(51.0);
        emailField.setPrefWidth(295.0);
        emailField.setStyle("-fx-background-color: transparent;");
        emailField.setOpacity(0.83);
        emailField.setPromptText("Email");

        // Adding child to parent
        hBox11.getChildren().add(emailField);

        // Adding child to parent
        HBox hBox13 = new HBox();
        hBox13.setPrefHeight(51.0);
        hBox13.setPrefWidth(345.0);
        gridPane6.add(hBox13,0,4);
        hBox13.setStyle("-fx-background-color: white;"+"-fx-border-color: #a2a2a2;"+"-fx-border-width: 0px 0px 2px 0px;");
        TextField phoneNumber = new TextField();
        phoneNumber.setPrefHeight(51.0);
        phoneNumber.setPrefWidth(295.0);
        phoneNumber.setStyle("-fx-background-color: transparent;");
        phoneNumber.setOpacity(0.83);
        phoneNumber.setPromptText("Phone number");

        // Adding child to parent
        hBox13.getChildren().add(phoneNumber);

        // Adding child to parent

        // Adding child to parent
        anchorPane0.getChildren().add(gridPane6);
        GridPane gridPane15 = new GridPane();
        gridPane15.setPrefHeight(277.0);
        gridPane15.setPrefWidth(356.0);
        gridPane15.setLayoutX(460.0);
        gridPane15.setLayoutY(187.0);
        HBox hBox16 = new HBox();
        hBox16.setPrefHeight(60.0);
        hBox16.setPrefWidth(316.0);
        hBox16.setStyle("-fx-background-color: white;"+"-fx-border-color: #a2a2a2;"+"-fx-border-width: 0px 0px 2px 0px;");
        TextField usernameField = new TextField();
        usernameField.setPrefHeight(51.0);
        usernameField.setPrefWidth(295.0);
        usernameField.setStyle("-fx-background-color: transparent;");
        usernameField.setOpacity(0.83);
        usernameField.setPromptText("Username");

        // Adding child to parent
        hBox16.getChildren().add(usernameField);

        // Adding child to parent
        gridPane15.add(hBox16,0,1);
        HBox companyNameHbox = new HBox();
        companyNameHbox.setPrefHeight(51.0);
        companyNameHbox.setPrefWidth(345.0);
        gridPane15.add(companyNameHbox,0,4);
        companyNameHbox.setStyle("-fx-background-color: white;"+"-fx-border-color: #a2a2a2;"+"-fx-border-width: 0px 0px 2px 0px;");
        TextField companyNameField = new TextField();
        companyNameField.setPrefHeight(51.0);
        companyNameField.setPrefWidth(295.0);
        companyNameField.setStyle("-fx-background-color: transparent;");
        companyNameField.setOpacity(0.83);
        companyNameField.setPromptText("Company name");

        // Adding child to parent
        companyNameHbox.getChildren().add(companyNameField);

        // Adding child to parent
        HBox creditHbox = new HBox();
        creditHbox.setPrefHeight(51.0);
        creditHbox.setPrefWidth(345.0);
        gridPane15.add(creditHbox,0,3);
        creditHbox.setStyle("-fx-background-color: white;"+"-fx-border-color: #a2a2a2;"+"-fx-border-width: 0px 0px 2px 0px;");
        TextField creditField = new TextField();
        creditField.setPrefHeight(51.0);
        creditField.setPrefWidth(295.0);
        creditField.setStyle("-fx-background-color: transparent;");
        creditField.setOpacity(0.83);
        creditField.setPromptText("Credit");

        // Adding child to parent
        creditHbox.getChildren().add(creditField);


        // Adding child to parent
        HBox hBox22 = new HBox();
        hBox22.setPrefHeight(51.0);
        hBox22.setPrefWidth(356.0);
        gridPane15.add(hBox22,0,2);
        hBox22.setStyle("-fx-background-color: white;"+"-fx-border-color: #a2a2a2;"+"-fx-border-width: 0px 0px 2px 0px;");
        PasswordField passwordField = new PasswordField();
        passwordField.setPrefHeight(51.0);
        passwordField.setPrefWidth(295.0);
        passwordField.setStyle("-fx-background-color: transparent;");
        passwordField.setPromptText("Password");

        // Adding child to parent
        hBox22.getChildren().add(passwordField);

        // Adding child to parent

        // Adding child to parent
        anchorPane0.getChildren().add(gridPane15);
        RadioButton customerButton = new RadioButton();
        customerButton.setLayoutX(319.0);
        customerButton.setLayoutY(129.0);
        customerButton.setText("Customer");
        customerButton.setMnemonicParsing(false);
        customerButton.setSelected(true);

        // Adding child to parent
        anchorPane0.getChildren().add(customerButton);
        RadioButton supplierButton = new RadioButton();
        supplierButton.setLayoutX(450.0);
        supplierButton.setLayoutY(129.0);
        supplierButton.setText("Supplier");
        supplierButton.setMnemonicParsing(false);

        // Adding child to parent
        anchorPane0.getChildren().add(supplierButton);
        Label label26 = new Label();
        label26.setLayoutX(179.0);
        label26.setLayoutY(129.0);
        label26.setText("Account type:");

        // Adding child to parent
        anchorPane0.getChildren().add(label26);
        Button signUpButton = new Button();
        signUpButton.setPrefHeight(33.0);
        signUpButton.setPrefWidth(233.0);
        signUpButton.setLayoutX(522.0);
        signUpButton.setStyle("-fx-background-color: #4678c8;"+" -fx-background-radius: 100PX;"+" -fx-text-fill: #f5f5f2;");
        signUpButton.setLayoutY(494.0);
        signUpButton.setText("Sign Up");
        signUpButton.setMnemonicParsing(false);

        // Adding child to parent
        anchorPane0.getChildren().add(signUpButton);

        // Adding child to parent
        gridPane6.setVgap(10);
        gridPane6.setHgap(15);

        gridPane15.setVgap(10);
        gridPane15.setHgap(15);
        // Adding Toggle Group

        ToggleGroup accountType = new ToggleGroup();

        customerButton.setToggleGroup(accountType);
        supplierButton.setToggleGroup(accountType);
        if(((RadioButton)accountType.getSelectedToggle()).getText().equals("Customer")){
            creditHbox.setOpacity(10);
            companyNameHbox.setOpacity(0.5);
            companyNameField.setDisable(true);
            creditField.setDisable(false);
        } else {
            creditHbox.setOpacity(0.5);
            companyNameHbox.setOpacity(10);
            companyNameField.setDisable(false);
            creditField.setDisable(true);
        }

        customerButton.setOnMouseClicked(e ->{
            creditHbox.setOpacity(10);
            companyNameHbox.setOpacity(0.5);
            companyNameField.setDisable(true);
            creditField.setDisable(false);
            companyNameField.clear();
            creditField.clear();
        });

        supplierButton.setOnMouseClicked(e ->{
            creditHbox.setOpacity(0.5);
            companyNameHbox.setOpacity(10);
            companyNameField.setDisable(false);
            creditField.setDisable(true);
            companyNameField.clear();
            creditField.clear();
        });
        // Adding Controller

        signUpButton.setDisable(true);


        signUpButton.setOnAction(e -> {
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String email = emailField.getText();
            String phoneNum = phoneNumber.getText();
            String userName = usernameField.getText();
            String password = passwordField.getText();
            String companyName = companyNameField.getText();
            /*
            if(((RadioButton)accountType.getSelectedToggle()).getText().equals("Customer")){

                try{
                    int credit = Integer.parseInt(creditField.getText());
                    try{
                        controller.getAccountController().controlCreateAccount(userName,"customer",firstName,lastName,email,phoneNum,password,credit,companyName);
                        stage.close();
                        popUpCallerStage.setScene(parentMenu.getScene());
                        //popUpCallerStage.setScene(new MainMenuG(null, stage, controller).getScene());
                    }catch (ExceptionalMassage ex){
                        new AlertBox(this, ex, controller).showAndWait();
                    }
                } catch (NumberFormatException ex){
                    new AlertBox(this, "Enter number for credit, please","OK",controller).showAndWait();
                }

            } else {
                try{
                    controller.getAccountController().controlCreateAccount(userName,"supplier",firstName,lastName,email,phoneNum,password,0,companyName);
                    stage.close();
                    popUpCallerStage.setScene(parentMenu.getScene());
                    //popUpCallerStage.setScene(new MainMenuG(null, stage, controller).getScene());
                }catch (ExceptionalMassage ex){
                    new AlertBox(this, ex, controller).showAndWait();
                }
            }

                 */
            passwordField.clear();
        });




        anchorPane0.onKeyReleasedProperty().set( e -> {
            boolean isDisable = (firstNameField.getText().trim().equals("") || lastNameField.getText().trim().equals("") || phoneNumber.getText().trim().equals("") ||
                    emailField.getText().trim().equals("") || usernameField.getText().trim().equals("") || passwordField.getText().trim().equals("") ||
                     (!creditField.isDisable() && creditField.getText().trim().equals("")) || (!companyNameField.isDisable() && companyNameField.getText().trim().equals("")));
            signUpButton.setDisable(isDisable);
        });



        GridPane background = new GridPane();
        background.setAlignment(Pos.CENTER);
        background.getChildren().add(anchorPane0);

        return new Scene(background);
    }
}
