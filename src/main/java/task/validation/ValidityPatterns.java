package task.validation;

public class ValidityPatterns {

    private ValidityPatterns() {
    }

    public static final String REGEX_OPERATION = "[1-5]";
    public static final String REGEX_NUMBER_CART = "([0-9]{4}-){3}[0-9]{4}";
    public static final String REGEX_AMOUNT = "[0-9]*";
}
