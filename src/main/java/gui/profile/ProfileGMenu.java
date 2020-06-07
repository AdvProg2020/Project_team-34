package gui.profile;

import account.Account;
import gui.GMenu;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import menu.menuAbstract.Menu;

public class ProfileGMenu extends GMenu {
    private final Account account;

    public ProfileGMenu(String menuName, Menu parentMenu, Account account) {
        super(menuName, parentMenu);
        this.account = account;
    }

    @Override
    protected Scene createScene() {
        TextField username = new TextField();
        TextField name = new TextField();
        TextField familyName = new TextField();
        TextField email = new TextField();
        TextField phoneNumber = new TextField();
        PasswordField password = new PasswordField();

        TextField nameOfCompany = new TextField();
        TextField credit = new TextField();

        username.setText(account.getUserName());
        username.setDisable(true);

        name.setText(account.getName());

        familyName.setText(account.getFamilyName());

        email.setText(account.getEmail());

        phoneNumber.setText(account.getPhoneNumber());

        return null;
    }
}
