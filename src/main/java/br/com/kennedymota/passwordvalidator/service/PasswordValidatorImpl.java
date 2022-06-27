package br.com.kennedymota.passwordvalidator.service;

import br.com.kennedymota.passwordvalidator.service.interfaces.PasswordValidatorService;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class PasswordValidatorImpl implements PasswordValidatorService {

    private static final String PASSWORD_PATTERN =
            "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()-+]).{9,}$";

    private static final String PATTERN_REPEATED_CHARACTERS_NOT_SEQUENTIAL =
            "(.)(?=.+\\1)";

    private static final String PATTERN_REPEATED_CHARACTERS_SEQUENTIAL =
            "(.)\\1";

    @Override
    public boolean checkPassword(final String password) {

        Pattern pattern = Pattern.compile(PATTERN_REPEATED_CHARACTERS_SEQUENTIAL);

        if (pattern.matcher(password).find()) {
            return false;
        }

        pattern = Pattern.compile(PATTERN_REPEATED_CHARACTERS_NOT_SEQUENTIAL, Pattern.MULTILINE);

        if (pattern.matcher(password).find()) {
            return false;
        }

        pattern = Pattern.compile(PASSWORD_PATTERN);

        if (!pattern.matcher(password).find()) {
            return false;
        }

        return true;
    }
}
