package operations;

import model.State;

public interface Operation {

    public void loadState(State state);

    public State doOperation();
}
