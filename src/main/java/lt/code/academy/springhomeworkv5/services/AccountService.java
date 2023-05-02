package lt.code.academy.springhomeworkv5.services;

import lt.code.academy.springhomeworkv5.dto.Account;
import lt.code.academy.springhomeworkv5.entities.AccountEntity;
import lt.code.academy.springhomeworkv5.repositories.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AccountService {
    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<Account> getAll() {
        return accountRepository.findAll()
                .stream()
                .map(Account::convert)
                .toList();
    }

    public Account saveAccount(Account account) {

        return Account.convert(accountRepository.save(AccountEntity.convert(account)));
    }

    public Account findOneByUsername(String username) {
        return Account.convert(accountRepository.findOneByUsername(username));
    }

    public Account findAccountById(UUID id) {
        return accountRepository.findById(id)
                .map(Account::convert)
                .orElse(null);
    }
}
