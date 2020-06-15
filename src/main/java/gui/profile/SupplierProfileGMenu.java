package gui.profile;

import controller.Controller;
import gui.GMenu;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SupplierProfileGMenu extends GMenu {
    public SupplierProfileGMenu(GMenu parentMenu, Stage stage, Controller controller) {
        super("Profile, Supplier", parentMenu, stage, controller);
    }

    @Override
    protected Scene createScene() {
        return null;
    }
}
