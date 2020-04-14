package menu.menuAbstract;

import controller.Controller;
import menu.loginMenu.LoginMenu;

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
    protected static  ArrayList<String> inAllMenusForShow;
    protected static HashMap<String, Menu> inAllMenus;
    private static boolean isFirstCall = true;
    protected ArrayList<String> menuForShow;
    protected HashMap<String, Menu> menusIn;
    protected Long userCode;
    protected String command;
    private String menuName;
    protected Menu parentMenu;

    public Menu(String menuName, Menu parentMenu) {
        this.menuName = menuName;
        this.parentMenu = parentMenu;
        if (isFirstCall) {
            isFirstCall = false;
            Menu Help = new Menu("Help", this) {
                @Override
                public void show() {
                }

                @Override
                public void execute() {
                }
            };
            inAllMenus.put("^Help$", Help);
            inAllMenusForShow.add("Help");
            Menu SortCommands = new Menu("Sort", this) {
                @Override
                public void show() {
                }

                @Override
                public void execute() {
                }
            };
            inAllMenus.put("^Sort$", SortCommands);
            inAllMenusForShow.add("Sort");
            inAllMenus.put("^Back$", parentMenu);
            inAllMenusForShow.add("Back");
        }
    }

    public static Matcher getMatcher(String command, String regex) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(command);
    }

    public void show() {
        System.out.println(menuName + ":");
        for (String menu : menuForShow) {
            System.out.println(menu);
        }
        if (!Controller.hasCodeLogin(userCode)) {
            System.out.println("Login/Register");
        } else {
            System.out.println("Logout");
        }
        for (String menu : inAllMenusForShow) {
            System.out.println(menu);
        }
    }

    public void execute() {
        command = scanner.nextLine();
        Menu nextMenu = null;
        for (String menuRegex : menusIn.keySet()) {
            if (command.matches(menuRegex)) {
                nextMenu = menusIn.get(menuRegex);
                break;
            }
        }
        for (String menuRegex : inAllMenus.keySet()) {
            if (command.matches(menuRegex)) {
                nextMenu = inAllMenus.get(menuRegex);
                break;
            }
        }
        if (command.matches("Login/Register")) {
            nextMenu = new LoginMenu(this);
        } else if (command.matches("Logout")) {
            //controller's fuction call
            nextMenu = this;
        }
        nextMenu.show();
        nextMenu.execute();
    }
}
