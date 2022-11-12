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


public class LoginCommand implements Command {

    @Override
    public void execute() throws ExecuteCommandException {
        Console.write(ConsoleMessage.ENTER_CART_NUMBER);
        try {
            String numberCart = Console.read();
            if (Validator.numberCardValid(numberCart)) {
                Optional<Card> card = Database.getCardByNumber(numberCart);
                if (card.isPresent()) {
                    Console.write(ConsoleMessage.ENTER_PIN);
                    String pin = Console.read();
                    if (pin.equals(card.get().getPin())) {
                        CashMachine.connectCard(card);
                    } else {
                        Console.write(ConsoleMessage.INCORRECT_PIN);
                    }
                } else {
                    Console.write(ConsoleMessage.CART_NOT_EXIST);
                }
            } else {
                Console.write(ConsoleMessage.INVALID_CART);
            }
        } catch (IOException | DataBaseException e) {
            throw new ExecuteCommandException(e.getMessage());
        }
    }
}
