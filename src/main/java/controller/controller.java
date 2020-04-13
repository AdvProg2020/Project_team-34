package controller;

import account.Account;
import cart.Cart;

public class controller {

    private boolean isFirstSupervisorCreated;

    public boolean controlAddToCart(Account account, Cart cart) {
        return false;
    }

    public Cart controlViewCart(Account account, Cart cart) {
        return null;
    }

    public boolean controlSubmitShippingInfo(Account account, Cart cart, String firstName, String lastName, String city, String address, long postalCode, long phoneNumber) {
        return false;
    }

    public boolean controlRemoveShippingInfo(Account account, Cart cart) {
        return false;
    }

    public boolean controlSubmitDiscountCode(Account account, Cart cart) {
        return false;
    }

    public boolean controlRemoveDiscountCode(Account account, Cart cart) {
        return false;
    }

    public boolean controlFinalizeOrder(Account account, Cart cart) {
        return false;
    }

    public boolean controlAddCategory(Account account, String name, String parentCategoryName) {
        return false;
    }

    public boolean controlAddSubcategoryToCategory(Account account, String name, String subcategoryName){
        return false;
    }

    public boolean controlRemoveSubcategoryFromCategory(Account account, String name, String subcategoryName){
        return false;
    }

    public boolean controlAddProductToCategory(Account account, String categoryName, String productIdentifier) {
        return false;
    }

    public boolean controlRemoveProductFromCategory(Account account, String categoryName, String productIdentifier) {
        return false;
    }


}
