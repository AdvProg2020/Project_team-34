package gui.profile;

import gui.GMenu;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AbstractProfileGMenu extends GMenu {


    public AbstractProfileGMenu(String menuName, GMenu parentMenu, Stage stage) {
        super(menuName, parentMenu, stage);
    }

    @Override
    protected Scene createScene() {
        return null;
    }
}
