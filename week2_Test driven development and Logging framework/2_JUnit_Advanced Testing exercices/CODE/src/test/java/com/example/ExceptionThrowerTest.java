package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ExceptionThrowerTest {

    private final ExceptionThrower thrower = new ExceptionThrower();

    @Test
    void testThrowsExceptionOnNullInput() {
        assertThrows(IllegalArgumentException.class, () -> {
            thrower.throwException(null);
        });
    }

    @Test
    void testNoExceptionOnValidInput() {
        assertDoesNotThrow(() -> {
            thrower.throwException("valid input");
        });
    }
}
