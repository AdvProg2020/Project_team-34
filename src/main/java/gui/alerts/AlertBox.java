package gui.alerts;

import gui.GMenu;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class AlertBox extends GMenu {
    protected String alert;
    protected String buttonText;

    public AlertBox(GMenu parentMenu, Stage stage, String alert, String buttonText) {
        super("Alert!", parentMenu, stage);
        this.alert = alert;
        this.buttonText = buttonText;
    }

    @Override
    protected Scene createScene() {
        HBox hBox = new HBox();
        Label alertLabel = new Label(alert);
        Button okButton = new Button(buttonText);
        hBox.getChildren().addAll(alertLabel, okButton);
        hBox.setAlignment(Pos.CENTER);
        GridPane background = new GridPane();
        background.setAlignment(Pos.CENTER);
        background.getChildren().addAll(hBox);
        Scene scene = new Scene(background);
        return scene;
    }
}
