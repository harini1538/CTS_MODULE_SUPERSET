package com.example;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MyClassTest {
    private MyClass myClass;

    @Before
    public void setUp() {
        // Setup
        myClass = new MyClass();
    }

    @Test
    public void testAdd() {
        // Arrange
        int a = 2, b = 3;

        // Act
        int result = myClass.add(a, b);

        // Assert
        assertEquals(5, result);
    }

    @Test
    public void testIsEven() {
        assertTrue(myClass.isEven(4));
        assertFalse(myClass.isEven(5));
    }

    @After
    public void tearDown() {
        // Clean up
        myClass = null;
    }
}
