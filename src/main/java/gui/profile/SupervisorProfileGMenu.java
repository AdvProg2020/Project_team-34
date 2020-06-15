package gui.profile;

import controller.Controller;
import gui.GMenu;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SupervisorProfileGMenu extends GMenu {
    public SupervisorProfileGMenu(GMenu parentMenu, Stage stage, Controller controller) {
        super("Profile, Supervisor", parentMenu, stage, controller);
    }

    @Override
    protected Scene createScene() {
        return null;
    }
}
