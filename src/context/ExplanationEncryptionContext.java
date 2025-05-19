package context;

import exception.ExitException;
import model.State;
import operations.*;
import util.ExplanationContextsUtil;

import java.util.ArrayList;
import java.util.List;

public class ExplanationEncryptionContext implements Context{


    private State plainBlock;

    List<Operation> operations = new ArrayList<>();

    private final List<State> subkeys;

    public ExplanationEncryptionContext(State plainBlock, List<State> subKeys, int numberOfCycles){
        this.plainBlock = plainBlock;
        this.subkeys = subKeys;

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
        for (int i = 0; i < operations.size(); i++) {
            while (true){
                String command = ExplanationContextsUtil.getCommandInput();

                if (ExplanationContextsUtil.HELP.equals(command)) {
                    ExplanationContextsUtil.printExplanationHelpMenu();
                } else if (ExplanationContextsUtil.NEXT.equals(command)){
                    break;
                } else if (ExplanationContextsUtil.INFO.equals(command)) {
                    Operation operation = operations.get(i);
                    operation.printDetails();
                } else if (ExplanationContextsUtil.PREVIEW.equals(command)) {
                    ExplanationContextsUtil.previewOperation(plainBlock, operations.get(i));
                } else if (ExplanationContextsUtil.PRINT.equals(command)) {
                    System.out.println("Current block's state:");
                    ExplanationContextsUtil.printState(plainBlock);
                } else if (ExplanationContextsUtil.CURRENT_CYCLE.equals(command)) {
                    System.out.println("Cycle number: " + ((Math.floorDiv(i, 4)) + 1));
                } else if (ExplanationContextsUtil.EXPANDED_KEY.equals(command)) {
                    ExplanationContextsUtil.printKeys(subkeys);
                }

                else if (ExplanationContextsUtil.EXIT.equals(command)) {
                    throw new ExitException();
                }

            }
            Operation operation = operations.get(i);
            operation.loadState(plainBlock);
            plainBlock = operation.doOperation();
            System.out.println();
        }
        return plainBlock;
    }

    @Override
    public State getState() {
        return plainBlock;
    }


}
