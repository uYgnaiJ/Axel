package omniaetern.axel.common.password;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

public class PasswordHandler {
    private static final int ITERATIONS = 10000;  // 迭代次数
    private static final int KEY_LENGTH = 64;     // 密钥长度
    private static final int SALT_LENGTH = 8;    // 盐值长度
    public static final String ALGORITHM = "PBKDF2WithHmacSHA256";

    public static byte[] generateSalt() {
        byte[] salt = new byte[SALT_LENGTH];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(salt);
        return salt;
    }

    public static String hashPassword(String password) {
        byte[] salt = generateSalt();
        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATIONS, KEY_LENGTH);
        byte[] hash;
        try{
            SecretKeyFactory factory = SecretKeyFactory.getInstance(ALGORITHM);
            hash = factory.generateSecret(spec).getEncoded();
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        String saltBase64 = Base64.getEncoder().encodeToString(salt);
        String passwordBase64 = Base64.getEncoder().encodeToString(hash);

        return "{pbkdf2}:" + saltBase64 + ":" + passwordBase64;
    }

    public static boolean verifyPassword(String password, String storedPassword) {
        try {
            String[] parts = storedPassword.split(":");
            byte[] salt = Base64.getDecoder().decode(parts[1]);
            byte[] storedHash = Base64.getDecoder().decode(parts[2]);

            PBEKeySpec spec = new PBEKeySpec(
                    password.toCharArray(),
                    salt,
                    ITERATIONS,
                    storedHash.length * 8
            );

            SecretKeyFactory factory = SecretKeyFactory.getInstance(ALGORITHM);
            byte[] testHash = factory.generateSecret(spec).getEncoded();

            return slowEquals(storedHash, testHash);

        } catch (Exception e) {
            return false;
        }
    }

    private static boolean slowEquals(byte[] a, byte[] b) {
        int diff = a.length ^ b.length;
        for (int i = 0; i < a.length && i < b.length; i++) {
            diff |= a[i] ^ b[i];
        }
        return diff == 0;
    }
}
