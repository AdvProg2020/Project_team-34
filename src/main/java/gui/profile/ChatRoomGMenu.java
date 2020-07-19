package gui.profile;

import controller.Controller;
import exceptionalMassage.ExceptionalMassage;
import gui.GMenu;
import gui.alerts.AlertBox;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class ChatRoomGMenu extends GMenu {
    private String chatRoomId;
    //private ArrayList<String> joinedUsers;

    public ChatRoomGMenu(GMenu parentMenu, Stage stage, Controller controller, String chatRoomId) {
        super("Chat Room", parentMenu, stage, controller);
        this.chatRoomId = chatRoomId;
        //this.joinedUsers = joinedUsers;
    }

    @Override
    protected Scene createScene() {
        AnchorPane anchorPane0 = new AnchorPane();
        anchorPane0.setPrefHeight(647.0);
        anchorPane0.setPrefWidth(600.0);
        anchorPane0.setStyle("-fx-background-color: #f5f5f2;");
        ScrollPane scrollPane1 = new ScrollPane();
        scrollPane1.setPrefHeight(513.0);
        scrollPane1.setPrefWidth(600.0);
        scrollPane1.setStyle("-fx-background-color: #9cbfe3;");
        scrollPane1.setLayoutY(51.0);

        // Adding child to parent
        anchorPane0.getChildren().add(scrollPane1);
        Button sendButton = new Button();
        sendButton.setPrefHeight(51.0);
        sendButton.setPrefWidth(192.0);
        sendButton.setLayoutX(379.0);
        sendButton.setStyle("-fx-background-color: #4678c8;"+"-fx-background-radius: 100PX;"+"-fx-text-fill: #f5f5f2;");
        sendButton.setLayoutY(582.0);
        sendButton.setText("Send Message");
        sendButton.setMnemonicParsing(false);

        // Adding child to parent
        anchorPane0.getChildren().add(sendButton);
        HBox hBox3 = new HBox();
        hBox3.setPrefHeight(51.0);
        hBox3.setPrefWidth(345.0);
        hBox3.setLayoutX(14.0);
        hBox3.setStyle("-fx-background-color: white;"+"-fx-border-color: #a2a2a2;"+"-fx-border-width: 0px 0px 2px 0px;");
        hBox3.setLayoutY(582.0);
        TextField messageBox = new TextField();
        messageBox.setPrefHeight(49.0);
        messageBox.setPrefWidth(346.0);
        messageBox.setStyle("-fx-background-color: transparent;");
        messageBox.setOpacity(0.83);
        messageBox.setPromptText("Write your message");

        // Adding child to parent
        hBox3.getChildren().add(messageBox);

        // Adding child to parent
        anchorPane0.getChildren().add(hBox3);
        Label label5 = new Label();
        label5.setLayoutX(14.0);
        label5.setLayoutY(21.0);
        label5.setText("Chat room id:");

        // Adding child to parent
        anchorPane0.getChildren().add(label5);
        Label chatRoomIdString = new Label();
        chatRoomIdString.setLayoutX(117.0);
        chatRoomIdString.setLayoutY(21.0);
        chatRoomIdString.setText(chatRoomId);

        // Adding child to parent
        anchorPane0.getChildren().add(chatRoomIdString);

        //Adding controller
        sendButton.setOnAction(e->{
            String content = messageBox.getText();
            String username = controller.getAccount().getUserName();
            String chatRoom = chatRoomId;
            try{
                controller.getAccountController().addMessageToChatRoom(username, content, chatRoom);
            } catch (ExceptionalMassage ex){
                new AlertBox(this, ex, controller).showAndWait();
            }
            messageBox.clear();
        });

        return new Scene(anchorPane0);
    }
}
