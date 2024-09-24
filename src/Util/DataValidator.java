package Util;

import Model.Entities.Labor;
import Model.Entities.Material;

public class DataValidator {
    public static boolean isNotEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }


    public static boolean isValidName(String name) {
        if (!isNotEmpty(name) || name.length() < 2) {
            System.out.println("Please enter a valid name. It must be at least 2 characters long.");
            return false;
        }
        return true;
    }

    public static boolean isValidPhoneNumber(String phone) {
        String phoneRegex = "^\\+?[0-9. ()-]{7,}$";
        if (!isNotEmpty(phone) || !phone.matches(phoneRegex)) {
            System.out.println("Please enter a valid phone number. It must contain at least 7 digits.");
            return false;
        }
        return true;
    }

    public static boolean isValidAddress(String address) {
        if (!isNotEmpty(address) || address.length() < 5) {
            System.out.println("Please enter a valid address. It must be at least 5 characters long.");
            return false;
        }
        return true;
    }

    public static boolean isValidBoolean(boolean value) {
        System.out.println("Please enter 'true' or 'false' for the professional status.");
        return value;
    }


    public static void validateMaterial(Material material) throws IllegalArgumentException {
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

    public static void validateLabor(Labor labor) throws IllegalArgumentException {
        if (labor.getName() == null || labor.getName().isEmpty()) {
            throw new IllegalArgumentException("Labor name cannot be null or empty.");
        }
        if (labor.getHourlyRate() < 0) {
            throw new IllegalArgumentException("Hourly rate cannot be negative.");
        }
        if (labor.getHoursWorked() < 0) {
            throw new IllegalArgumentException("Hours worked cannot be negative.");
        }
        if (labor.getWorkerProductivity() < 0 || labor.getWorkerProductivity() > 1) {
            throw new IllegalArgumentException("Worker productivity must be between 0 and 1.");
        }
        if (labor.getVatRate() < 0) {
            throw new IllegalArgumentException("VAT rate cannot be negative.");
        }
    }
}


