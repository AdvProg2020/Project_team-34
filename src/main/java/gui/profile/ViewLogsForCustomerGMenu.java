package gui.profile;

import account.Customer;
import cart.Cart;
import cart.ProductInCart;
import cart.ShippingInfo;
import discount.CodedDiscount;
import discount.Sale;
import gui.GMenu;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import log.CustomerLog;
import log.LogStatus;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.atomic.LongAccumulator;

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
