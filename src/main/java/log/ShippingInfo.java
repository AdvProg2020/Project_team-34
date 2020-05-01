package log;

import exceptionalMassage.ExceptionalMassage;

/**
 * @author Aryan Ahadinia
 * @since 0.0.1
 */

enum City {
    Tehran,
    Shiraz,
    Tabriz,
    Mashhad,
    Isfahan
}

public class ShippingInfo {
    private String firstName;
    private String lastName;
    private City city;
    private String address;
    private long postalCode;
    private long phoneNumber;

    public ShippingInfo(String firstName, String lastName, String city, String address, long postalCode, long phoneNumber) throws ExceptionalMassage {
        try {
            this.city = City.valueOf(city);
        } catch (Exception e) {
            throw new ExceptionalMassage(city + " isn't defined for System");
        }
        this.firstName = firstName;
        this.lastName = lastName;
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
}
