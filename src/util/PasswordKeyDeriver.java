package util;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

public class PasswordKeyDeriver {

    public static final int ITERATION_COUNT = 65536;
    //TODO: Change this to 16 when done with testing
    public static final int SALT_SIZE = 16;
    public static final int KEY_LENGTH_BITS_128 = 128; // For AES-256. Use 128 or 192 for other AES variants.
    public static final int KEY_LENGTH_BITS_192 = 192; // For AES-256. Use 128 or 192 for other AES variants.
    public static final int KEY_LENGTH_BITS_256 = 256; // For AES-256. Use 128 or 192 for other AES variants.

    public static byte[] deriveKeyFromPassword(char[] password, byte[] salt, int keyLength) throws NoSuchAlgorithmException, InvalidKeySpecException {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(password, salt, ITERATION_COUNT, keyLength);
        return factory.generateSecret(spec).getEncoded();
    }

    public static byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_SIZE];
        random.nextBytes(salt);
        return salt;
    }

}
