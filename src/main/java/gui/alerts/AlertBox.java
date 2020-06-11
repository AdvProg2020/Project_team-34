package gui.alerts;

import gui.GMenu;
import javafx.scene.Scene;
import javafx.stage.Stage;
import menu.menuAbstract.Menu;

public class AlertBox extends GMenu {

    public AlertBox(String menuName, GMenu parentMenu, Stage stage) {
        super(menuName, parentMenu, stage);
    }

    @Override
    protected Scene createScene() {
        return null;
    }
}
