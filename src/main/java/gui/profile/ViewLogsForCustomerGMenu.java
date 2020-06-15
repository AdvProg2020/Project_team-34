package gui.profile;

import controller.Controller;
import gui.GMenu;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ViewLogsForCustomerGMenu extends GMenu {
    public ViewLogsForCustomerGMenu(GMenu parentMenu, Stage stage, Controller controller) {
        super("Logs, Customer", parentMenu, stage, controller);
    }

    @Override
    protected Scene createScene() {
        VBox logsBox = new VBox();
        for (String log : controller.getAccountController().getCustomerLogs()) {
            logsBox.getChildren().add(customerLogBox(log));
        }
        return createLogScene(logsBox);
    }

    private VBox customerLogBox(String customerLog) {
        VBox vBox = getLogBox();
        Text log = new Text(customerLog);
        vBox.getChildren().add(log);
        vBox.setPrefWidth(600);
        vBox.setPadding(new Insets(10, 10, 10, 10));
        return vBox;
    }
}
