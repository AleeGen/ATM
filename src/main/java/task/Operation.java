package task;

public enum Operation {
    LOGIN,
    CHECK_BALANCE,
    WITHDRAW_AMOUNT,
    ADD_AMOUNT,
    EXIT,
    STOP;

    public static Operation getOperation(int numberOperation) {
        for (Operation o : Operation.values()) {
            if (o.ordinal() == numberOperation) {
                return o;
            }
        }
        return LOGIN;
    }

}
