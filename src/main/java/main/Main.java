package main;

import controller.Controller;
import gui.GMenu;
import gui.loginMenu.FirstSupervisorMenu;
import gui.mainMenu.MainMenuG;
import gui.profile.sendMenu;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import tornadofx.control.DateTimePicker;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTimePicker;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main extends Application {

    public static void main(String[] args)  {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Image logoImage = null;
        try {
            logoImage = new Image(new FileInputStream("./src/main/resources/header/Logo.png"));
        } catch (FileNotFoundException e) {
            System.out.println("Icon not found");
        }
        stage.setTitle("Team 34 Online retail store");
        stage.getIcons().add(logoImage);

        Controller controller = new Controller();

        GMenu mainMenu = new MainMenuG( null, stage, controller);
        GMenu initialMenu = new FirstSupervisorMenu(null, stage, controller);
        stage.setScene((controller.getIsFirstSupervisorCreated() ? mainMenu : initialMenu).getScene());
        stage.show();
    }

}
