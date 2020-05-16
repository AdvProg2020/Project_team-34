package menu.profile.customerProfileMenu;

import account.Customer;
import exceptionalMassage.ExceptionalMassage;
import log.CustomerLog;
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
                String regex = "show order (\\w+)";
                Matcher matcher = getMatcher(command, regex);
                if(matcher.find()){

                    if(CustomerLog.getCustomerLogById(matcher.group(1)) == null){
                        System.out.println("no such Log!");
                    } else {
                        System.out.println(CustomerLog.getCustomerLogById(matcher.group(1)));
                    }
                }
                parentMenu.show();
                parentMenu.execute();
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
                    System.out.println("Enter the score(0-5):");
                    score = scanner.nextFloat();
                }
                String regex = "rate product (\\w+)";
                Matcher matcher = getMatcher(command, regex);
                if(matcher.find()) {
                    try{
                        controller.getProductController().controlRateProductById(matcher.group(1),score);
                    } catch (ExceptionalMassage ex){
                        System.out.println(ex.getMessage());
                    }
                }
            }
        };
        menusIn.put("rate product (\\w+)", Rate);
        menuForShow.add("Rate");
    }

    @Override
    public void show() {
        for (CustomerLog log : CustomerLog.getCustomerCustomerLogs((Customer) controller.getAccount())) {
            System.out.println(log);
        }
        super.show();
    }
}
