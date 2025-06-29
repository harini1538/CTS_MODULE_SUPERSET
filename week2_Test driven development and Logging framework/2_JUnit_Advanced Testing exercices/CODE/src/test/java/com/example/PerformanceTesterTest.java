package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTimeout;
import java.time.Duration;

public class PerformanceTesterTest {

    private final PerformanceTester tester = new PerformanceTester();

    @Test
    void testTaskCompletesWithinTime() {
        assertTimeout(Duration.ofMillis(200), () -> {
            tester.performTask();
        });
    }
}
