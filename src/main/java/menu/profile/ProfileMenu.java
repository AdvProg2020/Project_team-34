package menu.profile;

import menu.Menu;

/**
 * @author Aryan Ahadinia
 * @since 0.0.1
 */

public abstract class ProfileMenu extends Menu {
    public ProfileMenu(String menuName, Menu parentMenu) {
        super(menuName, parentMenu);
        Menu viewPersonalInfo = new Menu("View Personal Info", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
            }
        };
        Menu editPersonalInfo = new Menu("Edit", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
            }
        };
    }
}
