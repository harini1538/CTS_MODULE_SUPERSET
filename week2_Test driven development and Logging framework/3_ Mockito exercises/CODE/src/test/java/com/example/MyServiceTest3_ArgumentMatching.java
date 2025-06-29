package com.example;

import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.eq;
import org.junit.jupiter.api.Test;

public class MyServiceTest3_ArgumentMatching {
    @Test
    public void testArgumentMatching() {
        ExternalApi mockApi = mock(ExternalApi.class);
        MyService service = new MyService(mockApi);
        service.process("input123");
        verify(mockApi).sendData(eq("input123"));
    }
}
