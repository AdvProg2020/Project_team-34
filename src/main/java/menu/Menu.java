package menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Aryan Ahadinia
 * @since 0.0.1
 */

public abstract class Menu {
    public static Scanner scanner = new Scanner(System.in);

    private String menuName;
    private Menu parentMenu;
    protected ArrayList<String> commandForShow;
    protected HashMap<String, Runnable> commandsIn;
    protected ArrayList<String> menuForShow;
    protected HashMap<String, Menu> menusIn;

    private String userCode;

    protected String command;

    public Menu(String menuName, Menu parentMenu) {
        this.menuName = menuName;
        this.parentMenu = parentMenu;
    }

    public void show() {
        for (String command : commandForShow) {
            System.out.println(command);
        }
        for (String menu : menuForShow) {
            System.out.println(menu);
        }
    }

    public void execute() {
        command = scanner.nextLine();
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

    public static Matcher getMatcher(String command, String regex) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(command);
    }
}
