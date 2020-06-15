package main;

import database.DataBase;
import gui.allProductMenu.AllProductGMenu;
import gui.cartMenu.CartGMenu;
import gui.loginMenu.LoginGMenu;
import gui.mainMenu.MainMenuG;
import gui.profile.CustomerProfileGMenu;
import javafx.application.Application;
import javafx.stage.Stage;
import menu.mainMenu.MainMenu;

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
        launch(args);
        run();
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


//        MainMenuG menu = new MainMenuG("All Product GMenu" , null, stage);
//        stage.setScene(menu.getScene());
//        stage.show();

        CustomerProfileGMenu menu = new CustomerProfileGMenu("Customer Profile Menu" , null, stage);
        stage.setScene(menu.getScene());
        stage.show();
    }
}
