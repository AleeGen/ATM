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

public class AddAmountCommand implements Command {
    @Override
    public void execute() throws MachineException {
        Console.write(ConsoleMessage.ENTER_ADD_AMOUNT);
        Optional<Card> cardOptional = CashMachine.getCurrentCard();
        if (cardOptional.isPresent()) {
            try {
                String amount = Console.read();
                if (Validator.amountValid(amount)) {
                    int sum = Integer.parseInt(amount);
                    if (sum <= 1000_000) {
                        CashMachine.add(sum);
                        Server.addAmount(cardOptional.get().getNumberCart(), sum);
                        Console.write(String.format(ConsoleMessage.ADD_AMOUNT_SUCCESSFULLY, sum));
                    } else {
                        Console.write(ConsoleMessage.EXCEEDED_MAX_ADD);
                    }
                } else {
                    Console.write(ConsoleMessage.INVALID_AMOUNT);
                }
            } catch (IOException | ServerException e) {
                throw new MachineException(e.getMessage());
            }
        } else {
            Console.write(ConsoleMessage.NOT_AUTHORIZED);
        }
    }
}
