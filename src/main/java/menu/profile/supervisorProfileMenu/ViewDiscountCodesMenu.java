package menu.profile.supervisorProfileMenu;

import discount.CodedDiscount;
import exceptionalMassage.ExceptionalMassage;
import menu.menuAbstract.Menu;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;

/**
 * @author Aryan Ahadinia
 * @since 0.0.1
 */

public class ViewDiscountCodesMenu extends Menu {
    public ViewDiscountCodesMenu(Menu parentMenu) {
        super("View Discount Codes Menu", parentMenu);

        Menu ViewDiscountCode = new Menu("View Discount Code", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                String regex = "^view discount code (\\w+)$";
                Matcher matcher = getMatcher(command, regex);
                if(matcher.find()){
                    if(controller.controlGetDiscountByCode(matcher.group(1)) == null){
                        System.out.println("no such code");
                    } else {
                        System.out.println(controller.controlGetDiscountByCode(matcher.group(1)));
                    }
                }
                parentMenu.show();
                parentMenu.execute();
            }
        };
        menusIn.put("^view discount code (\\w+)$", ViewDiscountCode);
        menuForShow.add("View Discount Code");

        Menu EditDiscountCode = new Menu("Edit Discount Code", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                System.out.println("Enter the starting date: (dd/MM/yyyy HH:mm:ss) ");
                String startingDate = scanner.next();
                Date startDate = null;
                try {
                    startDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(startingDate);
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                    parentMenu.show();
                    parentMenu.execute();
                }
                System.out.println("Enter the ending date: (dd/MM/yyyy HH:mm:ss) ");
                String endingDate = scanner.next();
                Date endDate = null;
                try {
                    endDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(endingDate);
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                    parentMenu.show();
                    parentMenu.execute();
                }
                System.out.println("Enter the discount amount :");
                int percent = scanner.nextInt();
                System.out.println("Enter the maximum discount amount :");
                int maxAmount = scanner.nextInt();
                String regex = "^edit discount code (\\w+)$";
                Matcher matcher = getMatcher(command, regex);
                if(matcher.find()){
                    try{
                        controller.controlEditDiscountByCode(matcher.group(1),startDate,endDate,percent,maxAmount);
                    } catch (ExceptionalMassage ex){
                        System.out.println(ex.getMessage());
                    }
                }
                parentMenu.show();
                parentMenu.execute();
            }
        };
        menusIn.put("^edit discount code (\\w+)$", EditDiscountCode);
        menuForShow.add("Edit Discount Code");

        Menu RemoveDiscountCode = new Menu("Remove Discount Code", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                String regex = "^remove discount code (\\w+)$";
                Matcher matcher = getMatcher(command, regex);
                if(matcher.find()){
                    try{
                        controller.controlRemoveDiscountCode(matcher.group(1));
                    } catch (ExceptionalMassage ex){
                        System.out.println(ex.getMessage());
                    }
                }
                parentMenu.show();
                parentMenu.execute();
            }
        };
        menusIn.put("^remove discount code (\\w+)$", RemoveDiscountCode);
        menuForShow.add("Remove Discount Code");
    }

    @Override
    public void show() {
        for (CodedDiscount codedDiscount : controller.controlGetAllCodedDiscounts()) {
            System.out.println(codedDiscount.getDiscountCode());
        }
        super.show();
    }
}
