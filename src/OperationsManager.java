import context.*;
import model.State;
import util.ExplanationContextsUtil;
import util.PasswordKeyDeriver;
import util.StateCreationUtil;
import util.SubKeyGenerationUtil;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;

public class OperationsManager {

    private List<Context> operationsContexts;

    private int numberOfCycles;

    private String operationType;

    private byte[] input;

    private List<State> states;

    private byte[] output;

    private char[] password;

    private List<State> subkeys;

    private final boolean explanationMode;

    public OperationsManager(byte[] input, String operationType, char[] password, boolean explanationMode){
        this.input = input;
        this.operationType = operationType;
        this.password = password;
        this.states = new ArrayList<>();
        this.operationsContexts = new ArrayList<>();
        this.explanationMode = explanationMode;
    }

    public void init() throws NoSuchAlgorithmException, InvalidKeySpecException {
        System.out.println("Initializing...");
        determineNumberOfCycles();
        generateSubKeys();
        splitInputIntoBlockStates();
        determineContexts();
        System.out.println("Initialization completed successfully.");
    }

    public byte[] doOperationsOnBlocks() {
        System.out.println();
        for (int i = 0; i < operationsContexts.size(); i++) {
            System.out.println("Beginning operation of block #" + (i + 1));
            System.out.println();
            System.out.println("Block's state: ");
            ExplanationContextsUtil.printState(operationsContexts.get(i).getState());
            State tmp = operationsContexts.get(i).doOperations();
            copyStateIntoOutput(tmp, i);
            System.out.println();
        }
        return output;
    }


    private void determineNumberOfCycles() {
        if (OperationTypes.ENCRYPT_128.equals(operationType) || OperationTypes.DECRYPT_128.equals(operationType)){
            this.numberOfCycles = 10;
        } else if (OperationTypes.ENCRYPT_192.equals(operationType) || OperationTypes.DECRYPT_192.equals(operationType)) {
            this.numberOfCycles = 12;
        } else if (OperationTypes.ENCRYPT_256.equals(operationType) || OperationTypes.DECRYPT_256.equals(operationType)) {
            this.numberOfCycles = 14;
        } else {
            throw new IllegalArgumentException("Operation type is not supported");
        }
    }

    private int determineKeySize() {
        if (OperationTypes.ENCRYPT_128.equals(operationType) || OperationTypes.DECRYPT_128.equals(operationType)){
            return PasswordKeyDeriver.KEY_LENGTH_BITS_128;
        } else if (OperationTypes.ENCRYPT_192.equals(operationType) || OperationTypes.DECRYPT_192.equals(operationType)) {
            return PasswordKeyDeriver.KEY_LENGTH_BITS_192;
        } else if (OperationTypes.ENCRYPT_256.equals(operationType) || OperationTypes.DECRYPT_256.equals(operationType)) {
            return PasswordKeyDeriver.KEY_LENGTH_BITS_256;
        } else {
            throw new IllegalArgumentException("Operation type is not supported");
        }
    }

    private void generateSubKeys() throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] salt;
        int numberOfBlocks = (int) Math.ceil((double) input.length / (double)16);
        if (OperationTypes.DECRYPT_128.equals(operationType) || OperationTypes.DECRYPT_192.equals(operationType) || OperationTypes.DECRYPT_256.equals(operationType)){
            numberOfBlocks -= 1;
            output = new byte[numberOfBlocks * 16];
            salt = new byte[PasswordKeyDeriver.SALT_SIZE];
            System.arraycopy(this.input, 0, salt, 0, PasswordKeyDeriver.SALT_SIZE);
            byte[] newInput = new byte[numberOfBlocks * 16];
            System.arraycopy(this.input, PasswordKeyDeriver.SALT_SIZE, newInput, 0, this.input.length - PasswordKeyDeriver.SALT_SIZE);
            this.input = newInput;
        } else {
            numberOfBlocks += 1;
            output = new byte[numberOfBlocks * 16];
            salt = PasswordKeyDeriver.generateSalt();
            System.arraycopy(salt, 0, this.output, 0, PasswordKeyDeriver.SALT_SIZE);
        }
        this.subkeys = SubKeyGenerationUtil.createSubKeys(PasswordKeyDeriver.deriveKeyFromPassword(password, salt, determineKeySize()), numberOfCycles);
    }

    private void splitInputIntoBlockStates() {
        int numberOfBlocks = (int) Math.ceil((double) input.length / 16.0);
        for (int i = 0; i < numberOfBlocks; i++) {
            byte[] tmp = new byte[16];
            if (i == numberOfBlocks - 1 && input.length - i * 16 < 16) {
                System.arraycopy(this.input, i * 16, tmp, 0, input.length - i * 16);
            } else {
                System.arraycopy(this.input, i * 16, tmp, 0, 16);
            }
            states.add(StateCreationUtil.createBlockState(tmp));
        }
    }

    private void determineContexts() {
        if (OperationTypes.DECRYPT_128.equals(operationType) || OperationTypes.DECRYPT_192.equals(operationType) || OperationTypes.DECRYPT_256.equals(operationType)) {
            if (explanationMode) {
                states.forEach(state -> {
                    operationsContexts.add(new ExplanationDecryptionContext(state, subkeys, numberOfCycles));
                });
            }else {
                states.forEach(state -> {
                    operationsContexts.add(new DecryptionContext(state, subkeys, numberOfCycles));
                });
            }
        } else if (OperationTypes.ENCRYPT_128.equals(operationType) || OperationTypes.ENCRYPT_192.equals(operationType) || OperationTypes.ENCRYPT_256.equals(operationType)) {
            if (explanationMode) {
                states.forEach(state -> {
                    operationsContexts.add(new ExplanationEncryptionContext(state, subkeys, numberOfCycles));
                });
            } else {
                states.forEach(state -> {
                    operationsContexts.add(new EncryptionContext(state, subkeys, numberOfCycles));
                });
            }
        }
    }

    private void copyStateIntoOutput(State state, int offset) {
        byte[] bytes = new byte[16];
        int counter = 0;
        for (int i = 0; i < state.getColumns(); i++) {
            for (int j = 0; j < state.getRows(); j++) {
                bytes[counter] = state.getValue(j, i);
                if (!(j == state.getRows() - 1)){
                    counter ++;
                }
            }
            counter++;
        }
        if (OperationTypes.DECRYPT_128.equals(operationType) || OperationTypes.DECRYPT_192.equals(operationType) || OperationTypes.DECRYPT_256.equals(operationType)) {
            System.arraycopy(bytes, 0, output, offset * 16, 16);
        } else if (OperationTypes.ENCRYPT_128.equals(operationType) || OperationTypes.ENCRYPT_192.equals(operationType) || OperationTypes.ENCRYPT_256.equals(operationType)) {
            System.arraycopy(bytes, 0, output, offset * 16 + 16, 16);
        }
    }

}
