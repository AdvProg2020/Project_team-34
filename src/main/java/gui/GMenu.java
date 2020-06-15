package gui;

import controller.Controller;
import javafx.scene.Node;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public abstract class GMenu {
    protected final GMenu parentMenu;
    protected final String menuName;
    protected final Stage stage;
    public final Controller controller;

    public GMenu(String menuName, GMenu parentMenu, Stage stage) {
        this.menuName = menuName;
        this.parentMenu = parentMenu;
        this.controller = new Controller();
        this.stage = stage;
    }

    public Scene getScene() {
        return createScene();
    }

    protected abstract Scene createScene();

    public Node createHeader() {
        Image logoImage = new Image("main/resources/header/Logo.png");
        ImageView logo = new ImageView(logoImage);
        return null;
    }
}
