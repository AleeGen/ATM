package task.machine.command.impl;

import task.machine.CashMachine;
import task.machine.Console;
import task.machine.ConsoleMessage;
import task.machine.command.Command;

public class ExitCommand implements Command {
    @Override
    public void execute() {
        CashMachine.disconnectCard();
        Console.write(ConsoleMessage.EXIT);
    }
}
