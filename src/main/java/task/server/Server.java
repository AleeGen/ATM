package task.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import task.Card;
import task.exception.ServerException;

import java.io.*;
import java.net.URLDecoder;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Server {
    private static final String PATH_DATA;
    private static final Timer updater = new Timer();
    private static final int DELAY = 1000;
    private static final int UPDATE_PERIOD = 1000 * 60;
    private static final int NUMBER_DAYS_BLOCK = 1;

    private static final Map<String, Card> cards = new HashMap<>();

    static {
        try {
            PATH_DATA = new File(URLDecoder.decode(Server.class
                    .getProtectionDomain()
                    .getCodeSource()
                    .getLocation()
                    .getPath(), "UTF-8"))
                    .getParent()+"\\database.txt";
        } catch (UnsupportedEncodingException e) {
            System.out.println(ServerMessage.ERROR_GETTING_DATABASE_PATH);
            throw new RuntimeException(e);
        }
        readDataBase();
        updater.schedule(new Updater(), DELAY, UPDATE_PERIOD);
    }

    private Server() {
    }

    public static Optional<Card> getCardByNumber(String numberCard) throws ServerException {
        try {
            Optional<Card> card = Optional.empty();
            if (cards.containsKey(numberCard)) {
                Card cardOriginal = cards.get(numberCard);
                card = Optional.of(cardOriginal.copy());
            }
            return card;
        } catch (Exception e) {
            System.out.println(ServerMessage.ERROR_GET_CARD);
            throw new ServerException(e.getMessage());
        }
    }

    public static void addAmount(String numberCard, int amount) throws ServerException {
        try {
            cards.get(numberCard).addAmount(amount);
            updateDataBase();
        } catch (Exception e) {
            System.out.println(ServerMessage.ERROR_ADD_AMOUNT);
            throw new ServerException(e.getMessage());
        }
    }

    public static void withdrawAmount(String numberCard, int amount) throws ServerException {
        try {
            cards.get(numberCard).withdrawAmount(amount);
            updateDataBase();
        } catch (Exception e) {
            System.out.println(ServerMessage.ERROR_WITHDRAW_AMOUNT);
            throw new ServerException(e.getMessage());
        }
    }

    public static void addFailedLogin(String numberCard) throws ServerException {
        if (cards.containsKey(numberCard)) {
            cards.get(numberCard).addFailedAttemptLogin();
            try {
                updateDataBase();
            } catch (IOException e) {
                System.out.println(ServerMessage.ERROR_UPDATE_FAILED_LOGIN);
                throw new ServerException(e.getMessage());
            }
        }

    }

    private static void updateDataBase() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PATH_DATA))) {
            new ObjectMapper().writeValue(writer, cards.values());
        } catch (IOException e) {
            System.out.println(ServerMessage.ERROR_UPDATE_DATA);
            throw new IOException(e);
        }
        readDataBase();
    }

    private static void readDataBase() {
        try (FileReader reader = new FileReader(PATH_DATA)) {
            ObjectMapper mapper = new ObjectMapper();
            Card[] cardMas = mapper.readValue(reader, Card[].class);
            Arrays.stream(cardMas).forEach(card -> cards.put(card.getNumberCart(), card));
        } catch (Exception e) {
            System.out.println(ServerMessage.ERROR_READ_DATABASE);
        }
    }

    public static void stopUpdater() {
        updater.cancel();
    }

    private static class Updater extends TimerTask {
        @Override
        public void run() {
            Date now = new Date();
            for (Card card : cards.values()) {
                if (card.getBlock()) {
                    long difference = now.getTime() - card.getBlockTime().getTime();
                    if (TimeUnit.DAYS.convert(difference, TimeUnit.MILLISECONDS) > NUMBER_DAYS_BLOCK) {
                        card.setBlock(false);
                        try {
                            updateDataBase();
                        } catch (IOException e) {
                            System.out.println(ServerMessage.ERROR_UPDATER);
                        }
                    }
                }
            }
        }
    }

}
