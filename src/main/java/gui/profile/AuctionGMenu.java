package gui.profile;

import account.Supplier;
import auction.Auction;
import controller.Controller;
import exceptionalMassage.ExceptionalMassage;
import feedback.Comment;
import gui.GMenu;
import gui.alerts.AlertBox;
import gui.cartMenu.CartGMenu;
import gui.productMenu.CompareGMenu;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.controlsfx.control.Rating;
import product.Product;

import java.util.ArrayList;

import static javafx.geometry.Pos.CENTER;
import static javafx.geometry.Pos.TOP_CENTER;
import static javafx.scene.shape.StrokeType.OUTSIDE;

public class AuctionGMenu extends GMenu {
    private Auction auction;

    public AuctionGMenu(GMenu parentMenu, Stage stage, Controller controller, Auction auction) {
        super("Auction Menu", parentMenu, stage, controller);
        this.auction = auction;
    }

    @Override
    protected Scene createScene() {
        AnchorPane anchorPane0 = new AnchorPane();
        anchorPane0.setPrefHeight(800.0);
        anchorPane0.setPrefWidth(1200.0);
        anchorPane0.setStyle("-fx-background-color: #f5f5f2;");
        GridPane imageViewGridPane = new GridPane();


        imageViewGridPane.setLayoutX(76.0);
        imageViewGridPane.setLayoutY(120);
        imageViewGridPane.setPrefWidth(250);
        imageViewGridPane.setPrefHeight(250);
        ImageView imageViewBox = new ImageView();
        imageViewBox.setSmooth(true);
        imageViewBox.setFitWidth(250.0);
        imageViewBox.setFitHeight(250.0);


        HBox header = createHeader();
        anchorPane0.getChildren().add(header);


        // Adding child to parent
        anchorPane0.getChildren().add(imageViewGridPane);
        Label label2 = new Label();
        label2.setLayoutX(76.0);
        label2.setLayoutY(390.0);
        label2.setText("Product Name:");

        // Adding child to parent
        anchorPane0.getChildren().add(label2);
        Label nameText = new Label();
        nameText.setLayoutX(191.0);
        nameText.setLayoutY(390.0);
        nameText.setText("name sits here");

        Label rateHere = new Label();
        rateHere.setLayoutX(76);
        rateHere.setLayoutY(420);
        rateHere.setText("Rate here:");

        Rating starRating = new Rating();
        starRating.setUpdateOnHover(true);
        starRating.setMax(5);
        starRating.setLayoutX(131);
        starRating.setLayoutY(410);
        try {
            starRating.setRating(controller.getProductController().controlGetAverageScoreByProduct(auction.getProduct()));
        } catch (ExceptionalMassage exceptionalMassage) {
            new AlertBox(this, exceptionalMassage, controller).showAndWait();
        }
        starRating.setEffect(new DropShadow());
        starRating.setPartialRating(true);


        anchorPane0.getChildren().addAll(rateHere, starRating);

        // Adding child to parent
        anchorPane0.getChildren().add(nameText);
        Label label4 = new Label();
        label4.setTranslateX(-100);
        label4.setTranslateY(170.0);
        label4.setText("Price:");

        // Adding child to parent
        Label priceLabel = new Label();
        priceLabel.setTranslateX(-10);
        priceLabel.setTranslateY(187);
        priceLabel.setText("price sits here");

        // Adding child to parent

        Label label9 = new Label();
        label9.setLayoutX(380.0);
        label9.setLayoutY(50.0);

        // Adding child to parent
        anchorPane0.getChildren().add(label9);
        Label label10 = new Label();
        label10.setLayoutX(380.0);
        label10.setLayoutY(100);
        label10.setText("Description:");

        // Adding child to parent
        anchorPane0.getChildren().add(label10);
        Text descriptionText = new Text();
        descriptionText.setStrokeWidth(0.0);
        descriptionText.setStrokeType(OUTSIDE);
        descriptionText.setLayoutX(476.0);
        descriptionText.setLayoutY(113.0);
        descriptionText.setText("description sits here!");
        descriptionText.setWrappingWidth(220.0);

        // Adding child to parent
        anchorPane0.getChildren().add(descriptionText);
        VBox vBox12 = new VBox();
        vBox12.setPrefHeight(497.0);
        vBox12.setPrefWidth(433.0);
        vBox12.setLayoutX(767.0);
        vBox12.setStyle("-fx-background-color: #9cbfe3;");
        vBox12.setAlignment(TOP_CENTER);
        Label label13 = new Label();
        label13.setText("Auction!");

        // Adding child to parent
        vBox12.getChildren().add(label13);


        HBox newPriceHBox = new HBox();
        newPriceHBox.setPrefHeight(37.0);
        newPriceHBox.setPrefWidth(433);
        newPriceHBox.setTranslateX(60);
        newPriceHBox.setTranslateY(150);
        newPriceHBox.setStyle("-fx-background-color: white;"+"-fx-border-color: #a2a2a2;"+"-fx-border-width: 0px 0px 2px 0px;");
        newPriceHBox.setLayoutY(179.0);
        TextField newPriceField = new TextField();
        newPriceField.setPrefHeight(51.0);
        newPriceField.setPrefWidth(295.0);
        newPriceField.setStyle("-fx-background-color: transparent;");
        newPriceField.setOpacity(0.83);
        newPriceField.setPromptText("Bid Higher Price!");

        // Adding child to parent
        newPriceHBox.getChildren().add(newPriceField);

        // Adding child to parent

        // Adding child to parent
        vBox12.getChildren().add(newPriceHBox);
        Button promotePriceButton = new Button();
        promotePriceButton.setPrefHeight(33.0);
        promotePriceButton.setPrefWidth(233.0);
        promotePriceButton.setTranslateY(280.0);
        promotePriceButton.setStyle("-fx-background-color: #4678c8;"+"-fx-background-radius: 100PX;"+"-fx-text-fill: #f5f5f2;");
        promotePriceButton.setText("Promote Price!");
        promotePriceButton.setMnemonicParsing(false);

        // Adding tabPane
        TabPane commentAndDetail = new TabPane();
        commentAndDetail.setPrefHeight(305);
        commentAndDetail.setPrefWidth(1200);
        commentAndDetail.setLayoutY(495);
        Tab details = new Tab("Details");
        Tab  comments = new Tab("Comments");
        commentAndDetail.getTabs().addAll(details, comments);


        anchorPane0.getChildren().add(commentAndDetail);
        // Adding child to parent
        vBox12.getChildren().add(promotePriceButton);

        // Adding
        vBox12.getChildren().add(priceLabel);
        vBox12.getChildren().add(label4);

        // Adding child to parent
        anchorPane0.getChildren().add(vBox12);

        // Adding Controller
        // adding image!
        Image[] productImage = new Image[1];
        productImage[0] = auction.getProduct().getImage();

        imageViewBox.setImage(productImage[0]);
        ImageView zoomedImageView = new ImageView();
        imageViewGridPane.add(zoomedImageView,1,0);
        int ratioWidth = (int)(productImage[0].getWidth()/250);
        int ratioHeight = (int)(productImage[0].getHeight()/250);
        imageViewGridPane.setOnMouseMoved( e -> {
            int x = (int) e.getX() * ratioWidth;
            int y = (int) e.getY() * ratioHeight;
            zoomedImageView.setImage(productImage[0]);
            Rectangle2D viewPortRec = new Rectangle2D(x, y, 250,250);
            zoomedImageView.setViewport(viewPortRec);
        });

        imageViewGridPane.setOnMouseExited( e -> {
            zoomedImageView.setImage(null);
        });

        ScrollPane detailsScrollPane = new ScrollPane();
        detailsScrollPane.setContent(createDetails());


        GridPane backGround = new GridPane();
        backGround.getChildren().add(createDetails());
        backGround.setAlignment(CENTER);
        backGround.setPadding(new Insets(10,10,10,10));
        details.setClosable(false);
        comments.setClosable(false);
        detailsScrollPane.setContent(backGround);
        details.setContent(detailsScrollPane);


        descriptionText.setText(auction.getProduct().getDescription());
        nameText.setText(auction.getProduct().getName());

        promotePriceButton.setOnAction( e -> {
            try {
                int price = Integer.parseInt(newPriceField.getText());
                try {
                    int minimumPrice = controller.getAccountController().controlGetMinimum();
                    controller.getProductController().promoteAuctionPrice(price,minimumPrice,auction.getIdentifier());
                    stage.setScene(new CartGMenu(this, stage, controller).getScene());
                } catch (ExceptionalMassage ex){
                    new AlertBox(this, ex, controller).showAndWait();
                }
            } catch (NumberFormatException ex) {
                new AlertBox(this, "Enter number for price, please","OK",controller).showAndWait();
            }

        });

        starRating.setOnMouseClicked( e -> {
            float rating =(float) starRating.getRating();
            try {
                controller.getProductController().controlRateProductById(auction.getProduct().getProductId(), rating);
                starRating.setRating(controller.getProductController().controlGetAverageScoreByProduct(auction.getProduct()));
                starRating.setDisable(true);
            } catch (ExceptionalMassage ex){
                new AlertBox(this, ex, controller).showAndWait();
            }
            try {
                starRating.setRating(controller.getProductController().controlGetAverageScoreByProduct(auction.getProduct()));
            } catch (ExceptionalMassage exceptionalMassage) {
                new AlertBox(this,exceptionalMassage,controller).showAndWait();
            }
        });

        starRating.setOnMouseExited( e -> {
            try {
                starRating.setRating(controller.getProductController().controlGetAverageScoreByProduct(auction.getProduct()));
            } catch (ExceptionalMassage exceptionalMassage) {
                new AlertBox(this,exceptionalMassage,controller).showAndWait();
            }

        });



        return new Scene(anchorPane0);
    }

