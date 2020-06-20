package gui.alerts;

import controller.Controller;
import gui.GMenu;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * @author Aryan Ahadinia
 * @since 0.0.2
 */

public class ChoiceBox extends GMenu {
    private String question;
    private String acceptButton;
    private String denyButton;
    public Boolean answer;

    public ChoiceBox(GMenu parentMenu, String question, String acceptButton, String denyButton, Controller controller) {
        super("Choose ...", parentMenu, new Stage(), controller);
        this.question = question;
        this.acceptButton = acceptButton;
        this.denyButton = denyButton;
        this.answer = null;
    }

    @Override
    protected Scene createScene() {
        HBox buttonLayout = new HBox();
        VBox mainLayout = new VBox();
        mainLayout.setSpacing(10);
        mainLayout.setPadding(new Insets(10, 10, 10, 10));
        buttonLayout.setSpacing(10);
        Label questionLabel = new Label(question);
        Button accept = new Button(acceptButton);
        Button deny = new Button(denyButton);
        buttonLayout.getChildren().addAll(accept, deny);
        mainLayout.getChildren().addAll(questionLabel, buttonLayout);
        GridPane background = new GridPane();
        background.getChildren().addAll(mainLayout);
        accept.setOnAction(e -> {
            answer = true;
            stage.close();
        });
        deny.setOnAction(e -> {
            answer = false;
            stage.close();
        });
        background.setAlignment(Pos.CENTER);
        return new Scene(background);
    }

    public Boolean getAnswer() {
        return answer;
    }
}
