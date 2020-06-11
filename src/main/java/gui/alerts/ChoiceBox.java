package gui.alerts;

import gui.GMenu;
import javafx.scene.Scene;
import menu.menuAbstract.Menu;

public class ChoiceBox extends GMenu {
    public ChoiceBox(String menuName, Menu parentMenu) {
        super(menuName, parentMenu);
    }

    @Override
    protected Scene createScene() {
        return null;
    }
}
