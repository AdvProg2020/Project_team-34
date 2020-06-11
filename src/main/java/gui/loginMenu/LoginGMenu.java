package gui.loginMenu;

import exceptionalMassage.ExceptionalMassage;
import gui.GMenu;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import menu.menuAbstract.Menu;

import java.io.IOException;

public class LoginGMenu extends GMenu {


    public LoginGMenu(String menuName, GMenu parentMenu, Stage stage) {
        super(menuName, parentMenu, stage);
    }

    @Override
    public Scene createScene() {
        Parent pane = null;
        try {
            pane = FXMLLoader.load(getClass().getResource("/org/example/fxml/loginG.fxml"));
        } catch (IOException ex){
            System.out.println("error khordam");
            System.out.println(ex.getMessage());
        }
        Scene scene = new Scene( new Pane());
        scene.setRoot(pane);
        return scene;
    }
}
