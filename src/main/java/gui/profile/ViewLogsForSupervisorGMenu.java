package gui.profile;

import gui.GMenu;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ViewLogsForSupervisorGMenu extends GMenu {
    public ViewLogsForSupervisorGMenu(String menuName, GMenu parentMenu, Stage stage) {
        super(menuName, parentMenu, stage);
    }

    @Override
    protected Scene createScene() {
        VBox logsBox = new VBox();
        for (String log : controller.getAccountController().getSupervisorLogs()) {
            logsBox.getChildren().add(SupervisorLogBox(log));
        }
        return createLogScene(logsBox);
    }

    private VBox SupervisorLogBox(String supervisorLog) {
        VBox vBox = getLogBox();
        Text log = new Text(supervisorLog);
        vBox.getChildren().add(log);
        vBox.setPrefWidth(600);
        vBox.setPadding(new Insets(10, 10, 10, 10));
        return vBox;
    }
}
