package gui.productMenu;

import gui.GMenu;
import javafx.scene.Scene;
import menu.menuAbstract.Menu;

public class ProductMenuG extends GMenu {
    public ProductMenuG(String menuName, Menu parentMenu) {
        super(menuName, parentMenu);
    }

    @Override
    protected Scene createScene() {
        return null;
    }
}
