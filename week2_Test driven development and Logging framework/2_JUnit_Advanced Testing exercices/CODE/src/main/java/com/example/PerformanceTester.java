package com.example;

public class PerformanceTester {
    public void performTask() {
        try {
            // Simulate a task (sleep for 100 ms)
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
