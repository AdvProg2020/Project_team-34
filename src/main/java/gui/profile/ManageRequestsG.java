package gui.profile;

import controller.Controller;
import exceptionalMassage.ExceptionalMassage;
import gui.GMenu;
import gui.alerts.AlertBox;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import static javafx.scene.shape.StrokeType.OUTSIDE;

public class ManageRequestsG extends GMenu {

    public ManageRequestsG(GMenu parentMenu, Stage stage, Controller controller) {
        super("Manage Requests", parentMenu, stage, controller);
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

        requests.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        // Adding controller
        try {
            for (String s : controller.getProductController().controlGetArrayOfRequestId()) {
                requests.getItems().add(s);
            }
        } catch (ExceptionalMassage exceptionalMassage) {
            new AlertBox(this,exceptionalMassage,controller).showAndWait();
        }

        acceptButton.setOnAction( e -> {
            ObservableList<String> ids = requests.getSelectionModel().getSelectedItems();
            for (String id : ids) {
                try {
                    controller.getProductController().controlAcceptOrDeclineRequest(id, true);
                    stage.setScene(new ManageRequestsG(parentMenu, stage, controller).getScene());
                } catch (ExceptionalMassage ex){
                    new AlertBox(this, ex, controller).showAndWait();
                }
            }
        });

        rejectButton.setOnAction( e -> {
            ObservableList<String> ids = requests.getSelectionModel().getSelectedItems();
            for (String id : ids) {
                try {
                    controller.getProductController().controlAcceptOrDeclineRequest(id, false);
                    stage.setScene(new ManageRequestsG(parentMenu, stage, controller).getScene());
                } catch (ExceptionalMassage ex){
                    new AlertBox(this, ex, controller).showAndWait();
                }
            }

        });

        detailsButton.setOnAction( e -> {
            ObservableList<String> ids = requests.getSelectionModel().getSelectedItems();
            for (String id : ids) {
                Stage newStage = new Stage();
                newStage.setScene(createDetails(id));
                newStage.showAndWait();
            }

        });



        return new Scene(anchorPane0);

    }

    private Scene createDetails(String requestsId){
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
            text3.setText(controller.getProductController().controlShowDetailForRequest(requestsId));
        } catch (ExceptionalMassage ex){
            new AlertBox(this, ex, controller).showAndWait();
        }

        return new Scene(anchorPane0);
    }
}
