package util;

import model.State;
import model.Word;

import java.util.ArrayList;
import java.util.List;

import static operations.SubBytesOperation.substituteTable;

public class SubKeyGenerationUtil {

    public static final List<Word> rCon = new ArrayList<>(){{
        add(WordCreationUtil.createWord(new byte[]{0x01, 0x00, 0x00, 0x00}));
        add(WordCreationUtil.createWord(new byte[]{0x02, 0x00, 0x00, 0x00}));
        add(WordCreationUtil.createWord(new byte[]{0x04, 0x00, 0x00, 0x00}));
        add(WordCreationUtil.createWord(new byte[]{0x08, 0x00, 0x00, 0x00}));
        add(WordCreationUtil.createWord(new byte[]{0x10, 0x00, 0x00, 0x00}));
        add(WordCreationUtil.createWord(new byte[]{0x20, 0x00, 0x00, 0x00}));
        add(WordCreationUtil.createWord(new byte[]{0x40, 0x00, 0x00, 0x00}));
        add(WordCreationUtil.createWord(new byte[]{(byte) 0x80, 0x00, 0x00, 0x00}));
        add(WordCreationUtil.createWord(new byte[]{0x1b, 0x00, 0x00, 0x00}));
        add(WordCreationUtil.createWord(new byte[]{0x36, 0x00, 0x00, 0x00}));
    }};

    public static List<State> createSubKeys(byte[] key, int numberOfCycles) {
        int nk = StateCreationUtil.checkLength(key);

        List<Word> words = new ArrayList<>();
        int i = 0;
        for (; i <= nk - 1; i++) {
            Word tmp = WordCreationUtil.createWord(new byte[]{key[4 * i], key[4 * i + 1], key[4 * i + 2], key[4 * i + 3]});
            words.add(tmp);
        }
        for (; i <= 4 * numberOfCycles + 3; i++) {
            Word tmp = WordCreationUtil.createTmpWord(words.get(i - 1).getBytes());
            if (i % nk == 0) {
                rotWord(tmp);
                subWord(tmp);
                tmp = WordUtil.xOrWords(tmp, rCon.get((i/nk) - 1));
            }else if(nk > 6 && i % nk == 4) {
                subWord(tmp);
            }
            tmp = WordUtil.xOrWords(tmp, words.get(i - nk));
            words.add(tmp);
        }

        return combineWordsIntoStates(words, numberOfCycles);
    }

    public static List<State> combineWordsIntoStates(List<Word> words, int numberOfCycles){
        ArrayList<State> subkeys = new ArrayList<>();
        for (int i = 0; i < numberOfCycles + 1; i++) {
            List<Word> tmp = new ArrayList<>();
            for (int j = 0; j < 4; j++) {
                tmp.add(words.get(i * 4 + j));
            }
            subkeys.add(StateCreationUtil.createStateFromWords(tmp));
        }

        return subkeys;
    }

    public static void rotWord(Word word) {
        byte first = word.getByte(0);
        byte second = word.getByte(1);
        byte third = word.getByte(2);
        byte fourth = word.getByte(3);
        word.setByte(0, second);
        word.setByte(1, third);
        word.setByte(2, fourth);
        word.setByte(3, first);
    }

    public static void subWord(Word word) {
        for (int i = 0; i < 4; i++) {
            byte position = word.getByte(i);
            int x = ((position & 0xf0) >> 4);
            int y = position & 0x0f;
            byte outByte = substituteTable[x][y];
            word.setByte(i, outByte);
        }
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
