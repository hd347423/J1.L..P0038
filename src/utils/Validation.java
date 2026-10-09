package utils;

import java.math.BigDecimal;


public class Validation {

    private Validation() {
    }

    public static boolean isNotEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }

    public static boolean isValidLicensePlate(String value) {
        if (!isNotEmpty(value)) {
            return false;
        }
        return value.matches(Constants.LICENSE_PLATE_REGEX);
    }

    public static boolean isValidInsuranceId(String value) {
        if (!isNotEmpty(value)) {
            return false;
        }
        return value.matches(Constants.INSURANCE_ID_REGEX);
    }

    

    public static boolean isInSet(int number, int[] set) {
        for (int i : set) {
            if (i == number) {
                return true;
            }
        }
        return false;
    }

    public static boolean isGreaterThan(BigDecimal value, BigDecimal threshold) {
        if (value == null || threshold == null) {
            return false;
        }
        return value.compareTo(threshold) > 0;
    }

    public static boolean isValidLength(String value, int min, int max) {
        if (!isNotEmpty(value)) {
            return false;
        }
        int length = value.trim().length();
        return length >= min && length <= max;
    }
}
