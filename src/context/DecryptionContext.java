package context;

import model.State;
import operations.*;

import java.util.ArrayList;
import java.util.List;

public class DecryptionContext implements Context{

    private State encryptedBlock;

    List<Operation> operations = new ArrayList<>();

    public DecryptionContext(State encryptedBlock, List<State> subKeys, int numberOfCycles){
        this.encryptedBlock = encryptedBlock;

        operations.add(new KeyXOROperation(subKeys.get(subKeys.size() - 1)));
        for (int i = numberOfCycles; i > 0; i--) {
            operations.add(new InvShiftRowsOperation());
            operations.add(new InvSubBytesOperation());
            operations.add(new KeyXOROperation(subKeys.get(i - 1)));
            if (i != 1){
                operations.add(new InvMixColumnsOperation());
            }
        }
    }

    @Override
    public State doOperations() {
        operations.forEach((operation -> {
            operation.loadState(encryptedBlock);
            encryptedBlock = operation.doOperation();
        }));
        return encryptedBlock;
    }
}
