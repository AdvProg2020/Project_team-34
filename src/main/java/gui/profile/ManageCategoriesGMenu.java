package gui.profile;

import controller.Controller;
import gui.GMenu;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class ManageCategoriesGMenu extends GMenu {
    public ManageCategoriesGMenu(GMenu parentMenu, Stage stage, Controller controller) {
        super("Manage Categories", parentMenu, stage, controller);
    }

    @Override
    protected Scene createScene() {
        HBox createCategoryBox = new HBox();
        TextField nameField = new TextField();
        CheckBox isClassifier = new CheckBox("create as category classifier");
        ChoiceBox<String> parentCategory = allCategoriesChoiceBox();
        Button done = new Button("Done");

        Scene scene = new Scene(null);

        nameField.setOnKeyTyped(e -> {

        });

        nameField.setPromptText("Category Name");

        done.setOnAction(e -> {

        });

        createCategoryBox.setSpacing(20);
        createCategoryBox.setPadding(new Insets(10, 10, 10, 10));
        createCategoryBox.getChildren().addAll(nameField, isClassifier, parentCategory, done);

        return scene;
    }

    private ChoiceBox<String> allCategoriesChoiceBox() {
        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        choiceBox.getItems().addAll(controller.getProductController().controlGetAllCategoriesName());
        return choiceBox;
    }

    private HBox createCategoryBox(String categoryName, String parentName, boolean isCategoryClassifier) {
        HBox box = new HBox();
        Label label = new Label("Category: " + categoryName + "\t\tParent: " + parentName + "\t\t" +
                (isCategoryClassifier ? "Category Classifier" : "Product Classifier"));
        Button remove = new Button("Remove");
        Button edit = new Button("Edit");

        remove.setOnAction(e -> {

        });

        edit.setOnAction(e -> {

        });

        box.setStyle("-fx-border-width: 2");
        box.setStyle("-fx-border-radius: 15");
        box.setStyle("-fx-border-color: #4678c8");
        box.setPadding(new Insets(5, 5, 5, 5));
        return box;
    }
}
