package operations;

import model.State;
import util.StateUtils;

public class ShiftRowsOperation implements Operation{
    private State state;

    @Override
    public void loadState(State state) {
        this.state = state;
    }

    @Override
    public State doOperation() {
        StateUtils.rotateStateRow(state, 1, 1);
        StateUtils.rotateStateRow(state, 2, 2);
        StateUtils.rotateStateRow(state, 3, 3);
        return state;
    }

    @Override
    public void printDetails() {
        System.out.println();
        System.out.println("Operation Name: Shift Rows");
        System.out.println("Operation Details: Shifts each row to the left a certain amount");
        System.out.println();

    }
}
