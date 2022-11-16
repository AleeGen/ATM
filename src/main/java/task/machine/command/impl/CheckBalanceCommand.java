package task.machine.command.impl;

import task.Card;
import task.machine.CashMachine;
import task.machine.Console;
import task.machine.ConsoleMessage;
import task.machine.command.Command;
import task.server.Server;
import task.exception.DataBaseException;
import task.exception.ExecuteCommandException;

import java.util.Optional;

public class CheckBalanceCommand implements Command {
    @Override
    public void execute() throws ExecuteCommandException {
        Optional<Card> cardOptional = CashMachine.getCurrentCard();
        if (cardOptional.isPresent()) {
            try {
                cardOptional = Server.getCardByNumber(cardOptional.get().getNumberCart());
                CashMachine.connectCard(cardOptional);
            } catch (DataBaseException e) {
                throw new ExecuteCommandException(e.getMessage());
            }
            Console.write(String.format(ConsoleMessage.YOUR_CASH, cardOptional.get().getCash()));
        } else {
            Console.write(ConsoleMessage.NOT_AUTHORIZED);
        }
    }
}
