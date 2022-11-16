package task.server;

import task.Card;
import task.exception.DataBaseException;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Timer;

public class Server {
    private static final String PATH_DATA = ClassLoader.getSystemResource("database.txt").getPath();
    private static final Timer updater = new Timer();
    private static final int DELAY = 1000;
    private static final int PERIOD = 1000 * 10;
    private static Map<String, Card> carts = new HashMap<>();

    static {
        updater.schedule(new Updater(), DELAY, PERIOD);
        readDataBase();
    }

    private Server() {
    }

    public static Optional<Card> getCardByNumber(String numberCard) throws DataBaseException {
        try {
            Optional<Card> card = null;
            if (carts.containsKey(numberCard)) {
                Card cardOriginal = carts.get(numberCard);
                card = Optional.of(cardOriginal.copy());
            }
            return card;
        } catch (Exception e) {
            System.out.println(ServerMessage.ERROR_GET_CARD);
            throw new DataBaseException(e.getMessage());
        }
    }

    public static void addAmount(String numberCard, int amount) throws DataBaseException {
        try {
            carts.get(numberCard).addAmount(amount);
            updateDataBase();
        } catch (Exception e) {
            System.out.println(ServerMessage.ERROR_ADD_AMOUNT);
            throw new DataBaseException(e.getMessage());
        }
    }

    public static void withdrawAmount(String numberCard, int amount) throws DataBaseException {
        try {
            carts.get(numberCard).withdrawAmount(amount);
            updateDataBase();
        } catch (Exception e) {
            System.out.println(ServerMessage.ERROR_WITHDRAW_AMOUNT);
            throw new DataBaseException(e.getMessage());
        }
    }

    public static void addFailedLogin(String numberCard) throws DataBaseException {
        if (carts.containsKey(numberCard)) {
            carts.get(numberCard).addFailedAttemptLogin();
            try {
                updateDataBase();
            } catch (IOException e) {
                System.out.println(ServerMessage.ERROR_WITHDRAW_AMOUNT);
                throw new DataBaseException(e.getMessage());
            }
        }

    }

    private static void updateDataBase() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PATH_DATA))) {
            for (Card card : carts.values()) {
                writer.write(card.toString() + "\n");
            }
        } catch (IOException e) {
            System.out.println(ServerMessage.ERROR_UPDATE_DATA);
            throw new IOException(e);
        }
        readDataBase();
    }

    private static void readDataBase() {
        try (BufferedReader reader = new BufferedReader(new FileReader(PATH_DATA))) {
            while (reader.ready()) {
                String[] cartInfo = reader.readLine().split(" ");
                String numberCart = cartInfo[0];
                String pin = cartInfo[1];
                int cash = Integer.parseInt(cartInfo[2]);
                int countFailed = Integer.parseInt(cartInfo[3]);
                Card cart = new Card(numberCart, pin, cash);
                cart.setCountFailedAttemptLogin(countFailed);
                carts.put(numberCart, cart);
            }
        } catch (Exception e) {
            System.out.println(ServerMessage.FATAL_DATABASE);
        }
    }

}
