package gui.profile;

import gui.GMenu;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import log.CustomerLog;
import log.SupplierLog;

public class ViewLogsForSupervisorGMenu extends GMenu {
    public ViewLogsForSupervisorGMenu(String menuName, GMenu parentMenu, Stage stage) {
        super(menuName, parentMenu, stage);
    }

    @Override
    protected Scene createScene() {
        return null;
    }

    public VBox SupervisorLogBox(String supplierLogID) {
        VBox vBox = getLogBox();
        vBox.getChildren().add(new Text(SupplierLog.getSupplierLogById(supplierLogID).toString()));
        return vBox;
    }
}
