package gui.allProductMenu;

import gui.GMenu;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import menu.menuAbstract.Menu;

public class AllProductGMenu extends GMenu {

    private final VBox filterAndSort;


    public AllProductGMenu(String menuName, Menu parentMenu) {
        super(menuName, parentMenu);
        filterAndSort = new VBox();
    }

    @Override
    protected Scene createScene() {
        Scene scene = new Scene(filterAndSort);
        return scene;
    }
}
