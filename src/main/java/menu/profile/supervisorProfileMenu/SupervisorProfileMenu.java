package menu.profile.supervisorProfileMenu;

import account.Customer;
import exceptionalMassage.ExceptionalMassage;
import menu.menuAbstract.Menu;
import menu.profile.ProfileMenu;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class SupervisorProfileMenu extends ProfileMenu {
    public SupervisorProfileMenu( Menu parentMenu) {
        super("Supervisor Profile Menu", parentMenu);

        menusIn.put("^manage users$", new ManageUsersMenu(this));
        menuForShow.add("Manage Users");

        menusIn.put("^manage all products$", new ManageProductsMenu(this));
        menuForShow.add("Manage Products");

        Menu CreateDiscountCode = new Menu("Create Discount Code", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                HashMap<Customer, Integer> maxNumberOfUsagePerCustomer = new HashMap<>();
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
                System.out.println("Enter the discount percent :");
                int percent = scanner.nextInt();
                System.out.println("Enter the maximum discount amount :");
                int maxAmount = scanner.nextInt();
                System.out.println("Enter the discount code :");
                String code = scanner.nextLine();
                String customerId;
                int max;
                System.out.println("Enter customer id :");
                while((customerId = scanner.nextLine()).equalsIgnoreCase("end")) {
                    System.out.println("enter max number of usage :");
                    max = scanner.nextInt();
                    if(Customer.getAccountByUsername(customerId) == null){
                        System.out.println("no such username!");
                    } else {
                        maxNumberOfUsagePerCustomer.put((Customer)Customer.getAccountByUsername(customerId), max);
                    }
                    System.out.println("Enter customer id:");
                }
                try {
                    controller.getOffController().controlCreateCodedDiscount(code,startDate,endDate,percent,maxAmount, maxNumberOfUsagePerCustomer);
                } catch (ExceptionalMassage ex){
                    System.out.println(ex.getMessage());
                }
                parentMenu.show();
                parentMenu.execute();
            }
        };
        menusIn.put("^create discount code$", CreateDiscountCode);
        menuForShow.add("Create Discount Code");

        menusIn.put("^view discount codes$", new ViewDiscountCodesMenu(this));
        menuForShow.add("View Discount Codes");

        menusIn.put("^manage requests$", new ManageRequestsMenu(this));
        menuForShow.add("Manage Requests");

        menusIn.put("^manage categories$", new ManageCategoriesMenu(this));
        menuForShow.add("Manage Categories");
    }
}
