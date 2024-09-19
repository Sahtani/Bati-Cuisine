package Util;

import Model.Entities.Material;

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

    public static void validate(Material material) throws IllegalArgumentException {
        if (material.getName() == null || material.getName().isEmpty()) {
            throw new IllegalArgumentException("Material name cannot be null or empty.");
        }
        if (material.getUnitCost() < 0) {
            throw new IllegalArgumentException("Material unit cost cannot be negative.");
        }
        if (material.getQuantity() < 0) {
            throw new IllegalArgumentException("Material quantity cannot be negative.");
        }
        if (material.getComponentType() == null || material.getComponentType().isEmpty()) {
            throw new IllegalArgumentException("Component type cannot be null or empty.");
        }
        if (material.getVatRate() < 0) {
            throw new IllegalArgumentException("VAT rate cannot be negative.");
        }
        if (material.getTransportCost() < 0) {
            throw new IllegalArgumentException("Transport cost cannot be negative.");
        }
        if (material.getQualityCoefficient() < 0 || material.getQualityCoefficient() > 1) {
            throw new IllegalArgumentException("Quality coefficient must be between 0 and 1.");
        }
    }
}


