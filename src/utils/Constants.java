package utils;

public class Constants {

    private Constants() {

    }
    // file path
    public static final String CAR_FILE = "carInfo.dat";
    public static final String INSURANCE_FILE = "insurances.dat";

    // regex
    public static final String LICENSE_PLATE_REGEX = "^\\d{2}[A-Z]\\d-\\d{3}\\.\\d{2}$";
    public static final String INSURANCE_ID_REGEX = "^\\d{4}$";

    //param keys
    public static final String PARAM_SORT_TYPE = "sortType";
    public static final String PARAM_SORT_FIELD = "sortField";
    public static final String PARAM_YEAR = "year";

    // Date types
    public static final String DATE_PAST = "PAST";
    public static final String DATE_FUTURE = "FUTURE";
}
