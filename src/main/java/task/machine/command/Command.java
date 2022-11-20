package task.machine.command;

import task.exception.MachineException;

public interface Command {
    void execute() throws MachineException;
}
