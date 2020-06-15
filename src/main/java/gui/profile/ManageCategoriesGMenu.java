package gui.profile;

import controller.Controller;
import gui.GMenu;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ManageCategoriesGMenu extends GMenu {
    public ManageCategoriesGMenu(GMenu parentMenu, Stage stage, Controller controller) {
        super("Manage Categories", parentMenu, stage, controller);
    }

    @Override
    protected Scene createScene() {
        return null;
    }
}
