package context;

import exception.ExitException;
import model.State;
import operations.*;
import util.ExplanationContextsUtil;

import java.util.ArrayList;
import java.util.List;

public class ExplanationDecryptionContext implements Context{

    private State encryptedBlock;

    List<Operation> operations = new ArrayList<>();

    private final List<State> subkeys;

    private boolean skipInput = false;

    public ExplanationDecryptionContext(State encryptedBlock, List<State> subKeys, int numberOfCycles){
        this.encryptedBlock = encryptedBlock;
        this.subkeys = subKeys;

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
        for (int i = 0; i < operations.size(); i++) {
            while (!skipInput){
                String command = ExplanationContextsUtil.getCommandInput();

                if (ExplanationContextsUtil.HELP.equals(command)) {
                    ExplanationContextsUtil.printExplanationHelpMenu();
                } else if (ExplanationContextsUtil.NEXT.equals(command)){
                    break;
                } else if (ExplanationContextsUtil.INFO.equals(command)) {
                    Operation operation = operations.get(i);
                    operation.printDetails();
                } else if (ExplanationContextsUtil.PREVIEW.equals(command)) {
                    ExplanationContextsUtil.previewOperation(encryptedBlock, operations.get(i));
                } else if (ExplanationContextsUtil.PRINT.equals(command)) {
                    System.out.println("Current block's state:");
                    ExplanationContextsUtil.printState(encryptedBlock);
                } else if (ExplanationContextsUtil.CURRENT_CYCLE.equals(command)) {
                    System.out.println("Cycle number: " + ((Math.floorDiv(i, 4)) + 1));
                } else if (ExplanationContextsUtil.EXPANDED_KEY.equals(command)) {
                    ExplanationContextsUtil.printKeys(subkeys);
                } else if (ExplanationContextsUtil.SKIP_EXPLANATION.equals(command)) {
                    skipInput = true;
                } else if (ExplanationContextsUtil.EXIT.equals(command)) {
                    throw new ExitException();
                }

            }
            Operation operation = operations.get(i);
            operation.loadState(encryptedBlock);
            encryptedBlock = operation.doOperation();
        }
        return encryptedBlock;
    }

    @Override
    public State getState() {
        return encryptedBlock;
    }


}
