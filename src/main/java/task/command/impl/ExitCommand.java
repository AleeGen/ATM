package task.command.impl;

import task.Console;
import task.ConsoleMessage;
import task.command.Command;

public class ExitCommand implements Command {
    @Override
    public void execute() {
        Console.write(ConsoleMessage.EXIT);
    }
}
