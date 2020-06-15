package gui.profile;

import gui.GMenu;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ViewLogsForCustomerGMenu extends GMenu {
    public ViewLogsForCustomerGMenu(GMenu parentMenu, Stage stage) {
        super("Customer Log", parentMenu, stage);
    }

    @Override
    protected Scene createScene() {
        VBox logsBox = new VBox();
        VBox mainLayout = new VBox();
        GridPane background = new GridPane();
        Scene scene = new Scene(background);

        logsBox.setPadding(new Insets(10, 10, 10, 10));
        logsBox.setSpacing(10);
        for (String log : controller.getAccountController().getCustomerLogs()) {
            logsBox.getChildren().add(customerLogBox(log));
        }

        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.getChildren().addAll(createHeader(), logsBox);

        background.getChildren().add(mainLayout);
        background.setAlignment(Pos.CENTER);

        return scene;
    }

    public VBox customerLogBox(String customerLog) {
        VBox vBox = getLogBox();
        Text log = new Text(customerLog);
        vBox.getChildren().add(log);
        vBox.setPrefWidth(600);
        vBox.setPadding(new Insets(10, 10, 10, 10));
        return vBox;
    }
}
