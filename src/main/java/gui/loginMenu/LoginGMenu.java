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
import javafx.scene.text.Text;
import menu.menuAbstract.Menu;

import java.io.IOException;

public class LoginGMenu extends GMenu {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button singInButton;

    @FXML
    private Text signUp;

    @FXML
    void singIn(ActionEvent event) {
        String userName = usernameField.getText();
        String passWord = passwordField.getText();
        try {
            controller.getAccountController().controlLogin(userName, passWord);
            System.out.println("Welcome");
        }catch (ExceptionalMassage ex){
            System.out.println("slm!");
        }

    }

    @FXML
    void goToSignUp(ActionEvent event) {

    }

    public LoginGMenu(String menuName, Menu parentMenu) {
        super(menuName, parentMenu);
    }

    @Override
    public Scene createScene() {
        Parent pane = null;
        try {
            pane = FXMLLoader.load(getClass().getResource("loginG.fxml"));
        } catch (IOException ex){
            System.out.println(ex.getMessage());
        }
        Scene scene = new Scene( pane );
        return scene;
    }
}
