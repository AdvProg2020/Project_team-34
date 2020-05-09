package cart;

import database.ShippingInfoDataBase;

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
        //check
    }

    //Test methods:
    public static void clear() {
        allShippingInfo.clear();
        totalShippingInfoCreated = 0;
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
}
