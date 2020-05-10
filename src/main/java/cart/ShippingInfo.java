package cart;

import java.util.ArrayList;

/**
 * @author Aryan Ahadinia
 * @since 0.0.1
 */

public class ShippingInfo {
    private static ArrayList<ShippingInfo> allShippingInfo = new ArrayList<>();
    private static int totalShippingInfoCreated = 0;

    private String identifier;
    private String firstName;
    private String lastName;
    private String city;
    private String address;
    private String  postalCode;
    private String phoneNumber;

    public ShippingInfo(String firstName, String lastName, String city, String address, String postalCode, String phoneNumber) {
        this.city = city;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
    }

    public ShippingInfo(String identifier, String firstName, String lastName, String city, String address, String postalCode, String phoneNumber) {
        this.identifier = identifier;
        this.firstName = firstName;
        this.lastName = lastName;
        this.city = city;
        this.address = address;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
    }

    public static boolean isPhoneNumberValid(long phoneNumber) {
        return true;
    }

    public static boolean isPostalCodeValid(long postalCode) {
        return true;
    }

    public static boolean isAddressValid(String Address) {
        return true;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getCity() {
        return city;
    }

    public String getAddress() {
        return address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    // Added by rpirayadi
    public static ShippingInfo getShippingInfoById (String identifier){
        for (ShippingInfo shippingInfo : allShippingInfo) {
            if(shippingInfo.getIdentifier().equals(identifier))
                return shippingInfo;
        }
        return null;
    }
}
