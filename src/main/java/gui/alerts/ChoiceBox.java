package gui.alerts;

import gui.GMenu;
import javafx.scene.Scene;
import javafx.stage.Stage;
import menu.menuAbstract.Menu;

public class ChoiceBox extends GMenu {
    public ChoiceBox(String menuName, GMenu parentMenu, Stage stage) {
        super(menuName, parentMenu, stage);
    }

    @Override
    protected Scene createScene() {
        return null;
    }
}
