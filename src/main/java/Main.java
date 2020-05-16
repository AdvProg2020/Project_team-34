import account.Customer;
import account.Supplier;
import database.DataBase;
import exceptionalMassage.ExceptionalMassage;
import menu.mainMenu.MainMenu;

public class Main {
    private static long timeBeginning;
    private static long timeProgramWasOpened;
    private static int timesDiscountCodeGenerated;
    private static final long WEEK = 7*24*3600*1000;

    public static void main(String[] args) {
        DataBase.createNewTablesToStart();
        DataBase.importAllData();
        timeProgramWasOpened = System.currentTimeMillis();
        if(timeProgramWasOpened - timeBeginning > timesDiscountCodeGenerated * WEEK ){
            generateRandomCodes();
        }
        System.out.println("Project_team-34");
        run();
    }

    private static void run() {

        MainMenu mainMenu = new MainMenu();
        mainMenu.show();
        mainMenu.execute();
    }

    private static void generateRandomCodes(){

    }
}
