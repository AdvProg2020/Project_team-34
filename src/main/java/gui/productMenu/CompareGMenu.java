package gui.productMenu;

import controller.Controller;
import gui.GMenu;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import product.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Aryan Ahadinia
 * @version 0.0.2
 */

public class CompareGMenu extends GMenu {
    private final Product product1;
    private final Product product2;
    private final HashMap<String, String> product1Specifications;
    private final HashMap<String, String> product2Specifications;

    public CompareGMenu(String menuName, GMenu parentMenu, Stage stage, Controller controller, Product product1, Product product2) {
        super(menuName, parentMenu, stage, controller);
        this.product1 = product1;
        this.product2 = product2;
        this.product1Specifications = product1.getSpecification();
        this.product2Specifications = product2.getSpecification();
    }

    @Override
    protected Scene createScene() {
        GridPane compareTable = new GridPane();
        VBox mainLayout = new VBox();
        ScrollPane backgroundLayout = new ScrollPane();
        Scene scene = new Scene(backgroundLayout);

        compareTable.setGridLinesVisible(true);
        compareTable.setAlignment(Pos.CENTER);

        compareTable.add(new Label("Product name"), 0, 0);
        compareTable.add(new Label(product1.getName()), 1, 0);
        compareTable.add(new Label(product2.getName()), 2, 0);

        int row = 1;

        //Set<String> keys = product1Specifications.keySet();
        //keys.addAll(product2Specifications.keySet());
        ArrayList<String> keysArray = new ArrayList<>();
        keysArray.addAll(product1Specifications.keySet());
        keysArray.addAll(product2Specifications.keySet());
        HashSet<String> keys = new HashSet<>(keysArray);


        for (String key : keys) {
            compareTable.add(new Label(key), 0, row);
            compareTable.add(new Label(product1Specifications.getOrDefault(key, "NA")), 1, row);
            compareTable.add(new Label(product2Specifications.getOrDefault(key, "NA")), 2, row);
            row++;
        }

        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.getChildren().addAll(createHeader(), compareTable);

//        backgroundLayout.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
//        backgroundLayout.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        backgroundLayout.setContent(mainLayout);

        return scene;
    }
}