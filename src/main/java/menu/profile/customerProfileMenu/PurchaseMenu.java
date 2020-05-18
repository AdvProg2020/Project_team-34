package menu.profile.customerProfileMenu;

import exceptionalMassage.ExceptionalMassage;
import menu.menuAbstract.Menu;

/**
 * @author Aryan Ahadinia
 * @since 0.0.1
 */

public class PurchaseMenu extends Menu {
    public PurchaseMenu(Menu parentMenu) {
        super("Purchase Menu", parentMenu);
    }

    public void show() {
        System.out.println("Purchase menu:");

    }

    public void execute() {
        System.out.println("Enter shipping information:");
        System.out.println("Enter your first name:");
        String firstName = scanner.nextLine();
        System.out.println("Enter your last name:");
        String lastName = scanner.nextLine();
        System.out.println("Enter your city name:");
        String city = scanner.nextLine();
        System.out.println("Enter your address:");
        String address = scanner.nextLine();
        System.out.println("Enter your postal code:");
        String postalCode = scanner.nextLine();
        System.out.println("Enter phone number:");
        String phoneNumber = scanner.nextLine();
        try {
            controller.getAccountController().controlSubmitShippingInfo(firstName, lastName, city, address, postalCode, phoneNumber);
        } catch (ExceptionalMassage ex){
            System.out.println(ex.getMessage());
            parentMenu.show();
            parentMenu.execute();
        }
        System.out.println("Enter discount code if you want:(if you dont want enter 0)");
        String discountCode = scanner.nextLine();
        if(!discountCode.equals("0")){
            try{
                controller.getAccountController().controlSubmitDiscountCode(discountCode);
            } catch (ExceptionalMassage ex){
                System.out.println(ex.getMessage());
                parentMenu.show();
                parentMenu.execute();
            }

        }
        System.out.println(controller.getAccountController().controlViewCart());
        System.out.println("If you want to finalize your order enter purchase:");
        String finalCommand = scanner.nextLine();
        if(finalCommand.equals("purchase")){
            try {
                controller.getAccountController().finalizeOrder();
                System.out.println("Purchase Complete!");
                System.out.println("Your remaining credit :" + controller.getAccountController().controlViewBalance());
            }catch (ExceptionalMassage ex){
                System.out.println(ex.getMessage());
            }
        }
        parentMenu.show();
        parentMenu.execute();

    }
}




