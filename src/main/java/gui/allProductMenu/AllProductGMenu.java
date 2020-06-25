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

    private boolean onlyProductInSale;

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
        VBox priceVBox = new VBox();
        VBox sort = new VBox();
        VBox specialFilterVBox = new VBox();
        GridPane productGridPane = new GridPane();
        ScrollPane productScrollPane = new ScrollPane();
        productScrollPane.setContent(productGridPane);

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
        sort.setPadding(new Insets(10, 10, 10, 10));


        numberOfViews.setOnMouseReleased(e -> {
            controller.getProductController().controlFilterSetSortType("by number of views");
            putNewProductsInProductGridPane(productGridPane);
        });

        score.setOnMouseClicked(e -> {
            controller.getProductController().controlFilterSetSortType("by score");
            putNewProductsInProductGridPane(productGridPane);
        });

        time.setOnMouseClicked(e -> {
            controller.getProductController().controlFilterSetSortType("by time added");
            putNewProductsInProductGridPane(productGridPane);
        });


        CheckBox saleCheck = new CheckBox("Only Products On Sale");
        saleCheck.setSelected(onlyProductInSale);
        controller.getProductController().getFilterAndSort().setInSaleOnly(onlyProductInSale);
        saleCheck.setOnMouseClicked(e -> {
            controller.getProductController().getFilterAndSort().setInSaleOnly(saleCheck.isSelected());
            putNewProductsInProductGridPane(productGridPane);
        });


        RangeSlider rangeSlider = new RangeSlider(0, 10000, 0, 10000);
        rangeSlider.setMinWidth(100);
        rangeSlider.setMajorTickUnit(1000);
        rangeSlider.setShowTickLabels(true);
        rangeSlider.setOnMouseReleased(e -> {
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
        availabilityVBox.setPadding(new Insets(10, 10, 10, 10));

        availabilityCheck.setOnMouseReleased(e -> {
            rangeSlider.setDisable(!availabilityCheck.isSelected());
            controller.getProductController().getFilterAndSort().setAvailabilityFilter(availabilityCheck.isSelected());
            putNewProductsInProductGridPane(productGridPane);

        });


        boolean isAvailability = controller.getProductController().getFilterAndSort().getAvailabilityFilter();
        rangeSlider.setDisable(!isAvailability);

        Label priceLabel = new Label("Price");
        priceLabel.setStyle("-fx-font-size: 14");
        priceVBox.getChildren().addAll(priceLabel, rangeSlider);
        priceVBox.setSpacing(10);
        priceVBox.setAlignment(Pos.CENTER);
        priceVBox.setPadding(new Insets(10, 20, 10, 15));

        putNewProductsInProductGridPane(productGridPane);

//        VBox treeViewVBox = new VBox();
        Label treeViewLabel = new Label("Category");
        treeViewLabel.setStyle("-fx-font-size:  14");
//        treeViewVBox.getChildren().add(treeViewLabel);

        TreeView<Label> treeView = new TreeView<>(getTreeItem(controller.getProductController().controlGetAllProductCategory(), controller, productGridPane, numberOfViews, saleCheck, availabilityCheck, rangeSlider, specialFilterVBox));
//        treeViewVBox.getChildren().add(treeView);

        treeView.setPrefHeight(250);
        filterAndSort.getStylesheets().add(new File("src/main/resources/css/Style.css").toURI().toString());

        VBox nameFilter = new VBox();
        nameFilter.setAlignment(Pos.CENTER);
        nameFilter.setSpacing(15);
        nameFilter.setPadding(new Insets(20, 15, 20, 10));

        Label nameAndBrandFilter = new Label("Name and Brand");
        nameFilter.getChildren().add(nameAndBrandFilter);
        nameAndBrandFilter.setStyle("-fx-font-size: 15");


        HBox choosingHBox = new HBox();
        choosingHBox.setSpacing(20);
        choosingHBox.setAlignment(Pos.CENTER);
        ComboBox<String> choseBetweenNameAndBrand = new ComboBox<>();
        choseBetweenNameAndBrand.setPromptText("Choose");
        choseBetweenNameAndBrand.getItems().add("Name");
        choseBetweenNameAndBrand.getItems().add("Brand");
        choosingHBox.getChildren().add(choseBetweenNameAndBrand);

        TextField filterByNameOrBrandTextField = new TextField();
        filterByNameOrBrandTextField.setMaxWidth(80);
        choosingHBox.getChildren().add(filterByNameOrBrandTextField);
        nameFilter.getChildren().add(choosingHBox);


        GridPane listViewsGridPane = new GridPane();
        listViewsGridPane.setVgap(10);
        listViewsGridPane.setHgap(20);
        listViewsGridPane.setAlignment(Pos.CENTER);
        Label filterByNameLabel = new Label("Filter By Name");
        listViewsGridPane.add(filterByNameLabel, 0, 0);
        Label filterByBrandLabel = new Label("Filter By Brand");
        listViewsGridPane.add(filterByBrandLabel, 1, 0);

        ListView<String> filterByNameListView = new ListView<>();
        filterByNameListView.setMaxHeight(100);
        filterByNameListView.setMaxWidth(100);
        listViewsGridPane.add(filterByNameListView, 0, 1);

        ListView<String> filterByBrandListView = new ListView<>();
        filterByBrandListView.setMaxHeight(100);
        filterByBrandListView.setMaxWidth(100);
        listViewsGridPane.add(filterByBrandListView, 1, 1);

        nameFilter.getChildren().add(listViewsGridPane);
        Button filterByNameAddButton = new Button("Add");
        GMenu.addStyleToButton(filterByNameAddButton);
        filterByNameAddButton.setOnMouseClicked(e -> {
            if (!filterByNameOrBrandTextField.getText().equals("")) {
                try {
                    if (choseBetweenNameAndBrand.getSelectionModel().getSelectedItem().equals("Name")) {
                        controller.getProductController().getFilterAndSort().addNameFilter(filterByNameOrBrandTextField.getText());
                        filterByNameListView.getItems().add(filterByNameOrBrandTextField.getText());
                    } else {
                        controller.getProductController().getFilterAndSort().addBrandFilter(filterByNameOrBrandTextField.getText());
                        filterByBrandListView.getItems().add(filterByNameOrBrandTextField.getText());
                    }
                    filterByNameOrBrandTextField.setText("");
                    putNewProductsInProductGridPane(productGridPane);
                } catch (ExceptionalMassage exceptionalMassage) {
                    new AlertBox(this, exceptionalMassage, controller).showAndWait();
                }
            }
        });
        nameFilter.getChildren().add(filterByNameAddButton);

//        Group group = new Group(
//                filterByBrandListView,
//                filterByNameListView
//        );
//
//        nameFilter.getChildren().addAll(filterByNameListView, group, filterByBrandListView);

        Button filterByNameRemoveButton = new Button("Remove");
        GMenu.addStyleToButton(filterByNameRemoveButton);
        filterByNameRemoveButton.setOnMouseClicked(e -> {
            if (choseBetweenNameAndBrand.getSelectionModel().getSelectedItem().equals("Name") &&
                    filterByNameListView.getSelectionModel().getSelectedItems().size() != 0) {
                    String itemWantedToBeRemoved = filterByNameListView.getSelectionModel().getSelectedItems().get(0);
                    try {
                        controller.getProductController().getFilterAndSort().removeNameFilter(itemWantedToBeRemoved);
                        filterByNameListView.getItems().remove(itemWantedToBeRemoved);
                        filterByNameOrBrandTextField.setText("");
                        putNewProductsInProductGridPane(productGridPane);
                    } catch (ExceptionalMassage exceptionalMassage) {
                        new AlertBox(this, exceptionalMassage, controller).showAndWait();
                    }
            } else if(choseBetweenNameAndBrand.getSelectionModel().getSelectedItem().equals("Brand") &&
                    filterByBrandListView.getSelectionModel().getSelectedItems().size() != 0) {
                String itemWantedToBeRemoved = filterByBrandListView.getSelectionModel().getSelectedItems().get(0);
                try {
                    controller.getProductController().getFilterAndSort().removeBrandFilter(itemWantedToBeRemoved);
                    filterByBrandListView.getItems().remove(itemWantedToBeRemoved);
                    filterByNameOrBrandTextField.setText("");
                    putNewProductsInProductGridPane(productGridPane);
                } catch (ExceptionalMassage exceptionalMassage) {
                    new AlertBox(this, exceptionalMassage, controller).showAndWait();
                }
            }
        });
        nameFilter.getChildren().add(filterByNameRemoveButton);

        specialFilterVBox.setPadding(new Insets(20, 20, 20, 20));
        specialFilterVBox.setSpacing(20);
        specialFilterVBox.setAlignment(Pos.CENTER);
        putNewSpecialFilters(specialFilterVBox, productGridPane);
        filterAndSort.getChildren().addAll(sortLabel, sort, filterLabel, availabilityVBox, priceVBox, specialFilterVBox, nameFilter,treeViewLabel,  treeView);
        filterAndSort.setStyle("-fx-background-color : #f8e8e2");


        productGridPane.setVgap(30);
        productGridPane.setHgap(30);

//        productGridPane.add(bottom, 4, 5);

//        productScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
//        productScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        VBox headerBackground = new VBox();
        headerBackground.setStyle("-fx-background-color: #4677c8");
        headerBackground.getChildren().add(createHeader());
        filterAndSortScrollPane.setContent(filterAndSort);
        mainPane.add(filterAndSortScrollPane, 0, 1);
        mainPane.add(productScrollPane, 1, 1);

        backgroundLayout.add(headerBackground, 0, 0);
        backgroundLayout.add(mainPane, 0, 1);
        backgroundLayout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(backgroundLayout);
        return scene;
    }

    private void putNewProductsInProductGridPane(GridPane productGridPane) {
        productGridPane.getChildren().clear();
        ArrayList<Product> products = controller.getProductController().getFilterAndSort().getProducts();
        int row = 0, column = 0;
        GridPane gridPane;

        for (Product product : products) {
            VBox mainVBox = new VBox();
            gridPane = new GridPane();
            mainVBox.setStyle("-fx-background-color: #9cbfe3");

            Label nameLabel = new Label(product.getName());
            nameLabel.setStyle("-fx-font-size: 20");


            ImageView productImageView = GMenu.getImageView(product.getImageUrl(), 200, 200);

            productImageView.setOnMouseClicked(e -> {
                controller.getProductController().controlViewThisProduct(product);
                stage.setScene(new ProductMenuG(this, stage, product, controller).getScene());
            });
            if (Sale.isProductHasAnySale(product)) {
                ImageView soldOutImageView = GMenu.getImageView("./src/main/resources/image/Sale.png", 200, 200);

                soldOutImageView.setBlendMode(BlendMode.SRC_OVER);
                Group blend = new Group(
                        productImageView,
                        soldOutImageView
                );

                gridPane.getChildren().addAll(productImageView, blend, soldOutImageView);
//                gridPane.getChildren().add(soldOutImageView);
            } else if (product.getAllSuppliersThatHaveAvailableProduct().size() == 0) {
                ImageView soldOutImageView = GMenu.getImageView("./src/main/resources/image/soldOut.png", 200, 200);

                soldOutImageView.setBlendMode(BlendMode.SRC_OVER);
                Group blend = new Group(
                        productImageView,
                        soldOutImageView
                );

                gridPane.getChildren().addAll(productImageView, blend, soldOutImageView);
//                gridPane.getChildren().add(soldOutImageView);
            } else {
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
            column++;
            if (column >= 4) {
                column = 0;
                row++;
            }

        }
    }

    public TreeItem<Label> getTreeItem(Category rootCategory, Controller controller, GridPane
            productGridPane, RadioButton numberOfViews, CheckBox saleCheck, CheckBox availabilityCheck, RangeSlider
                                               rangeSlider, VBox specialFilterVBox) {
        Label rootLabel = new Label(rootCategory.getName());
        rootLabel.setStyle("-fx-font-size: 14");

        rootLabel.setOnMouseClicked(e -> {
            controller.getProductController().getFilterAndSort().setCategory(rootCategory);
            resetAllFilters(numberOfViews, saleCheck, availabilityCheck, rangeSlider);
            putNewProductsInProductGridPane(productGridPane);
            putNewSpecialFilters(specialFilterVBox, productGridPane);
        });
        TreeItem<Label> rootTreeItem = new TreeItem<>(rootLabel);
        if (!rootCategory.isCategoryClassifier())
            return rootTreeItem;
        ArrayList<Category> allCategoriesIn = controller.getProductController().controlGetAllCategoriesInACategory(rootCategory);
        for (Category categoryIn : allCategoriesIn) {
            rootTreeItem.getChildren().add(getTreeItem(categoryIn, controller, productGridPane, numberOfViews, saleCheck, availabilityCheck, rangeSlider, specialFilterVBox));
        }
        return rootTreeItem;
    }

    public void resetAllFilters(RadioButton numberOfViews, CheckBox saleCheck, CheckBox
            availabilityCheck, RangeSlider rangeSlider) {
        numberOfViews.setSelected(true);
        saleCheck.setSelected(false);
        availabilityCheck.setSelected(false);
        rangeSlider.setHighValue(10000);
        rangeSlider.setLowValue(0);
        rangeSlider.setDisable(true);
    }

    public void putNewSpecialFilters(VBox specialFiltersVBox, GridPane productGridPane) {
        HashMap<String, ArrayList<String>> specialFilters = controller.getProductController().controlGetAllAvailableFilters();
        specialFiltersVBox.getChildren().clear();
        Label specialFieldsLabel = new Label("Special Fields");
        specialFieldsLabel.setStyle("-fx-font-size: 14");
        specialFiltersVBox.getChildren().add(specialFieldsLabel);

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
