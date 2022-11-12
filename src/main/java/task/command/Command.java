package task.command;

import task.exception.ExecuteCommandException;

public interface Command {
    void execute() throws ExecuteCommandException;
}
