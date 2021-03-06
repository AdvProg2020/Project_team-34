package menu.mainMenu;


import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;

public class MainMenuTest {

    @Test
    public void testShow() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        PrintStream old = System.out;
        System.setOut(ps);
        MainMenu mainMenu = new MainMenu();
        mainMenu.show();
        System.out.flush();
        System.setOut(old);
        assertEquals("Help\r\n" +
                "Sort\r\n" +
                "Exit\r\n" +
                "Products\r\n" +
                "Offs\r\n",baos.toString());
    }


}
