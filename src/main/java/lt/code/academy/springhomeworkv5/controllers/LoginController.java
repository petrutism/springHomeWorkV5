package lt.code.academy.springhomeworkv5.controllers;

import lt.code.academy.springhomeworkv5.services.MessageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    private final MessageService messageService;

    public LoginController(MessageService messageService){
        this.messageService = messageService;
    }
    @GetMapping("/login")
    public String getLoginPage(Model model, String messageKey){
        model.addAttribute("message", messageService.getMessage(messageKey));
        return "login";
    }
}
