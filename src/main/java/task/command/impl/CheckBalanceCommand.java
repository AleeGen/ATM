package task.command.impl;

import task.Card;
import task.CashMachine;
import task.Console;
import task.ConsoleMessage;
import task.command.Command;
import task.database.Database;
import task.exception.DataBaseException;
import task.exception.ExecuteCommandException;

import java.util.Optional;

public class CheckBalanceCommand implements Command {
    @Override
    public void execute() throws ExecuteCommandException {
        Optional<Card> cardOptional = CashMachine.getCurrentCard();
        if (cardOptional.isPresent()) {
            try {
                cardOptional = Database.getCardByNumber(cardOptional.get().getNumberCart());
                if (card.isPresent()) {
            } catch (DataBaseException e) {
                throw new ExecuteCommandException(e.getMessage());
            }
            Console.write(String.format(ConsoleMessage.YOUR_CASH, cardOptional.get().getCash()));
        } else {
            Console.write(ConsoleMessage.NOT_AUTHORIZED);
        }
    }
}
