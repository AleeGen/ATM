package task.machine.command.impl;

import task.*;
import task.machine.command.Command;
import task.server.Server;
import task.machine.CashMachine;
import task.machine.Console;
import task.machine.ConsoleMessage;
import task.exception.DataBaseException;
import task.exception.ExecuteCommandException;
import task.machine.validation.Validator;

import java.io.IOException;
import java.util.Optional;


public class LoginCommand implements Command {

    @Override
    public void execute() throws ExecuteCommandException {
        Console.write(ConsoleMessage.ENTER_CART_NUMBER);
        try {
            String numberCart = Console.read();
            if (numberCart.equals(CashMachine.STOP_COMMAND)) {
                CashMachine.shutdown();
                return;
            }
            if (Validator.numberCardValid(numberCart)) {
                Optional<Card> cardOptional = Server.getCardByNumber(numberCart);
                if (cardOptional.isEmpty()) {
                    Console.write(ConsoleMessage.CART_NOT_EXIST);
                } else {
                    Card card = cardOptional.get();
                    if (card.isBlocked()) {
                        Console.write(ConsoleMessage.CARD_BLOCKED);
                    } else {
                        Console.write(ConsoleMessage.ENTER_PIN);
                        String pin = Console.read();
                        if (pin.equals(card.getPin())) {
                            CashMachine.connectCard(cardOptional);
                        } else {
                            Console.write(ConsoleMessage.INCORRECT_PIN);
                            Server.addFailedLogin(numberCart);
                            card.addFailedAttemptLogin();
                            Console.write(String.format(ConsoleMessage.COUNT_FAILED_ATTEMPT_LOGIN, card.getCountFailedAttemptLogin()));
                            if (card.isBlocked()) {
                                Console.write(String.format(ConsoleMessage.CARD_BLOCKED));
                            }
                        }
                    }
                }
            } else {
                Console.write(ConsoleMessage.INVALID_CART);
            }
        } catch (IOException | DataBaseException e) {
            throw new ExecuteCommandException(e.getMessage());
        }
    }
}
