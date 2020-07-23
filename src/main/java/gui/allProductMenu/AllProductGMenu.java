package gui.allProductMenu;

import auction.Auction;
import controller.Controller;
import discount.Sale;
import exceptionalMassage.ExceptionalMassage;
import gui.GMenu;
import gui.alerts.AlertBox;
import gui.productMenu.ProductMenuG;
import gui.profile.AuctionGMenu;
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
        try {
            controller.getProductController().clearFilterAndSort();
        } catch (ExceptionalMassage exceptionalMassage) {
            new AlertBox(this, exceptionalMassage, controller).showAndWait();
        }
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
            try {
                controller.getProductController().controlFilterSetSortType("by number of views");
            } catch (ExceptionalMassage exceptionalMassage) {
                new AlertBox(this, exceptionalMassage, controller).showAndWait();
            }
            putNewProductsInProductGridPane(productGridPane);
        });

        score.setOnMouseClicked(e -> {
            try {
                controller.getProductController().controlFilterSetSortType("by score");
            } catch (ExceptionalMassage exceptionalMassage) {
                new AlertBox(this, exceptionalMassage, controller).showAndWait();
            }
            putNewProductsInProductGridPane(productGridPane);
        });

        time.setOnMouseClicked(e -> {
            try {
                controller.getProductController().controlFilterSetSortType("by time added");
            } catch (ExceptionalMassage exceptionalMassage) {
                new AlertBox(this, exceptionalMassage, controller).showAndWait();
            }
            putNewProductsInProductGridPane(productGridPane);
        });


        CheckBox saleCheck = new CheckBox("Only Products On Sale");
        saleCheck.setSelected(onlyProductInSale);
        try {
            controller.getProductController().controlFilterSetInSaleOnly(onlyProductInSale);
        } catch (ExceptionalMassage exceptionalMassage) {
            new AlertBox(this, exceptionalMassage, controller).showAndWait();
        }
        saleCheck.setOnMouseClicked(e -> {
            try {
                controller.getProductController().controlFilterSetInSaleOnly(saleCheck.isSelected());
            } catch (ExceptionalMassage exceptionalMassage) {
                new AlertBox(this, exceptionalMassage, controller).showAndWait();
            }
            putNewProductsInProductGridPane(productGridPane);
        });


        RangeSlider rangeSlider = new RangeSlider(0, 10000, 0, 10000);
        rangeSlider.setMinWidth(100);
        rangeSlider.setMajorTickUnit(1000);
        rangeSlider.setShowTickLabels(true);
        rangeSlider.setOnMouseReleased(e -> {
            try {
                controller.getProductController().controlFilterSetPriceUpperBound((int) rangeSlider.getHighValue());
            } catch (ExceptionalMassage exceptionalMassage) {
                new AlertBox(this, exceptionalMassage, controller).showAndWait();
            }
            try {
                controller.getProductController().controlFilterSetPriceLowerBound((int) rangeSlider.getLowValue());
            } catch (ExceptionalMassage exceptionalMassage) {
                new AlertBox(this, exceptionalMassage, controller).showAndWait();
            }
            putNewProductsInProductGridPane(productGridPane);
        });

        CheckBox availabilityCheck = new CheckBox("Only Available Products");
        availabilityCheck.setOnMouseReleased(e -> {
            rangeSlider.setDisable(!availabilityCheck.isSelected());
            try {
                controller.getProductController().controlFilterSetAvailabilityFilter(availabilityCheck.isSelected());
            } catch (ExceptionalMassage exceptionalMassage) {
                new AlertBox(this, exceptionalMassage, controller).showAndWait();
            }
            putNewProductsInProductGridPane(productGridPane);

        });

        CheckBox auctionCheckBox = new CheckBox("Only Products In Auction");
        auctionCheckBox.setOnMouseReleased(e -> {
            try {
                controller.getProductController().controlFilterSetOnlyInAuctionFilter(auctionCheckBox.isSelected());
            } catch (ExceptionalMassage exceptionalMassage) {
                new AlertBox(this, exceptionalMassage, controller).showAndWait();
            }
            putNewProductsInProductGridPane(productGridPane);
        });

        availabilityVBox.getChildren().addAll(availabilityCheck, saleCheck, auctionCheckBox);
        availabilityVBox.setSpacing(10);
        availabilityVBox.setPadding(new Insets(10, 10, 10, 10));

        boolean isAvailability = false;
        try {
            isAvailability = controller.getProductController().controlFilterGetAvailabilityFilter();
        } catch (ExceptionalMassage exceptionalMassage) {
            new AlertBox(this, exceptionalMassage, controller).showAndWait();
        }
        rangeSlider.setDisable(!isAvailability);

        Label priceLabel = new Label("Price");
        priceLabel.setStyle("-fx-font-size: 14");
        priceVBox.getChildren().addAll(priceLabel, rangeSlider);
        priceVBox.setSpacing(10);
        priceVBox.setAlignment(Pos.CENTER);
        priceVBox.setPadding(new Insets(10, 20, 10, 15));

        putNewProductsInProductGridPane(productGridPane);

        Label treeViewLabel = new Label("Category");
        treeViewLabel.setStyle("-fx-font-size:  14");

        Category allProductCategory = null;
        TreeView<Label> treeView = null;
        try {
            allProductCategory = controller.getProductController().controlGetAllProductCategory();
            treeView = new TreeView<>(getTreeItem(allProductCategory, controller, productGridPane, numberOfViews, saleCheck, availabilityCheck, rangeSlider, specialFilterVBox));

            treeView.setPrefHeight(250);
            filterAndSort.getStylesheets().add(new File("src/main/resources/cssInsider/Style.css").toURI().toString());
        } catch (ExceptionalMassage exceptionalMassage) {
            new AlertBox(this, exceptionalMassage, controller).showAndWait();
        }

        VBox nameFilter = new VBox();
        nameFilter.setAlignment(Pos.CENTER);
        nameFilter.setSpacing(15);
        nameFilter.setPadding(new Insets(20, 15, 20, 10));

        Label nameAndBrandFilter = new Label("Name,Brand and Company Name");
        nameFilter.getChildren().add(nameAndBrandFilter);
        nameAndBrandFilter.setStyle("-fx-font-size: 15");


        HBox choosingHBox = new HBox();
        choosingHBox.setSpacing(20);
        choosingHBox.setAlignment(Pos.CENTER);
        ComboBox<String> choseBetweenNameAndBrand = new ComboBox<>();
        choseBetweenNameAndBrand.setPromptText("Choose");
        choseBetweenNameAndBrand.getItems().add("Name");
        choseBetweenNameAndBrand.getItems().add("Brand");
        choseBetweenNameAndBrand.getItems().add("Name Of Company");
        choosingHBox.getChildren().add(choseBetweenNameAndBrand);

        TextField filterByNameOrBrandTextField = new TextField();
        filterByNameOrBrandTextField.setMaxWidth(80);
        choosingHBox.getChildren().add(filterByNameOrBrandTextField);
        nameFilter.getChildren().add(choosingHBox);


        GridPane listViewsGridPane = new GridPane();
        listViewsGridPane.setAlignment(Pos.CENTER);
        listViewsGridPane.setVgap(10);
        listViewsGridPane.setHgap(20);
        listViewsGridPane.setAlignment(Pos.CENTER);
        Label filterByNameLabel = new Label("Name");
        listViewsGridPane.add(filterByNameLabel, 0, 0);
        Label filterByBrandLabel = new Label("Brand");
        listViewsGridPane.add(filterByBrandLabel, 1, 0);
        Label filterByNameOfCompany = new Label("Name Of Company");
        listViewsGridPane.add(filterByNameOfCompany, 0, 2);

        ListView<String> filterByNameListView = new ListView<>();
        filterByNameListView.setMaxHeight(100);
        filterByNameListView.setMaxWidth(100);
        listViewsGridPane.add(filterByNameListView, 0, 1);

        ListView<String> filterByBrandListView = new ListView<>();
        filterByBrandListView.setMaxHeight(100);
        filterByBrandListView.setMaxWidth(100);
        listViewsGridPane.add(filterByBrandListView, 1, 1);

        ListView<String> filterByNameOfCompanyListView = new ListView<>();
        filterByNameOfCompanyListView.setMaxHeight(100);
        filterByNameOfCompanyListView.setMaxWidth(100);
        listViewsGridPane.add(filterByNameOfCompanyListView, 0, 3);

        nameFilter.getChildren().add(listViewsGridPane);

        VBox buttonVBox = new VBox();

        Button filterByNameAddButton = new Button("Add");
        GMenu.addStyleToSmallButton(filterByNameAddButton);
        filterByNameAddButton.setOnMouseClicked(e -> {
            if (!filterByNameOrBrandTextField.getText().equals("") && choseBetweenNameAndBrand.getSelectionModel().getSelectedItem() != null) {
                try {
                    if (choseBetweenNameAndBrand.getSelectionModel().getSelectedItem().equals("Name")) {
                        controller.getProductController().controlFilterAddNameFilter(filterByNameOrBrandTextField.getText());
                        filterByNameListView.getItems().add(filterByNameOrBrandTextField.getText());
                    } else if (choseBetweenNameAndBrand.getSelectionModel().getSelectedItem().equals("Brand")) {
                        controller.getProductController().controlFilterAddBrandFilter(filterByNameOrBrandTextField.getText());
                        filterByBrandListView.getItems().add(filterByNameOrBrandTextField.getText());
                    } else {
                        controller.getProductController().controlFilterAddSupplierFilter(filterByNameOrBrandTextField.getText());
                        filterByNameOfCompanyListView.getItems().add(filterByNameOrBrandTextField.getText());
                    }
                    filterByNameOrBrandTextField.setText("");
                    putNewProductsInProductGridPane(productGridPane);
                } catch (ExceptionalMassage exceptionalMassage) {
                    new AlertBox(this, exceptionalMassage, controller).showAndWait();
                }
            }
        });
        buttonVBox.getChildren().add(filterByNameAddButton);

        Button filterByNameRemoveButton = new Button("Remove");
        GMenu.addStyleToSmallButton(filterByNameRemoveButton);
        filterByNameRemoveButton.setOnMouseClicked(e -> {
            if (choseBetweenNameAndBrand.getSelectionModel().getSelectedItem() != null) {
                if (choseBetweenNameAndBrand.getSelectionModel().getSelectedItem().equals("Name") &&
                        filterByNameListView.getSelectionModel().getSelectedItems().size() != 0) {
                    String itemWantedToBeRemoved = filterByNameListView.getSelectionModel().getSelectedItems().get(0);
                    try {
                        controller.getProductController().controlFilterRemoveNameFilter(itemWantedToBeRemoved);
                        filterByNameListView.getItems().remove(itemWantedToBeRemoved);
                        filterByNameOrBrandTextField.setText("");
                        putNewProductsInProductGridPane(productGridPane);
                    } catch (ExceptionalMassage exceptionalMassage) {
                        new AlertBox(this, exceptionalMassage, controller).showAndWait();
                    }
                } else if (choseBetweenNameAndBrand.getSelectionModel().getSelectedItem().equals("Brand") &&
                        filterByBrandListView.getSelectionModel().getSelectedItems().size() != 0) {
                    String itemWantedToBeRemoved = filterByBrandListView.getSelectionModel().getSelectedItems().get(0);
                    try {
                        controller.getProductController().controlFilterRemoveBrandFilter(itemWantedToBeRemoved);
                        filterByBrandListView.getItems().remove(itemWantedToBeRemoved);
                        filterByNameOrBrandTextField.setText("");
                        putNewProductsInProductGridPane(productGridPane);
                    } catch (ExceptionalMassage exceptionalMassage) {
                        new AlertBox(this, exceptionalMassage, controller).showAndWait();
                    }
                } else if (choseBetweenNameAndBrand.getSelectionModel().getSelectedItem().equals("Name Of Company") &&
                        filterByNameOfCompanyListView.getSelectionModel().getSelectedItems().size() != 0) {
                    String itemWantedToBeRemoved = filterByNameOfCompanyListView.getSelectionModel().getSelectedItems().get(0);
                    try {
                        controller.getProductController().controlFilterRemoveSupplierFilter(itemWantedToBeRemoved);
                        filterByNameOfCompanyListView.getItems().remove(itemWantedToBeRemoved);
                        filterByNameOrBrandTextField.setText("");
                        putNewProductsInProductGridPane(productGridPane);
                    } catch (ExceptionalMassage exceptionalMassage) {
                        new AlertBox(this, exceptionalMassage, controller).showAndWait();
                    }
                }
                putNewProductsInProductGridPane(productGridPane);
            }
        });
        buttonVBox.getChildren().add(filterByNameRemoveButton);

        buttonVBox.setAlignment(Pos.CENTER);
        buttonVBox.setSpacing(15);
        listViewsGridPane.add(buttonVBox, 1, 3);

        specialFilterVBox.setPadding(new Insets(20, 20, 20, 20));
        specialFilterVBox.setSpacing(20);
        specialFilterVBox.setAlignment(Pos.CENTER);
        putNewSpecialFilters(specialFilterVBox, productGridPane);
        filterAndSort.getChildren().addAll(sortLabel, sort, filterLabel, availabilityVBox, priceVBox, specialFilterVBox, nameFilter, treeViewLabel, treeView);
        filterAndSort.setStyle("-fx-background-color : #f8e8e2");


        productGridPane.setVgap(30);
        productGridPane.setHgap(30);

