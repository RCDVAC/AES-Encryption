import model.State;
import operations.*;
import util.StateCreationUtil;

public class Main {
    public static void main(String[] args) {

        String string = "abcdefghijklmnop";
        String key = "abcdefghijklmnop";

        State keyState = StateCreationUtil.createBlockState(key);
        State state = StateCreationUtil.createBlockState(string);

        for (int i = 0; i < state.getRows(); i++) {
            for (int j = 0; j < state.getColumns(); j++) {
                System.out.printf("%02X ", Byte.valueOf(state.getValue(i, j)));
            }
            System.out.println();
        }

        Operation operation = new MixColumnsOperation();
        operation.loadState(state);
        operation.doOperation();
        System.out.println();

        for (int i = 0; i < state.getRows(); i++) {
            for (int j = 0; j < state.getColumns(); j++) {
                System.out.printf("%02X ", Byte.valueOf(state.getValue(i, j)));
            }
            System.out.println();
        }

        System.out.println("Hello world!");
    }
}