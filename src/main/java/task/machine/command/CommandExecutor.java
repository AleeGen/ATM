package task.machine.command;

import task.machine.Operation;
import task.exception.MachineException;
import task.machine.command.impl.*;

import java.util.HashMap;
import java.util.Map;

public class CommandExecutor {
    private static final Map<Operation, Command> commands = new HashMap<>();

    static {
        commands.put(Operation.LOGIN, new LoginCommand());
        commands.put(Operation.CHECK_BALANCE, new CheckBalanceCommand());
        commands.put(Operation.WITHDRAW_AMOUNT, new WithdrawAmountCommand());
        commands.put(Operation.ADD_AMOUNT, new AddAmountCommand());
        commands.put(Operation.EXIT, new ExitCommand());
    }

    private CommandExecutor(){}

    public static void executeCommand(Operation operation) throws MachineException {
        commands.get(operation).execute();
    }

}
