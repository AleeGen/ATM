package task.machine.command.impl;

import task.machine.CashMachine;
import task.machine.command.Command;


public class StopCommand implements Command {
    @Override
    public void execute()  {
        CashMachine.shutdown();
    }
}