//        productGridPane.add(bottom, 4, 5);

//        productScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
//        productScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        VBox headerBackground = new VBox();
        headerBackground.setStyle("-fx-background-color: #4677c8");
        headerBackground.getChildren().add(createHeader());
        filterAndSortScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
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
        ArrayList<Product> products = null;
        try {
            products = controller.getProductController().controlFilterGetFilteredAndSortedProducts();
        } catch (ExceptionalMassage exceptionalMassage) {
            new AlertBox(this, exceptionalMassage, controller).showAndWait();
            return;
        }
        int row = 0, column = 0;
        GridPane gridPane;

        for (Product product : products) {
            VBox mainVBox = new VBox();
            gridPane = new GridPane();
            mainVBox.setStyle("-fx-background-color: #9cbfe3");

            Auction auction = null;
            try {
                auction = controller.getProductController().controlGetAuctionForProduct(product);
            } catch (ExceptionalMassage exceptionalMassage) {
                new AlertBox(this, exceptionalMassage, controller).showAndWait();
            }
            Auction tempAuction = auction;

            Label nameLabel = new Label(product.getName());
            nameLabel.setStyle("-fx-font-size: 20");


            ImageView productImageView = GMenu.getImageViewFromImage(product.getImage(), 200, 200);

            productImageView.setOnMouseClicked(e -> {
                try {
                    controller.getProductController().controlViewThisProduct(product);
                } catch (ExceptionalMassage exceptionalMassage) {
                    new AlertBox(this, exceptionalMassage, controller).showAndWait();
                }
                if (tempAuction == null) {
                    stage.setScene(new ProductMenuG(this, stage, product, controller).getScene());
                } else {
                    stage.setScene(new AuctionGMenu(this, stage, controller, tempAuction).getScene());
                }
            });
            boolean doesProductHaveAnySale = false;
            boolean isSizeOfSuppliersZero = false;
            boolean isInAuction = false;
            boolean isFile = !(product.getFilePath().equals(""));
            if (auction != null)
                isInAuction = true;
            try {
                doesProductHaveAnySale = controller.getOffController().isProductHasAnySale(product);
                isSizeOfSuppliersZero = controller.getProductController().getAllSuppliersThatHaveAvailableProduct(product).size() == 0;
            } catch (ExceptionalMassage exceptionalMassage) {
                exceptionalMassage.printStackTrace();
            }
            if (isInAuction) {
                ImageView AuctionImageView = GMenu.getImageView("./src/main/resources/image/auction.png", 200, 200);

                AuctionImageView.setBlendMode(BlendMode.SRC_OVER);
                Group blend = new Group(
                        productImageView,
                        AuctionImageView
                );

                gridPane.getChildren().addAll(productImageView, blend, AuctionImageView);
            } else if (doesProductHaveAnySale) {
                ImageView soldOutImageView = GMenu.getImageView("./src/main/resources/image/Sale.png", 200, 200);

                soldOutImageView.setBlendMode(BlendMode.SRC_OVER);
                Group blend = new Group(
                        productImageView,
                        soldOutImageView
                );

                gridPane.getChildren().addAll(productImageView, blend, soldOutImageView);
//                gridPane.getChildren().add(soldOutImageView);
            }  else if (isSizeOfSuppliersZero) {
                ImageView soldOutImageView = GMenu.getImageView("./src/main/resources/image/soldOut.png", 200, 200);

                soldOutImageView.setBlendMode(BlendMode.SRC_OVER);
                Group blend = new Group(
                        productImageView,
                        soldOutImageView
                );

                gridPane.getChildren().addAll(productImageView, blend, soldOutImageView);
//                gridPane.getChildren().add(soldOutImageView);
            } else if (isFile) {
                ImageView AuctionImageView = GMenu.getImageView("./src/main/resources/image/file.png", 200, 200);

                AuctionImageView.setBlendMode(BlendMode.SRC_OVER);
                Group blend = new Group(
                        productImageView,
                        AuctionImageView
                );

                gridPane.getChildren().addAll(productImageView, blend, AuctionImageView);
            } else {
                gridPane.getChildren().add(productImageView);
            }

            Rating starRating = new Rating();
            starRating.setMax(5);
            try {
                starRating.setRating(controller.getProductController().controlGetAverageScoreByProduct(product));
            } catch (ExceptionalMassage exceptionalMassage) {
                new AlertBox(this, exceptionalMassage, controller).showAndWait();
            }
            starRating.setEffect(new DropShadow());
            starRating.setPartialRating(true);
            starRating.setFocusTraversable(false);
            starRating.setMouseTransparent(true);

            Label priceLabel = null;
            if (!product.isProductAvailableNow()) {
                priceLabel = new Label("Not Available");
            } else {
                if (auction == null) {
                    priceLabel = new Label(String.valueOf(product.getMinimumPrice()) + "$");
                    priceLabel.setStyle("-fx-font-size: 18");
                } else {
                    priceLabel = new Label("Highest Promotion:" + String.valueOf(auction.getHighestPromotion()) + "$");
                    priceLabel.setStyle("-fx-font-size: 14");
                }


            }
            Sale sale = null;
            try {
                sale = controller.getOffController().controlGetMaxSaleForThisProduct(product);
            } catch (ExceptionalMassage exceptionalMassage) {
                new AlertBox(this, exceptionalMassage, controller).showAndWait();
            }

            VBox saleVBox = new VBox();
            saleVBox.setAlignment(Pos.CENTER);
            if (sale != null) {
                Label percentLabel = new Label(sale.getPercent() + "%");
                percentLabel.setStyle("-fx-font-size: 13");
                Label fromLabel = new Label("from" + String.valueOf(sale.getStart()));
                fromLabel.setStyle("-fx-font-size: 13");
                Label toLabel = new Label("to   " + String.valueOf(sale.getEnd()));
                toLabel.setStyle("-fx-font-size: 13");
                saleVBox.getChildren().addAll(percentLabel, fromLabel, toLabel);
            } else {
                Label noSale = new Label("No Sale \n\n");
                noSale.setStyle("-fx-font-size: 13");
                saleVBox.getChildren().add(noSale);
            }

            saleVBox.setPrefHeight(70);

            mainVBox.getChildren().add(gridPane);
            mainVBox.getChildren().add(nameLabel);
            mainVBox.getChildren().add(priceLabel);
            mainVBox.getChildren().add(starRating);
            mainVBox.getChildren().add(saleVBox);

            mainVBox.setAlignment(Pos.CENTER);

            productGridPane.add(mainVBox, column, row);
            column++;
            if (column >= 4) {
                column = 0;
                row++;
            }

        }
        if (products.size() == 0) {
            Label tryAgainLabel = new Label("No Product Found! Try Another Filter\t\t\t\t\t");
            tryAgainLabel.setStyle("-fx-font-size: 20");
            productGridPane.add(tryAgainLabel, 7, 10);
            productGridPane.setAlignment(Pos.CENTER);
        }
    }

    public TreeItem<Label> getTreeItem(Category rootCategory, Controller controller, GridPane
            productGridPane, RadioButton numberOfViews, CheckBox saleCheck, CheckBox availabilityCheck, RangeSlider
                                               rangeSlider, VBox specialFilterVBox) {
        Label rootLabel = new Label(rootCategory.getName());
        rootLabel.setStyle("-fx-font-size: 14");

        rootLabel.setOnMouseClicked(e -> {
            try {
                controller.getProductController().controlFilterSetCategoryFilter(rootCategory.getName());
            } catch (ExceptionalMassage exceptionalMassage) {
                new AlertBox(this, exceptionalMassage, controller).showAndWait();
            }
            resetAllFilters(numberOfViews, saleCheck, availabilityCheck, rangeSlider);
            putNewProductsInProductGridPane(productGridPane);
            putNewSpecialFilters(specialFilterVBox, productGridPane);
        });
        TreeItem<Label> rootTreeItem = new TreeItem<>(rootLabel);
        if (!rootCategory.isCategoryClassifier())
            return rootTreeItem;
        ArrayList<Category> allCategoriesIn = null;
        try {
            allCategoriesIn = controller.getProductController().controlGetAllCategoriesInACategory(rootCategory);
        } catch (ExceptionalMassage exceptionalMassage) {
            new AlertBox(this, exceptionalMassage, controller).showAndWait();
        }
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
        HashMap<String, ArrayList<String>> specialFilters = null;
        try {
            specialFilters = controller.getProductController().controlGetAllAvailableFilters();
        } catch (ExceptionalMassage exceptionalMassage) {
            new AlertBox(this, exceptionalMassage, controller).showAndWait();
        }
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

                    try {
                        controller.getProductController().controlFilterRemoveAllSpecialFilter();
                    } catch (ExceptionalMassage exceptionalMassage) {
                        exceptionalMassage.printStackTrace();
                    }

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
