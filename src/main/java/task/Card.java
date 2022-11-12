package task;

public class Card {
    private final String numberCart;
    private final String pin;
    private int cash;

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

    public void addAmount(int amount) {
        cash += amount;
    }

    public void withdrawAmount(int amount) {
        cash -= amount;
    }

    public Card copy() {
        return new Card(numberCart, pin, cash);
    }

    @Override
    public String toString() {
        return numberCart + " " + pin + " " + cash;
    }
}
