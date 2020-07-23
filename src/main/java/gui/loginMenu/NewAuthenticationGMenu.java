package gui.loginMenu;

import account.Supervisor;
import account.Supplier;
import controller.Controller;
import exceptionalMassage.ExceptionalMassage;
import gui.GMenu;
import gui.alerts.AlertBox;
import gui.cartMenu.CartGMenu;
import gui.mainMenu.MainMenuG;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class NewAuthenticationGMenu extends GMenu {
    private final String username;
    private final Stage popupCaller;

    public NewAuthenticationGMenu(GMenu parentMenu, Stage stage, Stage popupCaller, Controller controller, String username) {
        super("Authentication", parentMenu, stage, controller);
        this.username = username;
        this.popupCaller = popupCaller;
    }


    @Override
    protected Scene createScene() {
        VBox vBox = new VBox();
        TextField username = new TextField();
        PasswordField password = new PasswordField();
        Button authenticate = new Button("Authorize");
        GridPane background = new GridPane();

        username.setText(this.username);
        username.setDisable(true);
        username.setStyle("-fx-alignment: center; -fx-max-width: 300; -fx-min-width: 300; -fx-pref-width: 300; " +
                "-fx-border-radius: 15; -fx-background-radius: 15; -fx-border-color: #4678c8; -fx-border-width: 2px;" +
                "-fx-font-size: 14px");

        password.setPromptText("Dynamic Password");
        password.setStyle("-fx-alignment: center; -fx-max-width: 300; -fx-min-width: 300; -fx-pref-width: 300; " +
                "-fx-border-radius: 15; -fx-background-radius: 15; -fx-border-color: #4678c8; -fx-border-width: 2px;" +
                "-fx-font-size: 14px");

        authenticate.setStyle("-fx-alignment: center; -fx-max-width: 140; -fx-min-width: 140; -fx-pref-width: 140; " +
                "-fx-border-radius: 15; -fx-background-radius: 15; -fx-border-width: 2px;" +
                "-fx-font-size: 14px");

        vBox.setSpacing(15);
        vBox.setPadding(new Insets(15, 15, 15, 15));
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(username, password, authenticate);

        authenticate.setOnAction(e -> {
            try {
                controller.getAccountController().controlAuthenticate(password.getText());
                if((controller.getAccount() instanceof Supplier || controller.getAccount() instanceof Supervisor) && parentMenu instanceof CartGMenu) {
                    stage.close();
                    popupCaller.setScene(new MainMenuG(null, popupCaller, controller).getScene());
                } else {
                    stage.close();
                    popupCaller.setScene(parentMenu.getScene());
                }
            } catch (ExceptionalMassage exceptionalMassage) {
                new AlertBox(this, exceptionalMassage, controller).showAndWait();
            }
        });

        background.getChildren().add(vBox);
        return new Scene(background);
    }
}
