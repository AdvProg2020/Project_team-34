package gui.profile;

import gui.GMenu;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ManageCategoriesGMenu extends GMenu {
    public ManageCategoriesGMenu(String menuName, GMenu parentMenu, Stage stage) {
        super("Manage Categories", parentMenu, stage);
    }

    @Override
    protected Scene createScene() {
        return null;
    }
}
