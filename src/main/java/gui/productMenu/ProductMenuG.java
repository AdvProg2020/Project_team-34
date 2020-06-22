package gui.productMenu;

import account.Supplier;
import controller.Controller;
import controller.FilterAndSort;
import exceptionalMassage.ExceptionalMassage;
import feedback.Comment;
import gui.GMenu;
import gui.alerts.AlertBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import menu.menuAbstract.Menu;
import product.Product;

import java.io.File;
import java.util.ArrayList;

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
        ImageView imageViewBox = new ImageView();
        imageViewBox.setPickOnBounds(true);
        imageViewBox.setFitWidth(285.0);
        imageViewBox.setFitHeight(305.0);
        imageViewBox.setPreserveRatio(true);
        imageViewBox.setLayoutX(76.0);
        imageViewBox.setLayoutY(100.0);
        HBox header = createHeader();
        anchorPane0.getChildren().add(header);


        // Adding child to parent
        anchorPane0.getChildren().add(imageViewBox);
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

        Label label6 = new Label();
        label6.setLayoutX(76.0);
        label6.setLayoutY(444.0);
        label6.setText("Score:");

        // Adding child to parent
        anchorPane0.getChildren().add(label6);
        Label scoreText = new Label();
        scoreText.setLayoutX(131.0);
        scoreText.setLayoutY(444.0);
        scoreText.setText("Score sits here");

        // Adding child to parent
        anchorPane0.getChildren().add(scoreText);

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
        label13.setText("Order now!");

        // Adding child to parent
        vBox12.getChildren().add(label13);
        HBox hBox16 = new HBox();
        hBox16.setPrefHeight(37.0);
        hBox16.setPrefWidth(433.0);
        hBox16.setTranslateY(150.0);
        hBox16.setTranslateX(60);
        ComboBox<String> suppliers = new ComboBox<>();
        suppliers.setPrefWidth(200.0);
        suppliers.setTranslateX(25.0);

        // Adding child to parent
        hBox16.getChildren().add(suppliers);

        // Adding child to parent
        vBox12.getChildren().add(hBox16);
        Button addToCartButton = new Button();
        addToCartButton.setPrefHeight(33.0);
        addToCartButton.setPrefWidth(233.0);
        addToCartButton.setTranslateY(280.0);
        addToCartButton.setStyle("-fx-background-color: #4678c8;"+"-fx-background-radius: 100PX;"+"-fx-text-fill: #f5f5f2;");
        addToCartButton.setText("Add to cart");
        addToCartButton.setMnemonicParsing(false);

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
        vBox12.getChildren().add(addToCartButton);

        // Adding
        vBox12.getChildren().add(priceLabel);
        vBox12.getChildren().add(label4);

        // Adding child to parent
        anchorPane0.getChildren().add(vBox12);
        suppliers.setPromptText("Choose your supplier");


        // Adding Controller
        // adding image!
        File file = new File(product.getImageUrl());
        Image productImage = null;
        try {
            productImage = new Image(file.toURI().toURL().toExternalForm());
        } catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        imageViewBox.setImage(productImage);

        ScrollPane detailsScrollPane = new ScrollPane();
        ScrollPane commentsScrollPane = new ScrollPane();
        GridPane commentsGridPane = new GridPane();
        detailsScrollPane.setContent(createDetails());
        int i = 0;
        for (Comment comment : controller.getProductController().controlGetCommentsOfAProduct(product)) {
            commentsGridPane.add(commentBox(comment),0,i);
            i++;
        }
        commentsGridPane.add(createAddComment(),i,0);
        commentsScrollPane.setContent(commentsGridPane);
        details.setContent(detailsScrollPane);
        comments.setContent(commentsScrollPane);



        ArrayList<String> suppliersIds = new ArrayList<>();
        for (Supplier supplier : controller.getProductController().controlGetAllSuppliersForAProduct(product)) {
            suppliersIds.add(supplier.getNameOfCompany());
        }
        suppliers.getItems().addAll(suppliersIds);


        descriptionText.setText(product.getDescription());
        scoreText.setText( String.valueOf(controller.getProductController().controlGetAverageScoreByProduct(product)));
        nameText.setText(product.getName());

        addToCartButton.setOnAction( e -> {
            String productId = product.getProductId();
            String supplierNameOfCompany = suppliers.getValue();
            try {
                controller.getAccountController().controlAddToCart(productId, supplierNameOfCompany);
            } catch (ExceptionalMassage ex) {
                new AlertBox(this, ex, controller).showAndWait();
            }

        });

        suppliers.setOnAction( e -> {
            priceLabel.setText(String.valueOf(product.getPrice(Supplier.getSupplierByCompanyName(suppliers.getValue()))));
            suppliers.setPromptText("Choose your supplier");
        });





        return new Scene(anchorPane0);
    }

    private HBox createDetails(){

        HBox background = new HBox();
        GridPane gridPane = new GridPane();
        gridPane.setVgap(5);
        gridPane.setHgap(5);
        gridPane.setGridLinesVisible(true);
        int i = 0;
        for (String key : product.getSpecification().keySet()) {
            gridPane.add(new Label(key), 0, i);
            gridPane.add(new Label(product.getSpecification().get(key)), 1, i);
            i++;
        }

        background.getChildren().add(gridPane);
        return background;

    }

    private HBox createAddComment(){
        HBox hBox0 = new HBox();
        hBox0.setPrefHeight(250.0);
        hBox0.setPrefWidth(300.0);
        GridPane gridPane1 = new GridPane();
        gridPane1.setPrefHeight(400.0);
        gridPane1.setPrefWidth(572.0);
        gridPane1.setAlignment(Pos.CENTER);
        Label label2 = new Label();
        label2.setText("Title:");
        label2.setAlignment(Pos.CENTER);

// Adding child to parent
        TextField titleField = new TextField();
        gridPane1.add(label2,0,0);
        gridPane1.add(titleField,1,0);

// Adding child to parent
        Label label4 = new Label();
        label4.setText("Description:");
        gridPane1.add(label4,0,1);


// Adding child to parent
        TextArea descriptionArea = new TextArea();
        gridPane1.add(descriptionArea, 1, 1);
        descriptionArea.setPrefHeight(200.0);
        descriptionArea.setPrefWidth(200.0);

        Button comment = new Button("Comment");
        gridPane1.add(comment,1,2);
        gridPane1.setVgap(5);

        comment.setDisable(true);

        comment.setOnAction( e -> {
            String title = titleField.getText();
            String description = descriptionArea.getText();
            controller.getProductController().controlAddCommentToProduct(title, description, product);
        });



// Adding child to parent
        hBox0.getChildren().add(gridPane1);
        hBox0.setOnKeyReleased( e -> {
            boolean isDisable = titleField.getText().equals("") || descriptionArea.getText().equals("");
            comment.setDisable(isDisable);
        });
        return hBox0;


    }

    protected HBox commentBox(Comment comment) {
        HBox mainLayout = new HBox();
        VBox userInfo = new VBox();
        VBox commentDetail = new VBox();

        String username = "@" + comment.getCustomer().getUserName();
        ImageView authorized = getImageView("./src/main/resources/icons/boughted.png", 25, 25);
        boolean isAuthorized = comment.isCustomerBoughtThisProduct();

        HBox authorizedBox = new HBox();
        authorizedBox.getChildren().addAll(authorized, new Label("Customer comment"));
        authorizedBox.setSpacing(10);

        if (isAuthorized) {
            userInfo.getChildren().addAll(authorizedBox);
        }
        userInfo.getChildren().addAll(new Label(username));
        userInfo.setMinWidth(200);
        userInfo.setMinHeight(200);
        userInfo.setSpacing(20);
        userInfo.setPadding(new Insets(10, 10, 10, 10));
        userInfo.setAlignment(Pos.TOP_LEFT);

        Text commentText = new Text(comment.getContent());
        Text commentTitle = new Text(comment.getTitle());
        commentTitle.setStyle("-fx-font-weight: bolder");
        commentText.setWrappingWidth(380);
        commentTitle.setWrappingWidth(380);
        commentDetail.setPadding(new Insets(10, 10, 10, 10));
        commentDetail.setSpacing(20);
        commentDetail.getChildren().addAll(commentTitle, commentText);

        mainLayout.setMinWidth(600);
        mainLayout.setMinHeight(200);
        mainLayout.getChildren().addAll(userInfo, commentDetail);

        return mainLayout;
    }
}
