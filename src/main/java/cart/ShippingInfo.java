package cart;

import account.Supplier;
import database.ShippingInfoDataBase;
import server.communications.Response;

import java.util.ArrayList;

/**
 * @author Aryan Ahadinia
 * @since 0.0.1
 */

public class ShippingInfo {
    private static final ArrayList<ShippingInfo> allShippingInfo = new ArrayList<>();
    private static int totalShippingInfoCreated = 0;

    private final String identifier;
    private final String firstName;
    private final String lastName;
    private final String city;
    private final String address;
    private final String postalCode;
    private final String phoneNumber;

    public ShippingInfo(String firstName, String lastName, String city, String address, String postalCode, String phoneNumber) {
        this.identifier = generateIdentifier();
        this.city = city;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        allShippingInfo.add(this);
        totalShippingInfoCreated++;
        ShippingInfoDataBase.add(this);
    }

    public ShippingInfo(String identifier, String firstName, String lastName, String city, String address,
                        String postalCode, String phoneNumber) {
        this.identifier = identifier;
        this.firstName = firstName;
        this.lastName = lastName;
        this.city = city;
        this.address = address;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        allShippingInfo.add(this);
        totalShippingInfoCreated++;
    }

    //Getters:
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

    public static int getTotalShippingInfoCreated() {
        return totalShippingInfoCreated;
    }

    //Modeling methods
    public static String generateIdentifier() {
        return "T34SI" + String.format("%015d", totalShippingInfoCreated + 1);
    }

    public static ShippingInfo getShippingInfoByIdentifier(String identifier) {
        for (ShippingInfo shippingInfo : allShippingInfo) {
            if (shippingInfo.getIdentifier().equals(identifier))
                return shippingInfo;
        }
        return null;
    }

    public static ShippingInfo convertJsonStringToShippingInfo(String jsonString){
        return (ShippingInfo) Response.convertStringToObject(jsonString, "cart.ShippingInfo");
    }


    @Override
    public String toString() {
        return "Ship to: " + city + ", " + address + "Postal code: " + postalCode + "\n" +
                "Deliver to: " + firstName + " " + lastName + "\n" +
                "Contact information: " + phoneNumber;
    }
}
