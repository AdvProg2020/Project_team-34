package gui.alerts;

import gui.GMenu;
import javafx.scene.Scene;
import menu.menuAbstract.Menu;

public class AlertBox extends GMenu {

    public AlertBox(String menuName, Menu parentMenu) {
        super(menuName, parentMenu);
    }

    @Override
    protected Scene createScene() {
        return null;
    }
}
