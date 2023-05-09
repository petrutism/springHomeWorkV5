package lt.code.academy.springhomeworkv5.controllers;

import jakarta.validation.Valid;
import lt.code.academy.springhomeworkv5.dto.Account;
import lt.code.academy.springhomeworkv5.services.AccountService;
import lt.code.academy.springhomeworkv5.services.RoleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Set;

@Controller
public class RegisterController {
    private final AccountService accountService;
    private final RoleService roleService;

    public RegisterController(AccountService accountService, RoleService roleService) {
        this.accountService = accountService;
        this.roleService = roleService;
    }

    @GetMapping("/register")
    public String getRegisterPage(Model model) {
        Account account = new Account();
        model.addAttribute("account", account);
        return "register";
    }

    @PostMapping("/register")
    public String registerNewUser(@Valid Account account, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "register";
        }
        List<Account> accounts = accountService.getAll();
        for (Account a : accounts) {
            if (a.getUsername().equals(account.getUsername())){
                Account acc = new Account();
                boolean usernameRegistered = true;
                model.addAttribute("account", acc);
                model.addAttribute("usernameRegistered", usernameRegistered);
                return "register";
            }
        }


        String messageKey = "lt.code.academy.blog.account.create.success.message";
        account.setRoles(Set.of(roleService.findByName("USER")));
        accountService.saveAccount(account);

        return "redirect:/login?messageKey=" + messageKey;
    }
}

