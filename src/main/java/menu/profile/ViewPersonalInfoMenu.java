package menu.profile;

import menu.menuAbstract.Menu;

public class ViewPersonalInfoMenu extends Menu {
    public ViewPersonalInfoMenu(Menu parentMenu) {
        super("View Personal Info", parentMenu);

        Menu EditPersonalInfo = new Menu("Edit Personal Info", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
            }
        };
        menusIn.put("edit", EditPersonalInfo);
        menuForShow.add("Edit Personal Info");
    }
}
