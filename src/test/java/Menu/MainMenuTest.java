package Menu;

import menu.mainMenu.MainMenu;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

public class MainMenuTest {

    @Test
    public void mainMenuOutPut() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        PrintStream old = System.out;
        System.setOut(ps);
        System.out.println("Foofoofoo!");
        System.out.flush();
        System.setOut(old);
        System.out.println("Here: " + baos.toString());
    }

}
