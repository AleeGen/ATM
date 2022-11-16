package task.machine.command;

import task.machine.Operation;
import task.exception.ExecuteCommandException;
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
        commands.put(Operation.STOP, new StopCommand());
    }

    public static void executeCommand(Operation operation) throws ExecuteCommandException {
        commands.get(operation).execute();
    }

}
