package menu.profile.supervisorProfileMenu;

import menu.menuAbstract.Menu;
import menu.profile.ProfileMenu;

public class SupervisorProfileMenu extends ProfileMenu {
    public SupervisorProfileMenu( Menu parentMenu) {
        super("Supervisor Profile Menu", parentMenu);

        menusIn.put("manage users", new ManageUsersMenu(this));
        menuForShow.add("Manage Users");

        menusIn.put("", );
        menuForShow.add();


    }
}
