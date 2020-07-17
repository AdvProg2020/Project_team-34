package gui.profile;

import controller.Controller;
import exceptionalMassage.ExceptionalMassage;
import gui.GMenu;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * @author Aryan Ahadinia
 * @since 0.0.2
 */

public class ViewLogsForCustomerGMenu extends GMenu {
    public ViewLogsForCustomerGMenu(GMenu parentMenu, Stage stage, Controller controller) {
        super("Logs, Customer", parentMenu, stage, controller);
    }

    @Override
    protected Scene createScene() {
        try {
            VBox logsBox = new VBox();
            for (String log : controller.getAccountController().getCustomerLogs()) {
                logsBox.getChildren().add(customerLogBox(log));
            }
            logsBox.setMaxHeight(720);
            logsBox.setPadding(new Insets(10, 10, 10, 10));
            return createLogScene(logsBox);
        } catch (ExceptionalMassage exceptionalMassage) {
            return new Scene(new Pane());
        }
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
