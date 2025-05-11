package operations;

import model.State;
import util.StateUtils;

public class InvMixColumnsOperation implements Operation{

    private State state;

    @Override
    public void loadState(State state) {
        this.state = state;
    }

    @Override
    public State doOperation() {
        computeColumn(0);
        computeColumn(1);
        computeColumn(2);
        computeColumn(3);
        return state;
    }

    private void computeColumn(int column) {
        byte first = state.getValue(0, column);
        byte second = state.getValue(1, column);
        byte third = state.getValue(2, column);
        byte fourth = state.getValue(3, column);

        byte firstOut = (byte) (computeMultBy0x0e(first) ^ computeMultBy0x0b(second) ^ computeMultBy0x0d(third) ^ computeMultBy0x09(fourth));
        byte secondOut = (byte) (computeMultBy0x09(first) ^ computeMultBy0x0e(second) ^ computeMultBy0x0b(third) ^ computeMultBy0x0d(fourth));
        byte thirdOut = (byte) (computeMultBy0x0d(first) ^ computeMultBy0x09(second) ^ computeMultBy0x0e(third) ^ computeMultBy0x0b(fourth));
        byte fourthOut = (byte) (computeMultBy0x0b(first) ^ computeMultBy0x0d(second) ^ computeMultBy0x09(third) ^ computeMultBy0x0e(fourth));

        state.setValue(0, column, firstOut);
        state.setValue(1, column, secondOut);
        state.setValue(2, column, thirdOut);
        state.setValue(3, column, fourthOut);
    }

    private byte computeMultBy0x09(byte b) {
        return (byte) (StateUtils.xTimes(StateUtils.xTimes(StateUtils.xTimes(b))) ^ b);
    }

    private byte computeMultBy0x0b(byte b) {
        return (byte) (StateUtils.xTimes(StateUtils.xTimes(StateUtils.xTimes(b))) ^ StateUtils.xTimes(b) ^ b);
    }

    private byte computeMultBy0x0d(byte b) {
        return (byte) (StateUtils.xTimes(StateUtils.xTimes(StateUtils.xTimes(b))) ^ StateUtils.xTimes(StateUtils.xTimes(b)) ^ b);
    }

    private byte computeMultBy0x0e(byte b) {
        return (byte) (StateUtils.xTimes(StateUtils.xTimes(StateUtils.xTimes(b))) ^ StateUtils.xTimes(StateUtils.xTimes(b)) ^ StateUtils.xTimes(b));
    }

    @Override
    public void printDetails() {

    }
}
