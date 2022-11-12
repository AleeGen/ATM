package task.command.impl;

import task.*;
import task.database.Database;
import task.CashMachine;
import task.Console;
import task.ConsoleMessage;
import task.command.Command;
import task.exception.DataBaseException;
import task.exception.ExecuteCommandException;
import task.validation.Validator;

import java.io.IOException;
import java.util.Optional;

public class AddAmountCommand implements Command {
    @Override
    public void execute() throws ExecuteCommandException {
        Console.write(ConsoleMessage.ENTER_ADD_AMOUNT);
        Optional<Card> cardOptional = CashMachine.getCurrentCard();
        if (cardOptional.isPresent()) {
            try {
                String amount = Console.read();
                if (Validator.amountValid(amount)) {
                    int sum = Integer.parseInt(amount);
                    if (sum < 1000_000) {
                        Database.addAmount(cardOptional.get().getNumberCart(), sum);
                        Console.write(String.format(ConsoleMessage.ADD_AMOUNT_SUCCESSFULLY, sum));
                    }
                } else {
                    Console.write(ConsoleMessage.INVALID_AMOUNT);
                }
            } catch (IOException | DataBaseException e) {
                throw new ExecuteCommandException(e.getMessage());
            }
        } else {
            Console.write(ConsoleMessage.NOT_AUTHORIZED);
        }
    }
}
