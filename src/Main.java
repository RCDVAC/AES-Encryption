import model.State;
import operations.KeyXOROperation;
import operations.Operation;
import operations.SubBytesOperation;
import util.StateCreationUtil;

public class Main {
    public static void main(String[] args) {

        String string = "abcdefghijklmnop";
        String key = "abcdefghijklmnop";

        State keyState = StateCreationUtil.createState(key);
        State state = StateCreationUtil.createState(string);

        for (int i = 0; i < state.getRows(); i++) {
            for (int j = 0; j < state.getColumns(); j++) {
                System.out.print(String.format("%02X ", state.getValue(i, j)));
            }
            System.out.println();
        }

        Operation operation = new SubBytesOperation();
        operation.loadState(state);

        state = operation.doOperation();

        System.out.println();

        for (int i = 0; i < state.getRows(); i++) {
            for (int j = 0; j < state.getColumns(); j++) {
                System.out.print(String.format("%02X ", state.getValue(i, j)));
            }
            System.out.println();
        }

        System.out.println("Hello world!");
    }
}