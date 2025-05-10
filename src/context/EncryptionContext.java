package context;

import model.State;
import operations.*;

import java.util.ArrayList;
import java.util.List;

public class EncryptionContext implements Context{

    private State plainBlock;

    List<Operation> operations = new ArrayList<>();

    public EncryptionContext(State plainBlock, List<State> subKeys, int numberOfCycles){
        this.plainBlock = plainBlock;

        operations.add(new KeyXOROperation(subKeys.get(0)));
        for (int i = 0; i < numberOfCycles; i++) {
            operations.add(new SubBytesOperation());
            operations.add(new ShiftRowsOperation());
            if (i != numberOfCycles - 1){
                operations.add(new MixColumnsOperation());
            }
            operations.add(new KeyXOROperation(subKeys.get(i + 1)));
        }
    }

    @Override
    public State doOperations() {
        operations.forEach((operation -> {
            operation.loadState(plainBlock);
            plainBlock = operation.doOperation();
        }));
        return plainBlock;
    }
}
