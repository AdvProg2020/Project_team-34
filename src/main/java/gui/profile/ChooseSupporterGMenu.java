package gui.profile;

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

public class ChooseSupporterGMenu extends GMenu {

    public ChooseSupporterGMenu(GMenu parentMenu, Stage stage, Controller controller) {
        super("Choose supporter", parentMenu, stage, controller);
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
        ListView onlineSupporters = new ListView();
        onlineSupporters.setPrefHeight(398.0);
        onlineSupporters.setPrefWidth(350.0);
        onlineSupporters.setLayoutX(50.0);
        onlineSupporters.setLayoutY(154.0);

        // Adding child to parent
        anchorPane0.getChildren().add(onlineSupporters);
        Label label4 = new Label();
        label4.setLayoutX(50.0);
        label4.setLayoutY(120.0);
        label4.setText("Online Supporters:");

        // Adding child to parent
        anchorPane0.getChildren().add(label4);
        Button refreshButton = new Button();
        refreshButton.setPrefHeight(33.0);
        refreshButton.setPrefWidth(233.0);
        refreshButton.setLayoutX(505.0);
        refreshButton.setStyle("-fx-background-color: #4678c8;"+"-fx-background-radius: 100PX;"+"-fx-text-fill: #f5f5f2;");
        refreshButton.setLayoutY(317.0);
        refreshButton.setText("Refresh");
        refreshButton.setMnemonicParsing(false);

        // Adding child to parent
        anchorPane0.getChildren().add(refreshButton);
        Button chatButton = new Button();
        chatButton.setPrefHeight(33.0);
        chatButton.setPrefWidth(233.0);
        chatButton.setLayoutX(505.0);
        chatButton.setStyle("-fx-background-color: #4678c8;"+"-fx-background-radius: 100PX;"+"-fx-text-fill: #f5f5f2;");
        chatButton.setLayoutY(375.0);
        chatButton.setText("Chat");
        chatButton.setMnemonicParsing(false);

        // Adding child to parent
        anchorPane0.getChildren().add(chatButton);

        // Adding controller!
        try {
            for (Supporter onlineSupporter : controller.getAccountController().getOnlineSupporters()) {
                onlineSupporters.getItems().add(onlineSupporter.getUserName());
            }
        } catch (ExceptionalMassage ex){
            new AlertBox(this,ex, controller).showAndWait();
        }

        refreshButton.setOnAction(e -> {
            onlineSupporters.getItems().clear();
            try {
                for (Supporter onlineSupporter : controller.getAccountController().getOnlineSupporters()) {
                    onlineSupporters.getItems().add(onlineSupporter.getUserName());
                }
            } catch (ExceptionalMassage ex){
                new AlertBox(this,ex, controller).showAndWait();
            }
        });

        chatButton.setOnAction(e->{
            ObservableList<String> username = onlineSupporters.getSelectionModel().getSelectedItems();
            for (String s : username) {
                String chatRoomId = "";
                try {
                    chatRoomId = controller.getAccountController().createChatRoomBetweenSupporterAndCustomer((Supporter) controller.getAccountController().getAccountByUsernameWithinAvailable(s));
                } catch (ExceptionalMassage exceptionalMassage){
                    new AlertBox(this, exceptionalMassage, controller).showAndWait();
                }
                stage.setScene(new ChatRoomGMenu(this, stage, controller, chatRoomId).createScene());
            }

        });

        return new Scene(anchorPane0);

    }
}
