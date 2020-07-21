package gui.profile;

import account.Customer;
import account.Supporter;
import controller.Controller;
import exceptionalMassage.ExceptionalMassage;
import gui.GMenu;
import gui.alerts.AlertBox;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import state.State;

public class ChooseRequestingCustomersGMenu extends GMenu {
    public ChooseRequestingCustomersGMenu(GMenu parentMenu, Stage stage, Controller controller) {
        super("Choose requesting customers", parentMenu, stage, controller);
    }

    @Override
    protected Scene createScene() {
        AnchorPane anchorPane0 = new AnchorPane();
        anchorPane0.setPrefHeight(700.0);
        anchorPane0.setPrefWidth(850.0);
        HBox hBox1 = new HBox();
        hBox1.setPrefHeight(102.0);
        hBox1.setPrefWidth(850.0);
        hBox1.setStyle("-fx-background-color: #4477c8;");

        HBox hbox = createHeader();
        hBox1.getChildren().add(hbox);

        // Adding child to parent
        anchorPane0.getChildren().add(hBox1);
        HBox hBox2 = new HBox();
        hBox2.setPrefHeight(102.0);
        hBox2.setPrefWidth(850.0);
        hBox2.setStyle("-fx-background-color: #4477c8;");
        hBox2.setLayoutY(598.0);

        // Adding child to parent
        anchorPane0.getChildren().add(hBox2);
        ListView requestingCustomers = new ListView();
        requestingCustomers.setPrefHeight(398.0);
        requestingCustomers.setPrefWidth(350.0);
        requestingCustomers.setLayoutX(50.0);
        requestingCustomers.setLayoutY(154.0);

        // Adding child to parent
        anchorPane0.getChildren().add(requestingCustomers);
        Label label4 = new Label();
        label4.setLayoutX(50.0);
        label4.setLayoutY(120.0);
        label4.setText("Requesting Customers:");

        // Adding child to parent
        anchorPane0.getChildren().add(label4);
        Button detailsButton = new Button();
        detailsButton.setPrefHeight(33.0);
        detailsButton.setPrefWidth(233.0);
        detailsButton.setLayoutX(505.0);
        detailsButton.setStyle("-fx-background-color: #4678c8;"+"-fx-background-radius: 100PX;"+"-fx-text-fill: #f5f5f2;");
        detailsButton.setLayoutY(286.0);
        detailsButton.setText("Details");
        detailsButton.setMnemonicParsing(false);

        // Adding child to parent
        anchorPane0.getChildren().add(detailsButton);
        Button closeChat = new Button();
        closeChat.setPrefHeight(33.0);
        closeChat.setPrefWidth(233.0);
        closeChat.setLayoutX(505.0);
        closeChat.setStyle("-fx-background-color: #4678c8;"+"-fx-background-radius: 100PX;"+"-fx-text-fill: #f5f5f2;");
        closeChat.setLayoutY(420.0);
        closeChat.setText("Close Chat");
        closeChat.setMnemonicParsing(false);

            // Adding child to parent
        anchorPane0.getChildren().add(closeChat);
        Button refreshButton = new Button();
        refreshButton.setPrefHeight(33.0);
        refreshButton.setPrefWidth(233.0);
        refreshButton.setLayoutX(505.0);
        refreshButton.setStyle("-fx-background-color: #4678c8;"+"-fx-background-radius: 100PX;"+"-fx-text-fill: #f5f5f2;");
        refreshButton.setLayoutY(353.0);
        refreshButton.setText("Refresh");
        refreshButton.setMnemonicParsing(false);

            // Adding child to parent
        anchorPane0.getChildren().add(refreshButton);
        Button openChatButton = new Button();
        openChatButton.setPrefHeight(33.0);
        openChatButton.setPrefWidth(233.0);
        openChatButton.setLayoutX(505.0);
        openChatButton.setStyle("-fx-background-color: #4678c8;"+"-fx-background-radius: 100PX;"+"-fx-text-fill: #f5f5f2;");
        openChatButton.setLayoutY(488.0);
        openChatButton.setText("Open Chat");
        openChatButton.setMnemonicParsing(false);

        // Adding child to parent
        anchorPane0.getChildren().add(openChatButton);
        Label customerLabel = new Label();
        customerLabel.setLayoutX(505.0);
        customerLabel.setLayoutY(177.0);
        customerLabel.setText("Customer of the chatIdSitsHere");

        // Adding child to parent
        anchorPane0.getChildren().add(customerLabel);

        try {
            for (String chatRoomId : controller.getAccountController().getRequestingCustomersBySupporter()) {
                requestingCustomers.getItems().add(chatRoomId);
            }
        } catch (ExceptionalMassage ex){
            new AlertBox(this,ex, controller).showAndWait();
        }

        refreshButton.setOnAction(e -> {
            requestingCustomers.getItems().clear();
            try {
                for (String chatRoomId : controller.getAccountController().getRequestingCustomersBySupporter()) {
                    requestingCustomers.getItems().add(chatRoomId);
                }
            } catch (ExceptionalMassage ex){
                new AlertBox(this,ex, controller).showAndWait();
            }
        });

        openChatButton.setOnAction(e -> {
            ObservableList<String> chatRoomId = requestingCustomers.getSelectionModel().getSelectedItems();
            for (String s : chatRoomId) {
                Stage stage1 = new Stage();
                stage1.setTitle(s);
                stage1.show();
                stage1.setScene(new ChatRoomGMenu(this, stage, controller, s).getScene());
            }
        });

        closeChat.setOnAction(e -> {
            ObservableList<String> chatRoomId = requestingCustomers.getSelectionModel().getSelectedItems();
            for (String s : chatRoomId) {
                try {
                    controller.getAccountController().controlCloseChatRoomById(s);
                } catch (ExceptionalMassage ex){
                    new AlertBox(this, ex, controller).showAndWait();
                }
            }
        });

        detailsButton.setOnAction(e -> {
            ObservableList<String> chatRoomId = requestingCustomers.getSelectionModel().getSelectedItems();
            Customer customer = null;
            for (String s : chatRoomId) {
                try {
                    customer = controller.getAccountController().getCustomerOfAChatRoom(s);
                } catch (ExceptionalMassage ex){
                    new AlertBox(this, ex, controller).showAndWait();
                }
                customerLabel.setText("Customer Username: " + customer.getUserName());
            }
        });

        return new Scene(anchorPane0);

    }
}
