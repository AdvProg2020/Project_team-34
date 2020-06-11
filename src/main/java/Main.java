import account.Account;
import account.Customer;
import account.Supplier;
import database.DataBase;
import exceptionalMassage.ExceptionalMassage;
import gui.profile.ProfileGMenu;
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
    }
}
