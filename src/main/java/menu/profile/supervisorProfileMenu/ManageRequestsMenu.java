package menu.profile.supervisorProfileMenu;

import exceptionalMassage.ExceptionalMassage;
import menu.menuAbstract.Menu;

import java.util.regex.Matcher;

public class ManageRequestsMenu extends Menu {
    public ManageRequestsMenu(Menu parentMenu) {
        super("Manage Requests Menu", parentMenu);

        Menu Details = new Menu("Details", this) {
            @Override
            public void show() {
                String regex = "^details (\\w+)$";
                Matcher matcher = getMatcher(command, regex);
                if(matcher.find()){
                    try{
                        System.out.println(controller.getProductController().controlShowDetailForRequest(matcher.group(1)));
                    } catch (ExceptionalMassage ex){
                        System.out.println(ex.getMessage());
                    }
                }

            }

            @Override
            public void execute() {
                parentMenu.show();
                parentMenu.execute();
            }
        };
        menusIn.put("^details (\\w+)$", Details);
        menuForShow.add("Details");

        Menu AcceptRequest = new Menu("Accept Request", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                String regex = "^accept (\\w+)$";
                Matcher matcher = getMatcher(command, regex);
                if(matcher.find()){
                    try{
                        controller.getProductController().controlAcceptOrDeclineRequest(matcher.group(1), true);
                    } catch (ExceptionalMassage ex){
                        System.out.println(ex.getMessage());
                    }
                }
                parentMenu.show();
                parentMenu.execute();
            }
        };
        menusIn.put("^accept (\\w+)$", AcceptRequest);
        menuForShow.add("Accept");

        Menu DeclineRequest = new Menu("Decline Request", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                String regex = "^decline (\\w+)$";
                Matcher matcher = getMatcher(command, regex);
                if(matcher.find()){
                    try{
                        controller.getProductController().controlAcceptOrDeclineRequest(matcher.group(1), false);
                    } catch (ExceptionalMassage ex){
                        System.out.println(ex.getMessage());
                    }
                }
                parentMenu.show();
                parentMenu.execute();
            }
        };
        menusIn.put("^decline (\\w+)$", DeclineRequest);
        menuForShow.add("Decline");
    }

    @Override
    public void show() {
        System.out.println(controller.getProductController().controlGetListOfRequestId());
        super.show();
    }
}
