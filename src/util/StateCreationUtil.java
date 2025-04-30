package util;

import model.State;

public class StateCreationUtil {

    public static State createState(String bytes) {
        checkLength(bytes);

        State state = new State(determineSize(bytes));

        return populateState(state, bytes);
    }

    public static State populateState(State state, String value) {
        byte[] bytes = value.getBytes();
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

    public static void checkLength(String value) {
        if (value.length() == 16) {
            return;
        } else if (value.length() == 24) {
            return;
        } else if (value.length() == 32) {
            return;
        }
        throw new IllegalArgumentException("Size of value is not 128, 192 or 256 bits");
    }

    public static int determineSize(String value) {
        if (value.length() == 16) {
            return 128;
        } else if (value.length() == 24) {
            return 192;
        } else if (value.length() == 32) {
            return 256;
        }
        throw new IllegalArgumentException("Size of value is not 128, 192 or 256 bits");
    }

}
