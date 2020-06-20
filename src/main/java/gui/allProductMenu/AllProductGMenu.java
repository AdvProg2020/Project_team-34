package gui.allProductMenu;

import controller.Controller;
import exceptionalMassage.ExceptionalMassage;
import gui.GMenu;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
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

    public AllProductGMenu(GMenu parentMenu, Stage stage, Controller controller) {
        super("All products Menu", parentMenu, stage, controller);
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

        mainPane.setMinWidth(1200);

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

        CheckBox availabilityCheck = new CheckBox("Only Available Products");
        availability.getChildren().addAll(availabilityCheck, saleCheck);
        availability.setSpacing(10);
        availability.setPadding(new Insets(10, 10 , 10 , 10));

        availabilityCheck.setOnMouseReleased(e->{
            controller.getProductController().getFilterAndSort().setAvailabilityFilter(availabilityCheck.isSelected());
        });

//        Label lowerBoundLabel = new Label("Lower Bound");
//        Slider lowerBound = new Slider();
//        Label upperBoundLabel = new Label("Upper Bound");
//        Slider upperBound = new Slider();
//        upperBound.setValue(100);
////        lowerBoundLabel, lowerBound,upperBoundLabel, upperBound

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
        System.out.println("oomadam toosh");
        ArrayList<Product> products =  controller.getProductController().getFilterAndSort().getProducts();
//        products.add(Product.getProductById("T34P000000000000003"));
//        products.add(Product.getProductById("T34P000000000000009"));
        System.out.println(products.size());
        int row =0 ;
        for (Product product : products) {
            System.out.println(product.getName());
            Image image = null;
            try {
                image = new Image(new FileInputStream(product.getImageUrl()));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(400);
            imageView.setFitWidth(400);
            productGridPane.add(imageView, row, row);
            row++;
        }
    }
}
