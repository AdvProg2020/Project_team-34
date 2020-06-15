package gui.profile;

import exceptionalMassage.ExceptionalMassage;
import gui.GMenu;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class ManageRequestsG extends GMenu {

    public ManageRequestsG(String menuName, GMenu parentMenu, Stage stage) {
        super(menuName, parentMenu, stage);
    }

    @Override
    protected Scene createScene() {
        AnchorPane anchorPane0 = new AnchorPane();

        anchorPane0.setPrefHeight(700.0);
        anchorPane0.setStyle("-fx-background-color: #f5f5f2");
        anchorPane0.setPrefWidth(850.0);


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
        ListView requests = new ListView();
        requests.setPrefHeight(398.0);
        requests.setPrefWidth(350.0);
        requests.setLayoutX(50.0);
        requests.setLayoutY(154.0);

        // Adding child to parent
        anchorPane0.getChildren().add(requests);
        Label label4 = new Label();
        label4.setLayoutX(50.0);
        label4.setLayoutY(120.0);
        label4.setText("Requests:");

        // Adding child to parent
        anchorPane0.getChildren().add(label4);
        Button detailsButton = new Button();
        detailsButton.setPrefHeight(33.0);
        detailsButton.setPrefWidth(233.0);
        detailsButton.setLayoutX(505.0);
        detailsButton.setStyle("-fx-background-color: #4678c8;"+"-fx-background-radius: 100PX;"+"-fx-text-fill: #f5f5f2;");
        detailsButton.setLayoutY(334.0);
        detailsButton.setText("View details");
        detailsButton.setMnemonicParsing(false);

        // Adding child to parent
        anchorPane0.getChildren().add(detailsButton);
        Button acceptButton = new Button();
        acceptButton.setPrefHeight(33.0);
        acceptButton.setPrefWidth(233.0);
        acceptButton.setLayoutX(505.0);
        acceptButton.setStyle("-fx-background-color: #4678c8;"+"-fx-background-radius: 100PX;"+"-fx-text-fill: #f5f5f2;");
        acceptButton.setLayoutY(519.0);
        acceptButton.setText("Accept");
        acceptButton.setMnemonicParsing(false);

        // Adding child to parent
        anchorPane0.getChildren().add(acceptButton);
        Button rejectButton = new Button();
        rejectButton.setPrefHeight(33.0);
        rejectButton.setPrefWidth(233.0);
        rejectButton.setLayoutX(505.0);
        rejectButton.setStyle("-fx-background-color: #4678c8;"+"-fx-background-radius: 100PX;"+"-fx-text-fill: #f5f5f2;");
        rejectButton.setLayoutY(457.0);
        rejectButton.setText("Reject");
        rejectButton.setMnemonicParsing(false);

        // Adding child to parent
        anchorPane0.getChildren().add(rejectButton);

        // Adding controller
        for (String s : controller.getProductController().controlGetArrayOfRequestId()) {
            requests.getItems().add(s);
        }

        acceptButton.setOnAction( e -> {
            ObservableList<String> ids = requests.getItems();
            for (String id : ids) {
                try {
                    controller.getProductController().controlAcceptOrDeclineRequest(id, true);
                } catch (ExceptionalMassage ex){
                    //
                }
            }

        });

        rejectButton.setOnAction( e -> {
            ObservableList<String> ids = requests.getItems();
            for (String id : ids) {
                try {
                    controller.getProductController().controlAcceptOrDeclineRequest(id, false);
                } catch (ExceptionalMassage ex){
                    //
                }
            }

        });

        detailsButton.setOnAction( e -> {
            //show details of requests!
        });



        return new Scene(anchorPane0);

    }
}
