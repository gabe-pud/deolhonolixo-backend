package br.edu.fatecpg.deolhonolixo.core.usecase.user;

import br.edu.fatecpg.deolhonolixo.core.domain.exception.ConfirmPasswordMismatchException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ConfirmPasswordMatchCaseTest {
    private final ConfirmPasswordMatchCase useCase = new ConfirmPasswordMatchCase();

    @Test
    void shouldAllowMatchingPasswords() {
        assertDoesNotThrow(() -> useCase.execute("secret", "secret"));
    }

    @Test
    void shouldThrowWhenPasswordsDoNotMatch() {
        assertThrows(ConfirmPasswordMismatchException.class, () -> useCase.execute("secret", "other"));
    }
}