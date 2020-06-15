package gui.allProductMenu;

import controller.Controller;
import exceptionalMassage.ExceptionalMassage;
import gui.GMenu;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import product.Product;
import org.controlsfx.control.RangeSlider;

import java.io.File;
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
        VBox price = new VBox();
        VBox sort = new VBox();
        VBox availability = new VBox();
        GridPane productGridPane = new GridPane();
        RangeSlider rangeSlider = new RangeSlider(0, 100, 10, 90);
        Label label = new Label("Applied Filter");
        appliedFilter.getChildren().add(label);
        appliedFilter.setSpacing(10);
        appliedFilter.setPadding(new Insets(10, 10 , 10 , 10));

        ToggleGroup toggleGroup = new ToggleGroup();
        RadioButton numberOfViews = new RadioButton("Number Of Views");
        RadioButton score = new RadioButton("Score");
        RadioButton time = new RadioButton("Time");

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


        RadioButton availabilityCheck = new RadioButton("Only Available Products");
        availability.getChildren().add(availabilityCheck);
        availability.setSpacing(10);
        availability.setPadding(new Insets(10, 10 , 10 , 10));

        availabilityCheck.setOnMouseReleased(e->{
            controller.getProductController().getFilterAndSort().setAvailabilityFilter(availabilityCheck.isSelected());
        });

        Label lowerBoundLabel = new Label("Lower Bound");
        Slider lowerBound = new Slider();
        Label upperBoundLabel = new Label("Upper Bound");
        Slider upperBound = new Slider();
        upperBound.setValue(100);
//        lowerBoundLabel, lowerBound,upperBoundLabel, upperBound
        price.getChildren().addAll(rangeSlider);
        price.setSpacing(10);
        price.setPadding(new Insets(10, 10 , 10 , 10));

        lowerBound.setOnMouseReleased(e->{
            try {
                controller.getProductController().getFilterAndSort().setPriceLowerBound((int) lowerBound.getValue());
            } catch (ExceptionalMassage exceptionalMassage) {
                exceptionalMassage.printStackTrace();
            }
            putNewProductsInProductGridPane(productGridPane);
        });

        upperBound.setOnMouseClicked(e->{
            try {
                controller.getProductController().getFilterAndSort().setPriceUpperBound((int) upperBound.getValue());
            } catch (ExceptionalMassage exceptionalMassage) {
                exceptionalMassage.printStackTrace();
            }
            putNewProductsInProductGridPane(productGridPane);
        });


        Button button = new Button("Apply Filter please");
        button.getStylesheets().add(new File("src/main/resources/css/Style.css").toURI().toString());
        button.getStyleClass().add("button");

        filterAndSort.getChildren().addAll(appliedFilter, sort,availability, price, button);
        filterAndSort.setStyle("-fx-background-color : #f8e8e2");

        button.setOnAction(e -> {
            System.out.println(Product.getProductById("T34P000000000000001").getName());

        });


        mainPane.add(createHeader(),0, 0 );
        mainPane.add(filterAndSort, 0, 1);

        backgroundLayout.getChildren().add(mainPane);
        backgroundLayout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(backgroundLayout);
        return scene;
    }

    private void putNewProductsInProductGridPane(GridPane productGridPane){
        System.out.println("oomadam toosh");
        ArrayList<Product> products =  controller.getProductController().getFilterAndSort().getProducts();
        System.out.println(products.size());
        int row =0 ;
        for (Product product : products) {
            System.out.println(product.getName());
            productGridPane.add(new Label(product.getName()), row, 0);
            row++;
        }
    }
}
