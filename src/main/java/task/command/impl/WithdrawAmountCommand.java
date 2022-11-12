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

public class WithdrawAmountCommand implements Command {
    @Override
    public void execute() throws ExecuteCommandException {
        Optional<Card> cardOptional = CashMachine.getCurrentCard();
        if (cardOptional.isPresent()) {
            Console.write(ConsoleMessage.ENTER_WITHDRAW_AMOUNT);
            try {
                String amount = Console.read();
                if (Validator.amountValid(amount)) {
                    int sum = Integer.parseInt(amount);
                    Card card = cardOptional.get();
                    if (card.getCash() < sum) {
                        Console.write(ConsoleMessage.NOT_ENOUGH_FUNDS_ON_CARD);
                        return;
                    }
                    Database.withdrawAmount(card.getNumberCart(), sum);
                    if (CashMachine.withdraw(sum)) {
                        Console.write(String.format(ConsoleMessage.WITHDRAW_AMOUNT_SUCCESSFULLY, sum));
                    } else {
                        Database.addAmount(card.getNumberCart(), sum);
                    }
                    Optional<Card> optionalCard = Database.getCardByNumber(card.getNumberCart());
                    CashMachine.connectCard(optionalCard);
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
