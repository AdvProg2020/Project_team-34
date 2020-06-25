package gui.profile;

import account.Supplier;
import controller.Controller;
import exceptionalMassage.ExceptionalMassage;
import gui.GMenu;
import gui.alerts.AlertBox;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import product.Product;
import state.State;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static javafx.scene.shape.StrokeType.OUTSIDE;

public class ManageRequestForSupplierGMenu extends GMenu{


    public ManageRequestForSupplierGMenu(GMenu parentMenu, Stage stage, Controller controller) {
        super("Manage Products, Supplier", parentMenu, stage, controller);
    }

    @Override
    protected Scene createScene() {
        AnchorPane anchorPane0 = new AnchorPane();

        anchorPane0.setPrefHeight(700.0);

        anchorPane0.setPrefWidth(850.0);

        anchorPane0.setStyle("-fx-background-color: #f5f5f2;");

        HBox hBox1 = new HBox();
        hBox1.setPrefHeight(102.0);
        hBox1.setPrefWidth(850.0);
        hBox1.setStyle("-fx-background-color: #4477c8;");

        HBox header = createHeader();
        hBox1.getChildren().add(header);

        // Adding child to parent
        anchorPane0.getChildren().add(hBox1);
        HBox hBox2 = new HBox();
        hBox2.setPrefHeight(102.0);
        hBox2.setPrefWidth(850.0);
        hBox2.setStyle("-fx-background-color: #4477c8;");
        hBox2.setLayoutY(598.0);

        // Adding child to parent
        anchorPane0.getChildren().add(hBox2);
        ListView products = new ListView();
        products.setPrefHeight(398.0);
        products.setPrefWidth(350.0);
        products.setLayoutX(50.0);
        products.setLayoutY(154.0);

        // Adding child to parent
        anchorPane0.getChildren().add(products);
        Label label4 = new Label();
        label4.setLayoutX(50.0);
        label4.setLayoutY(120.0);
        label4.setText("Requests:");

        // Adding child to parent
        anchorPane0.getChildren().add(label4);
        Button detailButton = new Button();
        detailButton.setPrefHeight(33.0);
        detailButton.setPrefWidth(233.0);
        detailButton.setLayoutX(505.0);
        detailButton.setStyle("-fx-background-color: #4678c8;"+"-fx-background-radius: 100PX;"+"-fx-text-fill: #f5f5f2;");
        detailButton.setLayoutY(238.0);
        detailButton.setText("View details");
        detailButton.setMnemonicParsing(false);



        // Adding controller

        for (Product product : controller.getAccountController().controlGetRequestForLoggedInSupplier()) {
            products.getItems().add(Product.convertProductIdToRequestId(product.getProductId()));
        }


        detailButton.setOnAction( e -> {
            ObservableList<String> ids = products.getSelectionModel().getSelectedItems();
            for (String id : ids) {
                Image logoImage = null;
                try {
                    logoImage = new Image(new FileInputStream("./src/main/resources/header/Logo.png"));
                } catch (FileNotFoundException exc) {
                }
                Stage newStage = new Stage();
                newStage.getIcons().add(logoImage);
                newStage.setTitle("Product details");
                newStage.setScene(createDetails(id));
                newStage.showAndWait();
            }
        });

        anchorPane0.getChildren().add(detailButton);



        return new Scene(anchorPane0);
    }

    private Scene createDetails(String productId){
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
        Product product = Product.getProductById(Product.convertRequestIdToProductId(productId));
        String detailsToShow = product.getProductState().toString();
        text3.setText(detailsToShow + "\n" + product.toString());

        String source ;
        State state = product.getProductState();
        if(state == State.DELETE_DECLINED || state == State.EDIT_DECLINED || state == State.BUILD_DECLINED){
            source = "./src/main/resources/icons/error.png";
        }
        else if(state == State.DELETE_ACCEPTED || state == State.EDIT_ACCEPTED || state == State.BUILD_ACCEPTED){
            source ="./src/main/resources/icons/boughted.png";
        }
        else  {
            source = "./src/main/resources/icons/hourglass-2.png";
        }
        ImageView stateImageView = GMenu.getImageView(source , 40, 40);
        stateImageView.setX(50);
        stateImageView.setY(50);
        anchorPane0.getChildren().add(stateImageView);

        return new Scene(anchorPane0);
    }
}
