package task;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import task.command.CommandExecutor;
import task.exception.ExecuteCommandException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;

public class CashMachine {
    private static final Logger logger = LogManager.getLogger();
    private static final String PATH_CASH = ClassLoader.getSystemResource("cash.txt").getPath();
    private static boolean isFree = true;
    private static boolean isOn = true;
    private static int cash;
    private static Optional<Card> currentCard = Optional.empty();


    public static void main(String[] args) {
        logger.log(Level.INFO, ServiceMessage.START_MACHINE);
        loadMoney();
        do {
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
        } while (isOn);
    }

    private static void loadMoney() {
        try (BufferedReader reader = new BufferedReader(new FileReader(PATH_CASH))) {
            int cash = Integer.parseInt(reader.readLine());
            CashMachine.cash = cash;
            logger.log(Level.INFO, String.format(ServiceMessage.LOAD_MONEY_SUCCESSFULLY, cash));
        } catch (IOException e) {
            logger.log(Level.ERROR, ServiceMessage.ERROR_LOAD_MONEY);
        }
    }

    public static void connectCard(Optional<Card> currentCard) {
        CashMachine.currentCard = currentCard;
        isFree = false;
    }

    public static void shutdown() {
        isOn = false;
        logger.log(Level.INFO, ServiceMessage.STOP_MACHINE);
        try {
            Console.close();
        } catch (IOException ignored) {
        }
    }

    public static boolean withdraw(int sum) {
        if (canWithdraw(sum)) {
            cash -= sum;
            logger.log(Level.INFO, String.format(ServiceMessage.WITHDRAW_AMOUNT, sum));
            return true;
        } else {
            logger.log(Level.INFO, ServiceMessage.INSUFFICIENT_FUNDS);
            Console.write(ConsoleMessage.INSUFFICIENT_FUNDS_ATM);
        }
        return false;
    }

    private static boolean canWithdraw(int sum) {
        return cash > sum;
    }

    public static Optional<Card> getCurrentCard() {
        return currentCard;
    }

}