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
    protected ArrayList<String> menuForShow;
    protected HashMap<String, Menu> menusIn;
    protected String userCode;
    protected String command;
    private String menuName;
    private Menu parentMenu;

    public Menu(String menuName, Menu parentMenu) {
        this.menuName = menuName;
        this.parentMenu = parentMenu;
    }

    public static Matcher getMatcher(String command, String regex) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(command);
    }

    public void show() {
        for (String menu : menuForShow) {
            System.out.println(menu);
        }
    }

    public void execute() {
        command = scanner.nextLine();
        Menu nextMenu = null;
        for (String menuRegex : menusIn.keySet()) {
            if (command.matches(menuRegex)) {
                nextMenu = menusIn.get(menuRegex);
            }
        }
        nextMenu.show();
        nextMenu.execute();
    }
}
