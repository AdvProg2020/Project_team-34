package log;

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

    public ShippingInfo(String firstName, String lastName, City city, String address, long postalCode, long phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.city = city;
        this.address = address;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
    }

    public static boolean isPhoneNumberValid(long phoneNumber) {
        return false;
    }

    public static boolean isPostalCodeValid(long postalCode) {
        return false;
    }

    public static boolean isAddressValid(String Address) {
        return false;
    }
}
