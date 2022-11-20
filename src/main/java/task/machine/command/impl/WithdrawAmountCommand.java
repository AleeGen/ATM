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

public class WithdrawAmountCommand implements Command {
    @Override
    public void execute() throws MachineException {
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
                    if (CashMachine.canWithdraw(sum)) {
                        Server.withdrawAmount(card.getNumberCart(), sum);
                        CashMachine.withdraw(sum);
                        Console.write(String.format(ConsoleMessage.WITHDRAW_AMOUNT_SUCCESSFULLY, sum));
                    }
                    Optional<Card> optionalCard = Server.getCardByNumber(card.getNumberCart());
                    CashMachine.connectCard(optionalCard);
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
