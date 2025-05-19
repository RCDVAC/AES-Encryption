package context;

import model.State;

import java.util.List;

public interface Context {

    public State doOperations();

    public State getState();

}
