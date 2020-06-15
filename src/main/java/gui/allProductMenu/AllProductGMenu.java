package gui.allProductMenu;

import controller.SortType;
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

import java.io.File;
import java.util.ArrayList;

public class AllProductGMenu extends GMenu {

    private final GridPane backgroundLayout;
    private final GridPane mainPane;
    private final VBox filterAndSort;
    private final VBox appliedFilter;
    private final VBox price ;
    private final VBox sort;
    private final VBox availability ;
    private final GridPane productGridPane;


    public AllProductGMenu(String menuName, GMenu parentMenu, Stage stage) {
        super(menuName, parentMenu, stage);
        appliedFilter = new VBox();
        sort = new VBox();
        filterAndSort = new VBox();
        price = new VBox();
        backgroundLayout = new GridPane();
        mainPane = new GridPane();
        availability = new VBox();
        productGridPane = new GridPane();
    }

    @Override
    protected Scene createScene() {
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
            putNewProductsInProductGridPane();
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
        price.getChildren().addAll(lowerBoundLabel, lowerBound,upperBoundLabel, upperBound);
        price.setSpacing(10);
        price.setPadding(new Insets(10, 10 , 10 , 10));

        lowerBound.setOnMouseReleased(e->{
            try {
                controller.getProductController().getFilterAndSort().setPriceLowerBound((int) lowerBound.getValue());
            } catch (ExceptionalMassage exceptionalMassage) {
                exceptionalMassage.printStackTrace();
            }
            putNewProductsInProductGridPane();
        });

        upperBound.setOnMouseClicked(e->{
            try {
                controller.getProductController().getFilterAndSort().setPriceUpperBound((int) upperBound.getValue());
            } catch (ExceptionalMassage exceptionalMassage) {
                exceptionalMassage.printStackTrace();
            }
            putNewProductsInProductGridPane();
        });


        Button button = new Button("Apply Filter please");
        button.getStylesheets().add(new File("src/main/resources/css/Style.css").toURI().toString());
        button.getStyleClass().add("button");

        filterAndSort.getChildren().addAll(appliedFilter, sort,availability, price, button);
        filterAndSort.setStyle("-fx-background-color : #f8e8e2");

        button.setOnAction(e -> {
            System.out.println(Product.getProductById("T34P000000000000001").getName());

        });


        mainPane.getChildren().addAll(filterAndSort);

        backgroundLayout.getChildren().add(mainPane);

        backgroundLayout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(backgroundLayout);
        return scene;
    }

    private void putNewProductsInProductGridPane(){
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