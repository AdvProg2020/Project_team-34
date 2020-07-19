package gui.profile;

import account.Message;
import controller.Controller;
import exceptionalMassage.ExceptionalMassage;
import gui.GMenu;
import gui.alerts.AlertBox;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class ChatRoomGMenu extends GMenu {
    private String chatRoomId;
    private ScrollPane messagesScrollPane;
    //private ArrayList<String> joinedUsers;

    public ChatRoomGMenu(GMenu parentMenu, Stage stage, Controller controller, String chatRoomId) {
        super("Chat Room", parentMenu, stage, controller);
        this.chatRoomId = chatRoomId;
        this.messagesScrollPane = new ScrollPane();
        //this.joinedUsers = joinedUsers;
    }

    @Override
    protected Scene createScene() {
        AnchorPane anchorPane0 = new AnchorPane();
        anchorPane0.setPrefHeight(647.0);
        anchorPane0.setPrefWidth(600.0);
        anchorPane0.setStyle("-fx-background-color: #f5f5f2;");
        messagesScrollPane.setPrefHeight(513.0);
        messagesScrollPane.setPrefWidth(600.0);
        messagesScrollPane.setStyle("-fx-background-color: #9cbfe3;");
        messagesScrollPane.setLayoutY(51.0);

        // Adding child to parent
        anchorPane0.getChildren().add(messagesScrollPane);
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
            updateScrollPane();
            messageBox.clear();
        });

        messagesScrollPane.setVvalue(1);

        Task task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                while (isThisStageOpen()){
                    System.out.println(1);
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            updateScrollPane();
                        }
                    });
                    Thread.sleep(3000);
                }
                System.out.println("Chat room thread closed!");
                return null;
            }
        };
        new Thread(task).start();
        return new Scene(anchorPane0);
    }

    private void updateScrollPane(){
        VBox allMessages = new VBox();
        allMessages.setMinWidth(600);
        ArrayList<Message> messages = null;
        try {
            messages = controller.getAccountController().getAllMessagesOfChatRoomById(chatRoomId);
        } catch (ExceptionalMassage exceptionalMassage) {
            new AlertBox(this, exceptionalMassage, controller).showAndWait();
            messagesScrollPane.getScene().getWindow().hide();
            return;
        }
        if(messages.size() != 0) {
            for (Message message : messages) {
                allMessages.getChildren().add(createLabelFromMessage(message));
            }
        }

        allMessages.setSpacing(10);
        allMessages.setPadding(new Insets(10, 10 , 10 , 10));
        allMessages.setAlignment(Pos.BASELINE_LEFT);

        messagesScrollPane.setContent(allMessages);

    }

    private boolean isThisStageOpen(){
        Stream<Window> open = Stage.getWindows().stream().filter(Window::isShowing);
        Object[] opens = open.toArray();
        for (Object o : opens) {
            if(((Stage)o).getTitle().equals(chatRoomId)){
                return true;
            }
        }
        return false;
    }


}
