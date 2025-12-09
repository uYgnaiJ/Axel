package omniaetern.axel.common.password;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PBKDF2PasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence rawPassword) {

        return PasswordHandler.hashPassword(rawPassword.toString());
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return PasswordHandler.verifyPassword(rawPassword.toString(), encodedPassword);
    }
}
