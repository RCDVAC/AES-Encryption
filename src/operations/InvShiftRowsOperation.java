package operations;

import model.State;
import util.StateUtils;

public class InvShiftRowsOperation implements Operation{
    private State state;

    @Override
    public void loadState(State state) {
        this.state = state;
    }

    @Override
    public State doOperation() {
        StateUtils.invRotateStateRow(state, 1, 1);
        StateUtils.invRotateStateRow(state, 2, 2);
        StateUtils.invRotateStateRow(state, 3, 3);
        return state;
    }

    @Override
    public void printDetails() {
        System.out.println();
        System.out.println("Operation Name: Inv Shift Rows");
        System.out.println("Operation Details: Rotates each row to the right a certain amount");
        System.out.println();

    }
}
