package main;

import controller.Controller;
import database.DataBase;
import gui.GMenu;
import gui.loginMenu.FirstSupervisorMenu;
import gui.mainMenu.MainMenuG;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import menu.mainMenu.MainMenu;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Main extends Application {
    private static long timeBeginning;
    private static long timeProgramWasOpened;
    private static int timesDiscountCodeGenerated;
    private static final long WEEK = 7*24*3600*1000;

    public static void main(String[] args)  {

        DataBase.createNewTablesToStart();
        DataBase.importAllData();
        timeProgramWasOpened = System.currentTimeMillis();
        if(timeProgramWasOpened - timeBeginning > timesDiscountCodeGenerated * WEEK ){
            generateRandomCodes();
        }
        //run();
        launch(args);

    }

    private static void run() {
        MainMenu mainMenu = new MainMenu();
        mainMenu.show();
        mainMenu.execute();
    }

    private static void generateRandomCodes(){

    }

    @Override
    public void start(Stage stage) throws Exception {
        Image logoImage = null;
        try {
            logoImage = new Image(new FileInputStream("./src/main/resources/header/Logo.png"));
        } catch (FileNotFoundException e) {
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
