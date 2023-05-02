package lt.code.academy.springhomeworkv5.controllers;

import lt.code.academy.springhomeworkv5.dto.Account;
import lt.code.academy.springhomeworkv5.services.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegisterController {
    private final AccountService accountService;

    public RegisterController(AccountService accountService) {
                this.accountService = accountService;
    }

    @GetMapping("/register")
    public String getRegisterPage(Model model){
        Account account = new Account();
        model.addAttribute("account", account);
        return "register";
    }

    @PostMapping("/register")
    public String registerNewUser(Model model, Account account){
        model.addAttribute("account", account);
        accountService.saveAccount(account);

        return "redirect:/home";
    }
}

