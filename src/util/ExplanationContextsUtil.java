package util;

import model.State;
import operations.Operation;

import javax.security.auth.kerberos.KerberosTicket;
import java.util.List;
import java.util.Scanner;

public class ExplanationContextsUtil {

    public static final String HELP = "h";
    public static final String NEXT = "n";
    public static final String PRINT = "s";
    public static final String PREVIEW = "r";
    public static final String INFO = "t";
    public static final String EXIT = "e";
    public static final String EXPANDED_KEY = "k";
    public static final String CURRENT_CYCLE = "c";
    public static final String SKIP_EXPLANATION = "q";

    private static final Scanner scanner;

    static {
        scanner = new Scanner(System.in);
    }

    public static void printExplanationHelpMenu() {
        System.out.println();
        System.out.println("Commands:");
        System.out.println("    h - Prints this menu");
        System.out.println("    n - Moves on to the next operation");
        System.out.println("    s - Prints the current block being processed and the state it's in");
        System.out.println("    r - Prints the current block being processed before and after the operation is applied to it");
        System.out.println("    t - Prints the name of the operation that is being applied to the block");
        System.out.println("    k - Prints the expanded key used for the operations");
        System.out.println("    c - Prints the cycle number");
        System.out.println("    q - Stops the user input and completes the operations on the current block");
        System.out.println("    e - Exit");
        System.out.println();
    }

    public static boolean isInputACommand(String input) {
        return HELP.equals(input) || NEXT.equals(input) || PRINT.equals(input)
                || PREVIEW.equals(input) || INFO.equals(input) || EXIT.equals(input)
                || EXPANDED_KEY.equals(input) || CURRENT_CYCLE.equals(input) || SKIP_EXPLANATION.equals(input);
    }

    public static void previewOperation(State state, Operation operation) {
        State tmp = StateCreationUtil.createTmpState(state);
        System.out.println();
        System.out.println("State before operation: ");
        for (int i = 0; i < tmp.getColumns(); i++) {
            for (int j = 0; j < tmp.getRows(); j++) {
                System.out.printf("%02X ", tmp.getValue(i, j));
            }
            System.out.println();
        }

        operation.loadState(tmp);
        tmp = operation.doOperation();

        System.out.println();
        System.out.println("State after operation: ");
        for (int i = 0; i < tmp.getColumns(); i++) {
            for (int j = 0; j < tmp.getRows(); j++) {
                System.out.printf("%02X ", tmp.getValue(i, j));
            }
            System.out.println();
        }

    }

    public static void printKeys(List<State> states) {
        for (int i = 0; i < states.size(); i++) {
            System.out.println("Key #" + (i + 1));
            printState(states.get(i));
        }
    }

    public static void printState(State state) {
        System.out.println();
        for (int i = 0; i < state.getColumns(); i++) {
            for (int j = 0; j < state.getRows(); j++) {
                System.out.printf("%02X ", state.getValue(i, j));
            }
            System.out.println();
        }
        System.out.println();
    }

    public static String getCommandInput() {
        String command = null;
        while (command == null){
            System.out.println("Please type a command and press ENTER. Type \"h\" for help");
            command = scanner.nextLine().strip();
            if (!ExplanationContextsUtil.isInputACommand(command)){
                System.out.println("Unknown command: " + command);
                command = null;
            }
        }
        return command;
    }


}
