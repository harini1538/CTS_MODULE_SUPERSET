package com.example;

import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Test;

public class MyServiceTest4_HandleVoidMethod {
    @Test
    public void testVoidMethod() {
        ExternalApi mockApi = mock(ExternalApi.class);
        doNothing().when(mockApi).sendData(anyString());
        MyService service = new MyService(mockApi);
        service.process("void call");
        verify(mockApi).sendData("void call");
    }
}
