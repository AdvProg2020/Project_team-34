package gui.profile;

import controller.Controller;
import gui.GMenu;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class EditCategoryGMenu extends GMenu {
    public EditCategoryGMenu(GMenu parentMenu, Stage stage, Controller controller) {
        super("Edit Category", parentMenu, stage, controller);
    }

    @Override
    protected Scene createScene() {
        return null;
    }
}
