package gui.productMenu;

import controller.Controller;
import gui.GMenu;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import menu.menuAbstract.Menu;
import product.Product;

import static javafx.geometry.Pos.TOP_CENTER;
import static javafx.scene.control.TabPane.TabClosingPolicy.UNAVAILABLE;
import static javafx.scene.shape.StrokeType.OUTSIDE;

public class ProductMenuG extends GMenu {
    private Product product;
    public ProductMenuG(GMenu parentMenu, Stage stage, Product product, Controller controller) {
        super("Product Menu", parentMenu, stage, controller);
        this.product = product;
    }
    @Override
    protected Scene createScene() {
        AnchorPane anchorPane0 = new AnchorPane();
        anchorPane0.setPrefHeight(800.0);
        anchorPane0.setPrefWidth(1200.0);
        anchorPane0.setStyle("-fx-background-color: #f5f5f2;");
        ImageView imageView1 = new ImageView();
        imageView1.setPickOnBounds(true);
        imageView1.setFitWidth(285.0);
        imageView1.setFitHeight(305.0);
        imageView1.setPreserveRatio(true);
        imageView1.setLayoutX(76.0);
        imageView1.setLayoutY(60.0);

        // Adding child to parent
        anchorPane0.getChildren().add(imageView1);
        Label label2 = new Label();
        label2.setLayoutX(76.0);
        label2.setLayoutY(390.0);
        label2.setText("Product Name:");

        // Adding child to parent
        anchorPane0.getChildren().add(label2);
        Label label3 = new Label();
        label3.setLayoutX(191.0);
        label3.setLayoutY(390.0);
        label3.setText("name sits here");

        // Adding child to parent
        anchorPane0.getChildren().add(label3);
        Label label4 = new Label();
        label4.setLayoutX(76.0);
        label4.setLayoutY(417.0);
        label4.setText("Price:");

        // Adding child to parent
        anchorPane0.getChildren().add(label4);
        Label label5 = new Label();
        label5.setLayoutX(131.0);
        label5.setLayoutY(417.0);
        label5.setText("price sits here");

        // Adding child to parent
        anchorPane0.getChildren().add(label5);
        Label label6 = new Label();
        label6.setLayoutX(76.0);
        label6.setLayoutY(444.0);
        label6.setText("Score:");

        // Adding child to parent
        anchorPane0.getChildren().add(label6);
        Label label7 = new Label();
        label7.setLayoutX(131.0);
        label7.setLayoutY(444.0);
        label7.setText("Score sits here");

        // Adding child to parent
        anchorPane0.getChildren().add(label7);
        TabPane tabPane8 = new TabPane();
        tabPane8.setPrefHeight(305.0);
        tabPane8.setPrefWidth(1200.0);
        tabPane8.setLayoutY(495.0);
        tabPane8.setTabClosingPolicy(UNAVAILABLE);

        // Adding child to parent
        anchorPane0.getChildren().add(tabPane8);
        Label label9 = new Label();
        label9.setLayoutX(380.0);
        label9.setLayoutY(50.0);

        // Adding child to parent
        anchorPane0.getChildren().add(label9);
        Label label10 = new Label();
        label10.setLayoutX(380.0);
        label10.setLayoutY(62.0);
        label10.setText("Description:");

        // Adding child to parent
        anchorPane0.getChildren().add(label10);
        Text text11 = new Text();
        text11.setStrokeWidth(0.0);
        text11.setStrokeType(OUTSIDE);
        text11.setLayoutX(476.0);
        text11.setLayoutY(77.0);
        text11.setText("description sits here!");
        text11.setWrappingWidth(220.0);

        // Adding child to parent
        anchorPane0.getChildren().add(text11);
        VBox vBox12 = new VBox();
        vBox12.setPrefHeight(497.0);
        vBox12.setPrefWidth(433.0);
        vBox12.setLayoutX(767.0);
        vBox12.setStyle("-fx-background-color: #9cbfe3;");
        vBox12.setAlignment(TOP_CENTER);
        Label label13 = new Label();
        label13.setText("Order now!");

        // Adding child to parent
        vBox12.getChildren().add(label13);
        HBox hBox14 = new HBox();
        hBox14.setPrefHeight(51.0);
        hBox14.setPrefWidth(403.0);
        hBox14.setTranslateY(90.0);
        hBox14.setStyle("-fx-background-color: white;"+"-fx-border-color: #a2a2a2;"+"-fx-border-width: 0px 0px 2px 0px;");
        TextField textField15 = new TextField();
        textField15.setPrefHeight(49.0);
        textField15.setPrefWidth(335.0);
        textField15.setStyle("-fx-background-color: transparent;");
        textField15.setOpacity(0.83);
        textField15.setPromptText("Enter the amount you want!");

        // Adding child to parent
        hBox14.getChildren().add(textField15);

        // Adding child to parent
        vBox12.getChildren().add(hBox14);
        HBox hBox16 = new HBox();
        hBox16.setPrefHeight(37.0);
        hBox16.setPrefWidth(433.0);
        hBox16.setTranslateY(150.0);
        Label label17 = new Label();
        label17.setText("Choose your supplier:");

        // Adding child to parent
        hBox16.getChildren().add(label17);
        ChoiceBox choiceBox18 = new ChoiceBox();
        choiceBox18.setPrefWidth(150.0);
        choiceBox18.setTranslateX(25.0);

        // Adding child to parent
        hBox16.getChildren().add(choiceBox18);

        // Adding child to parent
        vBox12.getChildren().add(hBox16);
        Button button19 = new Button();
        button19.setPrefHeight(33.0);
        button19.setPrefWidth(233.0);
        button19.setTranslateY(280.0);
        button19.setStyle("-fx-background-color: #4678c8;"+"-fx-background-radius: 100PX;"+"-fx-text-fill: #f5f5f2;");
        button19.setText("Add to cart");
        button19.setMnemonicParsing(false);

        // Adding child to parent
        vBox12.getChildren().add(button19);

        // Adding child to parent
        anchorPane0.getChildren().add(vBox12);

        //Controller

        return new Scene(anchorPane0);
    }
}
