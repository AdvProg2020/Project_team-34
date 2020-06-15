package gui.profile;

import gui.GMenu;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class EditCategoryGMenu extends GMenu {
    public EditCategoryGMenu(GMenu parentMenu, Stage stage) {
        super("Edit Category", parentMenu, stage);
    }

    @Override
    protected Scene createScene() {
        return null;
    }
}
