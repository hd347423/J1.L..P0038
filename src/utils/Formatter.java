package utils;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Locale;

public class Formatter {
    
    
    private static final DateTimeFormatter[] FORMATTERS = {
        DateTimeFormatter.ofPattern("MM/dd/uuuu").withResolverStyle(ResolverStyle.STRICT),
        DateTimeFormatter.ofPattern("uuuu-MM-dd").withResolverStyle(ResolverStyle.STRICT)
    };
    
    public static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("MM/dd/uuuu");
    
    private Formatter() {
    }
    
    public static String formatMoney(BigDecimal value) {
        if (value == null) {
            return "$0";
        }
        NumberFormat nf = NumberFormat.getInstance(Locale.US);
        return "$" + nf.format(value);
    }
    
    public static LocalDate parseFlexibleDate(String input) {
        if (input == null || input.isEmpty()) {
            return null;
        }
        for (DateTimeFormatter f : FORMATTERS) {
            try {
                return LocalDate.parse(input, f);
            } catch (DateTimeParseException e) {
                // Format không khớp — thử formatter tiếp theo
            }
        }
        return null;
    }
}