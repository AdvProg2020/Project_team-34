package menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * @author Aryan Ahadinia
 * @since 0.0.1
 */

public class Menu {
    public static Scanner scanner = new Scanner(System.in);

    private String menuName;
    private String parentMenu;
    private ArrayList<String> commandForShow;
    private HashMap<String, Runnable> commandsIn;
    private ArrayList<String> menuForShow;
    private HashMap<String, Menu> menusIn;

    private String userCode;

    public void show() {
        for (String command : commandForShow) {
            System.out.println(command);
        }
        for (String menu : menuForShow) {
            System.out.println(menu);
        }
    }

    public void execute() {
        String command = scanner.nextLine();
        Runnable nextCommand = null;
        for (String commandRegex : commandsIn.keySet()) {
            if (command.matches(commandRegex)) {
                nextCommand = commandsIn.get(commandRegex);
            }
        }
        Menu nextMenu = null;
        if (nextCommand == null) {
            for (String menuRegex : menusIn.keySet()) {
                if (command.matches(menuRegex)) {
                    nextMenu = menusIn.get(menuRegex);
                }
            }
        }
        if (nextCommand != null) {
            nextCommand.run();
        } else if (nextMenu != null) {
            nextMenu.show();
            nextMenu.execute();
        }
    }
}
