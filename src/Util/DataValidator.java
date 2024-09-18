package Util;

public class DataValidator {
    public static boolean isNotEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }



    public static boolean isValidName(String name) {
        return isNotEmpty(name) && name.length() >= 2;
    }

    public static boolean isValidPhoneNumber(String phone) {
        String phoneRegex = "^\\+?[0-9. ()-]{7,}$";
        return isNotEmpty(phone) && phone.matches(phoneRegex);
    }
    public static boolean isValidAddress(String address) {
        return isNotEmpty(address) && address.length() >= 5;
    }
    public static boolean isValidBoolean(boolean value) {
        return value;
    }
}
