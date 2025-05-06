package util;

import model.State;

import java.nio.charset.StandardCharsets;

public class StateCreationUtil {

    public static State createBlockState(String string) {
        byte[] bytes = string.getBytes(StandardCharsets.UTF_8);

        return createBlockState(bytes);
    }

    public static State createBlockState(byte[] bytes) {
        checkLength(bytes);

        State state = new State();

        return populateState(state, bytes);
    }

    public static State populateState(State state, byte[] bytes) {
        int counter = 0;
        for (int i = 0; i < state.getColumns(); i++) {
            for (int j = 0; j < state.getRows(); j++) {
                state.setValue(j, i, bytes[counter]);
                if (!(j == state.getRows() - 1)){
                    counter ++;
                }
            }
            counter++;
        }
        return state;
    }

    public static void checkLength(byte[] value) {
        if (value.length == 16) {
            return;
        } else if (value.length == 24) {
            return;
        } else if (value.length == 32) {
            return;
        }
        throw new IllegalArgumentException("Size of value is not 128, 192 or 256 bits");
    }

}
