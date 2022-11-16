package task.machine;

public final class ConsoleMessage {

    private ConsoleMessage() {
    }

    public static final String SELECT_ACTION = """
            Select an action:
            1. Check the balance
            2. Withdraw money
            3. Top up your balance
            4. exit
            """;
    public static final String ENTER_CART_NUMBER = "Enter the card number";
    public static final String ENTER_PIN = "Enter your pin";
    public static final String INVALID_CART = "The card number was entered incorrectly";
    public static final String CART_NOT_EXIST = "There is no such card";
    public static final String INCORRECT_PIN = "Incorrect pin entered";
    public static final String FAILED_READING = "Operation reading failed";
    public static final String FAILED_OPERATION = "Invalid operation selected. Return to the menu";
    public static final String NOT_AUTHORIZED = "You are not authorized for view this  information";
    public static final String YOUR_CASH = "Your balance is: %d";
    public static final String ERROR_EXECUTION = "Operation execution error";
    public static final String ENTER_ADD_AMOUNT = "Enter the amount you want to deposit";
    public static final String ENTER_WITHDRAW_AMOUNT = "Enter the amount you want to withdraw";
    public static final String ADD_AMOUNT_SUCCESSFULLY = "%d were successfully added";
    public static final String INVALID_AMOUNT = "Incorrect amount entered";
    public static final String EXIT = "User logged out";

    public static final String WITHDRAW_AMOUNT_SUCCESSFULLY = "%d were successfully withdrawn";

    public static final String NOT_ENOUGH_FUNDS_ON_CARD = "There are not enough funds on the card";
    public static final String INSUFFICIENT_FUNDS_ATM = "At the moment the ATM does not have enough funds, " +
            "please repeat later";

    public static final String EXCEEDED_MAX_ADD = "You have exceeded the maximum amount of 1000000";
    public static final String CARD_BLOCKED = "The card is blocked";
    public static final String COUNT_FAILED_ATTEMPT_LOGIN = "You have made %d failed login attempts";
}
