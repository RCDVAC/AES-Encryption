package operations;

import model.State;

public class KeyXOROperation implements Operation{

    private final State keyState;
    private State blockState;

    public KeyXOROperation(State keyState) {
        this.keyState = keyState;
    }


    @Override
    public void loadState(State state) {
        this.blockState = state;
    }

    @Override
    public State doOperation() {
        for (int i = 0; i < blockState.getColumns(); i++) {
            for (int j = 0; j < blockState.getRows(); j++) {
                byte xoredByte = (byte) (blockState.getValue(j, i) ^ keyState.getValue(j, i));
                blockState.setValue(j, i, xoredByte);
            }
        }
        return blockState;
    }

    @Override
    public void printDetails() {
        System.out.println();

        System.out.println("Operation Name: Add Round Key");
        System.out.println("Operation Details: XOR's the current block with the current round's key");
        System.out.println("Current round key state: ");
        System.out.println();
        for (int i = 0; i < keyState.getColumns(); i++) {
            for (int j = 0; j < keyState.getRows(); j++) {
                System.out.printf("%02X ", keyState.getValue(i, j));
            }
            System.out.println();
        }
        System.out.println();
    }

}
