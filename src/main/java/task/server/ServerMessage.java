package task.server;

public final class ServerMessage {
    private ServerMessage() {
    }

    public static final String ERROR_GETTING_DATABASE_PATH = "Error getting the database path";
    public static final String ERROR_READ_DATABASE = "Database read error";
    public static final String ERROR_GET_CARD = "Error in the database when performing the getCardByNumber operation";
    public static final String ERROR_ADD_AMOUNT = "Error in the database when performing the addAmount operation";
    public static final String ERROR_WITHDRAW_AMOUNT = "Error in the database when performing the withdrawAmount operation";
    public static final String ERROR_UPDATE_DATA = "Error updating data";
    public static final String ERROR_UPDATE_FAILED_LOGIN = "Error updating count failed attempt login";
    public static final String ERROR_UPDATER = "Error in the Updater class";
}
