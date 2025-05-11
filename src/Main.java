import context.DecryptionContext;
import context.EncryptionContext;
import model.State;
import operations.*;
import util.*;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException {

        byte[] encBlock = new byte[]{0x00, 0x00, 0x01, 0x01, 0x03, 0x03, 0x07, 0x07, 0x0f, 0x0f, 0x1f, 0x1f, 0x3f, 0x3f, 0x7f, 0x7f};
        State state = StateCreationUtil.createBlockState(encBlock);

        String pass = "123";
        char[] chars = pass.toCharArray();
        byte[] salt = new byte[]{(byte) 0x81, 0x69, (byte) 0xdb, 0x3f, 0x4d, (byte) 0xf5, (byte) 0x88, (byte) 0xd1, 0x67, 0x67, (byte) 0x92, (byte) 0xd5, 0x4b, 0x07, (byte) 0xe8, (byte) 0xdb, (byte) 0xfd};
        byte[] key = PasswordKeyDeriver.deriveKeyFromPassword(chars, salt, PasswordKeyDeriver.KEY_LENGTH_BITS_128);

        List<State> subkeys = SubKeyGenerationUtil.createSubKeys(key, 10);

        for (int i = 0; i < state.getRows(); i++) {
            for (int j = 0; j < state.getColumns(); j++) {
                System.out.printf("%02X ", state.getValue(i, j));
            }
            System.out.println();
        }
        System.out.println();

        EncryptionContext context = new EncryptionContext(state, subkeys, 10);
        state = context.doOperations();

        for (int i = 0; i < state.getRows(); i++) {
            for (int j = 0; j < state.getColumns(); j++) {
                System.out.printf("%02X ", Byte.valueOf(state.getValue(i, j)));
            }
            System.out.println();
        }


        DecryptionContext decryptionContext = new DecryptionContext(state, subkeys, 10);
        state = decryptionContext.doOperations();


//        KeyXOROperation keyXor = new KeyXOROperation(subkeys.get(10));
//        keyXor.loadState(state);
//        state = keyXor.doOperation();
//
//        InvShiftRowsOperation invShiftRowsOperation = new InvShiftRowsOperation();
//        invShiftRowsOperation.loadState(state);
//        state = invShiftRowsOperation.doOperation();
//
//        InvSubBytesOperation invSubBytesOperation = new InvSubBytesOperation();
//        invSubBytesOperation.loadState(state);
//        state = invSubBytesOperation.doOperation();
//
//        keyXor = new KeyXOROperation(subkeys.get(9));
//        keyXor.loadState(state);
//        state = keyXor.doOperation();
//
//        InvMixColumnsOperation invMixColumnsOperation = new InvMixColumnsOperation();
//        invMixColumnsOperation.loadState(state);
//        state = invMixColumnsOperation.doOperation();

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