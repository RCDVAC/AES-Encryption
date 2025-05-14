public class Arguments {

    public static final String OPERATION_TYPE_ENCRYPT = "--encrypt";
    public static final String OPERATION_TYPE_DECRYPT = "--decrypt";
    public static final String INPUT_FILE = "--input";
    public static final String OUTPUT_FILE = "--output";
    public static final String OPERATION_TYPE_128 = "--aes128";
    public static final String OPERATION_TYPE_192 = "--aes192";
    public static final String OPERATION_TYPE_256 = "--aes256";
    public static final String PASSWORD = "--password";
    public static final String HELP = "--help";

    public static boolean isStringAnArgument(String string) {
        return OPERATION_TYPE_ENCRYPT.equals(string) || OPERATION_TYPE_DECRYPT.equals(string) ||
                INPUT_FILE.equals(string) || OUTPUT_FILE.equals(string) ||
                OPERATION_TYPE_128.equals(string) || OPERATION_TYPE_192.equals(string) ||
                OPERATION_TYPE_256.equals(string) || PASSWORD.equals(string) || HELP.equals(string);
    }

}
