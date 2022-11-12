package task;

import task.validation.Validator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Console {

    private Console(){}

    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void write(Object info) {
        System.out.println(info);
    }

    public static String read() throws IOException {
        return reader.readLine();
    }


    public static Operation getOperation() {
        do {
            write(ConsoleMessage.SELECT_ACTION);
            String operation = null;
            try {
                operation = read();
            } catch (IOException e) {
                write(ConsoleMessage.FAILED_READING);
            }
            if (Validator.operationValid(operation)) {
                int numberOperation = Integer.parseInt(operation);
                return Operation.getOperation(numberOperation);
            } else {
                write(ConsoleMessage.FAILED_OPERATION);
            }
        } while (true);
    }

    public static void close() throws IOException {
        reader.close();
    }
}
