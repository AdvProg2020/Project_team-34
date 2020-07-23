package gui.loginMenu;

import controller.Controller;
import exceptionalMassage.ExceptionalMassage;
import gui.GMenu;
import gui.alerts.AlertBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class NewRequestDynamicPasswordGMenu extends GMenu {
    private Stage popupCaller;

    public NewRequestDynamicPasswordGMenu(GMenu parentMenu, Stage stage, Stage popupCaller, Controller controller) {
        super("Login", parentMenu, stage, controller);
        this.popupCaller = popupCaller;
    }

    @Override
    protected Scene createScene() {
        GridPane backgroundLayout = new GridPane();
        VBox vBox = new VBox();
        GridPane requestLayout = new GridPane();
        TextField usernameField = new TextField();
        Button requestButton = new Button("Request Password");
        Button signUpButton = new Button("Sign Up");

        usernameField.setStyle("-fx-alignment: center; -fx-max-width: 300; -fx-min-width: 300; -fx-pref-width: 300; " +
                "-fx-border-radius: 15; -fx-background-radius: 15; -fx-border-color: #4678c8; -fx-border-width: 2px;" +
                "-fx-font-size: 14px");
        usernameField.setPromptText("Username");

        requestButton.setStyle("-fx-alignment: center; -fx-max-width: 140; -fx-min-width: 140; -fx-pref-width: 140; " +
                "-fx-border-radius: 15; -fx-background-radius: 15; -fx-border-width: 2px;" +
                "-fx-font-size: 14px");
        requestButton.setDefaultButton(true);

        signUpButton.setStyle("-fx-alignment: center; -fx-max-width: 140; -fx-min-width: 140; -fx-pref-width: 140; " +
                "-fx-border-radius: 15; -fx-background-radius: 15; -fx-border-width: 2px;" +
                "-fx-font-size: 14px");

        requestLayout.setAlignment(Pos.CENTER);

        requestLayout.add(requestButton, 1, 0);
        requestLayout.add(signUpButton, 0, 0);
        requestLayout.setHgap(20);

        vBox.getChildren().addAll(usernameField, requestLayout);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(15);
        vBox.setPadding(new Insets(10, 10, 10, 10));

        requestButton.setOnAction(e -> {
            try {
                String username = usernameField.getText();
                controller.getAccountController().controlRequestDynamicPassword(username);
                stage.setScene(new NewAuthenticationGMenu(parentMenu, stage, controller, username).getScene());
            } catch (ExceptionalMassage exceptionalMassage) {
                new AlertBox(this, exceptionalMassage, controller).showAndWait();
            }
        });

        signUpButton.setOnAction(e -> stage.setScene(new NewRegisterGMenu(parentMenu, stage, popupCaller, controller).getScene()));

        backgroundLayout.getChildren().addAll(vBox);

        backgroundLayout.setAlignment(Pos.CENTER);

        return new Scene(backgroundLayout);
    }
}
