package task.machine.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

    private Validator(){}

    public static boolean operationValid(String operation) {
        Matcher pattern = Pattern.compile(ValidityPatterns.REGEX_OPERATION).matcher(operation);
        return pattern.matches();
    }

    public static boolean numberCardValid(String numberCard) {
        Matcher pattern = Pattern.compile(ValidityPatterns.REGEX_NUMBER_CART).matcher(numberCard);
        return pattern.matches();
    }

    public static boolean amountValid(String amount) {
        Matcher pattern = Pattern.compile(ValidityPatterns.REGEX_AMOUNT).matcher(amount);
        return pattern.matches();
    }

}
