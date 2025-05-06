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

    }

}
