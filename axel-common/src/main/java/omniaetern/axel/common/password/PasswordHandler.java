package omniaetern.axel.common.password;

import java.security.SecureRandom;

public class PasswordHandler {
    private static final int ITERATIONS = 10000;  // 迭代次数
    private static final int KEY_LENGTH = 64;     // 密钥长度
    private static final int SALT_LENGTH = 8;    // 盐值长度

    public byte[] generateSalt() {
        byte[] salt = new byte[SALT_LENGTH];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(salt);
        return salt;
    }
}
