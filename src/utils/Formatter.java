package utils;

import java.math.BigDecimal;
import java.text.NumberFormat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Formatter {

    public static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    private Formatter() {

    }

    public static LocalDate parseFlexibleDate(String input) {
        if (input == null || input.isEmpty()) {
            return null;
        }
        DateTimeFormatter[] formatters = {
            DateTimeFormatter.ofPattern("d/M/yyyy"),
            DateTimeFormatter.ofPattern("d-M-yyyy"),
            DateTimeFormatter.ofPattern("d.M.yyyy"),
            DateTimeFormatter.ofPattern("yyyy-M-d"),
            DateTimeFormatter.ofPattern("M/d/yyyy")

        };
        for (DateTimeFormatter f : formatters) {
            try {
                return LocalDate.parse(input, f);
            } catch (DateTimeParseException e) {

            }
        }
        return null;
    }

    public static String formatMoney(BigDecimal value) {
        if (value == null) {
            return "$0";
        }
        NumberFormat nf = NumberFormat.getInstance();
        return "$" + nf.format(value);
    }
}
