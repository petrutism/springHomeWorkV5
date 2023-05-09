package lt.code.academy.springhomeworkv5.repositories;

import lt.code.academy.springhomeworkv5.dto.Account;
import lt.code.academy.springhomeworkv5.entities.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<AccountEntity, UUID> {
    AccountEntity findOneByUsername(String username);

    Optional<AccountEntity> findByUsername(String username);

}
