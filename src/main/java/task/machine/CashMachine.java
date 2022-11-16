package task.machine;

import task.Card;
import task.machine.command.CommandExecutor;
import task.exception.ExecuteCommandException;

import java.io.*;
import java.util.Optional;

public class CashMachine {
    private static final String PATH_CASH = ClassLoader.getSystemResource("cash.txt").getPath();
    public static final String STOP_COMMAND = "stop";
    private static boolean isFree = true;
    private static boolean isOn = true;
    private static int cash;
    private static Optional<Card> currentCard = Optional.empty();

    static {
        try (BufferedReader reader = new BufferedReader(new FileReader(PATH_CASH))) {
            cash = Integer.parseInt(reader.readLine());
            System.out.println(String.format(ServiceMessage.LOAD_MONEY_SUCCESSFULLY, cash));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(ServiceMessage.ERROR_LOAD_MONEY);
        }
    }

    public static void main(String[] args) {
        System.out.println(ServiceMessage.START_MACHINE);
        while (isOn) {
            try {
                CommandExecutor.executeCommand(Operation.LOGIN);
                if (isFree) {
                    continue;
                }
                Operation operationClient;
                do {
                    operationClient = Console.getOperation();
                    CommandExecutor.executeCommand(operationClient);
                } while (operationClient != Operation.EXIT);
            } catch (ExecuteCommandException e) {
                Console.write(ConsoleMessage.ERROR_EXECUTION);
            }
        }
    }

    public static void connectCard(Optional<Card> currentCard) {
        CashMachine.currentCard = currentCard;
        isFree = false;
    }

    public static void disconnectCard() {
        CashMachine.currentCard = Optional.empty();
        isFree = true;
    }

    public static boolean withdraw(int sum) throws IOException {
        if (canWithdraw(sum)) {
            cash -= sum;
            try (BufferedWriter cashEditor = new BufferedWriter(new FileWriter(PATH_CASH))) {
                cashEditor.write(String.valueOf(cash));
            } catch (IOException e) {
                System.out.println(ServiceMessage.ERROR_CHANGING_CASH);
                throw e;
            }
            System.out.println(String.format(ServiceMessage.WITHDRAW_AMOUNT, sum));
            return true;
        } else {
            System.out.println(ServiceMessage.INSUFFICIENT_FUNDS);
            Console.write(ConsoleMessage.INSUFFICIENT_FUNDS_ATM);
        }
        return false;
    }

    public static void add(int sum) throws IOException {
        cash += sum;
        try (BufferedWriter cashEditor = new BufferedWriter(new FileWriter(PATH_CASH))) {
            cashEditor.write(String.valueOf(cash));
        } catch (IOException e) {
            //logger.log(Level.ERROR, ServiceMessage.ERROR_CHANGING_CASH);
            throw e;
        }
    }

    public static Optional<Card> getCurrentCard() {
        return currentCard;
    }

    public static void shutdown() {
        isOn = false;
        //logger.log(Level.INFO, ServiceMessage.STOP_MACHINE);
        try {
            Console.close();
        } catch (IOException ignored) {
        }
    }

    private static boolean canWithdraw(int sum) {
        return cash >= sum;
    }

}