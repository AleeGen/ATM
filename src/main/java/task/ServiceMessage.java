package task;

public final class ServiceMessage {

    private ServiceMessage(){}
    public static final String START_MACHINE = "The ATM is working...";
    public static final String LOAD_MONEY_SUCCESSFULLY = "The money is loaded into the ATM(%d)";
    public static final String ERROR_LOAD_MONEY = "Error load money";
    public static final String STOP_MACHINE = "The ATM turned off.";
    public static final String WITHDRAW_AMOUNT = "Money was withdrawn from the ATM in the amount of: %d";
    public static final String INSUFFICIENT_FUNDS = "insufficient funds at the ATM";
}
