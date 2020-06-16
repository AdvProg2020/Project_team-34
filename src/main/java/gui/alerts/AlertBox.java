package gui.alerts;

import controller.Controller;
import exceptionalMassage.ExceptionalMassage;
import gui.GMenu;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class AlertBox extends GMenu {
    protected String alert;
    protected String buttonText;

    public AlertBox(GMenu parentMenu, Stage stage, String alert, String buttonText, Controller controller) {
        super("Alert!", parentMenu, stage, controller);
        this.alert = alert;
        this.buttonText = buttonText;
    }

    public AlertBox(GMenu parentMenu, Stage stage, ExceptionalMassage exceptionalMassage, Controller controller) {
        super("Alert!", parentMenu, stage, controller);
        this.alert = exceptionalMassage.getMessage();
        this.buttonText = "OK";
    }

    @Override
    protected Scene createScene() {
        HBox hBox = new HBox();
        hBox.setSpacing(20);
        hBox.setPadding(new Insets(10, 10, 10, 10));
        Label alertLabel = new Label(alert);
        Button okButton = new Button(buttonText);
        hBox.getChildren().addAll(alertLabel, okButton);
        hBox.setAlignment(Pos.CENTER);
        GridPane background = new GridPane();
        background.setAlignment(Pos.CENTER);
        background.getChildren().addAll(hBox);
        okButton.setOnAction(e -> stage.close());
        return new Scene(background);
    }

    public void showAndWait() {
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Game Finish Menu");
        Image logoImage = null;
        try {
            logoImage = new Image(new FileInputStream("./src/main/resources/header/Logo.png"));
        } catch (FileNotFoundException e) {
        }
        stage.getIcons().add(logoImage);
        stage.setScene(createScene());
        stage.setOnCloseRequest(Event::consume);
        stage.showAndWait();
    }

}
