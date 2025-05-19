package util;

import model.State;
import model.Word;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class StateCreationUtil {

    public static State createBlockState(String string) {
        byte[] bytes = string.getBytes(StandardCharsets.UTF_8);

        return createBlockState(bytes);
    }

    public static State createStateFromWords(List<Word> words) {
        State state = createBlockState(new byte[words.size() * 4]);
        StateUtils.setWordInState(state, words.get(0), 0);
        StateUtils.setWordInState(state, words.get(1), 1);
        StateUtils.setWordInState(state, words.get(2), 2);
        StateUtils.setWordInState(state, words.get(3), 3);
        return state;
    }

    public static State createTmpState(State state) {
        byte[][] bytes = state.getTable();
        return new State(bytes);
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

    public static int checkLength(byte[] value) {
        if (value.length == 16) {
            return 4;
        } else if (value.length == 24) {
            return 6;
        } else if (value.length == 32) {
            return 8;
        }
        throw new IllegalArgumentException("Size of value is not 128, 192 or 256 bits");
    }

}
