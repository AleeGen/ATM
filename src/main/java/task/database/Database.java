package task.database;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import task.Card;
import task.exception.DataBaseException;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Database {

    private static final Logger logger = LogManager.getLogger();
    private static Map<String, Card> carts = new HashMap<>();
    private static final String PATH_DATA = "src/main/resources/data.txt";


    static {
        try (BufferedReader reader = new BufferedReader(new FileReader(PATH_DATA))) {
            while (reader.ready()) {
                String[] cartInfo = reader.readLine().split(" ");
                String numberCart = cartInfo[0];
                String pin = cartInfo[1];
                int cash = Integer.parseInt(cartInfo[2]);
                Card cart = new Card(numberCart, pin, cash);
                carts.put(numberCart, cart);
            }
        } catch (Exception e) {
            logger.log(Level.FATAL, DataBaseMessage.FATAL_DATABASE);
        }
    }

    private Database() {
    }

    public static Optional<Card> getCardByNumber(String numberCart) throws DataBaseException {
        try {
            Optional<Card> card = Optional.empty();
            if (carts.containsKey(numberCart)) {
                Card cardOriginal = carts.get(numberCart);
                card = Optional.of(cardOriginal.copy());
                occupyCard(cardOriginal);
            }
            return card;
        } catch (Exception e) {
            logger.log(Level.ERROR, DataBaseMessage.ERROR_GET_CARD);
            throw new DataBaseException(e.getMessage());
        }
    }

    public static void addAmount(String numberCard, int amount) throws DataBaseException {
        try {
            carts.get(numberCard).addAmount(amount);
            update();
        } catch (Exception e) {
            logger.log(Level.ERROR, DataBaseMessage.ERROR_ADD_AMOUNT);
            throw new DataBaseException(e.getMessage());
        }
    }

    public static void withdrawAmount(String numberCard, int amount) throws DataBaseException {
        try {
            carts.get(numberCard).withdrawAmount(amount);
            update();
        } catch (Exception e) {
            logger.log(Level.ERROR, DataBaseMessage.ERROR_WITHDRAW_AMOUNT);
            throw new DataBaseException(e.getMessage());
        }
    }

    private static void occupyCard(Card card) {
    }

    private static void update() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PATH_DATA))) {
            for (Card card : carts.values()) {
                writer.write(card.toString());
            }
        } catch (IOException e) {
            logger.log(Level.ERROR, DataBaseMessage.ERROR_UPDATE);
            throw new IOException(e);
        }
    }
}
