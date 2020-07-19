package gui.profile;

import controller.Controller;
import exceptionalMassage.ExceptionalMassage;
import gui.GMenu;
import gui.alerts.AlertBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import static javafx.scene.shape.StrokeType.OUTSIDE;

public class ManageAuctionsForSupplierGMenu extends GMenu {
    public ManageAuctionsForSupplierGMenu(GMenu parentMenu, Stage stage, Controller controller) {
        super("Manage Auctions", parentMenu, stage, controller);
    }

    @Override
    protected Scene createScene() {
        GridPane backgroundLayout = new GridPane();
        HBox mainPane = new HBox();
        VBox buttonPane = new VBox();

//        TableView<String> auctionsTableView = new TableView<>();
//
//        TableColumn<String, Auction> identifier = new TableColumn<>("identifier");
//        identifier.setCellValueFactory(new PropertyValueFactory<>("identifier"));
//        TableColumn<String, Auction> product_id = new TableColumn<>("product id");
////        product_id.setCellValueFactory(new PropertyValueFactory<>("percent"));
//        TableColumn<String, Auction> highestPromotion = new TableColumn<>("highest Promotion");
//        highestPromotion.setCellValueFactory(new PropertyValueFactory<>("highestPromotion"));
//        auctionsTableView.getColumns().addAll(identifier,product_id, highestPromotion);

        ListView<String> auctionsListView = new ListView<>();
        ArrayList<String> allAuctions = new ArrayList<>();
        try {
            allAuctions = controller.getProductController().controlGetAuctionsForASupplier();
        } catch (ExceptionalMassage exceptionalMassage) {
            new AlertBox(this, exceptionalMassage, controller).showAndWait();
        }
        for (String auction : allAuctions) {
            auctionsListView.getItems().add(auction);
        }

        Button viewDetailsButton = new Button("View details");
        GMenu.addStyleToButton(viewDetailsButton);
        viewDetailsButton.setOnMouseClicked(e -> {
            try {
                if (auctionsListView.getSelectionModel().getSelectedItems().size() != 0)
                    controller.getProductController().controlGetAuctionById(auctionsListView.getSelectionModel().getSelectedItems().get(0));
            } catch (ExceptionalMassage exceptionalMassage) {
                new AlertBox(this, exceptionalMassage, controller).showAndWait();
            }
            Scene scene = createNewScene("hiihihdfsj");
            Image logoImage = null;
            try {
                logoImage = new Image(new FileInputStream("./src/main/resources/header/Logo.png"));
            } catch (FileNotFoundException exc) {
            }
            Stage newStage = new Stage();
            newStage.getIcons().add(logoImage);
            newStage.setTitle("Product details");
            newStage.setScene(scene);
            newStage.showAndWait();

        });

        Button createAuctionButton = new Button("Create Auction");
        GMenu.addStyleToButton(createAuctionButton);
        createAuctionButton.setOnMouseClicked(e -> {
            stage.setScene(new CreateAuctionGMenu(this, stage, controller).createScene());
        });

        buttonPane.setSpacing(30);
        buttonPane.setAlignment(Pos.CENTER);
        buttonPane.getChildren().addAll(viewDetailsButton, createAuctionButton);
        mainPane.setSpacing(30);
        mainPane.setAlignment(Pos.CENTER);
        mainPane.setPadding(new Insets(15, 15, 15, 15));
        mainPane.getChildren().addAll(auctionsListView, buttonPane);

        VBox headerBackground = new VBox();
        headerBackground.setStyle("-fx-background-color: #4677c8");
        headerBackground.getChildren().add(createHeader());
        backgroundLayout.add(headerBackground, 0, 0);
        backgroundLayout.add(mainPane, 0, 1);
        backgroundLayout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(backgroundLayout);
        return scene;
    }

    private Scene createNewScene(String text) {
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
        text3.setText(text);

        text3.setWrappingWidth(477.6708984375);

        // Adding child to parent
        anchorPane0.getChildren().add(text3);

        Scene scene = new Scene(anchorPane0);
        return scene;
    }

}
