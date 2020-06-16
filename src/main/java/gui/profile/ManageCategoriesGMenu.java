package gui.profile;

import controller.Controller;
import exceptionalMassage.ExceptionalMassage;
import gui.GMenu;
import gui.alerts.AlertBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;

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
        Button done = new Button("Add");

        VBox allCategoriesBox = new VBox();

        VBox mainLayout = new VBox();
        GridPane backLayout = new GridPane();

        Scene scene = new Scene(backLayout);

        nameField.setPromptText("Category Name");

        done.setOnAction(e -> {
            try {
                controller.getProductController().controlAddCategory(nameField.getText(), isClassifier.isSelected(), parentCategory.getValue());
            } catch (ExceptionalMassage exceptionalMassage) {
                new AlertBox(this, exceptionalMassage, controller).showAndWait();
            }
            stage.setScene(getScene());
        });

        createCategoryBox.setSpacing(20);
        createCategoryBox.setPadding(new Insets(10, 10, 10, 10));
        createCategoryBox.getChildren().addAll(nameField, isClassifier, parentCategory, done);
        createCategoryBox.setAlignment(Pos.CENTER_LEFT);

        ArrayList<String> allCategoriesName = controller.getProductController().controlGetAllCategoriesName();
        for (String name : allCategoriesName) {
            allCategoriesBox.getChildren().add(createCategoryBox(name,
                    controller.getProductController().controlGetCategoryParentName(name),
                    controller.getProductController().isThisCategoryClassifier(name)));
        }
        allCategoriesBox.setSpacing(10);
        allCategoriesBox.setPadding(new Insets(10, 10, 10, 10));

        mainLayout.getChildren().addAll(createHeader(), createCategoryBox, allCategoriesBox);
        backLayout.getChildren().addAll(mainLayout);
        backLayout.setAlignment(Pos.CENTER);

        return scene;
    }

    private ChoiceBox<String> allCategoriesChoiceBox() {
        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        choiceBox.getItems().addAll(controller.getProductController().controlGetAllCategoriesName());
        choiceBox.setValue("All Products");
        return choiceBox;
    }

    private HBox createCategoryBox(String categoryName, String parentName, boolean isCategoryClassifier) {
        HBox box = new HBox();
        Label label = new Label("Category: " + categoryName + "\t\tParent: " + parentName + "\t\t" +
                (isCategoryClassifier ? "Category Classifier" : "Product Classifier") + "\t\t");
        Button remove = new Button("Remove");
        Button edit = new Button("Edit");

        remove.setOnAction(e -> {
            try {
                controller.getProductController().controlRemoveCategory(categoryName);
            } catch (ExceptionalMassage exceptionalMassage) {
                new AlertBox(this, exceptionalMassage, controller).showAndWait();
            }
            stage.setScene(createScene());
        });

        edit.setOnAction(e -> {

        });

        box.setStyle("-fx-border-width: 2");
        box.setStyle("-fx-border-color: #4678c8");
        box.setPadding(new Insets(5, 5, 5, 5));
        box.setSpacing(20);
        box.setPrefWidth(600);
        box.setAlignment(Pos.CENTER_LEFT);

        Region spacer = new Region();
        spacer.getStyleClass().add("h-box");
        HBox.setHgrow(spacer, Priority.SOMETIMES);

        if (categoryName.equals("All Products")) {
            box.getChildren().addAll(label);
        } else {
            box.getChildren().addAll(label, spacer, remove, edit);
        }

        return box;
    }
}
