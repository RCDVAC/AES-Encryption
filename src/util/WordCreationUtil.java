package util;

import model.State;
import model.Word;

public class WordCreationUtil {

    public static Word createWord(byte[] bytes) {
        return new Word(bytes);
    }

    public static Word createTmpWord (byte[] bytes) {
        byte[] tmp = new byte[] {bytes[0], bytes[1], bytes[2], bytes[3]};
        return WordCreationUtil.createWord(tmp);
    }

    public static Word getWordFromState(State state, int column) {
        byte[] bytes = new byte[] {state.getValue(0, column), state.getValue(1, column), state.getValue(2, column),state.getValue(3, column)};
        return new Word(bytes);
    }


}
