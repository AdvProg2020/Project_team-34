package gui.loginMenu;

import controller.Controller;
import gui.GMenu;
import gui.mainMenu.MainMenuG;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class FirstSupervisorMenu extends GMenu {
    public FirstSupervisorMenu(GMenu parentMenu, Stage stage, Controller controller) {
        super("Initializing", parentMenu, stage, controller);
    }

    @Override
    protected Scene createScene() {
        GridPane backgroundLayout = new GridPane();
        backgroundLayout.setAlignment(Pos.CENTER);
        backgroundLayout.setPadding(new Insets(10, 10, 10, 10));
        backgroundLayout.getChildren().add(createSupervisorBox(new MainMenuG(null, stage, controller), false));
        Scene scene = new Scene(backgroundLayout);
        return scene;
    }
}
