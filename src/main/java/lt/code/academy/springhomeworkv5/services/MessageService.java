package lt.code.academy.springhomeworkv5.services;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
    private final MessageSource messageSource;

    public MessageService(MessageSource messageSource){
        this.messageSource=messageSource;
    }

    public String getMessage(String key){
        if(key == null){
            return null;
        }
        return messageSource.getMessage(key, null, LocaleContextHolder.getLocale());
    }
}
