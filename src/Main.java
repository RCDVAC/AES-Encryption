import context.OperationTypes;
import exception.ExitException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLOutput;
import java.util.List;

public class Main {

    private static byte[] input;

    private static byte[] output;

    private static char[] password;

    private static String operationType;

    private static File outputFile;


    public static void main(String[] args) {

        try {
            processArguments(args);
            startOperationsManager();
            saveOutputToFile();
        } catch (ExitException e) {
            return;
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
            return;
        }

//        byte[] encBlock = new byte[]{ 0x00, 0x00, 0x01, 0x01, 0x03, 0x03, 0x07, 0x07, 0x0f, 0x0f, 0x1f, 0x1f, 0x3f, 0x3f, 0x7f, 0x7f, 0x12};
//
//        String pass = "123";
//        char[] chars = pass.toCharArray();
//
//
//        OperationsManager operationsManager = new OperationsManager(encBlock, OperationTypes.ENCRYPT_128, chars);
//        operationsManager.init();
//        byte[] output = operationsManager.doOperationsOnBlocks();
//
//        OperationsManager operationsManager1 = new OperationsManager(output, OperationTypes.DECRYPT_128, chars);
//        operationsManager1.init();
//        byte[] out = operationsManager1.doOperationsOnBlocks();


        System.out.println();

        System.out.println("Hello world!");
    }

    private static void saveOutputToFile() {
        try{
            Files.write(outputFile.toPath(), output);
        } catch (IOException e) {
            System.out.println("Failed to write to output file: " + e.getMessage());
        }
    }

    private static void startOperationsManager() throws NoSuchAlgorithmException, InvalidKeySpecException {
        OperationsManager operationsManager = new OperationsManager(input, operationType, password);
        operationsManager.init();
        output = operationsManager.doOperationsOnBlocks();
    }

    private static void processArguments(String[] args) throws ExitException {
        String tmpOperationType = null;
        String keySize = null;
        boolean chosenOperationType = false;
        boolean chosenKeySize = false;
        boolean givenInputFile = false;
        boolean givenOutputFile = false;
        boolean givenPassword = false;
        if (args.length == 0 || List.of(args).contains(Arguments.HELP)) {
            printHelpMenu();
            throw new ExitException();
        }

        for (int i = 0; i < args.length; i++) {
            if (!Arguments.isStringAnArgument(args[i])){
                System.out.println("Argument: " + args[i] + " is unknown");
                System.out.println("Use: aes --help  for more information");
            }

            if (Arguments.OPERATION_TYPE_ENCRYPT.equals(args[i])) {
                if (chosenOperationType) {
                    System.out.println("Decryption has already been chosen.");
                    throw new ExitException();
                }
                tmpOperationType = "enc";
                chosenOperationType = true;
            } else if (Arguments.OPERATION_TYPE_DECRYPT.equals(args[i])) {
                if (chosenOperationType) {
                    System.out.println("Encryption has already been chosen.");
                    throw new ExitException();
                }
                tmpOperationType = "dec";
                chosenOperationType = true;
            } else if (Arguments.INPUT_FILE.equals(args[i])) {
                try {
                    String filePath = args[++i];
                    File file = new File(filePath);
                    input = Files.readAllBytes(file.toPath());
                    givenInputFile = true;
                } catch (IOException e) {
                    System.out.println("Cannot open input file: " + e.getMessage());
                    throw new ExitException();
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Input file not specified.");
                    throw new ExitException();
                }
            } else if (Arguments.OUTPUT_FILE.equals(args[i])) {
                try {
                    String filePath = args[++i];
                    outputFile = new File(filePath);
                    givenOutputFile = true;
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Input file not specified.");
                    throw new ExitException();
                }
            } else if (Arguments.OPERATION_TYPE_128.equals(args[i])) {
                if (chosenKeySize) {
                    System.out.println("Key size has already been chosen");
                    throw new ExitException();
                }
                keySize = "128";
                chosenKeySize = true;
            } else if (Arguments.OPERATION_TYPE_192.equals(args[i])) {
                if (chosenKeySize) {
                    System.out.println("Key size has already been chosen");
                    throw new ExitException();
                }
                keySize = "192";
                chosenKeySize = true;
            } else if (Arguments.OPERATION_TYPE_256.equals(args[i])) {
                if (chosenKeySize) {
                    System.out.println("Key size has already been chosen");
                    throw new ExitException();
                }
                keySize = "256";
                chosenKeySize = true;
            } else if (Arguments.PASSWORD.equals(args[i])) {
                try {
                    String tmpPassword = args[++i];
                    password = tmpPassword.toCharArray();
                    givenPassword = true;
                }catch (IndexOutOfBoundsException e) {
                    System.out.println("Password not specified");
                    throw new ExitException();
                }
            }

        }

        if (!chosenOperationType) {
            System.out.println("Not specified whether to encrypt or decrypt. Use --encrypt/--decrypt");
            throw new ExitException();
        }
        if (!chosenKeySize) {
            System.out.println("Key size not specified. Use --aes128/--aes192/--aes256");
        }
        if (!givenInputFile) {
            System.out.println("Input file not given. Use --input <file>");
            throw new ExitException();
        }
        if (!givenOutputFile) {
            System.out.println("Output file not given. Use --output <file>");
            throw new ExitException();
        }
        if (!givenPassword) {
            System.out.println("Password not given. Use --password <file>");
            throw new ExitException();
        }

        if (tmpOperationType.equals("enc")) {
            if (keySize.equals("128")){
                operationType = OperationTypes.ENCRYPT_128;
            }
            if (keySize.equals("192")){
                operationType = OperationTypes.ENCRYPT_192;
            }
            if (keySize.equals("256")){
                operationType = OperationTypes.ENCRYPT_256;
            }
        }
        if (tmpOperationType.equals("dec")) {
            if (keySize.equals("128")){
                operationType = OperationTypes.DECRYPT_128;
            }
            if (keySize.equals("192")){
                operationType = OperationTypes.DECRYPT_192;
            }
            if (keySize.equals("256")){
                operationType = OperationTypes.DECRYPT_256;
            }
        }


    }

    private static void printHelpMenu() {
        System.out.println("usage: aes (--encrypt | --decrypt) (--aes128 | --aes192 | --aes256) <--input> <--output> <--password>");
        System.out.println();
        System.out.println("Options:");
        System.out.println("    --help                Show this help message");
        System.out.println("    --encrypt             Encrypts the input file and stores it in the output file");
        System.out.println("    --decrypt             Decrypts the input file and stores it in the output file");
        System.out.println("    --aes128              Specifies to use a 128 bit key");
        System.out.println("    --aes192              Specifies to use a 192 bit key");
        System.out.println("    --aes256              Specifies to use a 256 bit key");
        System.out.println("    --password <password> Gives the password used for encryption/decryption");
        System.out.println("    --input <file>        The file to encrypt/decrypt");
        System.out.println("    --output <file>       The file where the result should be stored (Will create one if it doesn't exist)");


    }


}