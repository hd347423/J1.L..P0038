package utils;

import java.math.BigDecimal;
import java.time.LocalDate;

import java.util.Scanner;

public class Inputter {

    private Scanner sc;

    public Inputter() {
        sc = new Scanner(System.in);
    }

    public String readString(String prompt) {
        System.out.print(prompt);
        return sc.nextLine().trim();
    }

    public int readChoice(String prompt, int min, int max) {
        while (true) {
            String input = readString(prompt);
            try {
                int choice = Integer.parseInt(input);
                if (choice >= min && choice <= max) {
                    return choice;
                }
                System.out.printf("Please enter a number from %d to %d%n", min, max);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number");
            }
        }
    }

    public boolean readYesNo(String prompt) {
        while (true) {
            String input = readString(prompt);
            if (input.equalsIgnoreCase("Y")) {
                return true;
            }
            if (input.equalsIgnoreCase("N")) {
                return false;
            }
            System.out.println("Please enter Y or N");
        }

    }

  public String readLicensePlate(String prompt) {
        while (true) {
            String raw = readString(prompt);
            if (raw.isEmpty()) {
                System.out.println("License plate cannot be blank");
                continue;
            }
            String normalized = normalizeLicensePlate(raw);
            if (Validation.isValidLicensePlate(normalized)) {
                return normalized;
            }
            System.out.println("Invalid license plate format");
        }

    }


    public String readInsuranceId(String prompt) {
        while (true) {
            String input = readString(prompt);
            if (Validation.isValidInsuranceId(input)) {
                return input;
            }
            System.out.println("Insurance id must be exactly 4 digits");

        }
    }

    public LocalDate readDate(String prompt, boolean allowBlank) {
        while (true) {
            String input = readString(prompt);
            if (input.isEmpty()) {
                if (allowBlank) {
                    return null;
                }
                System.out.println("Date cannot be blank");
                continue;
            }
            LocalDate date = Formatter.parseFlexibleDate(input);
            if (date != null) {
                return date;

            }
            System.out.println("Invalid date! Please use MM/dd/yyyy ");

        }
    }

    public BigDecimal readVehicleValue(String prompt, boolean allowBlank) {
        while (true) {
            String input = readString(prompt);
            if (input.isEmpty()) {
                if (allowBlank) {
                    return null;
                }
                System.out.println("Value cannot be blank");
                continue;
            }
            if (!input.matches("^\\d+$")) {
                System.out.println("Value must be a positive integer");
                continue;
            }

            try {
                BigDecimal value = new BigDecimal(input);
                if (Validation.isGreaterThan(value, new BigDecimal("999"))) {
                    return value;
                } else {
                    System.out.println("Value must be greater than 999");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number");
            }

        }
    }

    public int readVehicleType(String prompt, boolean allowBlank) {
        while (true) {
            String input = readString(prompt);
            if (input.isEmpty()) {
                if (allowBlank) {
                    return -1;
                }
                System.out.println("Vehicle type cannot be blank");
                continue;
            }
            try {
                int type = Integer.parseInt(input);
                if (Validation.isInSet(type, new int[]{5, 7, 9})) {
                    return type;
                } else {
                    System.out.println("Vehicle type must be 5, 7, or 9");
                }

            } catch (NumberFormatException e) {
                System.out.println("Please enter a number");
            }
        }
    }

    public int readInsurancePeriod(String prompt, boolean allowBlank) {
        while (true) {
            String input = readString(prompt);
            if (input.isEmpty()) {
                if (allowBlank) {
                    return -1;
                }
                System.out.println("Insurance period cannot be blank");
                continue;
            }
            try {
                int period = Integer.parseInt(input);
                if (Validation.isInSet(period, new int[]{12, 24, 36})) {
                    return period;
                } else {
                    System.out.println("Insurance period must be 12, 24 or 36");
                }

            } catch (NumberFormatException e) {
                System.out.println("Please enter a number");
            }
        }
    }

    public String readCarOwner(String prompt, boolean allowBlank) {
        while (true) {
            String input = readString(prompt);
            if (input.isEmpty()) {
                if (allowBlank) {
                    return null;
                }
                System.out.println("Car owner cannot be blank");
                continue;
            }
            if (Validation.isValidLength(input, 2, 35)) {
                return input;
            }
            System.out.println("Car owner must be 2-35 characters");
        }
    }

    public String readCarBrand(String prompt, boolean allowBlank) {
        while (true) {
            String input = readString(prompt);
            if (input.isEmpty()) {
                if (allowBlank) {
                    return null;
                }
                System.out.println("Car brand cannot be blank");
                continue;
            }
            return input;

        }
    }
    
    public String readLicensePlateLoose(String prompt) {
        String raw = readString(prompt);
        return normalizeLicensePlate(raw);
    }

    public String readRegistrationPlace(String prompt, boolean allowBlank) {
        while (true) {
            String input = readString(prompt);
            if (input.isEmpty()) {
                if (allowBlank) {
                    return null;
                }
                System.out.println("RegistrationPlace cannot be blank");
                continue;
            }
            return input;

        }
    }

    public String readCustomerName(String prompt, boolean allowBlank) {
        while (true) {
            String input = readString(prompt);
            if (input.isEmpty()) {
                if (allowBlank) {
                    return null;
                }
                System.out.println("Customer name cannot be blank");
                continue;
            }
            if (Validation.isValidLength(input, 2, 35)) {
                return input;
            }
            System.out.println("Customer name must be 2-35 characters");
        }
    }

    private String normalizeLicensePlate(String raw) {
        String cleaned = raw.replace(" ", "").replace("-", "").replace(".", "").replace(",", "");
        cleaned = cleaned.toUpperCase();
        if (cleaned.length() >= 7) {
            return cleaned.substring(0, 4) + "-" + cleaned.substring(4, 7) + "." + cleaned.substring(7);
        }
        return cleaned;

    }

}
