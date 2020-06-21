package gui.allProductMenu;

import controller.Controller;
import exceptionalMassage.ExceptionalMassage;
import gui.GMenu;
import gui.productMenu.ProductMenuG;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import product.Product;
import org.controlsfx.control.RangeSlider;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class AllProductGMenu extends GMenu {

    private boolean onlyProductInSale ;

    public AllProductGMenu(GMenu parentMenu, Stage stage, Controller controller, boolean onlyProductInSale) {
        super("All products Menu", parentMenu, stage, controller);
        this.onlyProductInSale = onlyProductInSale;
    }

    @Override
    protected Scene createScene() {
        GridPane backgroundLayout = new GridPane();
        GridPane mainPane = new GridPane();
        VBox filterAndSort = new VBox();
        VBox appliedFilter = new VBox();
        HBox price = new HBox();
        VBox sort = new VBox();
        VBox availability = new VBox();
        GridPane productGridPane = new GridPane();
        ScrollPane productScrollPane = new ScrollPane();

        mainPane.setMinWidth(1000);
        mainPane.setMinHeight(600);

        Label label = new Label("Applied Filter");
        appliedFilter.getChildren().add(label);
        appliedFilter.setSpacing(10);
        appliedFilter.setPadding(new Insets(10, 10 , 10 , 10));

        ToggleGroup toggleGroup = new ToggleGroup();
        RadioButton numberOfViews = new RadioButton("Number Of Views");
        RadioButton score = new RadioButton("Score");
        RadioButton time = new RadioButton("Time");
        numberOfViews.setSelected(true);

        numberOfViews.setToggleGroup(toggleGroup);
        score.setToggleGroup(toggleGroup);
        time.setToggleGroup(toggleGroup);


        sort.getChildren().addAll(numberOfViews, score, time);
        sort.setSpacing(10);
        sort.setPadding(new Insets(10, 10 , 10 , 10));


        numberOfViews.setOnMouseReleased(e->{
            controller.getProductController().controlFilterSetSortType("by number of views");
            putNewProductsInProductGridPane(productGridPane);
        });


        CheckBox saleCheck = new CheckBox("Only Products In Sale");
        saleCheck.setSelected(onlyProductInSale);
        saleCheck.setOnMouseClicked(e->{
            controller.getProductController().getFilterAndSort().setAvailabilityFilter(saleCheck.isSelected());
            putNewProductsInProductGridPane(productGridPane);
        });

        CheckBox availabilityCheck = new CheckBox("Only Available Products");
        availability.getChildren().addAll(availabilityCheck, saleCheck);
        availability.setSpacing(10);
        availability.setPadding(new Insets(10, 10 , 10 , 10));

        availabilityCheck.setOnMouseReleased(e->{
            controller.getProductController().getFilterAndSort().setAvailabilityFilter(availabilityCheck.isSelected());
            putNewProductsInProductGridPane(productGridPane);
        });

        RangeSlider rangeSlider = new RangeSlider(0, 10000, 0, 10000);
        rangeSlider.setMajorTickUnit(1000);
        rangeSlider.setShowTickLabels(true);
        rangeSlider.setOnMouseClicked(e->{
            putNewProductsInProductGridPane(productGridPane);
        });
        Label priceLabel  = new Label("Price");
        price.getChildren().addAll(priceLabel, rangeSlider);
        price.setSpacing(10);
        price.setPadding(new Insets(10, 10 , 10 , 10));

//        lowerBound.setOnMouseReleased(e->{
//            try {
//                controller.getProductController().getFilterAndSort().setPriceLowerBound((int) lowerBound.getValue());
//            } catch (ExceptionalMassage exceptionalMassage) {
//                exceptionalMassage.printStackTrace();
//            }
//            putNewProductsInProductGridPane(productGridPane);
//        });
//
//        upperBound.setOnMouseClicked(e->{
//            try {
//                controller.getProductController().getFilterAndSort().setPriceUpperBound((int) upperBound.getValue());
//            } catch (ExceptionalMassage exceptionalMassage) {
//                exceptionalMassage.printStackTrace();
//            }
//            putNewProductsInProductGridPane(productGridPane);
//        });


        Button button = new Button("Apply Filter please");
        GMenu.addStyleToButton(button);

        filterAndSort.getChildren().addAll(appliedFilter, sort,availability, price, button);
        filterAndSort.setStyle("-fx-background-color : #f8e8e2");

//        button.setOnAction(e -> {
//            System.out.println(Product.getProductById("T34P000000000000001").getName());
//
//        });


        productGridPane.setVgap(30);
        productGridPane.setHgap(30);

//        productGridPane.add(bottom, 4, 5);
        productScrollPane.setContent(productGridPane);
//        productScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
//        productScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        VBox headerBackground = new VBox();
        headerBackground.setStyle("-fx-background-color: #4677c8");
        headerBackground.getChildren().add(createHeader());
//        headerBackground.setMaxWidth(Double.MAX_VALUE);
//        mainPane.setFillWidth(headerBackground, true);
//        mainPane.add(headerBackground,0, 0 );
        mainPane.add(filterAndSort, 0, 1);
        mainPane.add(productScrollPane, 1,1);

        backgroundLayout.add(headerBackground, 0, 0);
        backgroundLayout.add(mainPane, 0, 1);
        backgroundLayout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(backgroundLayout);
        return scene;
    }

    private void putNewProductsInProductGridPane(GridPane productGridPane){
        productGridPane.getChildren().clear();
        ArrayList<Product> products =  controller.getProductController().getFilterAndSort().getProducts();
        System.out.println(products.size());
        int row =0 ;
        GridPane gridPane;

        for (Product product : products) {
            VBox mainVBox = new VBox();
            gridPane = new GridPane();
            mainVBox.setStyle("-fx-background-color: #9cbfe3");

            Label nameLabel = new Label(product.getName());
            nameLabel.setStyle("-fx-font-size: 20");

            ImageView productImageView = GMenu.getImageView(product.getImageUrl(), 200 , 200);

            productImageView.setOnMouseClicked(e->{
                stage.setScene(new ProductMenuG(this,stage,  product, controller).getScene());
            });

            ImageView soldOutImageView = GMenu.getImageView("./src/main/resources/image/soldOut.png",200, 200);

            soldOutImageView.setBlendMode(BlendMode.ADD);
            Group blend = new Group(
                    productImageView,
                    soldOutImageView
            );

            gridPane.getChildren().addAll(productImageView, blend, soldOutImageView);

            mainVBox.getChildren().add(gridPane);
            mainVBox.getChildren().add(nameLabel);
            mainVBox.setAlignment(Pos.CENTER);

            productGridPane.add(mainVBox, 0, row);
            row++;
        }
    }
}
