package task.server;

public final class ServerMessage {
    private ServerMessage() {
    }

    public static final String FATAL_DATABASE = "Error during database operation";
    public static final String ERROR_GET_CARD = "Error in the database when performing the getCardByNumber operation";
    public static final String ERROR_ADD_AMOUNT = "Error in the database when performing the addAmount operation";
    public static final String ERROR_WITHDRAW_AMOUNT = "Error in the database when performing the withdrawAmount operation";
    public static final String ERROR_UPDATE_DATA = "Error updating data";
    public static final String ERROR_UPDATE_FAILED_LOGIN = "Error updating count failed attempt login";
}
