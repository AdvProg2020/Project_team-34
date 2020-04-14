package menu;

public abstract class ProfileMenu extends Menu {
    public ProfileMenu(String menuName, Menu parentMenu) {
        super(menuName, parentMenu);
        Menu viewPersonalInfo = new Menu("View Personal Info", this) {
            @Override
            public void show() {
                super.show();
            }

            @Override
            public void execute() {
                super.execute();
            }
        };
        Menu editPersonalInfo = new Menu("Edit", this) {
            @Override
            public void show() {
                super.show();
            }

            @Override
            public void execute() {
                super.execute();
            }
        };
    }
}
