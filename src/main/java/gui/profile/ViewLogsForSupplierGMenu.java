package gui.profile;

import controller.Controller;
import gui.GMenu;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ViewLogsForSupplierGMenu extends GMenu {
    public ViewLogsForSupplierGMenu(GMenu parentMenu, Stage stage, Controller controller) {
        super("Logs, Supplier", parentMenu, stage, controller);
    }

    @Override
    protected Scene createScene() {
        VBox logsBox = new VBox();
        for (String log : controller.getAccountController().getSupplierLogs()) {
            logsBox.getChildren().add(SupplierLogBox(log));
        }
        return createLogScene(logsBox);
    }

    private VBox SupplierLogBox(String supplierLog) {
        VBox vBox = getLogBox();
        Text log = new Text(supplierLog);
        vBox.getChildren().add(log);
        vBox.setPrefWidth(600);
        vBox.setPadding(new Insets(10, 10, 10, 10));
        return vBox;
    }
}