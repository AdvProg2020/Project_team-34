package gui.profile;

import controller.Controller;
import gui.GMenu;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import peer.PeerNode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class sendMenu extends GMenu {
    public sendMenu( GMenu parentMenu, Stage stage, Controller controller) {
        super("literally nothing", parentMenu, stage, controller);
    }

    @Override
    protected Scene createScene() {
        HBox hBox = new HBox();
        TextField filePath = new TextField();
        TextField port = new TextField();
        Button button = new Button();
        hBox.getChildren().addAll(filePath,port,  button);

        button.setOnMouseClicked(e->{
            ArrayList<PeerNode> contacts = PeerNode.getContacts();
            try {
                contacts.get(0).sendRequest(filePath.getText(), "localhost", Integer.parseInt(port.getText()));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        return new Scene(hBox);
    }
}
