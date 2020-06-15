package gui.loginMenu;

import exceptionalMassage.ExceptionalMassage;
import gui.GMenu;
import gui.mainMenu.MainMenuG;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import menu.menuAbstract.Menu;

import java.io.IOException;

import static javafx.scene.control.ContentDisplay.CENTER;
import static javafx.scene.shape.StrokeType.OUTSIDE;

public class LoginGMenu extends GMenu {


    public LoginGMenu(String menuName, GMenu parentMenu, Stage stage) {
        super(menuName, parentMenu, stage);
    }

    @Override
    public Scene createScene() {


        AnchorPane anchorPane0 = new AnchorPane();
        anchorPane0.setPrefHeight(600.0);
        anchorPane0.setPrefWidth(500.0);
        anchorPane0.setStyle("-fx-background-color: #f5f5f2");
        HBox hBox1 = new HBox();
        hBox1.setPrefHeight(102.0);
        hBox1.setPrefWidth(500.0);
        hBox1.setStyle("-fx-background-color: #4677c8");
        hBox1.setLayoutY(-1.0);

        HBox header = createHeader();
        hBox1.getChildren().add(header);

        // Adding child to parent
        anchorPane0.getChildren().add(hBox1);
        HBox hBox4 = new HBox();
        hBox4.setPrefHeight(102.0);
        hBox4.setPrefWidth(500.0);
        hBox4.setStyle("-fx-background-color: #4677c8");
        hBox4.setLayoutY(498.0);

        // Adding child to parent
        anchorPane0.getChildren().add(hBox4);
        Label label5 = new Label();
        label5.setPrefHeight(51.0);
        label5.setPrefWidth(97.0);
        label5.setLayoutX(202.0);
        //label5.setStyle("-fx-font-style: Agene");
        label5.setContentDisplay(CENTER);
        label5.setLayoutY(114.0);
        label5.setText("Sign in");
        label5.setAlignment(Pos.CENTER);

        // Adding child to parent
        anchorPane0.getChildren().add(label5);
        HBox hBox6 = new HBox();
        hBox6.setPrefHeight(51.0);
        hBox6.setPrefWidth(345.0);
        hBox6.setLayoutX(78.0);
        hBox6.setStyle("-fx-background-color: white;" + "-fx-border-color: #a2a2a2;" +"-fx-border-width: 0px 0px 2px 0px;" );
        hBox6.setLayoutY(174.0);
        TextField usernameField = new TextField();
        usernameField.setPrefHeight(51.0);
        usernameField.setPrefWidth(295.0);
        usernameField.setStyle("-fx-background-color: transparent");
        usernameField.setOpacity(0.83);
        usernameField.setPromptText("Enter your username here!");

        // Adding child to parent
        hBox6.getChildren().add(usernameField);

        // Adding child to parent
        anchorPane0.getChildren().add(hBox6);
        HBox hBox8 = new HBox();
        hBox8.setPrefHeight(51.0);
        hBox8.setPrefWidth(345.0);
        hBox8.setLayoutX(78.0);
        hBox8.setStyle("-fx-background-color: white;" + "-fx-border-color: #a2a2a2;" +"-fx-border-width: 0px 0px 2px 0px;" );
        hBox8.setLayoutY(249.0);
        PasswordField passwordField = new PasswordField();
        passwordField.setPrefHeight(51.0);
        passwordField.setPrefWidth(295.0);
        passwordField.setStyle("-fx-background-color: transparent;");
        passwordField.setPromptText("Enter your password here!");

        // Adding child to parent
        hBox8.getChildren().add(passwordField);

        // Adding child to parent
        anchorPane0.getChildren().add(hBox8);
        Button signIn = new Button();
        signIn.setPrefHeight(33.0);
        signIn.setPrefWidth(233.0);
        signIn.setLayoutX(134.0);
        signIn.setStyle("-fx-background-color: #4678c8;"+" -fx-background-radius: 100PX;"+" -fx-text-fill: #f5f5f2;");
        signIn.setLayoutY(328.0);
        signIn.setText("Sign in");
        signIn.setMnemonicParsing(false);

        // Adding child to parent
        anchorPane0.getChildren().add(signIn);
        Text text11 = new Text();
        text11.setStrokeWidth(0.0);
        text11.setStrokeType(OUTSIDE);
        text11.setLayoutX(78.0);
        text11.setLayoutY(417.0);
        text11.setText("You don't have an account?");
        text11.setWrappingWidth(202.6708984375);

        // Adding child to parent
        anchorPane0.getChildren().add(text11);
        Text signUp = new Text();
        signUp.setStrokeWidth(0.0);
        signUp.setStrokeType(OUTSIDE);
        signUp.setUnderline(true);
        signUp.setLayoutX(288.0);
        signUp.setStyle("-fx-fill: #4678c8");
        signUp.setLayoutY(417.0);
        signUp.setText("Sign up!");

        // Adding child to parent

        anchorPane0.getChildren().add(signUp);
        GridPane background = new GridPane();
        background.setAlignment(Pos.CENTER);
        background.getChildren().add(anchorPane0);

        // Adding controller!

        signUp.setOnMouseClicked(e ->{
            stage.setScene(new RegisterGMenu("Register Menu",this,stage).getScene());
        });



        signIn.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            try {
                controller.getAccountController().controlLogin(username, password);
                stage.setScene(new MainMenuG("Main Menu",this, stage).getScene());
            } catch (ExceptionalMassage ex){
                System.out.println(ex.getMessage());
            }
        });


        return new Scene(background);
    }
}
