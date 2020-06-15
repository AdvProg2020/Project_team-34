package gui.profile;

import controller.Controller;
import gui.GMenu;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ViewLogsForSupervisorGMenu extends GMenu {
    public ViewLogsForSupervisorGMenu(GMenu parentMenu, Stage stage, Controller controller) {
        super("Logs, Supervisor", parentMenu, stage, controller);
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
        String id = getId(supervisorLog);
        VBox vBox = getLogBox();
        Text log = new Text(supervisorLog);
        Button proceed = new Button("Proceed");
        proceed.setDisable(controller.getAccountController().isLogProcessable(id));
        vBox.setPrefWidth(600);
        vBox.setPadding(new Insets(10, 10, 10, 10));
        proceed.setOnAction(e -> {
            controller.getAccountController().proceedCustomerLog(id);
            log.setText(controller.getAccountController().getCustomerLogById(id));
            proceed.setDisable(controller.getAccountController().isLogProcessable(id));
        });
        vBox.getChildren().addAll(log, proceed);

        return vBox;
    }

    private String getId(String log) {
        String regex = "Order Identifier: (\\w+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(log);
        matcher.find();
        return matcher.group(1);
    }
}
