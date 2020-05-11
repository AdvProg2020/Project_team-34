package menu.profile.customerProfileMenu;

import exceptionalMassage.ExceptionalMassage;
import menu.menuAbstract.Menu;

import java.util.regex.Matcher;

public class ViewOrderMenuForCustomer extends Menu {
    public ViewOrderMenuForCustomer(Menu parentMenu) {
        super("View Order Menu", parentMenu);

        Menu ShowOrderForCustomer = new Menu("Show Order", this) {
            @Override
            public void show() {

            }

            @Override
            public void execute() {
                //controller.
            }
        };
        menusIn.put("show order (\\w+)", ShowOrderForCustomer);
        menuForShow.add("Show Order");

        Menu Rate = new Menu("Rate", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                float score = -1;
                while(score<0 || score >5){
                    System.out.println("Enter the score(1-5):");
                    score = scanner.nextFloat();
                }
                String regex = "show order (\\w+)";
                Matcher matcher = getMatcher(command, regex);
                if(matcher.find()) {
                    try{
                        controller.controlRateProductById(matcher.group(1),score);
                    } catch (ExceptionalMassage ex){
                        System.out.println(ex.getMessage());
                    }
                }
            }
        };
        menusIn.put("show order (\\w+)", Rate);
        menuForShow.add("Rate");
    }
}
