package util;

import model.State;
import model.Word;

public class StateUtils {

    public static void rotateStateRow(State state, int rowNum, int shiftNum){
       byte first = state.getValue(rowNum, 0);
       byte second = state.getValue(rowNum, 1);
       byte third = state.getValue(rowNum, 2);
       byte fourth = state.getValue(rowNum, 3);

       int firstPosition = (0 - shiftNum) < 0 ? (0 - shiftNum) + 4 : 0 - shiftNum;
       int secondPosition = (1 - shiftNum) < 0 ? (1 - shiftNum) + 4 : 1 - shiftNum;
       int thirdPosition = (2 - shiftNum) < 0 ? (2 - shiftNum) + 4 : 2 - shiftNum;
       int fourthPosition = (3 - shiftNum) < 0 ? (3 - shiftNum) + 4 : 3 - shiftNum;

       state.setValue(rowNum, firstPosition, first);
       state.setValue(rowNum, secondPosition, second);
       state.setValue(rowNum, thirdPosition, third);
       state.setValue(rowNum, fourthPosition, fourth);
    }

    public static void setWordInState(State state, Word word, int column) {
        state.setValue(0, column, word.getByte(0));
        state.setValue(1, column, word.getByte(1));
        state.setValue(2, column, word.getByte(2));
        state.setValue(3, column, word.getByte(3));
    }

    public static byte xTimes(byte in) {
        return (byte) ((in << 1) ^ (((in >> 7) & 1) * 0x1b));
    }



}
