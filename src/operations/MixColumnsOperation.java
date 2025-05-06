package operations;

import model.State;
import util.StateUtils;

public class MixColumnsOperation implements Operation{
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

    private void computeColumn(int column){
        byte first = state.getValue(0, column);
        byte second = state.getValue(1, column);
        byte third = state.getValue(2, column);
        byte fourth = state.getValue(3, column);

        byte firstOut = (byte) (StateUtils.xTimes(first) ^ (StateUtils.xTimes(second) ^ second) ^ third ^ fourth);
        byte secondOut = (byte) (first ^ StateUtils.xTimes(second) ^ (StateUtils.xTimes(third) ^ third) ^ fourth);
        byte thirdOut = (byte) (first ^ second ^ StateUtils.xTimes(third) ^ (StateUtils.xTimes(fourth) ^ fourth));
        byte fourthOut = (byte) ((StateUtils.xTimes(first) ^ first) ^ second ^ third ^ StateUtils.xTimes(fourth));

        state.setValue(0, column, firstOut);
        state.setValue(1, column, secondOut);
        state.setValue(2, column, thirdOut);
        state.setValue(3, column, fourthOut);
    }

    @Override
    public void printDetails() {

    }
}