    private HBox createDetails(){

        HBox background = new HBox();
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(CENTER);
        gridPane.setVgap(5);
        gridPane.setHgap(5);
        gridPane.setGridLinesVisible(true);
        Button compareButton = new Button("Compare");
        TextField productName = new TextField();
        productName.setPromptText("Other product's name");
        int i = 0;
        for (String key : auction.getProduct().getSpecification().keySet()) {
            Label keyLabel = new Label(key);
            keyLabel.setAlignment(Pos.CENTER);
            GridPane.setHalignment(keyLabel, HPos.CENTER);
            Label value = new Label(auction.getProduct().getSpecification().get(key));
            GridPane.setHalignment(value, HPos.CENTER);
            value.setAlignment(Pos.CENTER);
            gridPane.add(keyLabel, 0, i);
            gridPane.add(value, 1, i);
            i++;
        }
        gridPane.add(compareButton,1,i);
        gridPane.add(productName,0,i);
        compareButton.setOnAction( e -> {
            Product comparing = null;
            try {
                comparing = controller.getProductController().getProductByName(productName.getText());
            } catch (ExceptionalMassage exceptionalMassage) {
                new AlertBox(this, exceptionalMassage, controller);
            }
            if(comparing == null){
                new AlertBox(this,"No such product","OK",controller).showAndWait();
            } else {
                stage.setScene(new CompareGMenu("Compare menu", this, stage, controller, auction.getProduct(),comparing ).createScene());
            }
        });

        gridPane.setPadding(new Insets(10,10,10,10));

        background.getChildren().add(gridPane);
        return background;

    }
}
