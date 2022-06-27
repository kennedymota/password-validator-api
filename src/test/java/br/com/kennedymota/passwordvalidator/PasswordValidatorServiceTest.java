package br.com.kennedymota.passwordvalidator;

import br.com.kennedymota.passwordvalidator.service.PasswordValidatorImpl;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PasswordValidatorServiceTest {
    @Mock
    PasswordValidatorImpl service;

    @ParameterizedTest(name = "#{index} - Run test with password = {0}")
    @MethodSource("validPasswordProvider")
    void test_password_regex_valid(String password) {
        when(service.checkPassword(Mockito.anyString())).thenCallRealMethod();
        assertTrue(service.checkPassword(password));
    }

    @ParameterizedTest(name = "#{index} - Run test with password = {0}")
    @MethodSource("invalidPasswordProvider")
    void test_password_regex_invalid(String password) {
        when(service.checkPassword(Mockito.anyString())).thenCallRealMethod();
        assertFalse(service.checkPassword(password));
    }

    static Stream<String> validPasswordProvider() {
        return Stream.of("AbTp9!fok");
    }

    static Stream<String> invalidPasswordProvider() {
        return Stream.of(
                "",
                "aa",
                "ab",
                "AAAbbbCc",
                "AbTp9!foo",
                "AbTp9!foA",
                "AbTp9 fok"
        );
    }

}
