package lt.code.academy.springhomeworkv5.services;

import lt.code.academy.springhomeworkv5.dto.Account;
import lt.code.academy.springhomeworkv5.entities.AccountEntity;
import lt.code.academy.springhomeworkv5.repositories.AccountRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AccountService implements UserDetailsService {

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
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return accountRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(String.format("User %s not found...", username)));
    }
}
