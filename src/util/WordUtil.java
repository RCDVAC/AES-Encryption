package util;

import model.Word;

public class WordUtil {

    public static Word xOrWords(Word first, Word second) {
        byte[] tmp = new byte[4];
        for (int i = 0; i < 4; i++) {
            tmp[i] = (byte) (first.getByte(i) ^ second.getByte(i));
        }
        return WordCreationUtil.createWord(tmp);
    }

}
