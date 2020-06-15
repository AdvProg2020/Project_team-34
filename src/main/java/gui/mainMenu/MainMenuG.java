package gui.mainMenu;

import gui.GMenu;
import gui.allProductMenu.AllProductGMenu;
import gui.loginMenu.LoginGMenu;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import menu.menuAbstract.Menu;

import static javafx.geometry.Pos.TOP_CENTER;
import static javafx.scene.shape.StrokeType.OUTSIDE;

public class MainMenuG extends GMenu {
    public MainMenuG(String menuName, GMenu parentMenu, Stage stage) {
        super(menuName, parentMenu, stage);
    }

    @Override
    protected Scene createScene() {
        AnchorPane anchorPane0 = new AnchorPane();
        anchorPane0.setPrefHeight(600.0);
        anchorPane0.setPrefWidth(750.0);
        anchorPane0.setStyle("-fx-background-color: #4677c8;");
        VBox vBox1 = new VBox();
        vBox1.setPrefHeight(640.0);
        vBox1.setPrefWidth(375.0);
        vBox1.setLayoutX(373.0);
        vBox1.setStyle("-fx-background-color: #f8e8e2;");

        // Adding child to parent
        anchorPane0.getChildren().add(vBox1);
        StackPane stackPane2 = new StackPane();
        stackPane2.setPrefHeight(492.0);
        stackPane2.setPrefWidth(405.0);
        stackPane2.setLayoutX(173.0);
        stackPane2.setStyle("-fx-background-color: #9cbfe3;");
        stackPane2.setLayoutY(71.0);
        stackPane2.setEffect(new DropShadow());
        Button products = new Button();
        products.setPrefHeight(33.0);
        products.setPrefWidth(233.0);
        products.setStyle("-fx-background-color: #4678c8;"+"-fx-background-radius: 100PX;"+"-fx-text-fill: #f5f5f2;");
        products.setText("Products");
        products.setMnemonicParsing(false);

        // Adding child to parent
        stackPane2.getChildren().add(products);
        Button loginMenu = new Button();
        loginMenu.setPrefHeight(33.0);
        loginMenu.setPrefWidth(233.0);
        loginMenu.setTranslateY(-50.0);
        loginMenu.setStyle("-fx-background-color: #4678c8;"+"-fx-background-radius: 100PX;"+"-fx-text-fill: #f5f5f2;");
        loginMenu.setText("Sign in/Register");
        loginMenu.setMnemonicParsing(false);

        // Adding child to parent
        stackPane2.getChildren().add(loginMenu);
        Button salesMenu = new Button();
        salesMenu.setPrefHeight(33.0);
        salesMenu.setPrefWidth(233.0);
        salesMenu.setTranslateY(50.0);
        salesMenu.setStyle("-fx-background-color: #4678c8;"+"-fx-background-radius: 100PX;"+"-fx-text-fill: #f5f5f2;");
        salesMenu.setText("Sales");
        salesMenu.setAlignment(Pos.CENTER);
        salesMenu.setMnemonicParsing(false);

        // Adding child to parent
        stackPane2.getChildren().add(salesMenu);
        Text text6 = new Text();
        text6.setStrokeWidth(0.0);
        text6.setStrokeType(OUTSIDE);
        text6.setText("Welcome to T34 Store");
        stackPane2.setAlignment(text6, TOP_CENTER);
        text6.setStyle("-fx-end-margin: 15px;");

        // Adding child to parent
        stackPane2.getChildren().add(text6);

        // Adding child to parent
        anchorPane0.getChildren().add(stackPane2);

        // Adding controller

        loginMenu.setOnAction( e-> {
            stage.setScene(new LoginGMenu("Login Menu",this,stage).getScene());
        });

        products.setOnAction( e-> {
            stage.setTitle(menuName);
            stage.setScene(new AllProductGMenu("Product Menu",this,stage).getScene());
        });

        //loginMenu.setOnAction( e-> {
            //stage.setScene(new LoginGMenu("Login Menu",this,stage).getScene());
        //});
        GridPane background = new GridPane();
        background.setAlignment(Pos.CENTER);
        background.getChildren().add(anchorPane0);



        return new Scene(background);
    }
}
