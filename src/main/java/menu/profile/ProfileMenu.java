package menu.profile;

import menu.menuAbstract.Menu;

/**
 * @author Aryan Ahadinia
 * @since 0.0.1
 */

public abstract class ProfileMenu extends Menu {
    public ProfileMenu(String menuName, Menu parentMenu) {
        super(menuName, parentMenu);

        menusIn.put("^view personal info$", this);
        menuForShow.add("View Personal Info");
    }
}
