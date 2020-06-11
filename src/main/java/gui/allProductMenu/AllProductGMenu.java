package gui.allProductMenu;

import gui.GMenu;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AllProductGMenu extends GMenu {

    private final VBox filterAndSort;
    private final VBox sort;


    public AllProductGMenu(String menuName, GMenu parentMenu, Stage stage) {
        super(menuName, parentMenu, stage);
        sort = new VBox();
        filterAndSort = new VBox();
    }

    @Override
    protected Scene createScene() {

           Label label = new Label("hi");
           sort.getChildren().add(label);
        filterAndSort.getChildren().add(sort);
        Scene scene = new Scene(filterAndSort);
        return scene;
    }
}
