package task.command.impl;

import task.CashMachine;
import task.command.Command;


public class StopCommand implements Command {
    @Override
    public void execute()  {
        CashMachine.shutdown();
    }
}
