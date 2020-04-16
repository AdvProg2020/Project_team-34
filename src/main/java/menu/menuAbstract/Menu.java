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
    protected ArrayList<String> menuForShow;
    protected HashMap<String, Menu> menusIn;
    protected String command;
    private String menuName;
    protected Menu parentMenu;
    protected Controller controller;

    public Menu(String menuName, Menu parentMenu) {
        this.menuName = menuName;
        this.parentMenu = parentMenu;
        menuForShow = new ArrayList<>();
        menusIn = new HashMap<>();
        if (!(menuName.equals("Help") || menuName.equals("Sort") || menuName.equals("Back"))) {
            Menu Help = new Menu("Help", this) {
                @Override
                public void show() {
                }

                @Override
                public void execute() {
                }
            };
            menusIn.put("^help$", Help);
            menuForShow.add("Help");

            Menu SortCommands = new Menu("Sort", this) {
                @Override
                public void show() {
                }

                @Override
                public void execute() {
                }
            };
            menusIn.put("^Sort$", SortCommands);
            menuForShow.add("Sort");

            menusIn.put("^Back$", parentMenu);
            menuForShow.add("Back");
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
        if (!controller.hasSomeOneLoggedIn()) {
            System.out.println("Login/Register");
        } else {
            System.out.println("Logout");
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
        if (command.matches("Login/Register")) {
            nextMenu = new LoginMenu(this);
        } else if (command.matches("Logout")) {
            //controller's fuction call
            nextMenu = this;
        }
        if (nextMenu == null) {
            nextMenu = this;
        }
        nextMenu.show();
        nextMenu.execute();
    }
}
