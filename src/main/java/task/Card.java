package task;

import java.util.Date;

public class Card {
    private static final int MAX_COUNT_FAILED_ATTEMPT_LOGIN = 3;
    private final String numberCart;
    private final String pin;
    private int cash;
    private boolean block = false;
    private int countFailedAttemptLogin = 0;
    private Date blockTime;


    public Card(String numberCart, String pin, int cash) {
        this.numberCart = numberCart;
        this.pin = pin;
        this.cash = cash;
    }

    public Card() {
        this.numberCart = "default";
        this.pin = "default";
    }

    public String getNumberCart() {
        return numberCart;
    }

    public String getPin() {
        return pin;
    }

    public int getCash() {
        return cash;
    }

    public boolean getBlock() {
        return block;
    }

    public int getCountFailedAttemptLogin() {
        return countFailedAttemptLogin;
    }

    public Date getBlockTime() {
        return blockTime;
    }

    public void setBlock(boolean block) {
        if (block) {
            blockTime = new Date();
        } else {
            blockTime = null;
            countFailedAttemptLogin = 0;
        }
        this.block = block;
    }

    public void addFailedAttemptLogin() {
        countFailedAttemptLogin++;
        if (countFailedAttemptLogin >= MAX_COUNT_FAILED_ATTEMPT_LOGIN) {
            setBlock(true);
        }
    }

    public void setCountFailedAttemptLogin(int countFailedAttemptLogin) {
        this.countFailedAttemptLogin = countFailedAttemptLogin;
        if (countFailedAttemptLogin >= MAX_COUNT_FAILED_ATTEMPT_LOGIN) {
            setBlock(true);
        }
    }

    public void addAmount(int amount) {
        cash += amount;
    }

    public void withdrawAmount(int amount) {
        cash -= amount;
    }

    public Card copy() {
        Card card = new Card(numberCart, pin, cash);
        card.setBlock(this.getBlock());
        card.setCountFailedAttemptLogin(this.getCountFailedAttemptLogin());
        return card;
    }

}
