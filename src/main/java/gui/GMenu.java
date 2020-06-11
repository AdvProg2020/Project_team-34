package gui;

import controller.Controller;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import menu.menuAbstract.Menu;

import java.io.File;

public abstract class GMenu {
    public static final Node HEADER = createHeader();
    private final Menu parentMenu;
    private final String menuName;
    private final Controller controller;

    public GMenu(String menuName, Menu parentMenu) {
        this.menuName = menuName;
        this.parentMenu = parentMenu;
        this.controller = new Controller();
    }

    public Scene getScene() {
        return createScene();
    }

    protected abstract Scene createScene();

    private static Node createHeader() {
        return null;
    }
}
