package gui.profile;

import gui.GMenu;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import log.CustomerLog;

public class ViewLogsForCustomerGMenu extends GMenu {
    public ViewLogsForCustomerGMenu(String menuName, GMenu parentMenu, Stage stage) {
        super(menuName, parentMenu, stage);
    }

    @Override
    protected Scene createScene() {
        return null;
    }

    public VBox customerLogBox(String customerLogID) {
        VBox vBox = getLogBox();
        Text log = new Text(CustomerLog.getCustomerLogById(customerLogID).toString());
        vBox.getChildren().add(log);
        return vBox;
    }
}
