package task.machine;

import task.Card;
import task.machine.command.CommandExecutor;
import task.exception.MachineException;
import task.server.Server;

import java.io.*;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;


public class CashMachine {
    private static final String PATH_CASH;
    public static final String STOP_COMMAND = "stop";
    private static Optional<Card> currentCard = Optional.empty();
    private static boolean isFree = true;
    private static boolean isOn = true;
    private static int cash;


    static {
        PATH_CASH = new File(URLDecoder.decode(Server.class
                .getProtectionDomain()
                .getCodeSource()
                .getLocation()
                .getPath(), StandardCharsets.UTF_8))
                .getParent() + "\\cash.txt";
        System.out.println(PATH_CASH);
        System.out.println(ServiceMessage.START_MACHINE);
        try (BufferedReader reader = new BufferedReader(new FileReader(PATH_CASH))) {
            cash = Integer.parseInt(reader.readLine());
            System.out.println(String.format(ServiceMessage.LOAD_MONEY_SUCCESSFULLY, cash));
        } catch (IOException e) {
            System.out.println(ServiceMessage.ERROR_LOAD_MONEY);
        }
    }

    public static void main(String[] args) {

        for (int i = 0; i < args.length; i++) {
            System.out.println(args[i]);
        }
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
            } catch (MachineException e) {
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

    public static void withdraw(int sum) throws IOException {
        cash -= sum;
        try (BufferedWriter cashEditor = new BufferedWriter(new FileWriter(PATH_CASH))) {
            cashEditor.write(String.valueOf(cash));
        } catch (IOException e) {
            System.out.println(ServiceMessage.ERROR_CHANGING_CASH);
            throw e;
        }
        System.out.println(String.format(ServiceMessage.WITHDRAW_AMOUNT, sum));
    }

    public static void add(int sum) throws IOException {
        cash += sum;
        try (BufferedWriter cashEditor = new BufferedWriter(new FileWriter(PATH_CASH))) {
            cashEditor.write(String.valueOf(cash));
        } catch (IOException e) {
            System.out.println(ServiceMessage.ERROR_CHANGING_CASH);
            throw e;
        }
    }

    public static Optional<Card> getCurrentCard() {
        return currentCard;
    }

    public static void shutdown() {
        isOn = false;
        System.out.println(ServiceMessage.STOP_MACHINE);
        try {
            Console.close();
        } catch (IOException e) {
            System.out.println(ServiceMessage.ERROR_SHUTDOWN);
        }
        Server.stopUpdater();
    }

    public static boolean canWithdraw(int sum) {
        boolean result = cash >= sum;
        if (!result) {
            System.out.println(ServiceMessage.INSUFFICIENT_FUNDS);
            Console.write(ConsoleMessage.INSUFFICIENT_FUNDS_ATM);
        }
        return result;
    }

}