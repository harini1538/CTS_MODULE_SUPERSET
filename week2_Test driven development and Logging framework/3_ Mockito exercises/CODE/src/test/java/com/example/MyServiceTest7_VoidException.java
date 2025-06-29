package com.example;

import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class MyServiceTest7_VoidException {
    @Test
    public void testVoidThrowsException() {
        ExternalApi mockApi = mock(ExternalApi.class);
        doThrow(new RuntimeException("Error")).when(mockApi).sendData("bad");

        MyService service = new MyService(mockApi);
        Assertions.assertThrows(RuntimeException.class, () -> {
            service.process("bad");
        });
    }
}
