package gui.allProductMenu;

import controller.Controller;
import discount.Sale;
import exceptionalMassage.ExceptionalMassage;
import gui.GMenu;
import gui.alerts.AlertBox;
import gui.productMenu.ProductMenuG;
import gui.profile.EditPersonalInfoGMenu;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.controlsfx.control.CheckComboBox;
import org.controlsfx.control.Rating;
import product.Category;
import product.Product;
import org.controlsfx.control.RangeSlider;

import javax.net.ssl.SSLEngineResult;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class AllProductGMenu extends GMenu {

    private boolean onlyProductInSale ;

    public AllProductGMenu(GMenu parentMenu, Stage stage, Controller controller, boolean onlyProductInSale) {
        super("All products Menu", parentMenu, stage, controller);
        this.onlyProductInSale = onlyProductInSale;
        controller.getProductController().clearFilterAndSort();
    }

    @Override
    protected Scene createScene() {
        GridPane backgroundLayout = new GridPane();
        GridPane mainPane = new GridPane();
        VBox filterAndSort = new VBox();
        ScrollPane filterAndSortScrollPane = new ScrollPane();
        VBox availabilityVBox = new VBox();
        HBox price = new HBox();
        VBox sort = new VBox();
        VBox specialFilterVBox = new VBox();
        GridPane productGridPane = new GridPane();
        ScrollPane productScrollPane = new ScrollPane();

        mainPane.setMinWidth(1150);
        mainPane.setMaxWidth(1150);
        mainPane.setMinHeight(650);



        Label sortLabel = new Label("Sort");
        sortLabel.setStyle("-fx-font-size: 20 ");
        filterAndSort.setAlignment(Pos.CENTER);

        Label filterLabel = new Label("Filter");
        filterLabel.setStyle("-fx-font-size: 20 ");

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

        score.setOnMouseClicked(e->{
            controller.getProductController().controlFilterSetSortType("by score");
            putNewProductsInProductGridPane(productGridPane);
        });

        time.setOnMouseClicked(e->{
            controller.getProductController().controlFilterSetSortType("by time added");
            putNewProductsInProductGridPane(productGridPane);
        });


        CheckBox saleCheck = new CheckBox("Only Products On Sale");
        saleCheck.setSelected(onlyProductInSale);
        controller.getProductController().getFilterAndSort().setInSaleOnly(onlyProductInSale);
        saleCheck.setOnMouseClicked(e->{
            controller.getProductController().getFilterAndSort().setInSaleOnly(saleCheck.isSelected());
            putNewProductsInProductGridPane(productGridPane);
        });



        RangeSlider rangeSlider = new RangeSlider(0, 10000, 0, 10000);
        rangeSlider.setMajorTickUnit(1000);
        rangeSlider.setShowTickLabels(true);
        rangeSlider.setOnMouseReleased(e->{
            try {
                controller.getProductController().getFilterAndSort().setPriceUpperBound((int) rangeSlider.getHighValue());
                System.out.println(controller.getProductController().getFilterAndSort().getPriceUpperBound());
            } catch (ExceptionalMassage exceptionalMassage) {
                exceptionalMassage.printStackTrace();
            }
            try {
                controller.getProductController().getFilterAndSort().setPriceLowerBound((int) rangeSlider.getLowValue());
            } catch (ExceptionalMassage exceptionalMassage) {
                exceptionalMassage.printStackTrace();
            }
            putNewProductsInProductGridPane(productGridPane);
        });

        CheckBox availabilityCheck = new CheckBox("Only Available Products");
        availabilityVBox.getChildren().addAll(availabilityCheck, saleCheck);
        availabilityVBox.setSpacing(10);
        availabilityVBox.setPadding(new Insets(10, 10 , 10 , 10));

        availabilityCheck.setOnMouseReleased(e->{
            rangeSlider.setDisable(!availabilityCheck.isSelected());
            controller.getProductController().getFilterAndSort().setAvailabilityFilter(availabilityCheck.isSelected());
            putNewProductsInProductGridPane(productGridPane);

        });



        boolean isAvailability = controller.getProductController().getFilterAndSort().getAvailabilityFilter();
        rangeSlider.setDisable(!isAvailability);

        Label priceLabel  = new Label("Price");
        price.getChildren().addAll(priceLabel, rangeSlider);
        price.setSpacing(10);
        price.setPadding(new Insets(10, 10 , 10 , 10));

        putNewProductsInProductGridPane(productGridPane);


        TreeView<Label> treeView = new TreeView<>(getTreeItem(controller.getProductController().controlGetAllProductCategory(), controller, productGridPane , numberOfViews, saleCheck, availabilityCheck, rangeSlider, specialFilterVBox));
//        TreeView<String> babyTreeView = new TreeView<>(baby);
        treeView.setPrefHeight(250);
        filterAndSort.getStylesheets().add(new File("src/main/resources/css/Style.css").toURI().toString());
//        treeView.getStyleClass().add("my tree");


        VBox nameFilter = new VBox();
        Label filterByNameLabel = new Label("Filter By Name");
        nameFilter.getChildren().add(filterByNameLabel);
        TextField filterByNameTextField = new TextField();
        nameFilter.getChildren().add(filterByNameTextField);


        ListView<String> filterByNameListView = new ListView<>();
        filterByNameListView.getItems().add("hi");
        nameFilter.getChildren().add(filterByNameListView);

        Button filterByNameAddButton = new Button("Add");
        GMenu.addStyleToButton(filterByNameAddButton);
        filterByNameAddButton.setOnMouseClicked(e->{
            filterByNameListView.getItems().add(filterByNameTextField.getText());
//            controller.getProductController().getFilterAndSort().addNameFilter();
        });
        nameFilter.getChildren().add(filterByNameAddButton);

        Button filterByNameRemoveButton = new Button("Remove");
        GMenu.addStyleToButton(filterByNameRemoveButton);
        filterByNameRemoveButton.setOnMouseClicked(e->{

        });
        nameFilter.getChildren().add(filterByNameRemoveButton);

        specialFilterVBox.setPadding(new Insets(20, 20, 20 , 20));
        specialFilterVBox.setSpacing(20);
        putNewSpecialFilters(specialFilterVBox, productGridPane);
        filterAndSort.getChildren().addAll(sortLabel, sort, filterLabel, availabilityVBox,  price, specialFilterVBox,nameFilter, treeView);
        filterAndSort.setStyle("-fx-background-color : #f8e8e2");



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
        filterAndSortScrollPane.setContent(filterAndSort);
        mainPane.add(filterAndSortScrollPane, 0, 1);
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
        int row =0 , column = 0 ;
        GridPane gridPane;

        for (Product product : products) {
            VBox mainVBox = new VBox();
            gridPane = new GridPane();
            mainVBox.setStyle("-fx-background-color: #9cbfe3");

            Label nameLabel = new Label(product.getName());
            nameLabel.setStyle("-fx-font-size: 20");



            ImageView productImageView = GMenu.getImageView(product.getImageUrl(), 200 , 200);

            productImageView.setOnMouseClicked(e->{
                controller.getProductController().controlViewThisProduct(product);
                stage.setScene(new ProductMenuG(this,stage,  product, controller).getScene());
            });
            if(Sale.isProductHasAnySale(product)) {
                ImageView soldOutImageView = GMenu.getImageView("./src/main/resources/image/Sale.png", 200, 200);

                soldOutImageView.setBlendMode(BlendMode.SRC_OVER);
                Group blend = new Group(
                        productImageView,
                        soldOutImageView
                );

                gridPane.getChildren().addAll(productImageView, blend, soldOutImageView);
//                gridPane.getChildren().add(soldOutImageView);
            }
            else if(product.getAllSuppliersThatHaveAvailableProduct().size() == 0) {
                ImageView soldOutImageView = GMenu.getImageView("./src/main/resources/image/soldOut.png", 200, 200);

                soldOutImageView.setBlendMode(BlendMode.SRC_OVER);
                Group blend = new Group(
                        productImageView,
                        soldOutImageView
                );

                gridPane.getChildren().addAll( productImageView, blend,soldOutImageView);
//                gridPane.getChildren().add(soldOutImageView);
            }
            else {
                gridPane.getChildren().add(productImageView);
            }

            Rating starRating = new Rating();
            starRating.setMax(5);
            starRating.setRating(controller.getProductController().controlGetAverageScoreByProduct(product));
            starRating.setEffect(new DropShadow());
            starRating.setPartialRating(true);
            starRating.setFocusTraversable(false);
            starRating.setMouseTransparent(true);

            mainVBox.getChildren().add(gridPane);
            mainVBox.getChildren().add(nameLabel);
            mainVBox.getChildren().add(starRating);
            mainVBox.setAlignment(Pos.CENTER);

            productGridPane.add(mainVBox, column, row);
            column ++;
            if(column>= 4 ){
                column = 0 ;
                row ++;
            }

        }
    }

    public TreeItem<Label> getTreeItem(Category rootCategory, Controller controller, GridPane productGridPane, RadioButton numberOfViews, CheckBox saleCheck , CheckBox availabilityCheck, RangeSlider rangeSlider , VBox specialFilterVBox){
        Label rootLabel = new Label(rootCategory.getName());
        rootLabel.setStyle("-fx-text-fill: #4677c8");
        rootLabel.setOnMouseClicked(e->{
            controller.getProductController().getFilterAndSort().setCategory(rootCategory);
            resetAllFilters(numberOfViews,saleCheck, availabilityCheck, rangeSlider);
            putNewProductsInProductGridPane(productGridPane);
            putNewSpecialFilters(specialFilterVBox, productGridPane);
        });
        TreeItem<Label> rootTreeItem = new TreeItem<>(rootLabel);
        if(!rootCategory.isCategoryClassifier())
            return rootTreeItem;
        ArrayList<Category> allCategoriesIn = controller.getProductController().controlGetAllCategoriesInACategory(rootCategory);
        for (Category categoryIn : allCategoriesIn) {
            rootTreeItem.getChildren().add(getTreeItem(categoryIn, controller, productGridPane, numberOfViews, saleCheck, availabilityCheck, rangeSlider, specialFilterVBox));
        }
        return rootTreeItem;
    }

    public void resetAllFilters(RadioButton numberOfViews ,CheckBox saleCheck , CheckBox availabilityCheck, RangeSlider rangeSlider){
        numberOfViews.setSelected(true);
        saleCheck.setSelected(false);
        availabilityCheck.setSelected(false);
        rangeSlider.setHighValue(10000);
        rangeSlider.setLowValue(0);
        rangeSlider.setDisable(true);
    }

    public void putNewSpecialFilters(VBox specialFiltersVBox, GridPane productGridPane){
        HashMap<String, ArrayList<String>> specialFilters = controller.getProductController().controlGetAllAvailableFilters();
        System.out.println(specialFilters);
        specialFiltersVBox.getChildren().clear();
        for (String specialField : specialFilters.keySet()) {

            CheckComboBox<String> checkComboBox = new CheckComboBox<>();
            checkComboBox.setTitle(specialField);
            checkComboBox.getItems().addAll(specialFilters.get(specialField));
            checkComboBox.setMinWidth(100);


            checkComboBox.getCheckModel().getCheckedItems().addListener(new ListChangeListener<String>() {
                public void onChanged(ListChangeListener.Change<? extends String> c) {

                    controller.getProductController().controlFilterRemoveAllSpecialFilter();

                    for (String checkedItem : checkComboBox.getCheckModel().getCheckedItems()) {
                        try {
                            controller.getProductController().controlFilterAddSpecialFilter(specialField, checkedItem);
                        } catch (ExceptionalMassage exceptionalMassage) {
                            System.out.println(exceptionalMassage.toString());
                        }
                    }

                    putNewProductsInProductGridPane(productGridPane);

                }
            });

            specialFiltersVBox.getChildren().add(checkComboBox);
        }
    }
}
