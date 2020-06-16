package gui.profile;

import controller.Controller;
import gui.GMenu;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;

public class EditCategoryGMenu extends GMenu {
    private final String editingCatName;
    private final HashMap<String, ArrayList<String>> fields;

    public EditCategoryGMenu(GMenu parentMenu, Stage stage, Controller controller, String editingCatName) {
        super("Edit Category", parentMenu, stage, controller);
        this.editingCatName = editingCatName;
        this.fields = controller.getProductController().controlGetCategorySpecialFields(editingCatName);
    }

    @Override
    protected Scene createScene() {
        Label nameLabel = new Label("Name");
        TextField nameField = new TextField(new String(editingCatName));
        Button changeNameButton = new Button("Change name");
        HBox editNameBox = new HBox();
        VBox mainLayout = new VBox();
        GridPane backgroundLayout = new GridPane();
        Scene scene = new Scene(backgroundLayout);

        HBox addRemoveFieldBox = new HBox();
        TextField key = new TextField();
        TextField value = new TextField();
        Button add = new Button("Add");
        Button remove = new Button("Remove");

        nameField.setOnKeyTyped(e -> {
            changeNameButton.setDisable(nameField.getText().equals(editingCatName));
        });

        changeNameButton.setDisable(true);
        changeNameButton.setOnAction(e -> {

        });

        editNameBox.setSpacing(20);
        editNameBox.setPadding(new Insets(5, 5, 5, 5));

        mainLayout.getChildren().addAll(createHeader(), editNameBox, addRemoveFieldBox, categoryFields());

        backgroundLayout.setAlignment(Pos.CENTER);
        backgroundLayout.getChildren().add(mainLayout);

        return scene;
    }

    private VBox categoryFields() {
        VBox fieldsBox = new VBox();
        fieldsBox.setSpacing(10);
        fieldsBox.setPadding(new Insets(10, 10, 10, 10));
        for (String key : fields.keySet()) {
            fieldsBox.getChildren().add(categoryField(key));
        }
        return fieldsBox;
    }

    private HBox categoryField(String key) {
        HBox fieldBox = new HBox();
        Label keyLabel = new Label(key);
        Label valuesLabel = new Label(fields.get(key).toString());

        fieldBox.setPadding(new Insets(5, 5, 5, 5));
        fieldBox.setSpacing(20);
        fieldBox.setStyle("-fx-border-width: 2");
        fieldBox.setStyle("-fx-border-radius: 15");
        fieldBox.setStyle("-fx-border-color: #4678c8");
        fieldBox.getChildren().addAll(keyLabel, valuesLabel);

        return fieldBox;
    }
}
