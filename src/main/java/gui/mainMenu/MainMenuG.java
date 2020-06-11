package gui.mainMenu;

import gui.GMenu;
import javafx.scene.Scene;
import menu.menuAbstract.Menu;

public class MainMenuG extends GMenu {
    public MainMenuG(String menuName, Menu parentMenu) {
        super(menuName, parentMenu);
    }

    @Override
    protected Scene createScene() {
        return null;
    }
}
