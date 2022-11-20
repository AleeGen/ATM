package task.machine.command.impl;

import task.*;
import task.machine.command.Command;
import task.server.Server;
import task.machine.CashMachine;
import task.machine.Console;
import task.machine.ConsoleMessage;
import task.exception.ServerException;
import task.exception.MachineException;
import task.machine.validation.Validator;

import java.io.IOException;
import java.util.Optional;


public class LoginCommand implements Command {

    @Override
    public void execute() throws MachineException {
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
                    if (cardOptional.get().getBlock()) {
                        Console.write(ConsoleMessage.CARD_BLOCKED);
                    } else {
                        enterPin(cardOptional.get());
                    }
                }
            } else {
                Console.write(ConsoleMessage.INVALID_CART);
            }
        } catch (IOException | ServerException e) {
            throw new MachineException(e.getMessage());
        }
    }

    private static void enterPin(Card card) throws IOException, ServerException {
        Console.write(ConsoleMessage.ENTER_PIN);
        String pin = Console.read();
        if (pin.equals(card.getPin())) {
            CashMachine.connectCard(Optional.of(card));
        } else {
            Console.write(ConsoleMessage.INCORRECT_PIN);
            Server.addFailedLogin(card.getNumberCart());
            card.addFailedAttemptLogin();
            Console.write(String.format(ConsoleMessage.COUNT_FAILED_ATTEMPT_LOGIN, card.getCountFailedAttemptLogin()));
            if (card.getBlock()) {
                Console.write(String.format(ConsoleMessage.CARD_BLOCKED));
            }
        }
    }
}
