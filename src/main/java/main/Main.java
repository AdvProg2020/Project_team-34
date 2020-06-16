package main;

import controller.Controller;
import database.DataBase;
import gui.mainMenu.MainMenuG;
import gui.profile.ManageRequestsG;
import javafx.application.Application;
import javafx.scene.Scene;
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

//        CartGMenu menu = new CartGMenu("Cart GMenu", null, stage);
//        stage.setScene(menu.getScene());
//        stage.show();

//        LoginGMenu menu = new LoginGMenu("login", null, stage);
//        stage.setScene(menu.createScene());
//        stage.show();
//
//        AllProductGMenu menu = new AllProductGMenu("All Product GMenu" , null, stage);
//        stage.setScene(menu.getScene());
//        stage.show();

        ManageRequestsG menu1 = new ManageRequestsG(null, stage, new Controller());
        MainMenuG menu = new MainMenuG( null, stage, new Controller());

        stage.setScene(menu.getScene());
        stage.show();

//        CustomerProfileGMenu menu = new CustomerProfileGMenu("All Product GMenu" , null, stage);
//        stage.setScene(menu.getScene());
//        stage.show();


    }
}
