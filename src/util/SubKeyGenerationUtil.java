package util;

import model.State;

import java.util.ArrayList;
import java.util.List;

public class SubKeyGenerationUtil {

    public static List<State> createSubKeys(byte[] key, int cycleNo) {
        StateCreationUtil.checkLength(key);

        List<State> subkeys = new ArrayList<>();
        return subkeys;
    }


    public static int determineCyclesNo(byte[] key) {
        if (key.length == 16) {
            return 10;
        } else if (key.length == 24) {
            return 12;
        } else if (key.length == 32) {
            return 14;
        }
        throw new IllegalArgumentException("Size of value is not 128, 192 or 256 bits");

    }

}
