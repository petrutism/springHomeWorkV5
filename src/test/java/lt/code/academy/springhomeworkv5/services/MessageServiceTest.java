package lt.code.academy.springhomeworkv5.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class MessageServiceTest {
    @Mock
    private MessageSource messageSource;
    @InjectMocks
    private MessageService messageService;

    @Test
    void testGetMessageWhenKeyIsNull() {
        String message = messageService.getMessage(null);

        assertNull(message);
    }

    @Test
    void testGetMessageWhenMessageSourceThrowsException(){
        String key = "some.message.key";
        doThrow(NoSuchMessageException.class).when(messageSource).getMessage(eq(key), any(), any(Locale.class));

        assertThrows(NoSuchMessageException.class, () -> messageService.getMessage(key));
    }

    @Test
    void testGetMessage(){
        String key = "some.message.key";
        String value = "some.message.value";

        when(messageSource.getMessage(eq(key), any(), any(Locale.class))).thenReturn(value);

        assertEquals(value, messageService.getMessage(key));
    }

}