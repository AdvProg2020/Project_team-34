package menu.profile;

import exceptionalMassage.ExceptionalMassage;
import menu.menuAbstract.Menu;

import java.util.regex.Matcher;

public class ViewPersonalInfoMenu extends Menu {
    public ViewPersonalInfoMenu(Menu parentMenu) {
        super("View Personal Info", parentMenu);

        Menu EditPersonalInfo = new Menu("Edit Personal Info", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                String regex = "edit (\\w+)";
                Matcher matcher = getMatcher(command, regex);
                System.out.print("Enter the new value:");
                String newValue = scanner.nextLine();
                if(matcher.find()){
                    try{
                        controller.getAccountController().controlEditField(matcher.group(1), newValue);
                    } catch (ExceptionalMassage ex){
                        System.out.println(ex.getMessage());
                    }
                }
                parentMenu.show();
                parentMenu.execute();
            }
        };
        menusIn.put("^edit (\\w+)$", EditPersonalInfo);
        menuForShow.add("Edit Personal Info");
    }

    @Override
    public void show() {
        System.out.println(controller.getAccountController().controlViewPersonalInfo());
        super.show();
    }
}

