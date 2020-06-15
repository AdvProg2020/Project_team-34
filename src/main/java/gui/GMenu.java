package gui;

import controller.Controller;
import javafx.scene.Node;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilterInputStream;

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
        Image logoImage = null;
        try {
            logoImage = new Image(new FileInputStream("./src/main/resources/header/Logo.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ImageView logo = new ImageView(logoImage);
        return null;
    }
}
