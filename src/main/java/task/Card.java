package task;

public class Card {
    private static final int MAX_COUNT_FAILED_ATTEMPT_LOGIN = 3;
    private final String numberCart;
    private final String pin;
    private int cash;
    private boolean block = false;
    private int countFailedAttemptLogin = 0;


    public Card(String numberCart, String pin, int cash) {
        this.numberCart = numberCart;
        this.pin = pin;
        this.cash = cash;
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

    public boolean isBlocked() {
        return block;
    }

    public void setBlock(boolean block) {
        if (block == true) {
            countFailedAttemptLogin = MAX_COUNT_FAILED_ATTEMPT_LOGIN;
        }
        this.block = block;
    }

    public void addFailedAttemptLogin() {
        countFailedAttemptLogin++;
        if (countFailedAttemptLogin > 2) {
            setBlock(true);
        }
    }

    public void setCountFailedAttemptLogin(int countFailedAttemptLogin) {
        this.countFailedAttemptLogin = countFailedAttemptLogin;
        if(countFailedAttemptLogin>2){
            setBlock(true);
        }
    }

    public int getCountFailedAttemptLogin() {
        return countFailedAttemptLogin;
    }

    public void addAmount(int amount) {
        cash += amount;
    }

    public void withdrawAmount(int amount) {
        cash -= amount;
    }

    public Card copy() {
        Card card = new Card(numberCart, pin, cash);
        card.setCountFailedAttemptLogin(this.getCountFailedAttemptLogin());
        return card;
    }

    @Override
    public String toString() {
        return numberCart + " " + pin + " " + cash + " " + countFailedAttemptLogin;
    }
}
